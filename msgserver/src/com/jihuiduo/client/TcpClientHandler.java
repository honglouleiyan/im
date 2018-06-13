package com.jihuiduo.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 客户端IoHandler
 */
public class TcpClientHandler extends IoHandlerAdapter {
	private int i;
	
	public TcpClientHandler(int i) {
		this.i = i;
	}
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger("test");
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("---出现错误---"+cause.getMessage(), cause);
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		logger.debug("---第"+i+"个客户端接收数据---" + message.toString());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.debug("---发送数据---" + session.toString());
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.debug("---连接关闭---" + session.toString());
		super.sessionClosed(session);
		session.close(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("---创建连接---" + session.toString());
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("---连接闲置---" + session.toString());
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("---打开连接---" + session.toString());
		super.sessionOpened(session);
	}

}

