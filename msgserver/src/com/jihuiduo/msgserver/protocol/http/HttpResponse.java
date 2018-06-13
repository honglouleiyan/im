package com.jihuiduo.msgserver.protocol.http;

/**
 * HTTP响应基本信息
 */
public interface HttpResponse extends HttpMessage {
	
	/**
     * The HTTP status code for the HTTP response (e.g. 200 for OK, 404 for not found, etc..)
     * 
     * @return the status of the HTTP response
     */
    public HttpStatus getStatus();
}
