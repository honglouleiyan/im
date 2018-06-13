package com.jihuiduo.netproxy.clientim;

import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.jihuiduo.netproxy.cache.ImAllSession;
import com.jihuiduo.netproxy.server.ClientInfo;
import com.jihuiduo.netproxy.utils.ReadXml;

public class ClientConnect implements Runnable{
	private Logger logger  =  Logger.getLogger(ClientConnect.class );
	private String ip;
	private int port;
	private int weight;
	public ClientConnect(String ip,int port,int weight){
		this.ip=ip;
		this.port=port;
		this.weight=weight;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		while (true) {
			logger.info("创建Im连接");
//			NioSocketConnector connector = new NioSocketConnector();
			NioSocketConnector connector = NioImClient.getInstance().getConnector();
//			DefaultIoFilterChainBuilder chain = connector.getFilterChain();
//			chain.addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));//指定编码过滤器 
//				   
//			connector.setHandler(new ClientHanlder());
//			connector.setConnectTimeoutMillis(60000);
			ConnectFuture cf = connector.connect(new InetSocketAddress(ip,port),new InetSocketAddress(ReadXml.getlocalPort()));
			try {
				cf.awaitUninterruptibly();
				ImAllSession.getInstance().put(ip+":"+port, cf.getSession());
				ClientInfo serverInfo = new ClientInfo(ip+":"+port, weight, (IoSession)ImAllSession.getInstance().get(ip+":"+port));
				ImAllServer.getInstance().add(serverInfo);
//				cf.getSession().getCloseFuture().awaitUninterruptibly();
				IoSession session = cf.getSession();
				if (session.isConnected()) {  
					logger.info("连接[" + ip + ":" + port + "]成功");
					break;
				}
//				connector.dispose();
			
			} catch (Exception e) {
				logger.error("连接IM服务器异常",e);
				cf.cancel();
				try {
					Thread.sleep(ReadXml.getImconnectionTime());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}

}
