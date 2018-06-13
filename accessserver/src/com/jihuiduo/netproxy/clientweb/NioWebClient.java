package com.jihuiduo.netproxy.clientweb;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.jihuiduo.netproxy.server.filter.TcpServerCodecFactory;

public class NioWebClient {
	private static NioWebClient single=null;  
	private NioSocketConnector connector;
	public static NioWebClient getInstance() {  
        if (single == null) {    
       	 synchronized(NioWebClient.class){
       		 single = new NioWebClient();  
       	 }
        }    
       return single;  
	}  
	public NioWebClient(){
		connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));//指定编码过滤器 
			   
		connector.setHandler(new WebHanlder());
		connector.setConnectTimeoutMillis(60000);
	}
	public NioSocketConnector getConnector() {
		return connector;
	}
	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}

}
