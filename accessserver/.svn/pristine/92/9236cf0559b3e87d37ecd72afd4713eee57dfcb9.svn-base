package com.jihuiduo.netproxy.server;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import com.jihuiduo.netproxy.cache.ClientCache;
import com.jihuiduo.netproxy.cache.PreparatorySessionCache;
import com.jihuiduo.netproxy.cache.DeviceServerCache;
import com.jihuiduo.netproxy.cache.UserServerCache;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;
import com.jihuiduo.netproxy.server.online.DeviceOnline;

public class ServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	    // 当一个新客户端连接后触发此方法.
	    public void sessionCreated(IoSession session) {
	    	logger.info("新客户端连接");
	    	SocketSessionConfig config = (SocketSessionConfig) session.getConfig();
	    	config.setKeepAlive(true);
	    	config.setWriteTimeout(45);
	    	config.setSoLinger(0);
	    	config.setTcpNoDelay(true);
	    }
	 
	    // 当一个客端端连结进入时 @Override
	    public void sessionOpened(IoSession session) throws Exception {
	        logger.info("enter sessionOpened" + session.getRemoteAddress());
	        NewConnect connect = new NewConnect();
	        connect.setSession(session);
	        session.setAttribute(Connect.CONNECTION,connect);
	        PreparatorySessionCache.putSession(session);
	    }
	 
	    // 当app发送的消息到达时:
		@Override
	    public void messageReceived(IoSession session, Object message) {
	    	ServerMsgHandler.messageReceived(session, message);
	    }
	 
	    // 当信息已经传送给客户端后触发此方法.
	    @Override
	    public void messageSent(IoSession session, Object message) {
	    	BasicOutgoingPacket msg = (BasicOutgoingPacket) message;
	    	logger.debug("信息已经传送给客户端:" + msg.getHeaders() + msg.getData());
//	        ServerCache serverCache = ServerCache.getInstance();
//	        Long userId =  (Long) serverCache.getKey(session);
//	        if(userId != null) {
//	        	serverCache.remove(userId);
//	        	WebCache webCache = WebCache.getInstance();
//	        	IoSession webSession = (IoSession) webCache.get(userId);
//	        	if(webSession != null) {
//	        		webCache.remove(userId);
//	        	}
//	        	ClientCache clientCache = ClientCache.getInstance();
//	        	IoSession clientSession = (IoSession) clientCache.get(userId);
//	        	if(clientSession != null) {
//	        		//同事需要通知IM服务器断线
//	        		clientCache.remove(userId);
//	        	}
//	        }
	 
	    }
	 
	    // 当一个客户端关闭时
	    @SuppressWarnings("unchecked")
		@Override
	    public void sessionClosed(IoSession session) {
	    	logger.info("one Clinet Disconnect !" + session.getRemoteAddress());
	    	NewConnect  connect = (NewConnect) session.getAttribute(NewConnect.CONNECTION);
	    	if(connect != null){
		        session.removeAttribute(Connect.CONNECTION);
		    	if(connect.getDevice() != null) {
		    		DeviceServerCache.getInstance().remove(connect.getDevice());
		    		String userId = (String) UserServerCache.getInstance().getKey(connect.getDevice());
		    		if(StringUtils.isNotBlank(userId)) {
		    			UserServerCache.getInstance().remove(userId);
			    		ClientCache.getInstance().remove(userId);
		    		}
		        	DeviceOnline.setUserInfoOffline(connect.getDevice());
		    	}
	    	}
	    }
	 
	    // 当连接空闲时触发此方法.
	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status) {
	        logger.info("连接空闲");
	        session.close(true);
	    }
	 
	    // 当接口中其他方法抛出异常未被捕获时触发此方法
	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause) {
	    	logger.error("其他方法抛出异常:" + cause.getMessage(),cause);
	    }
}
