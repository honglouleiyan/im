package com.jihuiduo.activemq;

import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.util.SequenceUtil;

/**
 * ActiveMQ消息模块入口
 *
 */
public class ActiveMQMessageHandler {
	
	/**
	 * 创建IM消息
	 * 
	 * @param text
	 * @return
	 */
	public HttpPacket createInstantMessage(String text) {
		String url = "/im";
		HttpPacket packet = packagingHttpPacket(url, text);
		return packet;
	}
	
	/**
	 * 创建用户、设备上下线通知消息
	 * 
	 * @param text
	 * @return
	 */
	public HttpPacket createUserOnlineNotification(String text) {
		String url = "/notification/user/online";
		HttpPacket packet = packagingHttpPacket(url, text);
		return packet;
	}
	
	/**
	 * 封装HttpPacket
	 * 
	 * @param url
	 * @param text
	 * @return
	 */
	private HttpPacket packagingHttpPacket(String url, String text) {
		HttpPacket packet = new HttpPacket();
		
		String seq = SequenceUtil.createSequence();
		url = Constants.VERSION + url + "?seq=" + seq;
		StringBuffer sb = new StringBuffer();
		sb.append("POST ");
		sb.append(url);
		sb.append(" ");
		sb.append("HTTP/1.1");
		sb.append("\r\n");
		
		sb.append("User-Agent: IMServer");
		sb.append("\r\n");
		sb.append("Date: " + Constants.getCurrentHttpDatetime());
		sb.append("\r\n");
		sb.append("Content-Type: application/json");
		sb.append("\r\n");
		try {
			sb.append("Content-Length: " + text.getBytes(Constants.CHARSET).length);
		} catch (Exception e) {
			sb.append("Content-Length: " + text.length());
		}
		sb.append("\r\n");
		sb.append("Connection: Keep-Alive");
		sb.append("\r\n");
		sb.append("Server: IMServer");
		sb.append("\r\n");
		sb.append("\r\n");
		
		packet.setPacket(sb.toString() + text);
		
		return packet;
	}

}
