package com.jihuiduo.netproxy.cache;


import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;



/**
 * 用户和设备的绑定关系
 */
public class UserServerCache<K,V> {
	private Logger logger  =  Logger.getLogger(UserServerCache.class );
	
//    private ServerCache() {}  
    @SuppressWarnings("rawtypes")
	private static UserServerCache single=null;  
    @SuppressWarnings("rawtypes")
	public static UserServerCache getInstance() {  
         if (single == null) {    
        	 synchronized(UserServerCache.class){
        		 single = new UserServerCache();  
        	 }
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
		logger.info("新加用户与设备关系:" + key+ "-" + value);
		data.put(key, value);
	}
	
	/**
	 * 放入唯一缓存对象
	 * @param key 缓存对象key
	 * @param value 缓存对象值
	 */
	public void putSingle(K key,V value){
		logger.info("UserServerCache用户与设备关系:" + key+ "-" + value);
		K ondKey = getKey(value);
		if(ondKey != null && !ondKey.equals(key)) {
			logger.info("UserServerCache去除旧关系:" + ondKey+ "-" + value);
			remove(ondKey);	
		}
		data.put(key, value);
	}
	
	/**
	 * 根据指定key移除缓存内容
	 * @param key 移除key
	 */
	public void remove(K key){
		logger.info("remove" + key);
		data.remove(key);
	}
	
	/**
	 * 根据指定key获取缓存内容
	 * @param key 缓存key
	 */
	public V get(K key){
		return data.get(key);
	}
	
	public K getKey(V value){
		for (K k : data.keySet()) {
			if(data.get(k).equals(value)){
				return k;
			}
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public   static   void  main(String[] args)  {
		UserServerCache cache = UserServerCache.getInstance();
		cache.put("111", "222");
		System.out.println(cache.get("111"));
	}
}
