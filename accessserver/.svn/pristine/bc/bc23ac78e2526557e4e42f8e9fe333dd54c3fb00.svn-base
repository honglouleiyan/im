package com.jihuiduo.netproxy.utils;

/**
 * 数据类型转换工具类
 */
public class ConversionUtil {
	
	/**
	 * byte字节转二进制字符串
	 */
	public static String byteToBinaryString(byte b) {
		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}
	
	/**
	 * 二进制字符串转byte字节
	 */
	public static byte binaryStringToByte(String bString) {
		 byte result=0;
		 for(int i=bString.length()-1,j=0;i>=0;i--,j++){
			 result+=(Byte.parseByte(bString.charAt(i)+"")*Math.pow(2, j));
		 }
		 return result;
	}
	
	/**
	 * byte字节数组转十六进制字符串
	 */
	public static String bytesToHexString(byte[] src) {   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString().toUpperCase();   
	}
	
	/**
	 * 十六进制字符串转byte字节数组
	 */
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	} 
	
	private static byte charToByte(char c) {   
	    return (byte) "0123456789ABCDEF".indexOf(c);   
	}
	
	/**
	 * byte字节转十六进制字符串
	 */
	public static String byteToHexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF);    
	     if (hex.length() < 2) {    
	       hex = '0' + hex;    
	     }   
	    return hex.toUpperCase();
	}
	
	/**
	 * 十六进制字符串转byte字节
	 */
	public static byte hexStringToByte(String hexString) {
	    hexString = hexString.toUpperCase(); 
	    char[] hexChars = hexString.toCharArray();   
	    byte d = (byte) (charToByte(hexChars[0]) << 4 | charToByte(hexChars[1]));
	    return d;   
	}
	
	/** 
     * 将16位的short转换成byte数组 
     *  
     * @param s short 
     * @return byte[] 长度为2 
     * */  
    public static byte[] shortToByteArray(short s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }
    
    /**
     * 将两个字节合并成一个16位的short
     */
    public static short byteToShort(byte high, byte low) {
    	short z = (short)(((high & 0xFF) << 8) | (0xFF & low));
    	return z;
    }
    
    /** 
     * 将32位整数转换成长度为4的byte数组 
     *  
     * @param s int 
     * @return byte[] 长度为4
     * */  
    public static byte[] intToByteArray(int s) {  
        byte[] targets = new byte[4];  
        for (int i = 0; i < 4; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }
    
    /**
	 * 将一个long数字转换为8个byte数组组成的数组.
	 */
	public static byte[] longToByte8(long sum) {
		byte[] arr = new byte[8];
		arr[0] = (byte) (sum >> 56);
		arr[1] = (byte) (sum >> 48);
		arr[2] = (byte) (sum >> 40);
		arr[3] = (byte) (sum >> 32);
		arr[4] = (byte) (sum >> 24);
		arr[5] = (byte) (sum >> 16);
		arr[6] = (byte) (sum >> 8);
		arr[7] = (byte) (sum & 0xff);
		return arr;
	}
    
    /**
	 * 将给定的字节数组转换成IPV4的十进制分段表示格式的ip地址字符串
	 * @param addr
	 * @return
	 */
	public static String byteArrayToIpv4Address(byte[] addr) {
		String ip = "";
		for(int i=0; i < addr.length; i++){
			ip += ((addr[i] & 0xFF) + ".");
		}
		return ip.substring(0, ip.length()-1);
	}
	
	/**
	 * 将给定的用十进制分段格式表示的ipv4地址字符串转换成字节数组
	 * @param ip
	 * @return
	 */
	public static byte[] ipv4AddressToByteArray(String ip) {
		byte[] addr = new byte[4];
		String[] strs = ip.split("\\.");
		for(int i = 0; i < strs.length; i++) {
			addr[i] = (byte)Integer.parseInt(strs[i]);
		}
		return addr;
	}
	
	
	/**
	 * 得到int类型数据target在i位的值
	 */
	public static int getBitFromInt(int target, int i) {
		if (i < 1 || i > 32) {
			return -1;
		}
		int temp = target >>> (32-i);
		return temp & 0x01;
		
	}
	
	/**
	 * 无符号byte转int
	 * @param b
	 * @return
	 */
	public static int unsignedByteToInt(byte b) {
		return b & 0xFF;
	}
	
	/**
	 * 无符号short转int
	 * @param s
	 * @return
	 */
	public static int unsignedShortToInt(short s) {
		return s & 0xFFFF;
	}
	
	/**
	 * 无符号int转long
	 * @param i
	 * @return
	 */
	public static long unsignedIntToLong(int i) {
		long temp = i & 0xFFFFFFFFl;
		return temp;
	}
	
	public static void main(String[] args) {
		
	}

}
