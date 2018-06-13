package com.jihuiduo.server;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.biz.message.MessageRouter;
import com.jihuiduo.config.SystemConfig;
import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.Connection;
import com.jihuiduo.msgserver.Session;
import com.jihuiduo.msgserver.SessionManager;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;

/** 
 * TCP服务器消息处理类
 */
public class TcpServerHandler extends IoHandlerAdapter {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);
	/**
	 * Session
	 */
	private static final String SESSION = "SESSION";
	/**
	 * 远程服务器IP地址和端口号
	 */
	private static final String REMOTE_SERVER = "REMOTE_SERVER";
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error(session.toString() + " 发生错误 : " + cause.getMessage(), cause);
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);

		String key = (String) session.getAttribute(REMOTE_SERVER);

		HttpPacket recv = (HttpPacket)message;
		logger.debug(session.toString() + " 收到的信息 : \r\n" + recv.toString());	
		
		try {
			MessageRouter.getInstance().handleMessage(recv, key);
		} catch (Exception e) {
			logger.error("处理收到的消息出现错误 : " + e.getMessage(), e);
			
			// 发送服务器内部错误提示返回
			HttpPacket packet = createInternalServerErrorResponse();
			session.write(packet);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.debug(session.toString() + " 发送信息 : \r\n" + message);
		super.messageSent(session, message);	
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// 若远程客户端关闭连接, 则此时无法通过IoSession获取远程主机的IP地址和端口号
		logger.debug("关闭连接 : " + session.toString());
		
		// 移除 IoSession
		removeIoSession(session);
		
		super.sessionClosed(session);
		session.close(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("创建连接 : " + session.toString());
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("连接空闲 : " + session.toString());
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("打开连接 : " + session.toString());
		super.sessionOpened(session);
		
		// 添加 IoSession
		addIoSession(session);
	}
	
	/**
	 * 添加客户端的IoSession
	 * 
	 * @param session
	 */
	private void addIoSession(IoSession session) {
		// 远程主机的IP地址和端口号
		InetSocketAddress inetSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		// ip地址
		String ip = inetSocketAddress.getAddress().getHostAddress();
		// 端口号
		int port = inetSocketAddress.getPort();
		String key = ip + ":" + port;
		
		Connection connection = new Connection(session);
		Session localSession = SessionManager.getInstance().createServerSession(connection);
		// 把关键参数放置在IoSession中
		session.setAttribute(SESSION, localSession);
		session.setAttribute(REMOTE_SERVER, key);
		
		// 判断远程服务器类别
		List<String> accessServerIpList = SystemConfig.getAccessServerList();
		// List<String> businessServerIpList = SystemConfig.getBusinessServerList();
		if (accessServerIpList.contains(ip)) {
			SessionManager.getInstance().addAccessServerSession(key, localSession);
			logger.debug("添加 AccessServerSession : " + key);
		} else {
			SessionManager.getInstance().addBusinessServerSession(key, localSession);
			logger.debug("添加 BusinessServerSession : " + key);
		}
		
	}
	
	/**
	 * 移除客户端的IoSession
	 * 
	 * @param session
	 */
	private void removeIoSession(IoSession session) {
		
		String key = (String) session.getAttribute(REMOTE_SERVER);
		
		SessionManager.getInstance().removeAccessServerSession(key);
		SessionManager.getInstance().removeBusinessServerSession(key);
		
		logger.debug("移除 Session : " + key);
	}
	
	/**
	 * 创建服务器内部错误回应
	 * 
	 */
	private HttpPacket createInternalServerErrorResponse() {
		String text = "{\"errcode\":500,\"errmsg\":\"Internal Server Error\"}";
		
		HttpPacket packet = new HttpPacket();
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP/1.1 500 Internal Server Error");
		sb.append("\r\n");
		sb.append("Date: " + Constants.getCurrentHttpDatetime());
		sb.append("\r\n");
		sb.append("Content-Type: text/html");
		sb.append("\r\n");
		try {
			sb.append("Content-Length: " + text.getBytes(Constants.CHARSET).length);
		} catch (Exception e) {
			sb.append("Content-Length: " + text.getBytes().length);
		}
		sb.append("\r\n");
		sb.append("Connection: Close");
		sb.append("\r\n");
		sb.append("Server: IMServer");
		sb.append("\r\n");
		sb.append("\r\n");
		
		// packet.setHeaders(sb.toString());
		// packet.setData(text);
		
		packet.setPacket(sb.toString() + text);
		
		return packet;
	}

}
