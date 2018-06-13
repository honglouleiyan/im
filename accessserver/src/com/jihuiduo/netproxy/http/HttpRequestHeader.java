package com.jihuiduo.netproxy.http;

import com.jihuiduo.netproxy.utils.ReadXml;

import net.sf.json.JSONObject;

/**
 * HTTP请求头
 */
public class HttpRequestHeader extends HttpRequestGeneralInfo {
	
	/**
	 * 请求的域名
	 */
	private String host;
	
	private String connection;
	
	/**
	 * 可接受的内容类型
	 */
	private String accept;
	
	/**
	 * 可接受的内容编码
	 * (UTF-8)
	 */
	private String acceptCharset;
	
	/**
	 * 可接受的压缩类型
	 * (gzip)
	 */
	private String acceptEncoding;
	
	/**
	 * 可接受的语言
	 */
	private String acceptLanguage;
	
	/**
	 * 客户端浏览器型号和版本
	 */
	private String userAgent;
	
	private String contentType;
	
	private Long contentLength;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}

	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
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
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getRequestMethod() + " " + getRequestUrl() + " " + getHttpVersion());
		sb.append("\r\n");
		sb.append("Host: " + getHost());
		sb.append("\r\n");
		sb.append("Connection: " + getConnection());
		sb.append("\r\n");
		sb.append("Accept: " + getAccept());
		sb.append("\r\n");
		sb.append("User-Agent: " + getUserAgent());
		sb.append("\r\n");
		sb.append("Accept-Charset: " + getAcceptCharset());
		sb.append("\r\n");
		sb.append("Accept-Encoding: " + getAcceptEncoding());
		sb.append("\r\n");
		sb.append("Accept-Language: " + getAcceptLanguage());
		sb.append("\r\n");
		sb.append("Content-type: " + getContentType());
		sb.append("\r\n");
		sb.append("Content-Length: " + getContentLength());
		sb.append("\r\n");
		
		sb.append("\r\n");
		return sb.toString();
	}
	
	public static String getHeartBeat(long userID) {
		HttpRequestHeader header = new HttpRequestHeader();
		header.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		header.setAcceptEncoding("gzip, deflate, sdch");
		header.setAcceptLanguage("zh-CN,zh;q=0.8");
		header.setConnection("keep-alive");
		header.setContentType("");
		header.setHost(ReadXml.getLocalAddress()+":"+ReadXml.getlocalMonitorPort());
		header.setHttpVersion("HTTP/1.1");
		header.setRequestMethod("POST");
		header.setRequestUrl("/jhd?rid=51");
		header.setUserAgent("netproxy");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seq", 123445);
		jsonObject.put("userID", userID);
		header.setContentLength((long)jsonObject.toString().length());
		return (header.toString() + jsonObject.toString());
	}
	public static void main(String[] args) {
		HttpRequestHeader header = new HttpRequestHeader();
		header.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		header.setAcceptEncoding("gzip, deflate, sdch");
		header.setAcceptLanguage("zh-CN,zh;q=0.8");
		header.setConnection("keep-alive");
		header.setContentType("");
		header.setHost(ReadXml.getLocalAddress()+":"+ReadXml.getlocalMonitorPort());
		header.setHttpVersion("HTTP/1.1");
		header.setRequestMethod("POST");
		header.setRequestUrl("/jhd?rid=51");
		header.setUserAgent("netproxy");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seq", 123445);
		jsonObject.put("userID", 3333);
		header.setContentLength((long)jsonObject.toString().length());
		System.out.println(header.toString() + jsonObject.toString());
	}
}
