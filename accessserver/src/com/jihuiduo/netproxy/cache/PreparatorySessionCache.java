package com.jihuiduo.netproxy.cache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.ConcurrentHashSet;

/**
 * 用于保存预验证session
 */
public class PreparatorySessionCache<K,V> {
	/**
     * 预验证session过期时间：秒
     */
	public final static Integer EXPIRATION_TIME= 60; 
	
	
	static Log logger = LogFactory.getLog(PreparatorySessionCache.class);
	//已map存储   不用set存储
	@SuppressWarnings("rawtypes")
	private static PreparatorySessionCache single=null; 
	private ConcurrentHashMap<K, V> data = new ConcurrentHashMap<K, V>();
	private ConcurrentHashSet<K> set = new ConcurrentHashSet<K>();
	/**
	 * 缓存的待移除的key
	 */
	private BlockingQueue<Key> keys = new DelayQueue<Key>(); 
	
	/**
	 * 构造函数启动--移除过期对象   若不需要启动  请注释
	 */
	public PreparatorySessionCache() {
		Thread checkThread = new Thread(new CheckTimeOutThread()); 
		checkThread.setDaemon(true);
		checkThread.start();
	}
	
	/**
	 * 启动线程--移除过期对象
	 */
	public void init(){
		Thread checkThread = new Thread(new CheckTimeOutThread()); 
		checkThread.setDaemon(true);
		checkThread.start();
	}
	
	
	/**
	 * 放入缓存对象
	 * @param key 缓存对象key
	 * @param value 缓存对象值
	 * @param timeout 在缓存内存放时间
	 * @param unit timeout的时间单位
	 */
	public void put(K key,V value,int timeout,TimeUnit unit){
		Key k = new Key(key, timeout,unit);
		data.put(key, value);
		keys.offer(k);
	}
	
	/**
	 * 放入缓存对象
	 * @param key 缓存对象key
	 * @param value 缓存对象值
	 * @param timeout 在缓存内存放时间，单位秒
	 */
	public void put(K key,V value,int timeout){
		Key k = new Key(key, timeout);
		data.put(key, value);
		keys.offer(k);
	}
	
	/**
	 * 根据指定key获取缓存内容
	 * @param key 缓存key
	 * @return 缓存值，当缓存内容超过制定时限则返回null
	 */
	public V get(K key){
		return data.get(key);
	}
	
	class Key implements Delayed{

		K key = null;
		
		/**
		 * 超时截止时间 ,单位毫秒
		 */
		long end;
		
		Key(K key,long timeout,TimeUnit timeUnit){
			this.key = key;
			this.end = System.currentTimeMillis()+TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
		}
		
		Key(K key,long timeout){
			this.key = key;
			this.end = System.currentTimeMillis()+TimeUnit.MILLISECONDS.convert(timeout, TimeUnit.SECONDS);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(Delayed o) {
			Key that = (Key)o;
			return this.end>that.end?1:this.end<that.end?-1:0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(this.end - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		
	}
	
	class CheckTimeOutThread implements Runnable{

		@Override
		public void run() {
			while(true){
				Key k;
				try {
					k = keys.take();
				} catch (InterruptedException e) {
					logger.info("获取key线程终端2",e);
					continue;
				}
				synchronized (data) {
					if(data.get(k.key) != null) {
						IoSession ioSession = (IoSession) k.key;
						ioSession.close(true);
						data.remove(k.key);
						logger.debug("removed " +k.key);
					}
				}
			}
		}
	}
	
    @SuppressWarnings("rawtypes")
	public static PreparatorySessionCache getInstance() {  
        if (single == null) {    
       	 synchronized(PreparatorySessionCache.class){
       		 single = new PreparatorySessionCache();  
       	 }
        }    
       return single;  
   } 
	
	@SuppressWarnings("unchecked")
	public static void putSession(IoSession session) {
		getInstance().put(session, "", PreparatorySessionCache.EXPIRATION_TIME,TimeUnit.SECONDS);
		logger.debug(session + "---" + getInstance().get(session));
		getInstance().allBist();
	}
	
	
	/**
	 * 根据指定key移除缓存内容
	 * @param key 移除key
	 */
	public void remove(K key) {
		synchronized (data) {
			logger.info("remove:" + key);
			data.remove(key);
		}
	}
	
	/**
	 * 根据指定value获取缓存内容
	 * @param value 
	 */
	public void removeByValue(V value) {
		for (K k : data.keySet()) {
			if(data.get(k).equals(value)){
				data.remove(k);
			}
		}
	}
	
	/**
	 * 遍历
	 * @param value 
	 */
	public void allBist() {
		for (K k : data.keySet()) {
			System.out.println("k="+k+",date="+data);
		}
	}
//	public static void main(String[] args) {
//		NewSessionCache tc = new NewSessionCache();
////		tc.init();
//		tc.put("abc", "", 3,TimeUnit.SECONDS);
//		tc.put("def", "", 6,TimeUnit.SECONDS);
//		while(true){
//			System.out.println("got!");
//			System.out.println("abc="+tc.get("abc"));
//			System.out.println("def="+tc.get("def"));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if(tc.get("abc")==null&&tc.get("def")==null){
//				break;
//			}
//		}
//		System.out.println();
//	}
	
	public static void main(String[] args) {
//		putSession();
//		putSession();
//		putSession();
		while(true){
			System.out.println("got!");
			getInstance().allBist();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
