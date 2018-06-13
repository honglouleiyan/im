package com.jihuiduo.msgserver.protocol.http;

import java.util.Map;
import java.util.Set;

/**
 * HTTP响应头
 */
public class HttpResponseHeader implements HttpResponse {
	/**
	 * 服务器端时间
	 */
	private String date;
	/**
	 * 连接类型
	 */
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
	private Integer contentLength;
	/**
	 * 语言
	 */
	private String contentLanguage;
	/**
	 * 传输编码方式
	 * (chunked)表示输出的内容长度不能确定, 一般和Content-Encoding: gzip结合使用
	 */
	private String transferEncoding;
	/**
	 * 服务器端标识
	 */
	private String server;
	
    private final HttpVersion version;

    private final HttpStatus status;

    private final Map<String, String> headers;

    public HttpResponseHeader(HttpVersion version, HttpStatus status, Map<String, String> headers) {
        this.version = version;
        this.status = status;
        this.headers = headers;
        
        // 读取参数
        this.date = getHeader("Date");
        this.connection = getHeader("Connection");
        this.contentType = getHeader("Content-Type");
        String contentLengthStr = getHeader("Content-Length");
        if (null != contentLengthStr) {
        	this.contentLength = Integer.parseInt(contentLengthStr);
		}
        this.server = getHeader("Server");
        this.contentEncoding = getHeader("Content-Encoding");
		this.contentLanguage = getHeader("Content-Language");
		this.transferEncoding = getHeader("Transfer-Encoding");
    }

    public HttpVersion getProtocolVersion() {
        return version;
    }

    public boolean isKeepAlive() {
        // check header and version for keep alive
    	if (null != connection) {
			if ("Keep-Alive".equalsIgnoreCase(connection)) {
				return true;
			}
		}
        return false;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public boolean containsHeader(String name) {
        return headers.containsKey(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpStatus getStatus() {
        return status;
    }
	
	public String getDate() {
		return date;
	}

	public String getConnection() {
		return connection;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public String getContentType() {
		return contentType;
	}

	public Integer getContentLength() {
		return contentLength;
	}

	public String getContentLanguage() {
		return contentLanguage;
	}

	public String getTransferEncoding() {
		return transferEncoding;
	}

	public String getServer() {
		return server;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getProtocolVersion().toString() + " " + getStatus().line());
		sb.append("\r\n");
		if (null != headers) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				String value = headers.get(key);
				sb.append(key);
				sb.append(": ");
				sb.append(value);
				sb.append("\r\n");
			}
		}
		
		sb.append("\r\n");
		return sb.toString();
	}

}
