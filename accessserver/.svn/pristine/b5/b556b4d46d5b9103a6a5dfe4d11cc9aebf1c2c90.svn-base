package com.jihuiduo.netproxy.constant;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Constant {
	public static final String CHARSET = "UTF-8";//编码
	public static final int MESSAGESENDRID = 50;//消息发送
	public static final int MESSAGEOFFRID = 51;//消息断开
	public static final int HEARTRID = 51;//心跳
	public static final Charset CHAR_SET = Charset.forName(CHARSET);
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
		DateFormat sdf = new SimpleDateFormat(Constant.RFC_1123_PATTERN, Constant.LOCALE);
		sdf.setTimeZone(Constant.GMT_ZONE);
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
