package com.jihuiduo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpTest {

	public static void main(String[] args) {
		new HttpTest().doPost();
	}
	
	public String doPost() {
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String url = "http://www.baidu.com";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Android");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            
            // 发送请求参数
            String param = "{\"a\":\"123\"}";
            out.println(param);
            // flush输出流的缓冲
            out.flush();
            
            // 定义BufferedReader输入流来读取URL的响应
          in = new BufferedReader(
                  new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("result : " + result);
            
            
            
            // 测试模块
            while(true) {
            	in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
            	line = null;
            	result = null;
                  while ((line = in.readLine()) != null) {
                      result += line;
                  }
                  if (result != null) {
                	  System.out.println("result : " + result);
				}
            }
            
            
            
              
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        return result;
	}
	
}
