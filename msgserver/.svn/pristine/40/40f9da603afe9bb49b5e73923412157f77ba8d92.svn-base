package com.jihuiduo.util;

import java.util.UUID;

/**
 * 序列号工具类
 */
public class SequenceUtil {
	
	/**
	 * 单例模式
	 */
	private static SequenceUtil instance;
	/**
	 * UUID Long
	 */
	private long uuidLong;
	/**
	 * 自增长
	 */
	private int autoIncrement;
	
	private SequenceUtil() {
		uuidLong = UUID.randomUUID().getMostSignificantBits();
		autoIncrement = 0;
	}
	
	/**
	 * 单例模式
	 */
	public static SequenceUtil getInstance() {
		if (null == instance) {
			synchronized(SequenceUtil.class){
				if(null == instance){
					instance = new SequenceUtil();
				}
			}
		}
		return instance;
	}
	
	public long getUuidLong() {
		if (autoIncrement >= (Integer.MAX_VALUE/2)) {
			uuidLong = UUID.randomUUID().getMostSignificantBits();
			autoIncrement = 0;
		}
		return uuidLong;
	}
	
	public int getAutoIncrement() {
		return ++autoIncrement;
	}
	
	public static String createSequence() {
		long uuidLong = getInstance().getUuidLong();
		int autoIncrement = getInstance().getAutoIncrement();
		byte[] uuidByte = ConversionUtil.longToByte8(uuidLong);
		byte[] autoByte = ConversionUtil.intToByteArray(autoIncrement);

		byte[] byteArray = new byte[uuidByte.length + autoByte.length];
		System.arraycopy(uuidByte, 0, byteArray, 0, uuidByte.length);
		System.arraycopy(autoByte, 0, byteArray, uuidByte.length, autoByte.length);
		
		String hex = ConversionUtil.bytesToHexString(byteArray);
		
		return hex;
	}
	
	public static String createSequence(int autoincrement) {
		
		long uuidLong = UUID.randomUUID().getMostSignificantBits();
		byte[] uuidByte = ConversionUtil.longToByte8(uuidLong);

		byte[] autoByte = ConversionUtil.intToByteArray(autoincrement);

		byte[] byteArray = new byte[uuidByte.length + autoByte.length];
		System.arraycopy(uuidByte, 0, byteArray, 0, uuidByte.length);
		System.arraycopy(autoByte, 0, byteArray, uuidByte.length, autoByte.length);
		
		String hex = ConversionUtil.bytesToHexString(byteArray);
		
		return hex;
	}
	
	
	public static void main(String[] args) {
		
	}

}
