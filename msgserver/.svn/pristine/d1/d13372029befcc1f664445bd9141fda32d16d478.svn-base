package com.jihuiduo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil {

	public static void main(String[] args)throws Exception {
		
		TrustManager[] tm = {  new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		}};               
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");               
		sslContext.init(null, tm, new java.security.SecureRandom());               
		// 从上述SSLContext对象中得到SSLSocketFactory对象               
		SSLSocketFactory ssf = sslContext.getSocketFactory();   

		String url = "https://***********************************";
		URL realUrl = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(25000);
		conn.setReadTimeout(25000);
		conn.setUseCaches(false);
		conn.setSSLSocketFactory(ssf);   
		conn.setRequestProperty("Charset", "UTF-8");
		
		HttpURLConnection.setFollowRedirects(true);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
		System.out.println(out.getEncoding());
		String s = "数据";
		out.write(s);
		out.flush();
		
		InputStream in = conn.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		String valueString = null;
		StringBuffer bufferRes = new StringBuffer();
		while ((valueString=read.readLine())!=null){
			bufferRes.append(valueString);
		}
		System.out.println(bufferRes.toString());
		in.close();
		
		out.close();
		if (conn != null) {
			conn.disconnect();
		}
		
	}
	
	
}


