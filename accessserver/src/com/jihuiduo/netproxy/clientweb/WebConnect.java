package com.jihuiduo.netproxy.clientweb;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.jihuiduo.netproxy.cache.WebAllSession;
import com.jihuiduo.netproxy.server.ClientInfo;
import com.jihuiduo.netproxy.utils.ReadXml;

public class WebConnect implements Runnable{
	private static final Logger logger = Logger.getLogger(WebConnect.class);
	private String ip;
	private int port;
	private int weight;
	public WebConnect(String ip,int port,int weight){
		this.ip=ip;
		this.port=port;
		this.weight=weight;
	}
	@Override
	public void run() {
		while (true) {
			logger.info("创建Web连接");
			try {
//				NioSocketConnector connector = new NioSocketConnector();
//				 
//			    DefaultIoFilterChainBuilder chain = connector.getFilterChain();
//			    chain.addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));//指定编码过滤器 
//			    connector.setHandler(new WebHanlder());
//			    connector.setConnectTimeoutMillis(60000);
				NioSocketConnector connector = NioWebClient.getInstance().getConnector();
			    ConnectFuture cf = connector.connect(new InetSocketAddress(ip,port));
			
			    cf.awaitUninterruptibly();
			    WebAllSession.getInstance().put(ip+":"+port, cf.getSession());
				ClientInfo serverInfo = new ClientInfo(ip+":"+port, weight, WebAllSession.getInstance().get(ip+":"+port));
				WebAllServer.getInstance().add(serverInfo);
				IoSession session = cf.getSession();
				
				if (session.isConnected()) {  
					logger.info("连接[" + ip + ":" + port + "]成功"); 
					break;  
				}
//			    cf.getSession().getCloseFuture().awaitUninterruptibly();
//			    connector.dispose();
			} catch (Exception e) {
				logger.error("连接Web服务器异常",e);
				try {
					Thread.sleep(ReadXml.getwebconnectionTime());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
