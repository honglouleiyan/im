package com.jihuiduo.msgserver.protocol.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTP请求头
 */
public class HttpRequestHeader implements HttpRequest {
	/**
	 * 请求的域名
	 */
	private String host;
	/**
	 * 连接类型
	 */
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
	/**
	 * content类型
	 */
	private String contentType;
	/**
	 * content长度
	 */
	private Integer contentLength;
	
	private final HttpVersion version;

    private final HttpMethod method;

    private final String requestedPath;
    
    private final String queryString;

    private final Map<String, String> headers;

    public HttpRequestHeader(HttpVersion version, HttpMethod method, String requestedPath, String queryString, Map<String, String> headers) {
        this.version = version;
        this.method = method;
        this.requestedPath = requestedPath;
        this.queryString = queryString;
        this.headers = headers;
        
        // 读取参数
        this.host = getHeader("Host");
        this.connection = getHeader("Connection");
		this.userAgent = getHeader("User-Agent");
        this.accept = getHeader("Accept");
		this.acceptCharset = getHeader("Accept-Charset");
		this.acceptEncoding = getHeader("Accept-Encoding");
		this.acceptLanguage = getHeader("Accept-Language");
		this.contentType = getHeader("Content-Type");
		String contentLengthStr = getHeader("Content-Length");
        if (null != contentLengthStr) {
        	this.contentLength = Integer.parseInt(contentLengthStr);
		}
    }

    public HttpVersion getProtocolVersion() {
        return version;
    }

    public boolean isKeepAlive() {
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

    public boolean containsParameter(String name) {
    	Matcher m = parameterPattern(name);
    	return m.find();
    }

    public String getParameter(String name) {
    	Matcher m = parameterPattern(name);
    	if (m.find()) {
    		return m.group(1);
    	} else {
    		return null;
    	}
    }
    
    protected Matcher parameterPattern(String name) {
    	return Pattern.compile("[&]"+name+"=([^&]*)").matcher("&"+queryString);
    }

    public Map<String, List<String>> getParameters() {
    	Map<String, List<String>> parameters = new HashMap<String, List<String>>();
        String[] params = queryString.split("&");
        if (params.length == 1) {
        	return parameters;
        }
        for (int i = 0; i < params.length; i++) {
			String[] param = params[i].split("=");
			String name = param[0];
			String value = param.length == 2 ? param[1] : "";
			if (!parameters.containsKey(name)) {
				parameters.put(name, new ArrayList<String>());
			}
			parameters.get(name).add(value);
		}
        return parameters;
    }
    
    public String getQueryString() {
    	return queryString;
    }

    public HttpMethod getMethod() {
        return method;
    }
    
    public String getRequestPath() {
    	return requestedPath;
    }

	public String getHost() {
		return host;
	}

	public String getConnection() {
		return connection;
	}

	public String getAccept() {
		return accept;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getContentType() {
		return contentType;
	}

	public Integer getContentLength() {
		return contentLength;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getMethod().toString() + " " + getRequestPath());
		if (null != queryString && !queryString.matches("\\s*")) {
			sb.append("?" + queryString);
		}
		sb.append(" ");
		sb.append(getProtocolVersion().toString());
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
