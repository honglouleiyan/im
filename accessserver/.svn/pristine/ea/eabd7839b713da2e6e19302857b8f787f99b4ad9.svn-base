package com.jihuiduo.netproxy.cache;


import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;



/**
 * 设备和session的绑定关系
 */
public class DeviceServerCache<K,V> {
	private Logger logger  =  Logger.getLogger(DeviceServerCache.class );
	
//    private ServerCache() {}  
    @SuppressWarnings("rawtypes")
	private static DeviceServerCache single=null;  
    @SuppressWarnings("rawtypes")
	public static DeviceServerCache getInstance() {  
         if (single == null) {    
        	 synchronized(DeviceServerCache.class){
        		 single = new DeviceServerCache();  
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
		logger.info("设备和session关系：" + key + "-" + value);
		data.put(key, value);
	}
	
	/**
	 * 根据指定key移除缓存内容
	 * @param key 移除key
	 */
	public void remove(K key){
		logger.info("remove:设备=" + key);
		data.remove(key);
	}
	
	/**
	 * 根据指定key获取缓存内容
	 * @param key 缓存key
	 */
	public V get(K key){
		return data.get(key);
	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public K getKey(V value){
//		for (K k : data.keySet()) {
//			if(data.get(k).equals(value)){
//				return k;
//			}
//		}
//		return null;
//	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public   static   void  main(String[] args)  {
		DeviceServerCache cache = DeviceServerCache.getInstance();
		cache.put("111", "222");
		System.out.println(cache.get("111"));
		cache.remove("111");
		System.out.println(cache.get("111"));
		cache.remove("111");
	}
}
