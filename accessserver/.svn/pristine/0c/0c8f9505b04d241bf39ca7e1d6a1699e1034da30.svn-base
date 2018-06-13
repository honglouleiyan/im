package com.jihuiduo.netproxy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.jihuiduo.netproxy.server.ServerManage;



public class TestJson {
	public static void main(String[] args) {
//			String sb = web();
//			System.out.println(sb);
//		String str1 = TestJson.web();
		String str1 = "POST /jhd?rid=50 HTTP/1.1";
//		String str1 = "POST /jhd?rid=50&userId=3&tst=tese HTTP/1.1";
		Pattern p = Pattern.compile("(?<=\\=)([\\W\\wHTTP]+)(?=\\sHTTP)");
		Matcher m = p.matcher(str1);
		StringBuffer sb = new StringBuffer(); 
		if(m.find()) {
			sb.append(m.group(1));  
		}
		String a = sb.toString();
		if(a.contains("&")) {
			System.out.println("**");
			System.out.println(a.substring(0, a.indexOf("&")));
		} else {
			System.out.println("&&");
			System.out.println(Integer.parseInt(a));
		}
	}
	
	public static String web(){
		StringBuffer sb= new StringBuffer();
		sb.append("GET /test HTTP/1.1").append("\r\n");
		sb.append("Host: localhost:5333").append("\r\n");
		sb.append("Connection: keep-alive").append("\r\n");
		sb.append("Cache-Control: max-age=0").append("\r\n");
		sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").append("\r\n");
		sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36").append("\r\n");
		sb.append("Accept-Encoding: gzip, deflate, sdch").append("\r\n");
		sb.append("Accept-Language: zh-CN,zh;q=0.8").append("\r\n");
		sb.append("\r\n");
		return sb.toString();
	}
	
	public static String web2(){
		StringBuffer sb= new StringBuffer();
		sb.append("GET /jhd?rid=6 HTTP/1.1").append("\r\n");
		sb.append("Host: localhost:5333").append("\r\n");
		sb.append("Connection: keep-alive").append("\r\n");
		sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").append("\r\n");
		sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36").append("\r\n");
		sb.append("Accept-Encoding: gzip, deflate, sdch").append("\r\n");
		sb.append("Accept-Language: zh-CN,zh;q=0.8").append("\r\n");
		sb.append("\r\n");
		System.out.println("http:\n" + sb.toString());
		return sb.toString();
	}
}
