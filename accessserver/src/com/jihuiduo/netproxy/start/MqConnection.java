package com.jihuiduo.netproxy.start;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.jihuiduo.netproxy.mq.JmsSenderService;


public class MqConnection {
	private static MqConnection instance;
	private static ResourceBundle rb;
	public static JmsSenderService jmsSenderService;
	/**
	 * 单例模式
	 */
	public static MqConnection getInstance(String fileName) throws Exception {
		if (null == instance) {
			synchronized(MqConnection.class){
				if(null == instance){
					instance = new MqConnection(fileName);
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("resource")
	public static JmsSenderService getMqService(String fileName){
		ApplicationContext context = new ClassPathXmlApplicationContext ("file:"+fileName);
		jmsSenderService = (JmsSenderService) context.getBean("jmsSenderService");
		return jmsSenderService;
	}
	
	private MqConnection(String fileName) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream(fileName));
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
		if (value == null) {
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
		if (value == null) {
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
		if (value == null) {
			return defaultValue;
		}
		try {
			return Boolean.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
