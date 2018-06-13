package com.jihuiduo.netproxy.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jihuiduo.netproxy.constant.Constant;


public class Utils {
	
	private static Long count = (long) 0;
	public static String createCryptonym() {
		String cryptonym = "cryptonym_" + count.toString();
		count++;
		return cryptonym;
	}
	
	/**
	 * 获取32位流水号 seq
	 * 
	 * @params 需要组成 前四位随机数+后四位自增长的。---需要注意
	 * 
	 * @return
	 */
	public static String createSeq() {
		long uuidLong = Math.abs(UUID.randomUUID().getMostSignificantBits());
		byte[] uuidByte = Utils.longToByte8(uuidLong);

//		int autoincrement = SharedPreferencesManager.getSeqPart();
//		byte[] autoByte = Utils.intToByteArray(autoincrement);
		byte[] autoByte = Utils.intToByteArray(0);
		byte[] byte_3 = new byte[uuidByte.length + autoByte.length];
		System.arraycopy(uuidByte, 0, byte_3, 0, uuidByte.length);
		System.arraycopy(autoByte, 0, byte_3, uuidByte.length, autoByte.length);

		String sendData = Utils.bytesToHexString(byte_3);
		// byte[] xxx = Base64.decode(sendData, 0);

		return sendData;
	}

	
	public static boolean mIsInstallingPackage = false;
	public static File mPackageFile;
	// 短日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATE_FORMAT_YEAR = "yyyy";
	public static String DATE_FORMAT_MONTH = "MM";
	public static String DATE_FORMAT_DAY = "dd";
	public static String DATE_FORMAT_HourMinute = "HH:mm";
	// 长日期格式
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @return 2014-8-16---(yyyy-MM-dd)
	 */
	public static String getTimeForLong(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	public static String getDayForLong(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_DAY);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthForLong(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_MONTH);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getHourMinuteFromLong(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_HourMinute);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convert2long(String date, String format) {
		try {
			// if (StringUtils.isNotBlank(date)) {
			// if (StringUtils.isBlank(format))
			// format = SimpleDateUtil.TIME_FORMAT;

			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.parse(date).getTime();
			// }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static boolean isEmpty(CharSequence chars) {
		return (chars == null || chars.toString().trim().equals(""));
	}




	public static boolean formatEmail(String email) {
		String format = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		if (email.matches(format)) {
			return true;
		} else {
			return false;
		}
	}

	// 判断手机格式是否正确
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断是否全是数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}





	// /////////////////////////////////////////////////////////////////////
	private final static String KEY = "www.douwan.cn";

	private final static Pattern PATTERN = Pattern.compile("\\d+");

	public static String encode(String src) {
		try {
			byte[] data = src.getBytes(Constant.CHARSET);
			byte[] keys = KEY.getBytes();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < data.length; i++) {
				int n = (0xff & data[i]) + (0xff & keys[i % keys.length]);
				sb.append("%" + n);
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}

	public static String decode(String src) {
		if (src == null || src.length() == 0) {
			return src;
		}
		Matcher m = PATTERN.matcher(src);
		List<Integer> list = new ArrayList<Integer>();
		while (m.find()) {
			try {
				String group = m.group();
				list.add(Integer.valueOf(group));
			} catch (Exception e) {
				e.printStackTrace();
				return src;
			}
		}

		if (list.size() > 0) {
			try {
				byte[] data = new byte[list.size()];
				byte[] keys = KEY.getBytes();

				for (int i = 0; i < data.length; i++) {
					data[i] = (byte) (list.get(i) - (0xff & keys[i % keys.length]));
				}
				return new String(data, Constant.CHARSET);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return src;
		} else {
			return src;
		}
	}






	private static String convertMillis2String(long millis) {
		long s = millis / 1000;
		int h = 0;
		int m = 0;
		int hour = 60 * 60;
		int minute = 60;
		if (s > hour) {
			h = (int) (s / hour);
			m = (int) (s % hour / minute);
			s = s % hour % minute;
		} else if (s > minute) {
			m = (int) (s / minute);
			s = s % minute;
		}
		return h + "小时" + m + "分钟" + s + "秒";
	}

	private static String printTime(long millis) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(new Date(millis));
	}

	public static String convertSize2String(int size) {
		String result = null;
		int K = 1024;
		int M = K * 1024;
		int G = M * 1024;
		DecimalFormat fmt = new DecimalFormat("#.##");
		if (size / K < 1) {
			result = size + "K";
		} else if (size / M < 1) {
			result = fmt.format(size * 1.0 / K) + "MB";
		} else if (size / G < 1) {
			result = fmt.format(size * 1.0 / M) + "G";
		} else {
			result = fmt.format(size * 1.0 * K / G) + "G";
		}
		return result;
	}







	/**
	 * 延时
	 * 
	 * @param milis
	 */
	public static void sleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
		}
	}

	public static String toSizeFromLongWithDot(long size) {
		int Kb = 1024;
		if (size == 0) {
			return "0B";
		} else if (size > 0 && size < Kb) {
			return "" + size + "B";
		}
		// kb
		else if (size > Kb) {
			long sizeKb = size / Kb;// 一定不小于1
			if (sizeKb < Kb) {
				double tempK = (double) (size / Kb);
				int intK = (int) tempK;
				int dotK = (int) ((tempK - intK) * 100);
				if (dotK == 0) {
					return "" + intK + "K";

				} else if (dotK < 10 && dotK > 0) {

					dotK = dotK / 10;
					return "" + intK + "." + "0" + dotK + "K";

				} else if (dotK >= 10 && dotK < 100) {
					return "" + intK + "." + dotK + "K";
				}
			}// M
			else if (sizeKb >= Kb) {
				long sizeM = size / Kb / Kb;// 一定不小于1
				if (sizeM < Kb) {
					double tempM = ((double) (size / Kb)) / Kb;
					int intM = (int) tempM;
					int dotM = (int) ((tempM - intM) * 100);
					if (dotM == 0) {
						return "" + intM + "M";
					} else if (dotM < 10 && dotM > 0) {
						dotM = dotM / 10;
						return "" + intM + "." + "0" + dotM + "M";
					} else if (dotM >= 10 && dotM < 100) {
						return "" + intM + "." + dotM + "M";
					}
				} else if (sizeM > Kb) {
					long sizeG = size / Kb / Kb / Kb;// 一定不小于1
					if (sizeG < Kb) {
						double tempG = ((double) (size / Kb / Kb)) / Kb;
						int intG = (int) tempG;
						int dotG = (int) ((tempG - intG) * 100);
						if (dotG == 0) {
							return "" + intG + "G";
						} else if (dotG < 10 && dotG > 0) {
							dotG = dotG / 10;
							return "" + intG + "." + "0" + dotG + "G";
						} else if (dotG >= 10 && dotG < 100) {
							return "" + intG + "." + dotG + "G";
						}

					} else if (sizeG > Kb) {
						double tempT = ((double) (size / Kb / Kb)) / Kb;
						int intT = (int) tempT;
						int dotT = (int) ((tempT - intT) * 100);
						if (dotT == 0) {
							return "" + intT + "T";
						} else if (dotT < 10 && dotT > 0) {
							dotT = dotT / 10;
							return "" + intT + "." + "0" + dotT + "T";
						} else if (dotT >= 10 && dotT < 100) {
							return "" + intT + "." + dotT + "T";
						}
					}
				}

			}
		}
		return "??B";
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

	public static long byte8ToLong(byte[] arr) {
		if (arr == null || arr.length != 8) {
			throw new IllegalArgumentException("byte数组必须不为空,并且是8位!");
		}
		return (long) (((long) (arr[0] & 0xff) << 56) | ((long) (arr[1] & 0xff) << 48)
				| ((long) (arr[2] & 0xff) << 40) | ((long) (arr[3] & 0xff) << 32)
				| ((long) (arr[4] & 0xff) << 24) | ((long) (arr[5] & 0xff) << 16)
				| ((long) (arr[6] & 0xff) << 8) | ((long) (arr[7] & 0xff)));
	}

	/**
	 * int到byte[]
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
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
	 * byte[]转int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	/**
	 * 计算字符串的真实长度 （每个汉字占2个字符大小）
	 * 
	 * @param str
	 *            : String
	 * @return str的真实长度
	 */
	public static int countWords(String str) {
		if (str == null || str.length() <= 0) {
			return 0;
		}
		int len = 0;
		char c;

		for (int i = str.length() - 1; i >= 0; i--) {
			c = str.charAt(i);

			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
					|| (c >= 'A' && c <= 'Z')) {
				// 字母, 数字
				len++;
			} else {
				if (Character.isLetter(c)) { // 中文
					len += 2;
				} else { // 符号或控制字符
					len++;
				}
			}
		}

		return len;
	}

	/**
	 * 在真实长度上转换成字的长度 除2
	 * 
	 * @param str
	 * @return
	 */
	public static int calculate(String str) {
		int len = countWords(str);
		if (len > 0) {
			if (len % 2 != 0) {
				len = len / 2 + 1;
			} else {
				len = len / 2;
			}
		}
		return len;
	}
	
	public static void main(String[] args){
		System.out.println(createSeq());
	}
}