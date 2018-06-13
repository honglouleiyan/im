package com.jihuiduo.netproxy.test;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <dl>
 * <dt>TempCache</dt>
 * <dd>Description:在制定时间内缓存对象，查过实现就移除</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2011-5-9</dd>
 * </dl>
 * 
 * @author qinbq
 */
public class TempCache<K,V> {
	
	Log logger = LogFactory.getLog(getClass());
    private static TempCache single=null;  
    public static TempCache getInstance() {  
         if (single == null) {    
        	 synchronized(TempCache.class){
        		 single = new TempCache();  
        	 }
         }    
        return single;  
    } 
	private ConcurrentHashMap<K, V> data = new ConcurrentHashMap<K, V>();
	
	/**
	 * 缓存的待移除的key
	 */
	private BlockingQueue<Key> keys = new DelayQueue<Key>(); 
	
	public void init(){
//		Thread checkThread = new Thread(new CheckTimeOutThread()); 
//		checkThread.setDaemon(true);
//		checkThread.start();
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
		
		@Override
		public int compareTo(Delayed o) {
			Key that = (Key)o;
			return this.end>that.end?1:this.end<that.end?-1:0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(this.end - System.currentTimeMillis(), unit.MILLISECONDS);
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
					data.remove(k.key);
				}
				System.out.println("removed " +k.key);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("111");
		System.out.println("111");
		System.out.println("111");
		System.out.println("111");
		System.out.println("111");
		System.out.println("111");
		System.out.println("111");
		TempCache tc = new TempCache();
		tc.init();
		tc.put("abc", "123",0,TimeUnit.SECONDS);
		tc.put("def", "456",0,TimeUnit.SECONDS);
		while(true){
			System.out.println("got!");
			System.out.println("abc="+tc.get("abc"));
			System.out.println("def="+tc.get("def"));
			System.out.println(tc.getData().size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(tc.get("abc")==null&&tc.get("def")==null){
				break;
			}
		}
		System.out.println();
	}


	public ConcurrentHashMap<K, V> getData() {
		return data;
	}


	public void setData(ConcurrentHashMap<K, V> data) {
		this.data = data;
	}
	
}
