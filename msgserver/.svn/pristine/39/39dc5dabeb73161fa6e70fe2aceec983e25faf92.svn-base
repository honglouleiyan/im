package com.jihuiduo.biz.message;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.jihuiduo.biz.MessageHandler;
import com.jihuiduo.biz.notification.PayloadMessage;
import com.jihuiduo.biz.notification.NotificationMessageHandler;
import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.UserOnlineOfflineBroadcast;
import com.jihuiduo.msgserver.protocol.basic.Packet;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.msgserver.protocol.im.OfflineMessage;
import com.jihuiduo.util.HttpHeaderUtil;
import com.jihuiduo.util.JsonUtil;

/**
 * 业务服务器消息处理类
 * 
 */
public class BusinessServerMessageHandler extends MessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(BusinessServerMessageHandler.class);
	
	private static BusinessServerMessageHandler instance;
	
	/**
	 * 单例模式
	 */
	public static BusinessServerMessageHandler getInstance() {
		if (instance == null) {
			synchronized (BusinessServerMessageHandler.class) {
				if (instance == null) {
					instance = new BusinessServerMessageHandler();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 处理业务服务器发送过来的消息
	 * 
	 */
	public void handle(Packet packet, boolean needReply) throws Exception {
		if (packet == null) {
			return;
		}
		HttpPacket httpPacket = (HttpPacket) packet;
		String headers = httpPacket.getHeaders();
		String body = httpPacket.getBody();
		if (body == null) {
			// 为HTTP GET, DELETE请求
			return;
		}
		if (headers.startsWith("HTTP/1.1")) {
			// 回执消息
			// 不处理
			return;
		}
		
		// 默认不应答
		if (needReply) {
			// 接收成功应答
			// HttpPacket responsePacket = createSuccessResponse();
		}
		
		HttpRequestHeader httpRequestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
		// URL统一为小写形式
		String requestPath = httpRequestHeader.getRequestPath().toLowerCase();
		
		if (requestPath == null || requestPath.matches("\\s*")) {
			logger.error("BusinessServerMessageHandler requestPath error, " + requestPath);
			return;
		}
		// 去除版本标识
		String requestPathDropVersionStr = getRequestPathDropVersionStr(requestPath);
		
		switch (requestPathDropVersionStr) {
		
		case "/notification/user/online":
			// 业务服务器推送过来的在线用户、设备通知, 推送离线消息
			// new TypeToken<List<String>>(){}.getType()
			UserOnlineOfflineBroadcast userOnlineOfflineBroadcast = JsonUtil.getGson().fromJson(body, UserOnlineOfflineBroadcast.class);
			doOfflineMessageBiz(userOnlineOfflineBroadcast);
			break;
			
		case "/im":
			// 推送消息
			Message message = JsonUtil.getGson().fromJson(body, Message.class);
			List<IMMessage> infos = message.getMessage_info();
			
			for (IMMessage info : infos) {
				// 消息类型
				int type = info.getType();
				switch (type) {
				case 1:
					// 单聊
					handleChatMessage(headers, info);
					break;
				case 2:
					// 群聊
					handleGroupChatMessage(headers, info);
					break;
				default:
					// 默认
					handleChatMessage(headers, info);
					break;
				}
			}
			break;
			
		default:
			String default_dst_user_id = getDstUserId(body);
			deliverToAccessServer(default_dst_user_id, new HttpPacket(headers+body));
			break;
		}
		
	}
	
	/**
	 * 离线消息业务
	 * 
	 * @param broadcast
	 */
	private void doOfflineMessageBiz(UserOnlineOfflineBroadcast broadcast) {
		if (broadcast != null) {
			String device_uid = broadcast.getDevice_uid();
			String userId = broadcast.getUser_id();
			// 用户、设备上下线广播消息, device_uid不允许为空
			if (device_uid == null || device_uid.matches("\\s*")) {
				return;
			}
			// 1 : 用户上下线
			// 2 : 设备上下线
			int type = (broadcast.getType() == null) ? 1 : broadcast.getType();
			Integer connectionState = broadcast.getConnection_state();
			Integer loginState = broadcast.getLogin_state();
			String userIdWithDevice = getUserIdWithDeviceUid(device_uid);
			
			// 登录成功的用户重新建立TCP长连接(TCP长连接重连)
			if (userId != null && !userId.matches("\\s*") && userIdWithDevice != null && !userIdWithDevice.matches("\\s*")
					&& connectionState != null && connectionState == 1 && loginState != null && loginState == 1
					&& userIdWithDevice.equalsIgnoreCase(userId) && type == 2) {
				
				// 清除还未完成外部推送的消息队列
				NotificationMessageHandler.getInstance().removeUserNotification(userId);
				
				// 若消息队列中存在正在发送的设备消息, 优先处理该消息
				List<String> unreadDeviceMessages = MessageQueueService.getInstance().getUnreadDeviceMessages(device_uid);
				if (unreadDeviceMessages != null && unreadDeviceMessages.size() > 0) {
					String packet = unreadDeviceMessages.get(0);
					HttpPacket httpPacket = new HttpPacket(packet);
					String body = httpPacket.getBody();
					Message message = JsonUtil.getGson().fromJson(body, Message.class);
					String targetUserId = message.getMessage_info().get(0).getUser_id();
					
					logger.debug("targetUserId : " + targetUserId + ", userIdWithDevice : " + userIdWithDevice);
					logger.debug("发送设备 " + device_uid + " 的在线消息, " + packet);
					deliverToAccessServer(device_uid, packet);
					
					return;
				}
				
				// 设备上线, 发送设备离线消息
				List<OfflineMessage> deviceOfflineMessages = OfflineMessageHandler.getInstance().getOfflineMessages(device_uid);
				if (deviceOfflineMessages != null && deviceOfflineMessages.size() > 0) {
					for (OfflineMessage deviceOfflineMessage : deviceOfflineMessages) {
						// 设备离线消息的发送者
						String p = deviceOfflineMessage.getMessage();
						HttpPacket hp = new HttpPacket(p);
						String b = hp.getBody();
						Message m = JsonUtil.getGson().fromJson(b, Message.class);
						String targetUserId = m.getMessage_info().get(0).getUser_id();
						// 消息发送者和设备上的用户需要保持一致
						// 防止该设备有历史遗留消息
						if (targetUserId.equalsIgnoreCase(userIdWithDevice)) {
							logger.debug("targetUserId : " + targetUserId + ", userIdWithDevice : " + userIdWithDevice);
							logger.debug("发送设备 " + device_uid + " 的离线消息, " + p);
							deliverToAccessServer(device_uid, p);
						} else {
							// 丢弃该类消息, 不再入库
							logger.debug("targetUserId : " + targetUserId + ", userIdWithDevice : " + userIdWithDevice);
							logger.debug("丢弃设备 " + device_uid + " 的离线消息, " + p);
						}
					}
					// 设备离线消息处理完成, 返回
					return;
				}
			}
			
			// 用户上线通知
			if (userId != null && !userId.matches("\\s*") && loginState != null && loginState == 1 && type == 1) {
				logger.debug("删除设备 " + device_uid + " 的离线消息. ");
				// 清除消息队列中的设备消息
				MessageQueueService.getInstance().removeUnreadDeviceMessages(device_uid);
				OfflineMessageHandler.getInstance().deleteOfflineMessages(device_uid);
				
				// 清除还未完成外部推送的消息队列
				NotificationMessageHandler.getInstance().removeUserNotification(userId);
			}
			
			// 用户退出登录
			if (userId != null && !userId.matches("\\s*") && loginState != null && loginState == 0 && type == 1) {
				// 清除还未完成外部推送的消息队列
				NotificationMessageHandler.getInstance().removeUserNotification(userId);
			}
			
			// 检查user_id的离线消息
			userId = getBareUserId(userId);
			// 检查用户的在线状态
			boolean isLogin = isLogin(userId);
			boolean isOnline = isOnline(userId);
			if (isLogin && isOnline) {
				// 用户在线时才会推送离线消息
				List<OfflineMessage> offlineMessages = OfflineMessageHandler.getInstance().getOfflineMessages(userId);
				if (offlineMessages != null && offlineMessages.size() > 0) {
					for (OfflineMessage offlineMessage : offlineMessages) {
						logger.debug("发送用户 " + userId + " 的离线消息, " + offlineMessage.getMessage());
						deliverToAccessServer(userId, offlineMessage.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 内部推送
	 */
	public void doInternalNotification(String headers, String data) {
		
	}
	
	/**
	 * 外部推送
	 */
	public void doExternalNotification(String userId, String alert) {
		
	}
	
	/**
	 * 截取dst_user_id
	 */
	public String getDstUserId(String data) {
		if (data == null || data.matches("\\s*")) {
			return "";
		}
		String target = "\"dst_user_id\":[";
		int index = data.indexOf(target);
		if (index > 0) {
			data = data.substring(index);
			int index2 = data.indexOf("]");
			if (index2 > 0) {
				data = data.substring(0, index2 + 1);
				data = "{" + data + "}";
				DstUserId dstUserId = JsonUtil.getGson().fromJson(data, DstUserId.class);
				String temp = dstUserId.getDst_user_id().get(0);
				temp = getBareUserId(temp);
				return temp;
			}
		}
		return "";
	}
	
	/**
	 * 创建服务器接收成功回应
	 * 
	 */
	private HttpPacket createSuccessResponse() {
		String text = "{\"errcode\":200,\"errmsg\":\"OK\"}";
		
		HttpPacket packet = new HttpPacket();
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP/1.1 200 OK");
		sb.append("\r\n");
		sb.append("Date: " + Constants.getCurrentHttpDatetime());
		sb.append("\r\n");
		sb.append("Content-Type: text/html");
		sb.append("\r\n");
		sb.append("Content-Length: " + text.getBytes(Constants.CHARSET).length);
		sb.append("\r\n");
		sb.append("Connection: Keep-Alive");
		sb.append("\r\n");
		sb.append("Server: IMServer");
		sb.append("\r\n");
		sb.append("\r\n");
		
		// packet.setHeaders(sb.toString());
		// packet.setData(text);
		packet.setPacket(sb.toString() + text);
		return packet;
	}
	
	/**
	 * 接收者id列表
	 */
	class DstUserId {
		
		private List<String> dst_user_id;

		public List<String> getDst_user_id() {
			return dst_user_id;
		}

		public void setDst_user_id(List<String> dst_user_id) {
			this.dst_user_id = dst_user_id;
		}
		
	}

}
