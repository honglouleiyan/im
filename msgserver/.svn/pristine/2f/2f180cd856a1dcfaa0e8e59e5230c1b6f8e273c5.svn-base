package com.jihuiduo.test;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jihuiduo.activemq.ActiveMQQueueMessageListener;
import com.jihuiduo.biz.message.BusinessServerMessageHandler;
import com.jihuiduo.biz.notification.PayloadMessage;
import com.jihuiduo.client.TcpClient;
import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.http.HttpResponseHeader;
import com.jihuiduo.msgserver.protocol.http.HttpStatus;
import com.jihuiduo.msgserver.protocol.http.HttpVersion;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.util.JsonUtil;

public class Test {
	
	public static void main(String[] args) {
//		new Test().test_client();
//		new Test().test_push();
//		try {
//			Runtime.getRuntime().exec("");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		
//		String url = Thread.currentThread().getContextClassLoader().getResource("").toString();
		//String url = ClassLoader.getSystemClassLoader().getResource("").toString();
//		System.out.println(url);
		
//		System.out.println(System.getProperty("user.dir"));
		
		
	}
	
	public void testActiveMQ() {
		String text = "{\"message_info\":[{\"msg_id\":\"11DBA76E53DBB89400000001\",\"user_id\":\"100105\","
				+ "\"dst_user_id\":[\"100461\"],\"type\":1001,\"title\":\"AKGQ701揭了你的榜单\",\"message\":\"\","
				+ "\"options\":{\"apply\":{\"apply_id\":2470,\"bang_id\":5826,"
				+ "\"apply_user\":{\"id\":100105,\"nick\":\"AKGQ701\","
				+ "\"head\":\"http://182.254.215.187:8888/group1/M00/00/5D/Cu3kG1ZVssaAMhZqAAAsmzwDTE0396.jpg\"},"
				+ "\"message\":\"\n\n\n\n\n\n\n\n\n\n\",\"apply_time\":1448961338361,\"apply_status\":0,\"is_delete\":0,"
				+ "\"create_time\":1448961338361,\"create_user\":100105,\"update_time\":1448961338361,\"update_user\":100105},"
				+ "\"bang_info\":{\"bang_id\":5826,\"title\":\"黑girl\\wwaasxdz………\",\"content\":\"的然后呢海内外不过瞧不起他表情特别特别罚球区日本人吧\","
				+ "\"reward\":\"一周morning call\",\"money\":0.00,\"end_time\":1448967600000,\"bang_status\":2,\"wait_num\":3,\"comment_num\":0,"
				+ "\"is_delete\":0,\"issue_time\":1448959607000,\"create_time\":1448959607000,"
				+ "\"create_user\":{\"id\":100461,\"nick\":\"bang5555\",\"head\":\"http://115.159.47.200:8888/bangbang/default_picture/dhead.png\"},"
				+ "\"update_time\":1448959608000,\"update_user\":100461,\"images\":[],\"complain_status\":0,\"filter_status\":0}}}]}";
		
		try {
			// new ActiveMQQueueMessageListener().doReceiveAndExecute(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void match() {
		String s = "-1";
		System.out.println(s.matches("[0-9]+"));
	}
	
	
	public void test_push() {
//		PayloadMessage message = new PayloadMessage();
//		List<String> dst_user_id = new ArrayList<String>();
//		dst_user_id.add("10000");
//		message.setDst_user_id(dst_user_id);
//		message.setTitle("title");
//		message.setAlert("alert");
//		System.out.println(JsonUtil.getGson().toJson(message));
	}
	
	/**
	 * 客户端压力测试
	 */
	public void test_client() {
		for (int i= 0; i<500; i++) {
			new Thread(new TcpClient(i)).start();
			System.out.println("开启第 "+i+" 个客户端***********************");
		}
	}
	
	public void test_getDstUserId() {
		String target = "{\"dst_user_id\":[10005],\"user_id\":10004,\"user_nick\":\"吴小平\",\"message\":\"\","
				+ "\"head_url\":\"http://182.254.215.187:8888/group1/M00/00/14/Cu3kG1WQ3XGAQdavAAF01g46Dq0013_thumbnails.jpg\",\"type\":1}";
		BusinessServerMessageHandler.getInstance().getDstUserId(target);
	}
	
	public void getUserDir() {
		System.out.println(System.getProperty("user.dir"));
	}
	
	public void getCurrentHttpDatetime() {
		System.out.println(Constants.getCurrentHttpDatetime());
	}
	
	public void test_pattern() {
		Pattern pattern = Pattern.compile("/im");
		boolean matches = "".matches("\\s*");
		System.out.println(matches);
		
		System.out.println(pattern.split("/im/heartbeat").length);
	}
	
	public void test_createHttpResponseHeader() {
		String s = "123木头人";
		
		HttpVersion httpVersion = HttpVersion.HTTP_1_1;
		HttpStatus httpStatus = HttpStatus.STATUS_200;
		Map<String, String> h = new HashMap<String, String>();
		h.put("Connection", "Keep-Alive");
		h.put("Content-Type", "application/json");
		h.put("Server", "TestByLuoLiang/1.0");
		h.put("Date", Constants.getCurrentHttpDatetime());
		try {
			h.put("Content-Length", String.valueOf(s.getBytes().length));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpResponseHeader responseHeader = new HttpResponseHeader(httpVersion, httpStatus, h);
		System.out.println(responseHeader.toString());
	}
	
}
