package com.jihuiduo.netproxy.server.online;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.start.RedisConnection;
import com.jihuiduo.netproxy.utils.ReadXml;
import com.jihuiduo.redis.cache.impl.CacheTypeEnum;

public class DeviceOnline {
	private static final Logger logger = Logger.getLogger(DeviceOnline.class);
    public static final String TOKEN = "token";
    public static final String EXPIRESIN = "expires_in";
    public static final String LOGIN_STATE = "login_state";
    public static final String LOGIN_TIME = "login_time";
    public static final String REMOTECLIENT = "remote_client";
    public static final String CONNETCTION_STATE = "connection_state";
    public static final String REMOTE_CLIENT = "remote_client";
    public static final String CONNETCTION_SERVER = "connection_server";
    public static final String ACTIVETIME = "active_time";
    public static final String DEVICE_UID = "device_uid";
    public static final String DEVICE_TYPE = "device_type";
    public static final String USER_ID = "user_id";
    public static final Integer NOTIFICATION = 1;
    public static final String STATEONE = "1";
    public static final String STATEZERO = "0";
    
	public static void setUserInfo(IoSession session,String device) {
		logger.debug("连接管理缓存："+CacheTypeEnum.DEVICE+":"+device);
		RedisConnection.redisStringCache.putHash(CacheTypeEnum.DEVICE,device,DeviceOnline.CONNETCTION_STATE,DeviceOnline.STATEONE);
		// 远程主机的IP地址和端口号
		InetSocketAddress inetSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		// ip地址
		String ip = inetSocketAddress.getAddress().getHostAddress();
		// 端口号
		int port = inetSocketAddress.getPort();
		String key = ip + ":" + port;
		RedisConnection.redisStringCache.putHash(CacheTypeEnum.DEVICE,device,DeviceOnline.REMOTE_CLIENT,key);
		RedisConnection.redisStringCache.putHash(CacheTypeEnum.DEVICE,device,DeviceOnline.CONNETCTION_SERVER,ReadXml.getLocalAddress()+":"+ReadXml.getlocalPort());
    }
	
	public static void setUserInfoOffline(String device) {
		logger.debug("连接管理缓存："+CacheTypeEnum.DEVICE+":"+device);
		RedisConnection.redisStringCache.putHash(CacheTypeEnum.DEVICE,device,DeviceOnline.CONNETCTION_STATE,DeviceOnline.STATEZERO);
    }
}
