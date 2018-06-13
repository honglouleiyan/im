package com.jihuiduo.netproxy.cache;


import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;


/*
 * 用于管理所有的web服务器缓存
 */
public class WebAllSession {
	private Logger logger  =  Logger.getLogger(WebAllSession.class );
	
//    private ServerCache() {}  
    private static WebAllSession single=null;  
    
    public static WebAllSession getInstance() {  
         if (single == null) {    
        	 synchronized(WebAllSession.class){
        		 if (single == null) {
        			 single = new WebAllSession(); 
				}
        	 }
         }    
        return single;  
    }  
    
    private ConcurrentHashMap<String, IoSession> data = new ConcurrentHashMap<String, IoSession>();
	/**
	 * 放入缓存对象
	 * @param key 缓存对象key
	 * @param value 缓存对象值
	 */
	public void put(String key,IoSession value){
		logger.debug("into:" + key);
		data.put(key, value);
	}
	
	/**
	 * 根据指定key移除缓存内容
	 * @param key 移除key
	 */
	public void remove(String key){
		logger.debug("remove:" + key);
		data.remove(key);
	}
	
	/**
	 * 根据指定key获取缓存内容
	 * @param key 缓存key
	 */
	public IoSession get(String key){
		logger.debug("get:" + key);
		return data.get(key);
	}


	/**
	 * 根据指定value获取缓存内容
	 * @param value 
	 */
	public String getKey(IoSession value){
		for (String k : data.keySet()) {
			if(data.get(k).equals(value)){
				return k;
			}
		}
		return null;
	}

	public ConcurrentHashMap<String, IoSession> getData() {
		return data;
	}

	public void setData(ConcurrentHashMap<String, IoSession> data) {
		this.data = data;
	}
	
}
