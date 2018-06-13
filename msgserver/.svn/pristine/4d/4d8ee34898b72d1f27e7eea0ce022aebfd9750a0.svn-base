package com.jihuiduo.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.codec.TcpServerCodecFactory;
import com.jihuiduo.config.SystemGlobals;
import com.jihuiduo.constant.Constants;

/**
 * 服务器端(使用TCP协议)
 */
public class TcpServer extends Thread {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);
	
	@Override
	public void run() {
		final IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new TcpServerHandler());
		
		// 添加日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		
		// 添加\r\n编解码过滤器
		// Charset charset = Charset.forName(ConstantValue.CHARSET_NAME);
		// TextLineCodecFactory codecFactory = new TextLineCodecFactory(charset, "\r\n", "\r\n");
		// acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		
		// 添加自定义编解码过滤器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));
		
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter(new OrderedThreadPoolExecutor()));
		
		// IoSession配置
//		IoSessionConfig config = acceptor.getSessionConfig();
//		config.setReadBufferSize(4096);
//		config.setIdleTime(IdleStatus.BOTH_IDLE, 60);
		try {
			int port = SystemGlobals.getMessageServerListenPort();
			acceptor.bind(new InetSocketAddress(port));
			logger.info("消息服务器监听端口 : " + port);
		} catch (IOException e) {
			logger.error("消息服务器监听端口发生错误 : " + e.getMessage(), e);
		}
		
	}

}
