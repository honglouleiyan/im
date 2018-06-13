package com.jihuiduo.netproxy.utils;

import java.util.UUID;


/**
 * 序列号工具类
 */
public class SequenceUtil {
	private static int count = 0;
	
	public static String createSequence(int autoincrement) {
		
		long uuidLong = Math.abs(UUID.randomUUID().getMostSignificantBits());
		byte[] uuidByte = ConversionUtil.longToByte8(uuidLong);

		byte[] autoByte = ConversionUtil.intToByteArray(autoincrement);

		byte[] byteArray = new byte[uuidByte.length + autoByte.length];
		System.arraycopy(uuidByte, 0, byteArray, 0, uuidByte.length);
		System.arraycopy(autoByte, 0, byteArray, uuidByte.length, autoByte.length);
		
		String hex = ConversionUtil.bytesToHexString(byteArray);
		
		return hex;
	}
	
	public static int getCount() {
		return count+1;
	}
	
	public static void main(String[] args) {
		
	}

}
