package com.jihuiduo.netproxy.utils;

import java.util.UUID;

/**
 * @author lihf
 * UUID 工具类
 */
public class UUIDFactory {
	/**
	 * 随机生成一个符合UUID定义但不含'-'的字符串，
	 * 如 84c19de937b04234b4c5e3cd63cda257
	 */
	public static String generateUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 随机生成一个符合UUID定义的字符串，hashCode 值为正
	 */
	public static String generatePositiveUUID(){
		while(true){
			String str = generateUUID();
			if(str.hashCode() > 0)
				return str;
			else
				continue;
		} 
	}
	
	/**
	 * 随机生成一个符合UUID定义的字符串，hashCode 值为负
	 */
	public static String generateNegativeUUID(){
		while(true){
			String str = generateUUID();
			if(str.hashCode() < 0)
				return str;
			else
				continue;
		} 
	}
	
	public static void main(String[] args){
		System.out.println(UUIDFactory.generateUUID());
	}
}
