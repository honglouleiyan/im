package com.jihuiduo.netproxy.clientim;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

import com.jihuiduo.netproxy.cache.ClientCache;
import com.jihuiduo.netproxy.cache.ImAllSession;
import com.jihuiduo.netproxy.server.Connect;
import com.jihuiduo.netproxy.server.filter.BasicIncomingPacket;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;

public class ClientHanlder extends IoHandlerAdapter {
	private Logger logger  =  Logger.getLogger(ClientHanlder.class );
	static Date heartTime = new Date();
	    public void sessionCreated(IoSession session) {
	    	logger.info("和服务端创建连接session");
	    	SocketSessionConfig config = (SocketSessionConfig) session.getConfig();
	    	config.setKeepAlive(true);
	    	config.setWriteTimeout(45);
	    	config.setSoLinger(0);
	    	config.setTcpNoDelay(true);
	    }
	 
	    public void sessionOpened(IoSession session) throws Exception {
	        logger.info("enter sessionOpened");
	        Connect connect = new Connect();
	        connect.setSession(session);
	        session.setAttribute(Connect.CONNECTION,connect);
	    }
	 
	    // 当客户端发送的消息到达时:
	    @Override
	    public void messageReceived(IoSession session, Object message) {
	    	BasicIncomingPacket msg = (BasicIncomingPacket) message;
	        ImMsgHanlder.messageReceived(session, msg);
	    }
	 
	    // 当信息已经传送给客户端后触发此方法.
	    @Override
	    public void messageSent(IoSession session, Object message) {
	    	heartTime = new Date();
	    	BasicOutgoingPacket msg = (BasicOutgoingPacket) message;
	    	logger.debug("信息已经传送给服务端：" + msg.getHeaders() + msg.getData());
	    }
	 
	    // 当一个客户端关闭时
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
	    public void sessionClosed(IoSession session) {
	    	logger.info("和服务端连接断开");
	    	ImAllSession imAllSession = ImAllSession.getInstance();
		    String address = (String) imAllSession.getKey(session);
		    imAllSession.remove(address);
		    ClientCache.getInstance().removeAll(session);
		    if(StringUtils.isNotBlank(address)) {
		    	String ip  = address.split(":")[0];
		    	int port  = Integer.valueOf(address.split(":")[1]);
		    	ImAllServer imAllServer = ImAllServer.getInstance();
	        	int weight = imAllServer.getServer(address).getWeight();
	        	imAllServer.getServerList().remove(imAllServer.getServer(address));
		        new Thread(new ClientConnect(ip,port,weight)).start();
		    }
		    if(session != null){  
	            session.close(true);  
	        }
	    }
	 
	    // 当连接空闲时触发此方法.
	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status) {
	        logger.info("enter sessionIdle");
	    }
	 
	    // 当接口中其他方法抛出异常未被捕获时触发此方法
	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause) {
	    	logger.error("其他方法抛出异常:" + cause.getMessage(),cause);
	    	
	    }
}
