package com.jihuiduo.msgserver.protocol.http;

/**
 * HTTP Request Method
 */
public enum HttpMethod {

	GET("GET"), 
	POST("POST"), 
	PUT("PUT"), 
	DELETE("DELETE");
	
	private String method;
	
	private HttpMethod(String method) {
		this.method = method;
	}
	
	public static HttpMethod fromString(String string) {
		if (GET.toString().equalsIgnoreCase(string)) {
			return GET;
		} else if (POST.toString().equalsIgnoreCase(string)) {
			return POST;
		} else if (PUT.toString().equalsIgnoreCase(string)) {
			return PUT;
		} else if (DELETE.toString().equalsIgnoreCase(string)) {
			return DELETE;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return method;
	}
}
