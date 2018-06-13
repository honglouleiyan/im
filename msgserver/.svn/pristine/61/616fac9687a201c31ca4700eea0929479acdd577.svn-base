package com.jihuiduo.msgserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.msgserver.protocol.basic.Packet;

/**
 * Connection
 * 
 * 管理一个IoSession
 */
public class Connection {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(Connection.class);
	/**
	 * 本地Session
	 */
	private Session session;
	/**
	 * IoSession
	 */
	private IoSession ioSession;
	
	public Connection(IoSession ioSession) {
		this.ioSession = ioSession;
	}
	
	/**
	 * 写入Packet数据包
	 */
	public void deliver(Packet packet) {
		try {
			if (!ioSession.isConnected()) {
				throw new IOException("Connection reset/closed by peer");
			}
		} catch (Exception e) {
			logger.error("发送数据包时发生错误, " + e.getMessage(), e);
		}
		ioSession.write(packet);
		
		// 写数据同步方法和关闭session同步方法
		// WriteFuture writeFuture = ioSession.write(packet);
		// boolean sucess = writeFuture.awaitUninterruptibly(1000);
		// if (sucess) {
		// logger.info("write ok");
		// CloseFuture closeFuture = ioSession.close(true);
		// boolean close = closeFuture.awaitUninterruptibly(1000);
		// if(close) {
		// logger.info("close ok");
		// }
		// }
	}
	
	/**
	 * 初始化本地Session
	 * 
	 */
	public void init(Session session) {
		this.session = session;
	}
	
	/**
	 * 获取本地Session
	 * 
	 */
	public Session getSession() {
		return session;
	}
	
	/**
	 * 获取远程主机IP地址
	 */
	public String getRemoteHostAddress() throws UnknownHostException {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) ioSession.getRemoteAddress();
		return inetSocketAddress.getAddress().getHostAddress();
	}
	
	/**
	 * 获取远程主机名
	 */
	public String getRemoteHostName() throws UnknownHostException {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) ioSession.getRemoteAddress();
        return inetSocketAddress.getAddress().getHostName();
    }
	
	/**
	 * 获取远程主机端口号
	 */
	public int getRemotePort() throws UnknownHostException {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) ioSession.getRemoteAddress();
		return inetSocketAddress.getPort();
	}
	
	/**
	 * 判断连接是否正常
	 * 
	 */
	public boolean isConnected() {
		return ioSession.isConnected();
	}
	
	/**
	 * 关闭连接
	 */
	public void close() {
		// 关闭IoSession
		ioSession.close(true);
	}

}
