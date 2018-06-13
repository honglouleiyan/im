package com.jihuiduo.netproxy.clientweb;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

import com.jihuiduo.netproxy.cache.WebAllSession;
import com.jihuiduo.netproxy.server.Connect;
import com.jihuiduo.netproxy.server.filter.BasicIncomingPacket;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;

public class WebHanlder extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(WebHanlder.class);
	    public void sessionCreated(IoSession session) {
	    	logger.info("web端到服务器：session创建");
	    	SocketSessionConfig config = (SocketSessionConfig) session.getConfig();
	    	config.setKeepAlive(true);
	    	config.setWriteTimeout(45);
	    	config.setSoLinger(0);
	    	config.setTcpNoDelay(true);
	    }
	 
	    public void sessionOpened(IoSession session) throws Exception {
	        logger.info("web端到服务器：session打开");
	        Connect connect = new Connect();
	        connect.setSession(session);
	        session.setAttribute(Connect.CONNECTION,connect);
	    }
	 
	    // 当web客户端发送的消息到达时:
		@Override
	    public void messageReceived(IoSession session, Object message) {
			BasicIncomingPacket msg = (BasicIncomingPacket) message;
			logger.info("收到web端的消息:" + msg.getHeaders() + msg.getData());
	    	WebMsgHanlder.messageReceived(session, msg);
	    }
	 
	    // 当信息已经传送给客户端后触发此方法.
	    @Override
	    public void messageSent(IoSession session, Object message) {
	    	BasicOutgoingPacket msg = (BasicOutgoingPacket) message;
	    	logger.info("web端到服务器：信息已经传送给web服务端\n" + msg.getHeaders() + msg.getData());
	    	try {
				super.messageSent(session, message);
			} catch (Exception e) {
				logger.error("发送消息异常：" + e);
			}
	    }
	 
	    // 当一个客户端关闭时
	    @SuppressWarnings("unchecked")
		@Override
	    public void sessionClosed(IoSession session) {
	    	logger.info("web端到服务器：和服务端连接断开" + session);
	        WebAllSession webAllSession = WebAllSession.getInstance();
	        String address = (String) webAllSession.getKey(session);
	        webAllSession.remove(address);
	        if(StringUtils.isNotBlank(address)) {
	        	String ip  = address.split(":")[0];
	        	int port  = Integer.valueOf(address.split(":")[1]);
	        	WebAllServer webAllServer = WebAllServer.getInstance();
	        	int weight = webAllServer.getServer(address).getWeight();
	        	webAllServer.getServerList().remove(webAllServer.getServer(address));
	        	new Thread(new WebConnect(ip,port,weight)).start();
	        }
	    }
	 
	    // 当连接空闲时触发此方法.
	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status) {
	        logger.info("web端到服务器：连接空闲");
	        try {
				super.sessionIdle(session, status);
			} catch (Exception e) {
				logger.error("空闲异常：" + e);
			}
	    }
	 
	    // 当接口中其他方法抛出异常未被捕获时触发此方法
	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause) {
	    	logger.error("其他方法抛出异常:" + cause.getMessage(),cause);
	    	try {
				super.exceptionCaught(session, cause);
			} catch (Exception e) {
				logger.error("其他方法抛出异常：" + e);
			}
	    }
}
