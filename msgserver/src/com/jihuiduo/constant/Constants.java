package com.jihuiduo.constant;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 静态常量类
 */
public class Constants {
	
	/**
	 * 协议编解码使用的字符集
	 */
	public static final String CHARSET_NAME = "UTF-8";
	public static final Charset CHARSET = Charset.forName(CHARSET_NAME);
	
	/**
	 * 当前版本号
	 */
	public static final String VERSION = "/v2";
	/**
	 * v1版本号
	 */
	public static final String VERSION_V1 = "/v1";
	/**
	 * v2版本号
	 */
	public static final String VERSION_V2 = "/v2";
	/**
	 * v3版本号
	 */
	public static final String VERSION_V3 = "/v3";
	
	/**
	 * 接入服务器
	 */
	public static final String ACCESS_SERVER = "ACCESS_SERVER";
	
	/**
	 * 业务服务器
	 */
	public static final String BUSINESS_SERVER = "BUSINESS_SERVER";
	
	/**
	 * 简单日期格式
	 */
	public static final String SIMPLE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * HTTP协议日期格式规范
	 */
	private static final String RFC_1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
	
	/**
	 * 美国地区, 区域
	 */
	private static final Locale LOCALE = Locale.US;
	
	/**
	 * GMT时区
	 */
	private static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");
	
	/**
	 * 获取HTTP日期格式的DateFormat
	 * @return
	 */
	private static synchronized DateFormat getHttpDateFormat() {
		DateFormat sdf = new SimpleDateFormat(Constants.RFC_1123_PATTERN, Constants.LOCALE);
		sdf.setTimeZone(Constants.GMT_ZONE);
		return sdf;
	}
	
	/**
	 * 获取当前日期时间(HTTP协议格式规范)
	 * @return
	 */
	public static String getCurrentHttpDatetime() {
		DateFormat sdf = getHttpDateFormat();
		return sdf.format(new Date());
	}
	
	/**
	 * 获取指定的日期时间(HTTP协议格式规范)
	 * @return
	 */
	public static String getHttpDatetime(Date date) {
		DateFormat sdf = getHttpDateFormat();
		return sdf.format(date);
	}
	
}
