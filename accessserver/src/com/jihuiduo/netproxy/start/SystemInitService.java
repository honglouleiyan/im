package com.jihuiduo.netproxy.start;

import java.io.File;
import org.apache.log4j.PropertyConfigurator;
import com.jihuiduo.netproxy.utils.ReadXml;


/**
 * 系统初始化服务
 */
public class SystemInitService {
	
	private static String baseDir;
	
	private static SystemInitService instance;
	
	private SystemInitService() {
		String userDir = System.getProperty("user.dir");
		// 此时日志配置文件还未完成初始化, 故用System.out
//		System.out.println("系统属性 user.dir : " + userDir);
		// 设置工程根目录路径属性
		if (!userDir.endsWith("bin") && !userDir.endsWith("build")) {
			// .bat .sh命令文件在根目录下
			baseDir = userDir;
		} else {
			// .bat .sh命令文件在bin或build目录下
			baseDir = userDir + File.separator + "..";
		}
		
		System.setProperty("BaseDir", baseDir);
//		System.out.println("设置系统属性 BaseDir : " + baseDir);
	}
	
	public static SystemInitService getInstance() {
		if (instance == null) {
			synchronized (SystemInitService.class) {
				if (instance == null) {
					instance = new SystemInitService();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 加载日志配置文件
	 */
	public void loadLog4j() throws Exception {
		PropertyConfigurator.configure(baseDir + "/conf/log4j.properties");
	}
	
	/**
	 * 加载mina连接信息
	 */
	public void loadStart() throws Exception {
		ReadXml.getInstance(baseDir + "/conf/netproxy.xml");
	}
	
	/**
	 * 加载redis数据库配置文件
	 */
	public void loadRedis() throws Exception {
//		RedisConnection.getInstance(baseDir + "/conf/redis_config.properties");
//		RedisConnection.getRedisService(baseDir + File.separator  + "conf"+File.separator+"spring-redis-beans.xml");
		RedisConnection.getRedisService(baseDir + "/conf/spring_redis_beans.xml");
	}
	
	public void loadMq() throws Exception {
//		RedisConnection.getInstance(baseDir + "/conf/redis_config.properties");
//		RedisConnection.getRedisService(baseDir + File.separator  + "conf"+File.separator+"spring-redis-beans.xml");
		MqConnection.getMqService(baseDir + "/conf/spring_activemq.xml");
	}
	
	
	/**
	 * 获取根目录路径
	 */
	public static String getBaseDir() {
		return baseDir;
	}
	
}
