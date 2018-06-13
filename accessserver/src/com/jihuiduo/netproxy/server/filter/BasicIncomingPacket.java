package com.jihuiduo.netproxy.server.filter;

/**
 * 服务器收到的数据包
 */
public class BasicIncomingPacket {
	
	/**
	 * 消息头
	 */
	private String headers;
	
	/**
	 * 消息数据
	 */
	private String data;
	
	public BasicIncomingPacket() {
		
	}
	
	public BasicIncomingPacket(String headers, String data) {
		this.headers = headers;
		this.data = data;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
