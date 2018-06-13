package com.jihuiduo.client;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.codec.TcpServerCodecFactory;
import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.http.HttpMethod;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.http.HttpRequestHeader;
import com.jihuiduo.msgserver.protocol.http.HttpVersion;

/**
 * 客户端(使用TCP协议)
 */
public class TcpClient implements Runnable {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger("test");
	
	private int i;
	
	public TcpClient(int i) {
		this.i = i;
	}
	
	public void run() {
		final IoConnector connector = new NioSocketConnector();
		
		connector.setHandler(new TcpClientHandler(i));
		
		// 添加日志过滤器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		// 添加编解码过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TcpServerCodecFactory()));
		
		connector.getFilterChain().addLast("executor", new ExecutorFilter(new OrderedThreadPoolExecutor()));
		
		// 超时设置
		connector.setConnectTimeoutMillis(600*1000);
		
		try {
			String ip = "115.159.47.200";
//			String ip = "192.168.3.17";
			int port = 5222;
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ip, port));

			System.out.println("client start........." + i);
			
			// 阻塞方式
			// 等待连接创建完成
			connectFuture.awaitUninterruptibly();
			 IoSession session = connectFuture.getSession();
			 int user_id = 11000+i;
			 
			 HttpPacket heartbeatPacket = createHeartbeatPacket(user_id);
			 session.write(heartbeatPacket);
			 HttpPacket outgoingPacket = createMessage(user_id);
			session.write(outgoingPacket);
			 
			// 等待连接断开
//			 CloseFuture closeFuture = session.close(true);
//			 closeFuture.awaitUninterruptibly();
			 
			 
//			 connector.dispose();
			
			
			 
			// 异步方式
//			 connectFuture.addListener(new IoFutureListener<ConnectFuture>() {
//
//				public void operationComplete(ConnectFuture connectFuture) {
//					// TODO Auto-generated method stub
//					IoSession session = connectFuture.getSession();
//					
//					BasicPacket outgoingPacket = createMessage();
//					session.write(outgoingPacket);
//				}
//				
//			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public HttpPacket createHeartbeatPacket(int i) {
		String data = "{\"user_id\":\""+i+"\"}";
		
		HttpVersion httpVersion = HttpVersion.HTTP_1_1;
		HttpMethod httpMethod = HttpMethod.POST;
		String requestPath = "/v1/im/heartbeat";
		String queryString = "seq=41FF447D25FA493700000003&access_token=**********************";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Host", "182.254.215.187:333");
		headers.put("Connection", "Keep-Alive");
		headers.put("User-Agent", "TestByLuoLiang/1.0");
		headers.put("Accept", "*/*");
		headers.put("Accept-Charset", "UTF-8");
		headers.put("Accept-Encoding", "gzip");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Content-Type", "application/json");
		headers.put("Content-Length", String.valueOf(data.getBytes(Constants.CHARSET).length));
		
		HttpRequestHeader requestHeader = new HttpRequestHeader(httpVersion, httpMethod, requestPath, queryString, headers);

		HttpPacket outgoingPacket = new HttpPacket();
		// outgoingPacket.setHeaders(requestHeader.toString());
		// outgoingPacket.setData(data);
		outgoingPacket.setPacket(requestHeader.toString() + data);
		
		return outgoingPacket;
	}
	
	/**
	 * 创建Message
	 */
	public HttpPacket createMessage(int i) {
		String data = "{\"message_info\":[{\"message\":\"硝化细菌"+i+"\",\"time\":1434331245923,\"dst_user_id\":[\""+(11000+(499-(i-11000)))+"\"],\"priority\":0,"
				+ "\"att\":[{\"msgtype\":1,\"totaltime\":\"0\"}],\"state\":0,\"msg_id\":\"41FF447D25FA493700000003\","
				+ "\"user_id\":\""+i+"\",\"type\":1,\"offline\":0}]}";
		
		HttpVersion httpVersion = HttpVersion.HTTP_1_1;
		HttpMethod httpMethod = HttpMethod.POST;
		String requestPath = "/v1/im";
		String queryString = "seq=41FF447D25FA493700000003&access_token=**********************";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Host", "182.254.215.187:333");
		headers.put("Connection", "Keep-Alive");
		headers.put("User-Agent", "TestByLuoLiang/1.0");
		headers.put("Accept", "*/*");
		headers.put("Accept-Charset", "UTF-8");
		headers.put("Accept-Encoding", "gzip");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Content-Type", "application/json");
		headers.put("Content-Length", String.valueOf(data.getBytes(Constants.CHARSET).length));
		
		HttpRequestHeader requestHeader = new HttpRequestHeader(httpVersion, httpMethod, requestPath, queryString, headers);

		HttpPacket outgoingPacket = new HttpPacket();
		// outgoingPacket.setHeaders(requestHeader.toString());
		// outgoingPacket.setData(data);
		outgoingPacket.setPacket(requestHeader.toString() + data);
		return outgoingPacket;
	}
	
	public HttpPacket getTestData() {
		StringBuffer sb = new StringBuffer();
		sb.append("GET / HTTP/1.1");
		sb.append("\r\n");
		sb.append("Host: www.baidu.com");
		sb.append("\r\n");
		sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
		sb.append("\r\n");
		sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		sb.append("\r\n");
		sb.append("Accept-Language: zh-CN");
		sb.append("\r\n");
		sb.append("Accept-Encoding: gzip, deflate");
		sb.append("\r\n");
		sb.append("Connection: Keep-Alive");
		sb.append("\r\n");
		sb.append("\r\n");
		
		HttpPacket basicOutgoingPacket = new HttpPacket();
		// basicOutgoingPacket.setHeaders(sb.toString());
		// basicOutgoingPacket.setData("");
		basicOutgoingPacket.setPacket(sb.toString());
		
		return basicOutgoingPacket;
	}

	
}
