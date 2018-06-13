package com.jihuiduo.msgserver;

import com.jihuiduo.msgserver.protocol.basic.Packet;

/**
 * Session
 *
 * 代表一个服务端和服务端之间的连接, 消息转发基于userId, 而不是Session
 */
public class Session {
	/**
	 * Connection连接
	 */
	private Connection connection;
	
	public Session(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * 转发数据包
	 * 
	 * @param packet
	 */
	public void deliverPacket(Packet packet) {
		connection.deliver(packet);
	}
	
	/**
	 * 获取Connection连接
	 * 
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * 关闭Session
	 */
	public void close() {
		// 关闭连接
		connection.close();
	}

}
