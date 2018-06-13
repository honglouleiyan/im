package com.jihuiduo.msgserver.protocol.http;

import java.util.Map;

/**
 * An HTTP message, the ancestor of HTTP request & response.
 */
public interface HttpMessage {

    /**
     * The HTTP version of the message
     * 
     * @return HTTP/1.0 or HTTP/1.1
     */
    public HttpVersion getProtocolVersion();

    /**
     * Gets the <tt>Content-Type</tt> header of the message.
     * 
     * @return The content type.
     */
    public String getContentType();

    /**
     * Returns <tt>true</tt> if this message enables keep-alive connection.
     */
    public boolean isKeepAlive();

    /**
     * Returns the value of the HTTP header with the specified name. If more than one header with the given name is
     * associated with this request, one is selected and returned.
     * 
     * @param name The name of the desired header
     * @return The header value - or null if no header is found with the specified name
     */
    public String getHeader(String name);

    /**
     * Returns <tt>true</tt> if the HTTP header with the specified name exists in this request.
     */
    public boolean containsHeader(String name);

    /**
     * Returns a read-only {@link Map} of HTTP headers whose key is a {@link String} and whose value is a {@link String}
     * s.
     */
    public Map<String, String> getHeaders();
}

