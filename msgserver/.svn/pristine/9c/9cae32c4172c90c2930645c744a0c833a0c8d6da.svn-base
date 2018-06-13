package com.jihuiduo.msgserver.starter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.constant.Constants;
import com.jihuiduo.server.TcpServer;

/**
 * 消息服务器入口类
 */
public class ServerStarter {
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServerStarter.class);
	
	public static void main(String[] args) {
		try {
			SystemInitService initService = SystemInitService.getInstance();
			// 启动初始化服务
			initService.initialize();
			
			// 开启端口监听
			new Thread(new TcpServer()).start();
			
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATETIME_FORMAT);
			logger.info("---启动消息服务---" + sdf.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("启动消息服务发生错误 : " + e.getMessage(), e);
			System.exit(0);
		}
		
	}

}
