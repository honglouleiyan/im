package com.jihuiduo.netproxy.clientim;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.jihuiduo.netproxy.cache.ImAllSession;
import com.jihuiduo.netproxy.server.ClientInfo;
import com.jihuiduo.netproxy.server.filter.TcpServerCodecFactory;

public class ImConnect implements Runnable{
	private Logger logger  =  Logger.getLogger(ImConnect.class);
	NioSocketConnector connector;
	IoSession session;
	private String ip;
	private int port;
	private int weight;
	public ImConnect(String ip,int port,int weight){
		this.ip=ip;
		this.port=port;
		this.weight=weight;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		final InetSocketAddress serverAddress = new InetSocketAddress(ip, port);
		final InetSocketAddress localhostAddress = new InetSocketAddress(ip, port);
		connector = new NioSocketConnector();  //创建连接客户端  
	    connector.setConnectTimeoutMillis(30000); //设置连接超时  
	    connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));  
//	    connector.setDefaultRemoteAddress(new InetSocketAddress(ip, port));
//	    connector.setDefaultLocalAddress(new InetSocketAddress("localhost",ReadXml.getlocalPort()));
	    System.out.println("默认端口：" + connector.getDefaultLocalAddress());
	    //添加处理器  
	    connector.setHandler(new ClientHanlder());   
	     // 添加重连监听  
	   connector.addListener(new IoListener() {  
	   @Override  
	   public void sessionDestroyed(IoSession arg0) throws Exception {  
		   for (;;) {  
			
				   Thread.sleep(3000);  
				   ConnectFuture future = connector.connect(serverAddress,localhostAddress); 
//				     ConnectFuture future = connector.connect();				   
				try {  
				   future.awaitUninterruptibly();// 等待连接创建成功  
				   session = future.getSession();// 获取会话  
				   
				   ImAllSession.getInstance().put(ip+":"+port, future.getSession());
				   ClientInfo serverInfo = new ClientInfo(ip+":"+port, weight, (IoSession)ImAllSession.getInstance().get(ip+":"+port));
				   ImAllServer.getInstance().add(serverInfo);
				   if (session.isConnected()) {  
					   logger.info("断线重连[" + connector.getDefaultRemoteAddress().getHostName() + ":" + connector.getDefaultRemoteAddress().getPort() + "]成功");  
					   break;  
				   }  
			   } catch (Exception ex) {  
				   if(session != null) {
		    		   session.close(true);
		    	   }
				   future.cancel();
//				   logger.info("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());
				   ex.printStackTrace();
			   }  
		   }  
	  }});  
	  for (;;) {  
	   
	    	   ConnectFuture future = connector.connect(serverAddress,localhostAddress); 
//	    	     ConnectFuture future = connector.connect();
	    	 try { 
	    	   future.awaitUninterruptibly(); // 等待连接创建成功    
	    	   session = future.getSession(); // 获取会话     
	    	   ImAllSession.getInstance().put(ip+":"+port, future.getSession());
			   ClientInfo serverInfo = new ClientInfo(ip+":"+port, weight, (IoSession)ImAllSession.getInstance().get(ip+":"+port));
			   ImAllServer.getInstance().add(serverInfo);
	    	   logger.info("连接服务端" + ip + ":" + port + "[成功]" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
	    	   break;  
	       	} catch (RuntimeIoException e) {  
	    	   logger.error("连接服务端" + ip + ":" + port + "失败" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:" + e.getMessage(), e);  
	    	   if(session != null) {
	    		   session.close(true);
	    	   }
	    	   future.cancel();
	    	   try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	       	}  
	  	}  
	}
}
