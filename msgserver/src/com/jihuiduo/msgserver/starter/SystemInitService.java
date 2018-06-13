package com.jihuiduo.msgserver.starter;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jihuiduo.biz.message.MessageQueueService;
import com.jihuiduo.biz.notification.NotificationMessageHandler;
import com.jihuiduo.config.SystemConfig;
import com.jihuiduo.config.SystemGlobals;
import com.jihuiduo.database.DbConnectionManager;
import com.jihuiduo.redis.JedisFactory;

/**
 * 系统初始化服务
 */
public class SystemInitService {
	/**
	 * 单例模式
	 */
	private static SystemInitService instance;
	
	private String baseDir;
	
	private ApplicationContext applicationContext;
	
	private SystemInitService() {
		
	}
	
	/**
	 * 单例模式
	 */
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
	 * 定位BaseDir
	 */
	private void locateBaseDir() {
		// 获取当前用户目录路径, 若启动脚本所在目录发生变化, 则该值也会相应变化
		String userDir = System.getProperty("user.dir");
		// 此时日志配置文件还未完成初始化, 故用System.out
		System.out.println("系统属性 user.dir : " + userDir);
		// 获取当前classpath的绝对路径
		ClassLoader loader = getParentClassLoader();
		System.out.println("ClassLoader : " + loader);
		URL url = loader.getResource("");
		System.out.println("Resource URL : " + url);
		String classpath = url.getPath();
		System.out.println("当前classpath的绝对路径 : " + classpath);
		// 设置工程根目录路径属性
		// 使用classpath而不是userDir
		if (!classpath.endsWith("/bin/") && !classpath.endsWith("/build/") && !classpath.endsWith("/lib/")) {
			baseDir = classpath;
		} else {
			baseDir = classpath + "../";
		}
		System.setProperty("BaseDir", baseDir);
		System.out.println("设置系统属性 BaseDir : " + baseDir);
	}
	
	/**
	 * 加载日志配置文件
	 */
	private void loadLog4j() throws Exception {
		PropertyConfigurator.configure(getConfDir() + "log4j.properties");
	}
	
	/**
	 * 加载全局系统属性配置文件
	 */
	private void loadSystemGlobals() throws Exception {
		SystemGlobals.getInstance().initialize(getConfDir() + "SystemGlobals.properties");
	}
	
	/**
	 * 加载系统配置文件(XML)
	 */
	private void loadSystemConfig() throws Exception {
		SystemConfig.getInstance().initialize(getConfDir() + "SystemConfig.xml");
	}
	
	/**
	 * 加载数据库配置文件
	 */
	private void loadDB() throws Exception {
		DbConnectionManager.getInstance().initialize(getConfDir() + "proxool.xml", "msgserver");
	}
	
	/**
	 * 加载Redis
	 */
	private void loadRedis() throws Exception {
		JedisFactory.getInstance().initialize();
	}
	
	/**
	 * 通过SpringFramework加载Redis
	 */
	private void loadRedisWithSpringFramework() throws Exception {
		// String fileName = getConfDir() + "spring_redis.xml";
		// ApplicationContext context = new ClassPathXmlApplicationContext ("file:"+fileName);
		// context.getBean("redisCacheService");
	}
	
	/**
	 * 加载Spring Framework配置文件
	 */
	private void loadSpringFrameworkConfig() throws Exception {
		String fileName = getConfDir() + "spring_context.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext ("file:"+fileName);
		this.applicationContext = context;
	}
	
	/**
	 * 启动初始化服务
	 */
	public void initialize() throws Exception {
		// 定位BaseDir
		locateBaseDir();
		// 加载日志配置文件位置
		loadLog4j();
		
		// 加载配置文件
		loadSystemGlobals();
		loadSystemConfig();
		
		// 初始化业务服务
		initBizService();
	}
	
	/**
	 * 初始化业务服务
	 * 
	 * @throws Exception
	 */
	private void initBizService() throws Exception {
		// 确认数据库启动正常
		loadDB();
		
		// 确认缓存启动正常
		loadRedis();
		
		// 加载Spring Framework配置文件
		loadSpringFrameworkConfig();
		
		// 启动消息队列线程
		MessageQueueService.getInstance();
		// 启动消息推送服务
		NotificationMessageHandler.getInstance();
	}
	
	/**
	 * 获取ClassLoader
	 */
	private ClassLoader getParentClassLoader() {
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null) {
            parent = this.getClass().getClassLoader();
            if (parent == null) {
                parent = ClassLoader.getSystemClassLoader();
            }
        }
        return parent;
    }
	
	public static ApplicationContext getApplicationContext() {
		return instance.applicationContext;
	}
	
	/**
	 * 获取conf文件目录路径
	 * 
	 */
	public static String getConfDir() {
		return instance.baseDir + "conf/";
	}
	
	/**
	 * 获取bin文件目录路径
	 * 
	 */
	public static String getBinDir() {
		return instance.baseDir + "bin/";
	}
	
	/**
	 * 获取lib文件目录路径
	 * 
	 */
	public static String getLibDir() {
		return instance.baseDir + "lib/";
	}
	
	/**
	 * 获取logs文件目录路径
	 * 
	 */
	public static String getLogsDir() {
		return instance.baseDir + "logs/";
	}
	
	/**
	 * 获取根目录路径
	 * 
	 */
	public static String getBaseDir() {
		return instance.baseDir;
	}

}
