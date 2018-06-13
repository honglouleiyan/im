package com.jihuiduo.netproxy.http;

import java.util.List;



import org.apache.log4j.Logger;

import com.jihuiduo.netproxy.cache.WebCache;
import com.jihuiduo.netproxy.clientweb.WebAllServer;
import com.jihuiduo.netproxy.server.ClientInfo;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;
import com.jihuiduo.netproxy.utils.ReadXml;

public class RequestHttpForWeb implements Runnable{
	private static final Logger logger = Logger.getLogger(RequestHttpForIm.class);
	private String userId;
	private String hearder;
	private String datafromapp;
	public RequestHttpForWeb(String userId,String hearder,String datafromapp){
		this.userId=userId;
		this.hearder=hearder;
		this.datafromapp=datafromapp;
	}
	@SuppressWarnings("unchecked")
	public void run() {
		logger.error("web服务器断开，进入消息重发线程");
		try {
			Thread.sleep(ReadXml.getwebouttime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebAllServer webAllServer = WebAllServer.getInstance();
		List<ClientInfo> clientInfos = webAllServer.getServerList();
		if(clientInfos != null && clientInfos.size() > 0){
			ClientInfo client = WebAllServer.getInstance().getBestServer();
			WebCache.getInstance().put(userId, client.getSession());
			client.getSession().write(new BasicOutgoingPacket(hearder, datafromapp));
		}
		logger.error("web服务器断开，规定时间重发失败，丢弃消息");
	}
	public static String web(){
		StringBuffer sb= new StringBuffer();
		sb.append("GET /test HTTP/1.1").append("\r\n");
		sb.append("Host: localhost:5333").append("\r\n");
		sb.append("Connection: keep-alive").append("\r\n");
		sb.append("Cache-Control: max-age=0").append("\r\n");
		sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").append("\r\n");
		sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36").append("\r\n");
		sb.append("Accept-Encoding: gzip, deflate, sdch").append("\r\n");
		sb.append("Accept-Language: zh-CN,zh;q=0.8").append("\r\n");
		sb.append("\r\n");
		return sb.toString();
	}
	
	public static String connectOuttime(){
		StringBuffer sb= new StringBuffer();
		return sb.toString();
	}
	
}
