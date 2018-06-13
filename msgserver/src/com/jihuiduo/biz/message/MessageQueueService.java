package com.jihuiduo.biz.message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.biz.MessageHandler;
import com.jihuiduo.msgserver.Session;
import com.jihuiduo.msgserver.SessionManager;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.MessageAck;
import com.jihuiduo.msgserver.protocol.Result;
import com.jihuiduo.msgserver.protocol.basic.Packet;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.util.HttpHeaderUtil;
import com.jihuiduo.util.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 消息队列服务
 *
 */
public class MessageQueueService extends MessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MessageQueueService.class);
	/**
	 * 单例模式
	 */
	private static MessageQueueService instance;
	/**
	 * 消息队列key字符串
	 */
	private static final String MQ_KEY = "IM:MESSAGE:QUEUE";
	/**
	 * 超时毫秒数
	 */
	private static final long TIMEOUT_MILLIS = 60000L;
	/**
	 * 用来统计回执消息超时
	 * key值为：
	 * 流水号ID
	 * 
	 * value值为：
	 * 最后一次推送时间的毫秒数
	 */
	private Map<String, Long> ackTimeout = new ConcurrentHashMap<String, Long>();
	/**
	 * 未收到回执的消息
	 * key值为：
	 * 流水号ID
	 * 
	 * value值为：
	 * 最后一次推送时间的毫秒数
	 */
	private Map<String, MessageRetryCount> ackMessages = new ConcurrentHashMap<String, MessageRetryCount>();
	/**
	 * 用户未发送回执的消息列表
	 * 
	 * key值为：
	 * 用户ID
	 * 
	 * value值为：
	 * 未收到回执的msg_id列表
	 */
	private Map<String, Set<String>> unreadDeviceMessages = new ConcurrentHashMap<String, Set<String>>();
	
	public static MessageQueueService getInstance() {
		if (instance == null) {
			synchronized (MessageQueueService.class) {
				if (instance == null) {
					instance = new MessageQueueService();
				}
			}
		}
		return instance;
	}
	
	private MessageQueueService() {
		initialize();
	}
	
	/**
	 * 完善消息发送机制，保障消息百分百发送成功
	 * 
	 */
	public void handle(String dst_user_id, Packet packet) {
		if (packet == null) {
			return;
		}
		HttpPacket httpPacket = (HttpPacket) packet;
		// 消息头
		String headers = httpPacket.getHeaders();
		// 消息体
		String body = httpPacket.getBody();
		
		if (headers == null || headers.matches("\\s*")) {
			logger.debug("消息队列处理消息异常, headers : " + headers + " , body : " + body);
			return;
		}
		
		// 收到的数据包
		if (headers.startsWith("HTTP")) {
			// 只处理收到的 HTTP Response
			Result result = JsonUtil.getGson().fromJson(body, Result.class);
			String dataStr = result.getData().toString();
			MessageAck messageAck = JsonUtil.getGson().fromJson(dataStr, MessageAck.class);
			
			List<String> msgIdList = messageAck.getMsg_id();
			if (msgIdList != null && msgIdList.size() > 0) {
				for (String packetId : msgIdList) {
					remove(packetId);
				}
			} else {
				String seq = result.getSeq();
				if (seq != null) {
					remove(seq);
				}
			}
			
		} else if (headers.startsWith("GET") || headers.startsWith("POST") || headers.startsWith("PUT") || headers.startsWith("DELETE")) {
			if (dst_user_id == null || dst_user_id.matches("\\s*")) {
				// 未知用户, 不处理
				return;
			}
			dst_user_id = getBareUserId(dst_user_id);
			
			// 只处理发出的 HTTP Request
			HttpRequestHeader httpRequestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
			String requestPath = httpRequestHeader.getRequestPath().toLowerCase();
			// 去除版本标识
			if (requestPath.startsWith("/v")) {
				String temp = requestPath.substring(1);
				int index = temp.indexOf("/");
				if (index > -1) {
					requestPath = temp.substring(index);
				}
			}
			switch (requestPath) {
			case "/im":
				Message message = JsonUtil.getGson().fromJson(body, Message.class);
				List<IMMessage> messageInfoList = message.getMessage_info();
				for (IMMessage messageInfo : messageInfoList) {
					String msgId = messageInfo.getMsg_id();
					// 默认是在线消息
					int isOffline = messageInfo.getOffline() == null ? 0 : messageInfo.getOffline();
					// 默认是普通消息
					int type = messageInfo.getType() == null ? 1 : messageInfo.getType();
					// 消息队列机制需要处理在线消息和 离线消息
					if (isOffline == 0 || isOffline == 1) {
						put(msgId, dst_user_id, packet);
						if (type == 101) {
							// 添加踢下线未读消息msg_id列表
							Set<String> msgIdList = unreadDeviceMessages.get(dst_user_id);
							if (msgIdList == null) {
								msgIdList = new HashSet<String>();
							}
							msgIdList.add(msgId);
							unreadDeviceMessages.put(dst_user_id, msgIdList);
						}
					}
					
				}
				break;
				
			default:
				String seq = httpRequestHeader.getParameter("seq");
				if (seq != null && !seq.matches("\\s*")) {
					put(seq, dst_user_id, packet);
				}
				break;
			}
		} else {
			
		}
		
	}
	
	private void put(String packetId, String dst_user_id, Packet packet) {
		if (packetId == null || packetId.matches("\\s*")) {
			return;
		}
		
		// 保证一条消息只添加一次
		if (!ackTimeout.containsKey(packetId) && !ackMessages.containsKey(packetId)) {
			long currentTimeMillis = System.currentTimeMillis();
			ackTimeout.put(packetId, currentTimeMillis);
			MessageRetryCount ack = new MessageRetryCount(packet.toString(), dst_user_id, 0, currentTimeMillis);
			ackMessages.put(packetId, ack);
		}
	}
	
	private void remove(String packetId) {
		if (packetId == null || packetId.matches("\\s*")) {
			return;
		}
		
		MessageRetryCount ack = ackMessages.remove(packetId);
		if (ack != null) {
			ackTimeout.remove(packetId);
			
			// 清除系统未读消息记录
			String dstUserId = ack.getDst_user_id();
			unreadDeviceMessages.remove(dstUserId);
		}
	}
	
	/**
	 * 获取在消息队列中的设备消息
	 * 
	 */
	public List<String> getUnreadDeviceMessages(String deviceUid) {
		if (deviceUid == null || deviceUid.matches("\\s*")) {
			// 非法设备标识符
			return null;
		}
		List<String> list = new ArrayList<String>();
		Set<String> sets = unreadDeviceMessages.get(deviceUid);
		if (sets != null && sets.size() > 0) {
			for (String msgId : sets) {
				// 获取设备消息
				MessageRetryCount mrc = ackMessages.get(msgId);
				if (mrc != null) {
					String packet = mrc.getPacket();
					list.add(packet);
				}
			}
		} else {
			return null;
		}
		removeUnreadDeviceMessages(deviceUid);
		
		return list;
	}
	
	/**
	 * 消除设备消息
	 * 
	 */
	public void removeUnreadDeviceMessages(String deviceUid) {
		if (deviceUid == null || deviceUid.matches("\\s*")) {
			// 非法设备标识符
			return;
		}
		Set<String> sets = unreadDeviceMessages.get(deviceUid);
		if (sets != null && sets.size() > 0) {
			for (String s : sets) {
				remove(s);
			}
		}
	}
	
	public void removeUnreadUserMessages(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return;
		}
	}
	
	/**
	 * 判断该消息是否需要APNS推送服务
	 * 
	 */
	public boolean needApplePushNotificationService(String packetId) {
		if (packetId == null || packetId.matches("\\s*")) {
			// packetId 异常
			return false;
		}
		MessageRetryCount ack = ackMessages.get(packetId);
		if (ack == null) {
			return true;
		} else {
			// 重发次数
			int count = ack.getCount();
			if (count > 0) {
				// 已经推送过, 不需要重新推送
				return false;
			} else {
				return true;
			}
		}
	}
	
	public void doRedisService() {
		Jedis jedis = JedisFactory.getResource();
		jedis.rpush(MQ_KEY, "");
		jedis.lrem(MQ_KEY, 0l, "");
		jedis.rpoplpush("", "");
		
		Transaction trans = jedis.multi();
		trans.exec();
	}
	
	private void initialize() {
		new Timer().scheduleAtFixedRate(new TimeoutTask(), 5000, 5000);
		
		// 开启N + 1个线程
//		int count = Runtime.getRuntime().availableProcessors() + 1;
//		logger.info("当前启动线程数量 : " + count);
//		for (int i = 0; i < count; i++) {
//			Thread thread = new Thread(new Runnable(){
//				public void run() {
//					while(true) {
//						try {
//							// 业务处理
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							
//						}
//					}
//				}
//			},"MessageAck_" + i);
//			thread.start();
//		}
		
	}
	
	
	/**
	 * 消息重发处理机制
	 *
	 */
	public class TimeoutTask extends TimerTask {

		@Override
		public void run() {
			
			final Iterator<Map.Entry<String, Long>> it = ackTimeout.entrySet().iterator();
			while (it.hasNext()) {
				final Map.Entry<String, Long> pointer = it.next();
				if (System.currentTimeMillis() - pointer.getValue() < TIMEOUT_MILLIS) {
					continue;
				}
				
				final String packetId = pointer.getKey();
				it.remove();
				final MessageRetryCount ack = ackMessages.remove(packetId);
				if (ack != null) {
					// 处理业务
					String packet = ack.getPacket();
					String dst_user_id = ack.getDst_user_id();
					int count = ack.getCount();
					
					// 再次判断该用户是否在线, 若不在线, 直接存入离线消息
					
					// 判断 dst_user_id 是否在线
					boolean isOnline = isOnline(dst_user_id);
					// 判断 dst_user_id 是否登录
					boolean isLogin = isLogin(dst_user_id);
					if (isLogin) {
						if (isOnline) {
							if (count < 2) {
								// 更新重发次数值
								long currentTimeMillis = System.currentTimeMillis();
								ack.setCount(count+1);
								ack.setTime(currentTimeMillis);
								ackTimeout.put(packetId, currentTimeMillis);
								ackMessages.put(packetId, ack);
								try {
									logger.info("尝试重发消息 : " + packet);
									// AccessServerMessageHandler.getInstance().deliverToAccessServer(dst_user_id, packet);
									
									HttpPacket httpPacket = rebuildHttpPacket(packet);
									// 由 dst_user_id 得到正确的session
									List<Session> sessions = SessionManager.getInstance().getAccessServerSessions();
									if (sessions == null) {
										return;
									}
									for(Session session : sessions) {
										session.deliverPacket(httpPacket);
									}
								} catch (Exception e) {
									logger.error("尝试重发消息出现错误 : " + packet, e);
								}
								
							} else {
								// 重发超过次数
								// 重发的离线消息需要再次存入数据库
								 boolean isOffline = false;
								 if (isOffline) {
									// 丢弃该离线消息
									 logger.info("重发机制推送离线消息失败，丢弃该消息 : " + packet);
								} else {
									// 不再重发消息，存储离线消息
									logger.info("重发消息超过次数限制, 存离线消息 : "+packet);
									// 路由失败，存储离线消息
									routingFailed(dst_user_id, packet);
								}
							}	
						} else {
							// 用户已登录, 未连接
							// 不再重发消息，存储离线消息
							logger.info("重发消息时用户断开连接, 存离线消息 : "+packet);
							// 路由失败，存储离线消息
							routingFailed(dst_user_id, packet);
						}
					} else {
						// 用户此时未登录
						// 不再重发消息，存储离线消息
						logger.info("重发消息时用户不在线, 存离线消息 : "+packet);
						// 路由失败，存储离线消息
						routingFailed(dst_user_id, packet);
					}
				}
			}
		}
		
	}
	
}
