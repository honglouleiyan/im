package com.jihuiduo.netproxy.constant;

public class ProtocolMean {

	public final static String HEARTBEAT= "heartbeat"; 

	
	
	public final static String USERID= "user_id";
	public final static String MESSAGEINFO= "message_info";
	
	public static final String HTTP = "HTTP";//HTTP response相应标示
	public static final String SEQ = "seq";//消息标示
	public static final String RESULT = "result";//结果集
	public static final String DSTUSERID = "dst_user_id";//目标Id
	public static final String DEVICE_UID = "device_uid";//目标Id
	public static final String TYPE = "type";//消息类型
	public static Integer TYPE_101 = 101;// 根据设备推送
	//协议第二级  第一季为版本报v1
	public final static String IM= "im"; 
	public final static String USER= "user"; 
	public final static String DEVICE= "device"; 
	public final static String NOTIFICATION = "notification";
	public final static String GROUPS = "groups";
	public final static String SEARCH= "search";
	public final static String REGISTRATION= "registration"; 
	//协议第三级  第一季为版本报v1
	public final static String ARTICLES= "articles"; 
	public final static String SEEKHELPS= "seekhelps"; 
	public final static String SUPPORTS= "supports";
	public final static String SUBSCRIPTION = "subscription";
	public final static String SEEKHELPINFO = "seekHelpInfo";
	public final static String SIPPORTINFO = "supportInfo";
	public final static String ONLINE = "online";
	public final static String FOLLOWERS = "followers";
	public final static String INDENTITY_AUTH = "auth";
}
