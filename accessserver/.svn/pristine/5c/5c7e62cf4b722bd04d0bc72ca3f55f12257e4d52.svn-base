package com.jihuiduo.netproxy.http;


/**
 * HTTP响应头
 */
public class HttpResponseHeader extends HttpResponseGeneralInfo {
	
	/**
	 * 服务器端时间
	 */
	private String date;
	
	private String connection;
	
	/**
	 * 传送启用的压缩方式
	 * (gzip)
	 */
	private String contentEncoding;
	
	/**
	 * 内容类型
	 */
	private String contentType;
	
	/**
	 * 内容长度
	 */
	private Long contentLength;
	
	private String contentLanguage;
	
	private String location;
	
	/**
	 * 服务器端的服务器软件
	 */
	private String server;
	
	private String setCookie;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentLanguage() {
		return contentLanguage;
	}

	public void setContentLanguage(String contentLanguage) {
		this.contentLanguage = contentLanguage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSetCookie() {
		return setCookie;
	}

	public void setSetCookie(String setCookie) {
		this.setCookie = setCookie;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getHttpVersion() + " " + getStatusCode() + " " + getReasonPhrase());
		sb.append("\r\n");
		sb.append("Date: " + getDate());
		sb.append("\r\n");
		sb.append("Connection: " + getConnection());
		sb.append("\r\n");
		sb.append("Content-type: " + getContentType());
		sb.append("\r\n");
		sb.append("Content-Length: " + getContentLength());
		sb.append("\r\n");
		sb.append("Location: " + getLocation());
		sb.append("\r\n");
		sb.append("Server: " + getServer());
		sb.append("\r\n");
		sb.append("Set-Cookie: " + getSetCookie());
		sb.append("\r\n");
		
		sb.append("\r\n");
		return sb.toString();
		
		// 创建http协议头
//		StringBuffer sb = new StringBuffer();
//		sb.append("HTTP/1.1 200 OK");
//		sb.append("\r\n");
//		sb.append("Date: " + new Date().toString());
//		sb.append("\r\n");
//		sb.append("Content-Type: application/json");
//		sb.append("\r\n");
//		sb.append("Content-Length: " + length);
//		sb.append("\r\n");
//		sb.append("Connection: Keep-Alive");
//		sb.append("\r\n");
//		// 非必选项
//		sb.append("Location: http://192.168.3.13:5222/error.html");
//		sb.append("\r\n");
//		// 非必选项
//		sb.append("Server: TestByLuoLiang/1.0");
//		sb.append("\r\n");
//		// 非必选项
//		sb.append("Set-Cookie: BDSVRTM=0; path=/");
//		sb.append("\r\n");
//		sb.append("\r\n");
//		String header = sb.toString();
	}


}
