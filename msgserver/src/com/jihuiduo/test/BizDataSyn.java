package com.jihuiduo.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.internal.StringMap;
import com.jihuiduo.database.DbConnectionManager;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.util.HttpHeaderUtil;
import com.jihuiduo.util.JsonUtil;

public class BizDataSyn {
	
	private static final String QUERY_BIZ = "SELECT id, message FROM t_im_message_history WHERE message_type > 1000 AND message_type < 1010";
	
	private static final String UPDATE_BIZ = "UPDATE t_im_message_history SET bang_id = ? WHERE id = ?";

	public static void main(String[] args) {
		
		// test
		
	}
	
	public void doSyn() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Map<Long, String> map = new HashMap<Long, String>();
		try {
	        con = DbConnectionManager.getConnection();
	    	pstmt = con.prepareStatement(QUERY_BIZ);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	long id = rs.getLong(1);
            	String message = rs.getString(2);
            	map.put(id, message);
            }
	    	
	    	for (long id : map.keySet()) {
	    		String message = map.get(id);
	    		HttpPacket httpPacket = new HttpPacket(message);
	    		
	    		String headers = httpPacket.getHeaders();
	    		String body = httpPacket.getBody();
	    		
	    		HttpRequestHeader httpRequestHeader = HttpHeaderUtil.parseHttpRequestHeader(headers);
	    		// URL统一为小写形式
	    		String requestPath = httpRequestHeader.getRequestPath().toLowerCase();
	    		
	    		// 去除版本标识
	    		String requestPathDropVersionStr = getRequestPathDropVersionStr(requestPath);
	    		
	    		switch (requestPathDropVersionStr) {
	    			
	    		case "/im":
	    			// 推送消息
	    			IMMessage im = JsonUtil.getGson().fromJson(body, IMMessage.class);
	    			int type = im.getType();
	    			
	    			Long bang_id = null;
	    			if (type > 1000 && type < 1010) {
	    				Map<String, Object> options = im.getOptions();
	    				if (options != null && options.size() > 0) {
	    					Object object = options.get("bang_info");
	    					if (object != null) {
	    						StringMap<Object> map2 = (StringMap<Object>)object;
	    						Object o = map2.get("bang_id");
	    						if (o != null) {
	    							String str = o.toString();
	    							try {
	    								bang_id = (long) Double.parseDouble(str);
	    							} catch (Exception e) {
	    								bang_id = null;
	    							}
	    						}
	    					}
	    				}	
	    			}
	    			
	    			if (bang_id != null) {
						pstmt = con.prepareStatement(UPDATE_BIZ);
						pstmt.setLong(1, bang_id);
						pstmt.setLong(2, id);
						pstmt.executeUpdate();
					}
	    			
	    			
	    			break;
	    			
	    		default:
	    			break;
	    		}
	    		
	    	}
	    	
		} catch (SQLException e) {
	    	
	    }
	    finally {
	        DbConnectionManager.closeConnection(rs, pstmt, con);
	    }
	}
	

	public String getRequestPathDropVersionStr(String requestPath) {
		if (requestPath == null || requestPath.matches("\\s*")) {
			return null;
		}
		// 去除版本标识
		if (requestPath.startsWith("/v") || requestPath.startsWith("/V")) {
			String temp = requestPath.substring(1);
			int index = temp.indexOf("/");
			if (index > -1) {
				requestPath = temp.substring(index);
			}
		}
		return requestPath;
	}
	
}
