package com.jihuiduo.netproxy.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CachedThreadPool {
	private static CachedThreadPool single = null;
	private static ExecutorService pool;
	public static CachedThreadPool getInstance(){
		if(single == null){
			synchronized(CachedThreadPool.class){
				if(single == null){
					single = new CachedThreadPool();
				}
			}
		}
		return single;
	}
	
	public ExecutorService getCachedThreadPool() {
		return CachedThreadPool.pool;
	}
	
	private CachedThreadPool(){
		pool = Executors.newCachedThreadPool();
	}
	
}
