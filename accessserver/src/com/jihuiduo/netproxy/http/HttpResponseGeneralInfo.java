package com.jihuiduo.netproxy.http;

/**
 * HTTP响应基本信息
 */
public class HttpResponseGeneralInfo {
	
	/**
	 * HTTP协议版本
	 * (HTTP/1.1)
	 */
	private String httpVersion;
	
	/**
	 * 状态码
	 */
	private Integer statusCode;
	
	/**
	 * 响应信息
	 */
	private String reasonPhrase;

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

}
