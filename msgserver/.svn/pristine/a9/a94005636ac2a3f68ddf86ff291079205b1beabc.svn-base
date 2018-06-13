package com.jihuiduo.msgserver.protocol.http;

import com.jihuiduo.msgserver.protocol.basic.Packet;

/**
 * HTTP协议数据包
 *
 */
public class HttpPacket extends Packet {
    /**
     * string to parse raw headers and body
     */
    private static final String HEADERS_BODY_INDEX = "\r\n\r\n";

	public HttpPacket() {
		super();
	}
	
	public HttpPacket(String packet) {
		super(packet);
	}
	
	public HttpPacket(String headers, String body) {
		super(headers + body);
	}
	
	public String getHeaders() {
		return getHttpHeaders();
	}

	public String getBody() {
		return getHttpBody();
	}

	/**
	 * 解析http协议头
	 */
	private String getHttpHeaders() {
		if (packet == null || packet.matches("\\s*")) {
			return null;
		}
		int index = packet.indexOf(HEADERS_BODY_INDEX);
		if (index == -1) {
			return null;
		}
		String headers = packet.substring(0, index + 4);
		return headers;
	}
	
	/**
	 * 解析http协议消息体
	 */
	private String getHttpBody() {
		if (packet == null || packet.matches("\\s*")) {
			return null;
		}
		int index = packet.indexOf(HEADERS_BODY_INDEX);
		if (index == -1) {
			return null;
		}
		String body = packet.substring(index + 4);
		return body;
	}
	
}
