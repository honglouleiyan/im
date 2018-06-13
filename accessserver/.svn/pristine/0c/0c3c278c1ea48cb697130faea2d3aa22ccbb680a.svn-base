package com.jihuiduo.netproxy.clientim;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.jihuiduo.netproxy.server.filter.TcpServerCodecFactory;

public class NioImClient {
	private static NioImClient single=null;  
	private NioSocketConnector connector;
	public static NioImClient getInstance() {  
        if (single == null) {    
       	 synchronized(NioImClient.class){
       		 single = new NioImClient();  
       	 }
        }    
       return single;  
	}  
	public NioImClient(){
		connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));//指定编码过滤器 
			   
		connector.setHandler(new ClientHanlder());
		connector.setConnectTimeoutMillis(60000);
	}
	public NioSocketConnector getConnector() {
		return connector;
	}
	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}

}
