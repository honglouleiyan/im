package com.jihuiduo.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 读取SystemGlobals配置文件
 */
public class SystemGlobals {
	
	private static SystemGlobals instance;
	private static ResourceBundle rb;
	
	/**
	 * 单例模式
	 */
	public static SystemGlobals getInstance() {
		if (null == instance) {
			synchronized(SystemGlobals.class){
				if(null == instance){
					instance = new SystemGlobals();
				}
			}
		}
		return instance;
	}
	
	private SystemGlobals() {
		
	}
	
	public void initialize(String fileName) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream(fileName));
		// ResourceBundle.getBundle("");
		rb = new PropertyResourceBundle(in);
	}

	public static String getValue(String key) {
		return rb.getString(key);
	}
	
	
	public static String getValue(String key, String defaultValue) {
		String value = rb.getString(key);
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		return value;
	}

	public static int getIntValue(String key, int defaultValue) {
		String value = rb.getString(key);
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static float getFloatValue(String key, float defaultValue) {
		String value = rb.getString(key);
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static boolean getBooleanValue(String key, boolean defaultValue) {
		String value = rb.getString(key);
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		try {
			return Boolean.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 消息服务器监听端口号
	 */
	public static int getMessageServerListenPort() {
		return getIntValue("message.server.listen.port", 5222);
	}
	
	
	
	
}
