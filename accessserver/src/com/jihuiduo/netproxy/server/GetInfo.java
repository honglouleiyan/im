package com.jihuiduo.netproxy.server;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import com.jihuiduo.netproxy.constant.Constant;
import com.jihuiduo.netproxy.constant.ProtocolMean;
import com.jihuiduo.netproxy.http.HttpRequestHeader;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;
import com.jihuiduo.netproxy.utils.ReadXml;
import com.jihuiduo.netproxy.utils.UUIDFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetInfo {
	public static String getUserIdForImpost(String datafromapp) {
		String userId = null;
	 	JSONObject data = JSONObject.fromObject(datafromapp);
	 	if(data != null) {
			JSONArray array =  (JSONArray) data.get(ProtocolMean.MESSAGEINFO);
			if(array != null) {
				JSONObject messageInfo = (JSONObject) array.get(0);
				String info = messageInfo.get(ProtocolMean.USERID)!= null ? messageInfo.get(ProtocolMean.USERID).toString() : null;
				if(StringUtils.isNotBlank(info)) {
					int dex = info.indexOf("@");
					if(dex == -1) {
						userId = info;
					} else {
						userId = info.substring(0,dex);
					}
					int dex2 = info.indexOf("/");
					if(dex2 == -1) {
						userId = info;
					} else {
						userId = info.substring(0,dex2);
					}
				}
			}
	 	}
		return userId;
	}
	
	public static Long getUserIdForImheart(String datafromapp) {
		Long userId = null;
	 	JSONObject data = JSONObject.fromObject(datafromapp);
	 	if(data != null) {
			Object jsonObject =  data.get(ProtocolMean.USERID);
			if(jsonObject != null) {
				userId = Long.valueOf(jsonObject.toString());
			}
	 	}
		return userId;
	}
	
	public static String getUserIdForRespone(String datafromapp) {
		String userId = null;
		String info = null;
	 	JSONObject data = JSONObject.fromObject(datafromapp);
	 	if(data != null) {
			Object jsonObject =  data.get(ProtocolMean.USERID);
			if(jsonObject != null) {
				info = jsonObject.toString();
			}
	 	}
		if(StringUtils.isNotBlank(info)) {
			int dex = info.indexOf("@");
			if(dex == -1) {
				userId = info;
			} else {
				userId = info.substring(0,dex);
			}
			int dex2 = info.indexOf("/");
			if(dex2 == -1) {
				userId = info;
			} else {
				userId = info.substring(0,dex2);
			}
		}
		return userId;
	}
	
	
	public static String getDeviceIdForAuth(String datafromapp) {
		String deviceUid = null;
	 	JSONObject data = JSONObject.fromObject(datafromapp);
	 	if(data != null) {
			Object jsonObject =  data.get(ProtocolMean.DEVICE_UID);
			if(jsonObject != null) {
				deviceUid = jsonObject.toString();
			}
	 	}
		return deviceUid;
	}
	
	
	public static String getPara(String header,String name) {
		String seq = null;
		int dex = header.indexOf("?");
		if(dex != -1) {
			String para = header.substring(dex, header.length());
			Matcher matcher = Pattern.compile("[&]"+name+"=([^&]*)").matcher("&"+para);
		  	if (matcher.find()) {
	    		seq = matcher.group(1);
	    	}
		}
		return seq;
	}
	
	
	
	public static BasicOutgoingPacket getNotify(Long userID) {
		HttpRequestHeader header = new HttpRequestHeader();
		header.setAccept("*/*");
		header.setAcceptEncoding("gzip");
		header.setAcceptLanguage("zh-CN,zh;q=0.8");
		header.setConnection("keep-alive");
		header.setHost(ReadXml.getLocalAddress() + ":" + ReadXml.getlocalMonitorPort());
		header.setHttpVersion("HTTP/1.1");
		header.setRequestMethod("POST");
		header.setRequestUrl("/jhd?rid=51");
		header.setContentType("application/json");
		header.setUserAgent("netproxy");
		header.setAcceptCharset(Constant.CHARSET);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seq", UUIDFactory.generateUUID());
		jsonObject.put("userID", userID);
		header.setContentLength(Long.valueOf(jsonObject.toString().length()));
		BasicOutgoingPacket basicOutgoingPacket = new BasicOutgoingPacket();
		basicOutgoingPacket.setHeaders(header.toString());
		basicOutgoingPacket.setData(jsonObject.toString());
		return basicOutgoingPacket;
	}
	
}
