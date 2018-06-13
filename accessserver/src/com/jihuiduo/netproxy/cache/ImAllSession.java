package com.jihuiduo.netproxy.cache;


import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


/*
 * 用于管理所有Im连接的缓存
 */
public class ImAllSession<K,V> {
	private Logger logger  =  Logger.getLogger(ImAllSession.class );
	
//    private ServerCache() {}  
    @SuppressWarnings("rawtypes")
	private static ImAllSession single=null;  
    @SuppressWarnings("rawtypes")
	public synchronized  static ImAllSession getInstance() {  
        if (single == null) {    
            single = new ImAllSession();  
        }    
       return single;  
   }   
    
    private ConcurrentHashMap<K, V> data = new ConcurrentHashMap<K, V>();
	/**
	 * 放入缓存对象
	 * @param key 缓存对象key
	 * @param value 缓存对象值
	 */
	public void put(K key,V value){
		logger.debug("into:" + key);
		data.put(key, value);
	}
	
	/**
	 * 根据指定key移除缓存内容
	 * @param key 移除key
	 */
	public void remove(K key){
		logger.debug("remove:" + key);
		data.remove(key);
	}
	
	/**
	 * 根据指定value获取缓存内容
	 * @param value 
	 */
	public K getKey(V value){
		for (K k : data.keySet()) {
			if(data.get(k).equals(value)){
				return k;
			}
		}
		return null;
	}
	
	/**
	 * 根据指定key获取缓存内容
	 * @param key 缓存key
	 */
	public V get(K key){
		logger.debug("get:" + key);
		return data.get(key);
	}

	public ConcurrentHashMap<K, V> getData() {
		return data;
	}

	public void setData(ConcurrentHashMap<K, V> data) {
		this.data = data;
	}
	
}
