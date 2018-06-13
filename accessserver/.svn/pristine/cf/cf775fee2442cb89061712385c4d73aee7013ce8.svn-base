package com.jihuiduo.netproxy.start;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import com.jihuiduo.netproxy.clientim.ClientManage;
import com.jihuiduo.netproxy.server.ServerManage;


/**
 * 消息服务器入口类
 */
public class ServerStart {
	/**
	 * 日志
	 */
    public static Logger logger  =  Logger.getLogger(ServerStart.class );
	
	public static void main(String[] args) {
		try {
			SystemInitService initService = SystemInitService.getInstance();
			// 加载日志配置文件位置
			initService.loadLog4j();
			 
			// 确认缓存启动正常
			initService.loadRedis();
			
			// 确认mina连接信息正常
			initService.loadStart();
			
			initService.loadMq();
			
			new ServerStart().start();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("---启动消息服务---" + sdf.format(new Date()));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("启动消息服务发生错误 : " + e.getMessage(), e);
			System.exit(0);
		}
		
	}
	public void start() {
		//启动netproxy 服务端
		new Thread(new ServerManage()).start();
//		//启动netproxy 客户端 连接 Im服务器
		new Thread(new ClientManage()).start();
//		//启动netproxy 服务端 连接web服务器
//		new Thread(new WebManage()).start();
	}
}
