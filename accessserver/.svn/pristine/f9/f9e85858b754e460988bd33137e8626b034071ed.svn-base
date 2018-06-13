package com.jihuiduo.netproxy.http;

import com.jihuiduo.netproxy.constant.Constant;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;

public class HttpResponse {
	public static BasicOutgoingPacket createOffline(String seq,String userId) {
		String text = "{\"seq\":\""+seq+"\",\"user_id\":\""+userId+"\",\"rcode\":401,\"rmsg\":\"您的帮帮帐号已在其它地方登录，请注意帐号安全。\"}";
		
		BasicOutgoingPacket packet = new BasicOutgoingPacket();
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP/1.1 401 Unauthorized");
		sb.append("\r\n");
		sb.append("Date: " + Constant.getCurrentHttpDatetime());
		sb.append("\r\n");
		sb.append("Content-Type: application/json");
		sb.append("\r\n");
		sb.append("Content-Length: " + text.getBytes(Constant.CHAR_SET).length);
		sb.append("\r\n");
		sb.append("Connection: Close");
		sb.append("\r\n");
		sb.append("Server: NetProxy");
		sb.append("\r\n");
		sb.append("\r\n");
		
		packet.setHeaders(sb.toString());
		packet.setData(text);
		
		return packet;
	}
	
	public static BasicOutgoingPacket createAuth(String seq) {
		String text = "{\"seq\":\""+seq+"\",\"rcode\":0,\"rmsg\":\"成功\"}";
		
		BasicOutgoingPacket packet = new BasicOutgoingPacket();
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP/1.1 200 OK");
		sb.append("\r\n");
		sb.append("Date: " + Constant.getCurrentHttpDatetime());
		sb.append("\r\n");
		sb.append("Content-Type: application/json");
		sb.append("\r\n");
		sb.append("Content-Length: " + text.getBytes(Constant.CHAR_SET).length);
		sb.append("\r\n");
		sb.append("Connection: Keep-Alive");
		sb.append("\r\n");
		sb.append("Server: NetProxy");
		sb.append("\r\n");
		sb.append("\r\n");
		
		packet.setHeaders(sb.toString());
		packet.setData(text);
		
		return packet;
	}
	
	
	public static void main(String[] args) {
		BasicOutgoingPacket packet = createOffline("seq","userId");
		System.out.println(packet.getHeaders()+packet.getData());
//		BasicOutgoingPacket packet = createAuth("seq");
//		System.out.println(packet.getHeaders()+packet.getData());
	}
}
