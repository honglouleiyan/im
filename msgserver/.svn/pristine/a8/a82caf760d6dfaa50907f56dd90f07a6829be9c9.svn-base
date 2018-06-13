package com.jihuiduo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 系统配置文件(XML)读取类
 */
public class SystemConfig {
	
	private static SystemConfig instance;
	
	private Document document;
	
	public static SystemConfig getInstance() {
		if(instance == null) {
			synchronized(SystemConfig.class){
				if(instance == null){
					instance = new SystemConfig();
				}
			}
		}
		return instance;
	}
	
	private SystemConfig() {
		
	}
	
	public void initialize(String path) throws Exception {
		SAXReader reader = new SAXReader();
		this.document = reader.read(new File(path));
		if (document == null) {
			throw new Exception("读取XML配置文件时发生错误, document == null");
		}
	}
	
	/**
	 * getSingleNode
	 * 
	 * @param key
	 * @return
	 */
	private String getSingleNode(String key) {
		String value = document.selectSingleNode(key).getText();
		return StringUtils.trimToEmpty(value);
	}
	
	/**
	 * getNodes
	 * 
	 * @param key
	 * @return
	 */
	private List<String> getNodes(String key) {
		List<String> list = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<Node> nodes = document.selectNodes(key);
		if (nodes == null) {
			return null;
		}
		for(Node node : nodes) {
			String text = node.getText();
			String value = StringUtils.trimToEmpty(text);
			list.add(value);
		}
		return list;
	}
	
	/**
	 * Redis Host
	 */
	public static String getRedisHost() {
		return instance.getSingleNode("system/redis/host");
	}
	
	/**
	 * Redis Port
	 */
	public static int getRedisPort() {
		return Integer.parseInt(instance.getSingleNode("system/redis/port"));
	}
	
	/**
	 * Redis Password
	 */
	public static String getRedisPassword() {
		return instance.getSingleNode("system/redis/password");
	}
	
	/**
	 * Redis Timeout
	 */
	public static int getRedisTimeout() {
		return Integer.parseInt(instance.getSingleNode("system/redis/timeout"));
	}
	
	/**
	 * Redis Max Active
	 */
	public static int getRedisMaxActive() {
		return Integer.parseInt(instance.getSingleNode("system/redis/maxActive"));
	}
	
	/**
	 * Redis Max Idle
	 */
	public static int getRedisMaxIdle() {
		return Integer.parseInt(instance.getSingleNode("system/redis/maxIdle"));
	}
	
	/**
	 * Redis Max Wait
	 */
	public static int getRedisMaxWait() {
		return Integer.parseInt(instance.getSingleNode("system/redis/maxWait"));
	}
	
	/**
	 * Redis TestOn Borrow
	 */
	public static boolean getRedisTestOnBorrow() {
		return Boolean.valueOf(instance.getSingleNode("system/redis/testOnBorrow"));
	}
	
	/**
	 * Redis TestOn Return
	 */
	public static boolean getRedisTestOnReturn() {
		return Boolean.valueOf(instance.getSingleNode("system/redis/testOnReturn"));
	}

	/**
	 * APNS Host
	 */
	public static String getApnsHost() {
		return instance.getSingleNode("system/apns/host");
	}
	
	/**
	 * APNS Test Host
	 */
	public static String getApnsTestHost() {
		return instance.getSingleNode("system/apns/testHost");
	}
	
	/**
	 * APNS Port
	 */
	public static int getApnsPort() {
		return Integer.parseInt(instance.getSingleNode("system/apns/port"));
	}
	
	/**
	 * APNS Test Port
	 */
	public static int getApnsTestPort() {
		return Integer.parseInt(instance.getSingleNode("system/apns/testPort"));
	}
	
	/**
	 * APNS Certificate Name
	 */
	public static String getApnsCertificateName() {
		return instance.getSingleNode("system/apns/certificateName");
	}
	
	/**
	 * APNS Test Certificate Name
	 */
	public static String getApnsTestCertificateName() {
		return instance.getSingleNode("system/apns/testCertificateName");
	}
	
	/**
	 * APNS Certificate Password
	 */
	public static String getApnsCertificatePassword() {
		return instance.getSingleNode("system/apns/certificatePassword");
	}
	
	/**
	 * APNS Test Certificate Password
	 */
	public static String getApnsTestCertificatePassword() {
		return instance.getSingleNode("system/apns/testCertificatePassword");
	}
	
	/**
	 * APNS Test On
	 */
	public static boolean getApnsTestOn() {
		return Boolean.valueOf(instance.getSingleNode("system/apns/testOn"));
	}
	
	/**
	 * JPush AppKey
	 */
	public static String getJPushAppKey() {
		return instance.getSingleNode("system/jpush/appKey");
	}
	
	/**
	 * JPush Test AppKey
	 */
	public static String getJPushTestAppKey() {
		return instance.getSingleNode("system/jpush/testAppKey");
	}
	
	/**
	 * JPush Master Secret
	 */
	public static String getJPushMasterSecret() {
		return instance.getSingleNode("system/jpush/masterSecret");
	}
	
	/**
	 * JPush Test Master Secret
	 */
	public static String getJPushTestMasterSecret() {
		return instance.getSingleNode("system/jpush/testMasterSecret");
	}
	
	/**
	 * JPush Test On
	 */
	public static boolean getJPushTestOn() {
		return Boolean.valueOf(instance.getSingleNode("system/jpush/testOn"));
	}
	
	/**
	 * 获取接入服务器组列表
	 */
	public static List<String> getAccessServerList() {
		return instance.getNodes("system/accessServers/server");
	}
	
	/**
	 * 获取业务服务器组列表
	 */
	public static List<String> getBusinessServerList() {
		return instance.getNodes("system/businessServers/server");
	}
	
}
