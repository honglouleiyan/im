package com.jihuiduo.biz.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.biz.MessageHandler;
import com.jihuiduo.biz.notification.PayloadMessage;
import com.jihuiduo.biz.notification.NotificationMessageHandler;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.msgserver.protocol.im.OfflineMessage;
import com.jihuiduo.util.HttpHeaderUtil;
import com.jihuiduo.util.JsonUtil;

/**
 * 离线消息业务
 *
 */
public class OfflineMessageHandler extends MessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(OfflineMessageHandler.class);
	/**
	 * 单例模式
	 */
	private static OfflineMessageHandler instance;
	
	private OfflineMessageStore offlineMessageStore;
	/**
	 * 离线消息key字符串(OFFLINE_PREFIX_KEY + 用户标识)
	 */
	private static final String OFFLINE_PREFIX_KEY = "IM_MESSAGES_OFFLINE:";
	
	/**
	 * 单例模式
	 */
	public static OfflineMessageHandler getInstance() {
		if (instance == null) {
			synchronized (OfflineMessageHandler.class) {
				if (instance == null) {
					instance = new OfflineMessageHandler();
				}
			}
		}
		return instance;
	}
	
	private OfflineMessageHandler() {
		offlineMessageStore = OfflineMessageStore.getInstance();
	}
	
	/**
	 * 储存离线消息
	 * 
	 */
	public void storeOfflineMessage(String dstUserId, String packet) {
		if (dstUserId == null || dstUserId.matches("\\s*")) {
			return;
		}
		// 得到bare dstUserId
		dstUserId = getBareUserId(dstUserId);
		// 存储离线消息
		// 离线消息必须为http协议格式
		HttpPacket httpPacket = rebuildHttpPacket(packet);
		String headers = httpPacket.getHeaders();
		String body = httpPacket.getBody();
		if (body == null || body.matches("\\s*")) {
			// 为HTTP GET, DELETE请求
			return;
		}
		if (headers.startsWith("HTTP/1.1")) {
			// 回执消息
			// 不存储
			return;
		}
		
		// HTTP Request
		HttpRequestHeader httpRequestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
		String requestPath = httpRequestHeader.getRequestPath().toLowerCase();
		if (requestPath == null || requestPath.matches("\\s*")) {
			logger.error("OfflineMessageHandler requestPath error, " + requestPath);
			return;
		}
		// 去除版本标识
		String requestPathDropVersionStr = getRequestPathDropVersionStr(requestPath);
		switch (requestPathDropVersionStr) {
			case "/im":
				Message message = JsonUtil.getGson().fromJson(body, Message.class);
				List<IMMessage> infos = message.getMessage_info();
				for (IMMessage info : infos) {
					String message_id = info.getMsg_id();
					// 消息生产者
					String userId = info.getUser_id();
					userId = getBareUserId(userId);
					int message_type = (info.getType() == null ? 1 : info.getType());
					
					// 好评推送不存离线
					if (message_type == 3000) {
						continue;
					}
					
					int isOffline = (info.getOffline() == null) ? 0 : info.getOffline();
					OfflineMessage offline = new OfflineMessage();
					offline.setDst_user_id(dstUserId);
					offline.setUser_id(userId);
					offline.setMessage_id(message_id);
					offline.setMessage_type(message_type);
					// 设置离线消息标识
					info.setOffline(1);
					// 消息有变化, 此时需要重构消息
					Message newMessage = new Message();
					List<IMMessage> newInfos = new ArrayList<IMMessage>();
					newInfos.add(info);
					newMessage.setMessage_info(newInfos);
					String newBody = JsonUtil.getGson().toJson(newMessage);
					HttpPacket newHttpPacket = rebuildHeadersAndData(headers, newBody);
					String newPacket = newHttpPacket.toString();
					offline.setMessage_size((long)newPacket.length());
					offline.setMessage(newPacket);
					Date currentDate = new Date();
					offline.setCreate_time(currentDate);
					// 设置有效期
					offline.setDeadline(new Date(currentDate.getTime() + 30*24*60*60*1000L));
					// 存储离线消息
					storeOfflineMessage(dstUserId, offline);
					
					// 判断 dst_user_id 是否登录
					boolean isLogin = isLogin(dstUserId);
					// 在线消息, 使用外部推送提醒用户
					if (isOffline == 0 && isLogin) {
						// 若用户此时是登录成功状态, 则对满足条件的消息使用外部推送提醒该用户
						doExternalNotification(dstUserId, info);
					}
				}
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * 外部推送
	 * 
	 */
	private void doExternalNotification(String userId, IMMessage message) {
		
		// 发送者
		String fromUserId = message.getUser_id();
		String nickname = getNickname(fromUserId);
		int message_type = (message.getType() == null ? 1 : message.getType());
		String alert = null;
		if (message_type < 100) {
			if (nickname == null) {
				alert = "你有一条新消息";
			} else {
				alert = nickname + "发来一条消息";
			}
		} else {
			String m = message.getTitle();
			if (m == null || m.matches("\\s*")) {
				alert = "你有一条新消息";
			} else {
				alert = m;
			}
		}
		
		// Device Token
		String deviceToken = NotificationMessageHandler.getInstance().getDeviceToken(userId);
		// Device Type
		Integer deviceType = NotificationMessageHandler.getInstance().getDeviceType(userId);
		if (deviceToken == null) {
			return;
		} else {
			List<String> deviceTokens = new ArrayList<String>();
			deviceTokens.add(deviceToken);
			List<Integer> deviceTypes = new ArrayList<Integer>();
			deviceTypes.add(deviceType);
			PayloadMessage payload = new PayloadMessage();
			payload.setDevice_tokens(deviceTokens);
			payload.setDevice_types(deviceTypes);
			payload.setAlert(alert);
			NotificationMessageHandler.getInstance().push(userId, payload);
		}
	}
	
	/**
	 * 储存离线消息
	 * 
	 */
	public void storeOfflineMessage(String dstUserId, OfflineMessage offline) {
		if (dstUserId == null || dstUserId.matches("\\s*")) {
			return;
		}
		dstUserId = getBareUserId(dstUserId);
		// 存储离线消息
		String key = OFFLINE_PREFIX_KEY + dstUserId;
		if (offline == null) {
			return;
		}
		String value = JsonUtil.getGson().toJson(offline);
		
		offlineMessageStore.addOffline(offline);
		rpush(key, value);
		
	}
	
	/**
	 * 获取指定用户的离线消息大小
	 * 
	 * @param userId
	 * @return
	 */
	public long getOfflineMessagesSize(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return 0;
		}
		userId = getBareUserId(userId);
		String key = OFFLINE_PREFIX_KEY + userId;
		
		return llen(key);
	}
	
	/**
	 * 获取指定用户的离线消息
	 * 
	 * @param userId
	 * @return
	 */
	public List<OfflineMessage> getOfflineMessages(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return null;
		}
		userId = getBareUserId(userId);
		String key = OFFLINE_PREFIX_KEY + userId;
		List<String> members = lrange(key);
		List<OfflineMessage> list = new ArrayList<OfflineMessage>();
		if (members == null || members.size() == 0) {
			return null;
		}
		for(String member : members) {
			OfflineMessage offlineMessage = JsonUtil.getGson().fromJson(member, OfflineMessage.class);
			// 需要判断消息有效性
			// 检查消息有效期
			
			list.add(offlineMessage);
		}
		// 删除离线消息
		deleteOfflineMessages(userId);
		return list;
	}
	
	/**
	 * 删除指定用户的离线消息队列
	 * 
	 * @param userId
	 */
	public void deleteOfflineMessages(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return;
		}
		userId = getBareUserId(userId);
		String key = OFFLINE_PREFIX_KEY + userId;
		del(key);
		offlineMessageStore.deleteOffline(userId);
	}
	
}
