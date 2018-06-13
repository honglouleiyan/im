package com.jihuiduo.netproxy.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.jihuiduo.netproxy.server.filter.TcpServerCodecFactory;
import com.jihuiduo.netproxy.utils.ReadXml;


public class ServerManage implements Runnable{
	private static final Logger logger = Logger.getLogger(ServerManage.class);
	@Override
	public void run() {
		logger.info("启动监听 " + ReadXml.getlocalMonitorPort());

		    SocketAcceptor acceptor = new NioSocketAcceptor();
		try {
		    acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));//指定编码过滤器 
		    acceptor.getFilterChain().addLast("executor", new ExecutorFilter(new OrderedThreadPoolExecutor()));
		    acceptor.setHandler( new ServerHandler() );//指定业务逻辑处理器 
		    acceptor.setDefaultLocalAddress( new InetSocketAddress(ReadXml.getlocalMonitorPort()) );//设置端口号 
	        final int idleTime = ReadXml.getIdleTime();
	        if(idleTime > 0) {
	        	acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, idleTime);
	        }
			acceptor.bind();
		} catch (IOException e) {
			logger.error("启动错误:" + e);
			acceptor.dispose();
		}
	}
    
   
}
