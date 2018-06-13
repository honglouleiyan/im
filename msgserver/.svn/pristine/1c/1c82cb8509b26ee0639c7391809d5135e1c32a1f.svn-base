package com.jihuiduo.biz.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.biz.MessageHandler;
import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.MessageAck;
import com.jihuiduo.msgserver.protocol.Result;
import com.jihuiduo.msgserver.protocol.basic.Packet;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.http.HttpResponseHeader;
import com.jihuiduo.msgserver.protocol.http.HttpStatus;
import com.jihuiduo.msgserver.protocol.http.HttpVersion;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.util.HttpHeaderUtil;
import com.jihuiduo.util.JsonUtil;

/**
 * 接入服务器消息处理
 * 
 */
public class AccessServerMessageHandler extends MessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccessServerMessageHandler.class);
	/**
	 * 单例模式
	 */
	private static AccessServerMessageHandler instance;
	
	/**
	 * 单例模式
	 */
	public static AccessServerMessageHandler getInstance() {
		if (instance == null) {
			synchronized (AccessServerMessageHandler.class) {
				if (instance == null) {
					instance = new AccessServerMessageHandler();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 处理接入服务器发送过来的消息
	 * 
	 */
	public void handle(Packet packet, String remoteServer) throws Exception {
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
			// 消息保障机制
			MessageQueueService.getInstance().handle("Access Server Incoming Packet", packet);
			return;
		}
		
		// HTTP Request
		HttpRequestHeader httpRequestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
		String requestPath = httpRequestHeader.getRequestPath().toLowerCase();
		if (requestPath == null || requestPath.matches("\\s*")) {
			logger.error("AccessServerMessageHandler requestPath error, " + requestPath);
			return;
		}
		
		// 去除版本标识
		String requestPathDropVersionStr = getRequestPathDropVersionStr(requestPath);
		
		switch (requestPathDropVersionStr) {
			case "/im":
				handleIM(headers, body);
				break;
				
			default:
				break;
		}
		
	}
	
	/**
	 * 处理IM消息
	 * 
	 * @param headers
	 * @param data
	 */
	private void handleIM(String headers, String data) {
		HttpRequestHeader httpRequestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
		String seq = httpRequestHeader.getParameter("seq");
		
		// 身份凭证标识
		String access_token = httpRequestHeader.getParameter("access_token");
		
		Message message = JsonUtil.getGson().fromJson(data, Message.class);
		List<IMMessage> infos = message.getMessage_info();
		
		if (infos == null || infos.size() == 0) {
			return;
		}
		
		for (IMMessage info : infos) {
			// 消息类型
			int type = info.getType();
			// 发送者
			String user_id = info.getUser_id();
			// 消息ID
			String msg_id = info.getMsg_id();
			
			// 发送消息回执
			List<String> msgIdList = new ArrayList<String>();
			msgIdList.add(msg_id);
			
			HttpPacket outgoingPacket = null;
			String bareUserId = getBareUserId(user_id);
			
			String queryUserId = getUserIdByAccessToken(access_token);
			if (queryUserId == null || !bareUserId.equalsIgnoreCase(queryUserId)) {
				// 用户未验证或身份凭证错误
				outgoingPacket = createMessageAckPacket(HttpStatus.STATUS_401, seq, user_id, msgIdList, 403, "用户未登录");
				deliverToAccessServer(bareUserId, outgoingPacket);
				return;
			} else {
				if (isBlack(bareUserId)) {
					// 黑名单
					outgoingPacket = createMessageAckPacket(HttpStatus.STATUS_403, seq, user_id, msgIdList, 407, "账号存在违规操作, 已被加入黑名单");
					deliverToAccessServer(bareUserId, outgoingPacket);
					return;
				} else {
					outgoingPacket = createMessageAckPacket(HttpStatus.STATUS_200, seq, user_id, msgIdList, 0, "OK");
					deliverToAccessServer(bareUserId, outgoingPacket);
				}
			}
			
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
	}
	
	/**
	 * 生成消息回执包
	 * 
	 */
	private HttpPacket createMessageAckPacket(HttpStatus httpStatus, String seq, String user_id,
			List<String> msg_id, Integer rcode, String rmsg) {
		MessageAck messageAck = new MessageAck();
		messageAck.setMsg_id(msg_id);
		Result result = new Result();
		result.setSeq(seq);
		result.setUser_id(user_id);
		result.setRcode(rcode);
		result.setRmsg(rmsg);
		result.setData(messageAck);;
		
		String s = JsonUtil.getGson().toJson(result);
		
		HttpVersion httpVersion = HttpVersion.HTTP_1_1;
		Map<String, String> h = new HashMap<String, String>();
		h.put("Connection", "Keep-Alive");
		h.put("Content-Type", "application/json");
		h.put("Server", "IMServer");
		h.put("Date", Constants.getCurrentHttpDatetime());
		h.put("Content-Length", String.valueOf(s.getBytes(Constants.CHARSET).length));
		
		HttpResponseHeader responseHeader = new HttpResponseHeader(httpVersion, httpStatus, h);
		HttpPacket outgoingPacket = new HttpPacket();
		outgoingPacket.setPacket(responseHeader.toString() + s);
		return outgoingPacket;
	}
	
}
