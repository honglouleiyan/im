package com.jihuiduo.netproxy.server;

import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;

/**
 * @ClassName: NewConnect
 * @Description: 旧的连接管理  用userId绑定设备
 */
public class Connect {
    public static final String CONNECTION = "CONNECTION";
	private String userId;
	private IoSession session;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		
	}
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	public void write(BasicOutgoingPacket data) {
		this.session.write(data);
	}
}
