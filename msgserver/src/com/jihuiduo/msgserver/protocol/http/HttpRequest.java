package com.jihuiduo.msgserver.protocol.http;

import java.util.List;
import java.util.Map;

/**
 * HTTP请求基本信息
 */
public interface HttpRequest extends HttpMessage {

	/**
	 * Determines whether this request contains at least one parameter with the specified name
	 * 
	 * @param name The parameter name
	 * @return <code>true</code> if this request contains at least one parameter with the specified name
	 */
	boolean containsParameter(String name);
	
	/**
	 * Returns the value of a request parameter as a String, or null if the parameter does not exist.
	 * 
	 * If the request contained multiple parameters with the same name, this method returns the first parameter
	 * encountered in the request with the specified name
	 * 
	 * @param name The parameter name
	 * @return The value
	 */
	String getParameter(String name);
	
	String getQueryString();
	
	/**
	 * Returns a read only {@link Map} of query parameters whose key is a {@link String} and whose value is a
	 * {@link List} of {@link String}s.
	 */
	Map<String, List<String>> getParameters();
	
	/**
	 * Return the HTTP method used for this message {@link HttpMethod}
	 * 
	 * @return the method
	 */
	HttpMethod getMethod();
	
	/**
	 * Retrurn the HTTP request path
	 * @return the request path
	 */
	String getRequestPath();
}

