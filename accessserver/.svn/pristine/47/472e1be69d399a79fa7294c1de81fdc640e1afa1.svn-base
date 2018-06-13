package com.jihuiduo.netproxy.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


/*
 * 匿名用户缓存   seq和匿名用户名映射
 */
public class CryptonymCache<K,V> {
	private Logger logger  =  Logger.getLogger(ClientCache.class );
		
	    private CryptonymCache() {}  
		@SuppressWarnings("rawtypes")
		private static CryptonymCache single=null;  
		@SuppressWarnings("rawtypes")
		public synchronized  static CryptonymCache getInstance() {  
	         if (single == null) {    
	             single = new CryptonymCache();  
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
			logger.debug("into" + key);
			data.put(key, value);
		}
		
		/**
		 * 根据指定key移除缓存内容
		 * @param key 移除key
		 */
		public void remove(K key){
			logger.debug("remove" + key);
			data.remove(key);
		}
		
		/**
		 * 根据指定key获取缓存内容
		 * @param key 缓存key
		 */
		public V get(K key){
			return data.get(key);
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
		 * 根据指定value移除对象
		 * @param value 
		 */
		public void removeAll(V value){
			for (K k : data.keySet()) {
				if(data.get(k).equals(value)){
					remove(k);
				}
			}
		}
}
