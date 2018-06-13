package com.jihuiduo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jihuiduo.msgserver.protocol.Message;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.util.JsonUtil;

public class GsonTest {
	
	public static void main(String[] args) {
		
	}
	
	public void htmlTest() {
		String s = "{\"message_info\":[{\"msg_id\":\"1098726DE05EBBFC00000001\",\"user_id\":\"10000\",\"dst_user_id\":[\"100028\"],\"type\":1000,\"title\":\"来自麦圈的红包\",\"message\":\"http://115.159.47.200/html/red_packet_good.html?red_packet=103\",\"att_type\":1}]}";
		Message message = JsonUtil.getGson().fromJson(s, Message.class);
		System.out.println(message.getMessage_info().get(0).getMessage());
		String s2 = JsonUtil.getGson().toJson(message);
		System.out.println(s2);
	}
	
	public void testJson() {
		String text = "{\"apply\":{\"apply_id\":2470,\"bang_id\":5826,"
				+ "\"apply_user\":{\"id\":100105,\"nick\":\"AKGQ701\","
				+ "\"head\":\"http://182.254.215.187:8888/group1/M00/00/5D/Cu3kG1ZVssaAMhZqAAAsmzwDTE0396.jpg\"},"
				+ "\"message\":\"\n\n\n\n\n\n\n\n\n\n\",\"apply_time\":1448961338361,\"apply_status\":0,\"is_delete\":0,"
				+ "\"create_time\":1448961338361,\"create_user\":100105,\"update_time\":1448961338361,\"update_user\":100105},"
				+ "\"bang_info\":{\"bang_id\":5826,\"title\":\"黑girl\\wwaasxdz………\",\"content\":\"的然后呢海内外不过瞧不起他表情特别特别罚球区日本人吧\","
				+ "\"reward\":\"一周morning call\",\"money\":0.00,\"end_time\":1448967600000,\"bang_status\":2,\"wait_num\":3,\"comment_num\":0,"
				+ "\"is_delete\":0,\"issue_time\":1448959607000,\"create_time\":1448959607000,"
				+ "\"create_user\":{\"id\":100461,\"nick\":\"bang5555\",\"head\":\"http://115.159.47.200:8888/bangbang/default_picture/dhead.png\"},"
				+ "\"update_time\":1448959608000,\"update_user\":100461,\"images\":[],\"complain_status\":0,\"filter_status\":0}}";
		
		
		String text2 = "{\"apply\":\"{\"apply_id\":2470,\"bang_id\":5826,"
				+ "\"apply_user\":{\"id\":100105,\"nick\":\"AKGQ701\","
				+ "\"head\":\"http://182.254.215.187:8888/group1/M00/00/5D/Cu3kG1ZVssaAMhZqAAAsmzwDTE0396.jpg\"},"
				+ "\"message\":\"\n\n\n\n\n\n\n\n\n\n\",\"apply_time\":1448961338361,\"apply_status\":0,\"is_delete\":0,"
				+ "\"create_time\":1448961338361,\"create_user\":100105,\"update_time\":1448961338361,\"update_user\":100105}\","
				+ "\"bang_info\":\"{\"bang_id\":5826,\"title\":\"黑girl\\wwaasxdz………\",\"content\":\"的然后呢海内外不过瞧不起他表情特别特别罚球区日本人吧\","
				+ "\"reward\":\"一周morning call\",\"money\":0.00,\"end_time\":1448967600000,\"bang_status\":2,\"wait_num\":3,\"comment_num\":0,"
				+ "\"is_delete\":0,\"issue_time\":1448959607000,\"create_time\":1448959607000,"
				+ "\"create_user\":{\"id\":100461,\"nick\":\"bang5555\",\"head\":\"http://115.159.47.200:8888/bangbang/default_picture/dhead.png\"},"
				+ "\"update_time\":1448959608000,\"update_user\":100461,\"images\":[],\"complain_status\":0,\"filter_status\":0}\"}";
		
		try {
			Map<String, Object> map = JsonUtil.getGson().fromJson(text, new TypeToken<Map<String, Object>>(){}.getType());
			String s = JsonUtil.getGson().toJson(map);
			System.out.println(map.toString());
			
			
			Map<String, String> map2 = new HashMap<String, String>();
			for (String key : map.keySet()) {
				map2.put(key, map.get(key).toString());
			}
			// JsonUtil.getGson().fromJson(text2, new TypeToken<Map<String, String>>(){}.getType());
			String s2 = JsonUtil.getGson().toJson(map2);
			System.out.println(s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用GSON解析json
	 */
	public Message test_parse(String s) {
		s = "{\"seq\":\"WEv9quiQgoAAAAR\",\"messageInfo\":[{\"message\":\"dcfgj\",\"userID\":\"222222222222222\",\"priority\":\"0\","
				+ "\"att\":[{\"totalTime\":\"0\",\"time\":\"1429081263578\",\"msgtype\":\"0\"}],"
				+ "\"state\":\"0\",\"msgID\":\"WEv9quiQgoAAAAR\",\"dstUserId\":[11111111111,2222222222],\"type\":2}]}";
		Gson gson = new Gson();
		Message message = new Message();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(s);
		JsonObject object = null;
		if (element.isJsonObject()) {
			object = element.getAsJsonObject();
			
			String seq = object.get("seq").getAsString();
			// message.setSeq(seq);
			JsonArray array = object.get("messageInfo").getAsJsonArray();
			Iterator<JsonElement> iterator = array.iterator();
			
			List<IMMessage> list = new ArrayList<IMMessage>();
			while(iterator.hasNext()) {
				JsonElement e = iterator.next();
				JsonObject o = e.getAsJsonObject();
				
				IMMessage info = new IMMessage();
				info.setMsg_id(o.get("msgID").getAsString());
				info.setUser_id(o.get("userID").getAsString());
				info.setType(o.get("type").getAsInt());
//				info.setTitle(o.get("title").getAsString());
				info.setMessage(o.get("message").getAsString());
//				System.out.println(o.get("dstUserId").getAsString());;
				info.setDst_user_id((List<String>) gson.fromJson(o.get("dstUserId"), new TypeToken<List<String>>(){}.getType()));
				
				info.setAtt((List<Object>) gson.fromJson(o.get("att"), new TypeToken<List<Object>>(){}.getType()));
				info.setState(o.get("state").getAsInt());
				info.setPriority(o.get("priority").getAsInt());
				
				list.add(info);
			}
			message.setMessage_info(list);
		}
		
		String temp = gson.toJson(message);
		System.out.println(temp);
		
		Message m = gson.fromJson(temp, Message.class);
		System.out.println(m);
		return message;
		
	}
	
}
