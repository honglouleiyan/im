package com.jihuiduo.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.jihuiduo.biz.message.HistoryMessageHandler;
import com.jihuiduo.biz.message.MessageQueueService;
import com.jihuiduo.biz.message.OfflineMessageHandler;
import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.Session;
import com.jihuiduo.msgserver.SessionManager;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.User;
import com.jihuiduo.msgserver.protocol.basic.GroupMember;
import com.jihuiduo.msgserver.protocol.basic.Packet;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.util.HttpHeaderUtil;
import com.jihuiduo.util.JsonUtil;
import com.jihuiduo.util.SequenceUtil;
import com.jihuiduo.util.SerializeUtil;

/**
 * 消息模块入口
 *
 */
public abstract class MessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	/**
	 * 一台设备上存储的用户信息(DEVICE_PREFIX_KEY + 设备标识)
	 * 
	 * Map<String, String>
	 * user_id				用户id
	 * access_token			access_token值
	 * expires_in			有效期时长(单位:秒)
	 * login_time			登录时间毫秒数
	 * login_state			登录状态(1:正常登录)
	 * remote_client		远程客户端(TCP长连接)使用的ip:port
	 * connection_state		连接状态(1:正常连接)
	 * active_time			最后一次心跳时间毫秒数
	 * device_type			客户端标识 11:Android 12:IOS 默认11
	 * device_token			客户端推送使用的device_token
	 * connection_server	标识当前远程客户端所连接的接入服务器(连接IM服务器)使用的ip:port
	 */
	private static final String DEVICE_PREFIX_KEY = "DEVICE:";
	/**
	 * 用户使用的设备信息
	 * 
	 * Map<String, String>
	 * device_uid			设备唯一标识符
	 */
	private static final String DEVICE_USER_PREFIX_KEY = "DEVICE_USER:";
	/**
	 * 用户登录信息
	 * 
	 * Map<String, String>
	 * device_uid			设备唯一标识符
	 * user_id				用户id
	 */
	private static final String ACCESS_TOKEN_PREFIX_KEY = "ACCESS_TOKEN:";
	/**
	 * 用户基本信息
	 */
	private static final String USER_PREFIX_KEY = "USER:";
	/**
	 * 用户会话关系列表
	 * 
	 * Map<String, String>
	 * user_id				会话关系级别
	 */
	private static final String CONVERSATION_PREFIX_KEY = "IM_CONVERSATION:";
	/**
	 * 用headers和body重建一个输出包
	 * 
	 * @param headers
	 * @param body
	 * @return
	 */
	public HttpPacket rebuildHeadersAndData(String headers, String body) {
		// 将headers解析成http request header
		HttpRequestHeader requestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
		// 重置Content-Length值
		Map<String, String> h = requestHeader.getHeaders();
		h.put("Content-Length", String.valueOf(body.getBytes(Constants.CHARSET).length));
		
		HttpRequestHeader newRequestHeader = new HttpRequestHeader(requestHeader.getProtocolVersion(),
				requestHeader.getMethod(), requestHeader.getRequestPath(), requestHeader.getQueryString(), h);
		HttpPacket outgoingPacket = new HttpPacket();
		outgoingPacket.setPacket(newRequestHeader.toString() + body);
		return outgoingPacket;
	}
	
	public HttpPacket rebuildHttpPacket(String packet) {
		if (packet == null || packet.matches("\\s*")) {
			return null;
		}
		return new HttpPacket(packet);
	}
	
	/**
	 * 推送消息给接入服务器
	 * 
	 * @param dst_user_id
	 * @param packet
	 */
	public void deliverToAccessServer(String dst_user_id, Packet packet) {
		dst_user_id = getBareUserId(dst_user_id);
		// 判断 dst_user_id 是否在线
		boolean isOnline = isOnline(dst_user_id);
		// 判断 dst_user_id 是否登录
		boolean isLogin = isLogin(dst_user_id);
		if (isOnline && isLogin) {
			// 消息保障机制
			MessageQueueService.getInstance().handle(dst_user_id, packet);
			
			// 由 dst_user_id 得到正确的session
			List<Session> sessions = SessionManager.getInstance().getAccessServerSessions();
			if (sessions == null) {
				return;
			}
			for(Session session : sessions) {
				session.deliverPacket(packet);
			}
		} else {
			// 路由失败，存储离线消息
			routingFailed(dst_user_id, packet.toString());
		}
		
	}
	
	/**
	 * 推送消息给接入服务器
	 * 
	 * @param dst_user_id
	 * @param packet
	 */
	public void deliverToAccessServer(String dst_user_id, String packet) {
		HttpPacket httpPacket = rebuildHttpPacket(packet);
		deliverToAccessServer(dst_user_id, httpPacket);		
	}
	
	/**
	 * 判断该用户是否在线
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isOnline(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该用户为系统用户或特殊用户, 返回默认值: online
			return true;
		}
		userId = getBareUserId(userId);
		
		// 默认不在线
		boolean isOnline = false;
		boolean isUser = userId.matches("[0-9]+");
		if (isUser) {
			String deviceUid = getDeviceUid(userId);
			if (deviceUid != null) {
				// 查询userId用户在线状态
				String key = DEVICE_PREFIX_KEY + deviceUid;
				List<String> res = hmget(key, "connection_state");
				if (res != null && res.size() > 0) {
					String temp = res.get(0);
					if (temp != null) {
						isOnline = temp.equalsIgnoreCase("1") ? true : false;
					}
				}
			}
		} else {
//			String key = DEVICE_PREFIX_KEY + userId;
//			List<String> res = hmget(key, "connection_state");
//			if (res != null && res.size() > 0) {
//				String temp = res.get(0);
//				if (temp != null) {
//					isOnline = temp.equalsIgnoreCase("1") ? true : false;
//				}
//			}
			return true;
		}
		return isOnline;
	}
	
	/**
	 * 判断该用户/设备是否登录
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isLogin(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该用户为系统用户或特殊用户, 返回默认值: login
			return true;
		}
		userId = getBareUserId(userId);
		
		// 默认不登录
		boolean isLogin = false;
		boolean isUser = userId.matches("[0-9]+");
		if (isUser) {
			String deviceUid = getDeviceUid(userId);
			if (deviceUid != null) {
				// 查询userId用户登录状态
				String key = DEVICE_PREFIX_KEY + deviceUid;
				List<String> res = hmget(key, "login_state");
				if (res != null && res.size() > 0) {
					String temp = res.get(0);
					if (temp != null) {
						isLogin = temp.equalsIgnoreCase("1") ? true : false;
					}
				}
			}
		} else {
			// 设备
//			String key = DEVICE_PREFIX_KEY + userId;
//			List<String> res = hmget(key, "login_state");
//			if (res != null && res.size() > 0) {
//				String temp = res.get(0);
//				if (temp != null) {
//					isLogin = temp.equalsIgnoreCase("1") ? true : false;
//				}
//			}
			return true;
		}
		return isLogin;
	}
	
	/**
	 * 判断userId的会话关系列表中是否存在dstUserId
	 * 
	 * @param userId
	 * @param dstUserId
	 * @return
	 */
	public boolean hasConversationRelationship(String userId, String dstUserId) {
		if (userId == null || userId.matches("\\s*") || dstUserId == null || dstUserId.matches("\\s*")) {
			// 该用户为系统用户或特殊用户, 默认存在会话关系
			return true;
		}
		userId = getBareUserId(userId);
		dstUserId = getBareUserId(dstUserId);
		
		// 默认不存在会话关系
		boolean has = false;
		
		// 查询userId的会话关系列表
		String key = CONVERSATION_PREFIX_KEY + userId;
		List<String> res = hmget(key, dstUserId);
		if (res != null && res.size() > 0) {
			String temp = res.get(0);
			if (temp != null && !temp.matches("\\s*")) {
				has = true;
			}
		}
		return has;
	}
	
	/**
	 * 查找使用指定设备的user_id
	 * 
	 * @param deviceUid
	 * @return
	 */
	public String getUserIdWithDeviceUid(String deviceUid) {
		if (deviceUid == null || deviceUid.matches("\\s*")) {
			// 非法设备
			return null;
		}
		
		// 查询设备信息
		String key = DEVICE_PREFIX_KEY + deviceUid;
		List<String> res = hmget(key, "user_id");
		if (res != null && res.size() > 0) {
			String temp = res.get(0);
			if (temp != null && !temp.matches("\\s*")) {
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * 获取用户身份标识对应的userId
	 * 
	 * @param accessToken
	 * @return
	 */
	public String getUserIdByAccessToken(String accessToken) {
		if (accessToken == null || accessToken.matches("\\s*")) {
			return null;
		}
		String userId = null;
		String key = ACCESS_TOKEN_PREFIX_KEY + accessToken;
		List<String> res = hmget(key, "user_id");
		if (res != null && res.size() > 0) {
			String temp = res.get(0);
			if (temp != null && !temp.matches("\\s*")) {
				userId = temp;
			}
		}
		return userId;
	}
	
	/**
	 * 查询该用户使用的设备标识
	 * 
	 * @param userId
	 * @return
	 */
	public String getDeviceUid(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return null;
		}
		userId = getBareUserId(userId);
		String deviceUid = null;
		String key = DEVICE_USER_PREFIX_KEY + userId;
		List<String> res = hmget(key, "device_uid");
		if (res != null && res.size() > 0) {
			String temp = res.get(0);
			if (temp != null && !temp.matches("\\s*")) {
				deviceUid = temp;
			}
		}
		return deviceUid;
	}
	
	public String getNickname(String userId) {
		User user = getUser(userId);
		if (user == null) {
			return null;
		} else {
			String nickname = user.getNick();
			return nickname;
		}
	}
	
	/**
	 * 是否在黑名单中
	 */
	public boolean isBlack(String userId) {
		User user = getUser(userId);
		if (user == null) {
			return false;
		} else {
			Integer userStatus = user.getUser_status();
			// user_status 用户状态 0正常  1黑名单  2禁言
			if (userStatus != null && userStatus == 1) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public User getUser(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该userId非法
			return null;
		}
		userId = getBareUserId(userId);
		String key = USER_PREFIX_KEY + userId;
		String value = get(key);
		if (value == null || value.matches("\\s*")) {
			return null;
		} else {
			User user = JsonUtil.getGson().fromJson(value, User.class);
			return user;
		}
	}
	
	public String getBareUserId(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该userId非法
			return null;
		}
		// userId有四种可能性
		// 10000
		// 10000/11
		// 10000@888888
		// 10000@888888/11
		String temp = null;
		int atIndex = userId.indexOf("@");
		int slashIndex = userId.indexOf("/");
		if (atIndex > 0) {
			temp = userId.substring(0, atIndex);
		} else {
			if (slashIndex > 0) {
				temp = userId.substring(0, slashIndex);
			} else {
				temp = userId;
			}
		}
		return temp;
	}
	
	/**
	 * 消息路由失败, 存储离线消息
	 * 
	 * @param dst_user_id
	 * @param packet
	 */
	public void routingFailed(String dst_user_id, String packet) {
		if (dst_user_id == null || dst_user_id.matches("\\s*")) {
			// 该用户为系统用户或特殊用户, 不处理消息消息
			sendMessageToAdmins(packet);
			return;
		}
		
		dst_user_id = getBareUserId(dst_user_id);
		logger.debug("routingFailed, 存储离线消息 . dst_user_id : " + dst_user_id + " packet : " + packet);
		
		// 存储离线消息
		OfflineMessageHandler.getInstance().storeOfflineMessage(dst_user_id, packet);
	}
	
	/**
	 * 推送消息给管理员
	 * 
	 * @param packet
	 */
	public void sendMessageToAdmins(String packet) {
		
	}
	
	/**
	 * 去除请求URL中的版本标识字符
	 * 
	 * @param requestPath
	 * @return
	 */
	public String getRequestPathDropVersionStr(String requestPath) {
		if (requestPath == null || requestPath.matches("\\s*")) {
			return null;
		}
		// 去除版本标识
		if (requestPath.startsWith("/v") || requestPath.startsWith("/V")) {
			String temp = requestPath.substring(1);
			int index = temp.indexOf("/");
			if (index > -1) {
				requestPath = temp.substring(index);
			}
		}
		return requestPath;
	}
	
	/**
	 * 处理单聊消息
	 * 
	 */
	public void handleChatMessage(String headers, IMMessage info) {
		int type = (info.getType() == null) ? 1 : info.getType();
		
		long time = System.currentTimeMillis();
		// 将时间重置为服务器时间
		info.setTime(time);
		
		Message message = new Message();
		// 目标ID集合
		List<String> targetIdList = info.getDst_user_id();
		String fromUserId = info.getUser_id();
		fromUserId = getBareUserId(fromUserId);
		for(String targetId : targetIdList) {
			// 单聊消息不支持自己给自己发送消息
			if (fromUserId.equalsIgnoreCase(getBareUserId(targetId))) {
				continue;
			}
			
			if (type == 1) {
				// 检查是否存在会话关系, 只有存在会话关系才能发送聊天消息
				boolean hasConversation = hasConversationRelationship(targetId, fromUserId);
				logger.debug("检查 " + targetId + " 的会话关系中是否存在 " + fromUserId + " , 结果 : " + hasConversation);
				if (!hasConversation) {
					IMMessage newMessage = new IMMessage();
					newMessage.setMsg_id(SequenceUtil.createSequence());
					newMessage.setType(100);
					List<String> newDstUserId = new ArrayList<String>();
					newDstUserId.add(fromUserId);
					newMessage.setDst_user_id(newDstUserId);
					newMessage.setUser_id(targetId);
					newMessage.setMessage("对方已经手动结束了对话, 发送的消息不再有效. ");
					newMessage.setTime(System.currentTimeMillis());
					List<IMMessage> newList = new ArrayList<IMMessage>();
					newList.add(newMessage);
					message.setMessage_info(newList);
					deliverMessage(fromUserId, headers, message);
					
					return;
				}
			}
			
			// 重新生成Message
			List<String> userIdList = new ArrayList<String>();
			userIdList.add(targetId);
			info.setDst_user_id(userIdList);
			List<IMMessage> list = new ArrayList<IMMessage>();
			list.add(info);
			message.setMessage_info(list);
			
			deliverMessage(targetId, headers, message);
			
			// 历史记录入库
			HistoryMessageHandler.getInstance().addIMMessage(headers, info);
		}
	}
	
	/**
	 * 处理群聊消息
	 * 
	 */
	public void handleGroupChatMessage(String headers, IMMessage info) {
		long time = System.currentTimeMillis();
		// 将时间重置为服务器时间
		info.setTime(time);
		
		Message message = new Message();
		// 目标ID集合
		List<String> targetIdList = info.getDst_user_id();
		String fromUserId = info.getUser_id();
		
		for(String targetId : targetIdList) {
			// 群组ID 或 群组成员ID@群组ID
			if (targetId.contains("@")) {
				// targetId 形如 群组成员ID@群组ID
				// 直接转发
				// 重新生成Message
				List<String> targetIdList2 = new ArrayList<String>();
				String dst_user_id = targetId;
				targetIdList2.add(dst_user_id);
				info.setDst_user_id(targetIdList2);
				
				List<IMMessage> list = new ArrayList<IMMessage>();
				list.add(info);
				message.setMessage_info(list);
				deliverMessage(dst_user_id, headers, message);
			} else {
				// targetId 为群组ID
				
				// 检查群聊消息发送人是否是群组成员
				boolean isGroupMember = isGroupMember(fromUserId, targetId);
				if (!isGroupMember) {
					// 群聊消息发送人不是群组成员, 忽略
					return;
				}
				
				List<String> memberList = getGroupMembers(targetId);
				if (memberList == null) {
					// 群组ID在缓存中不存在或成员列表为空
					return;
				}
				
				for(String groupMember : memberList) {
					// groupMember 为 GroupMember 的json格式
					GroupMember member = JsonUtil.getGson().fromJson(groupMember, GroupMember.class);
					
					String userId = member.getUser_id();
					// 群聊消息不再转发给发送者
					if (fromUserId.equalsIgnoreCase(userId)) {
						continue;
					}
					String dst_user_id = userId + "@" + targetId;
					
					// 重新生成Message
					List<String> targetIdList3 = new ArrayList<String>();
					
					targetIdList3.add(dst_user_id);
					info.setDst_user_id(targetIdList3);
					
					List<IMMessage> list = new ArrayList<IMMessage>();
					list.add(info);
					message.setMessage_info(list);
					
					deliverMessage(dst_user_id, headers, message);
					
					// 历史记录入库
					HistoryMessageHandler.getInstance().addIMMessage(headers, info);
				}
			}
		}
	}
	
	/**
	 * 判断targetUserId是否是群组groupId的成员
	 */
	private boolean isGroupMember(String targetUserId, String groupId) {
		boolean isGroupMember = false;
		List<String> memberList = getGroupMembers(groupId);
		if (memberList == null) {
			// 群组ID在缓存中不存在或成员列表为空
			return false;
		}
		for(String groupMember : memberList) {
			// groupMember 为 GroupMember 的json格式
			GroupMember member = JsonUtil.getGson().fromJson(groupMember, GroupMember.class);
			String userId = member.getUser_id();
			if (targetUserId.equalsIgnoreCase(userId)) {
				isGroupMember = (isGroupMember || true);
			} else {
				isGroupMember = (isGroupMember || false);
			}
			if (isGroupMember) {
				break;
			}
		}
		return isGroupMember;
	}
	
	/**
	 * 从缓存中读取群组成员列表
	 * 
	 * @param groupId
	 * @return
	 */
	private List<String> getGroupMembers(String groupId) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getResource();
			String key = "GROUP:MEMBERS";
			byte [] value = jedis.get(key.getBytes());
			Object temp =  SerializeUtil.unserialize(value);
			if (temp == null) {
				// 缓存中没有群组成员列表数据
				logger.error("缓存中未找到群组成员列表 : GROUP:MEMBERS");
				return null;
			} else {
				@SuppressWarnings("unchecked")
				Map<String, List<String>> map = (Map<String, List<String>>)temp;
				List<String> memberList = map.get(groupId);
				if (memberList == null || memberList.size() == 0) {
					logger.error("缓存中未找到指定的群组成员列表, GroupId : " + groupId);
					return null;
				} else {
					return memberList;
				}
			}
		} catch (Exception e) {
			logger.error("ModulesMessageHandler redis getGroupMembers error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
			return null;
		} finally {
			JedisFactory.returnResource(jedis);
		}	
	}
	
	/**
	 * 转发Message
	 * 
	 * @param dst_user_id
	 * @param headers
	 * @param message
	 */
	private void deliverMessage(String dst_user_id, String headers, Message message) {
		String s = JsonUtil.getGson().toJson(message);
		HttpPacket outgoingPacket = rebuildHeadersAndData(headers, s);
		deliverToAccessServer(dst_user_id, outgoingPacket);
	}
	
	private String hmset(String key, Map<String, String> map) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hmset(key, map);
		} catch (Exception e) {
			logger.error("ModulesMessageHandler redis hmset error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	private List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hmget(key, fields);
		} catch (Exception e) {
			logger.error("ModulesMessageHandler redis hmget error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	private Set<String> hkeys(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hkeys(key);
		} catch (Exception e) {
			logger.error("ModulesMessageHandler redis hkeys error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	private Map<String, String> gethm(String key) {
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = JedisFactory.getResource();
			Set<String> keySet = hkeys(key);
			if(keySet != null && keySet.size() > 0) {
				res = new HashMap<String, String>();
		        Iterator<String> iter = keySet.iterator();  
		        while (iter.hasNext()){  
		            String field = iter.next();  
		            List<String> values = hmget(key, field);
		            if(values != null && values.size() > 0){
		            	res.put(field, values.get(0));
		            }
		        }
			}
		} catch (Exception e) {
			logger.error("ModulesMessageHandler redis gethm error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	/**
	 * 插入元素到队列尾部
	 * 
	 * @param key
	 * @param strings
	 * @return
	 */
	public Long rpush(String key, String... strings) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.rpush(key, strings);
		} catch (Exception e) {
			logger.error("OfflineMessageHandler redis rpush error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	/**
	 * 获取key值对应的元素集合
	 * 
	 * @param key
	 * @return
	 */
	public List<String> lrange(String key) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.lrange(key, 0 , -1);
		} catch (Exception e) {
			logger.error("OfflineMessageHandler redis smembers error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	/**
	 * 统计key值对应的元素集合长度
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.llen(key);
		} catch (Exception e) {
			logger.error("MessageHandler redis llen error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	/**
	 * 删除key值对应的元素集合
	 * 
	 * @param keys
	 * @return
	 */
	public Long del(String... keys) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.del(keys);
		} catch (Exception e) {
			logger.error("MessageHandler redis del error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
	public String get(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.get(key);
		} catch (Exception e) {
			logger.error("MessageHandler redis get error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

}
