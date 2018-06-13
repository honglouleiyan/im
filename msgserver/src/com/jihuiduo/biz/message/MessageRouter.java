package com.jihuiduo.biz.message;

import java.util.List;

import com.jihuiduo.config.SystemConfig;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;

/**
 * 消息处理入口
 */
public class MessageRouter {
	
	private static MessageRouter instance;
	
	public static MessageRouter getInstance() {
		if (instance == null) {
			synchronized (MessageRouter.class) {
				if (instance == null) {
					instance = new MessageRouter();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 处理消息
	 * 
	 * @param headers
	 * @param data
	 * @param remoteServer
	 * @throws Exception
	 */
	public void handleMessage(HttpPacket packet, String remoteServer) throws Exception {
		AccessServerMessageHandler.getInstance().handle(packet, remoteServer);
	}
	
	/**
	 * 判断该key=ip:port是否来自接入服务器
	 * 
	 * @param key
	 * @return
	 */
	public boolean fromAccessServer(String key) {
		List<String> accessServerIpList = SystemConfig.getAccessServerList();
		if (accessServerIpList.contains(key)) {
			return true;
		} else {
			return false;
		}
	}
	
}
