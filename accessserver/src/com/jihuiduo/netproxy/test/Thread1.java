package com.jihuiduo.netproxy.test;

import java.util.concurrent.TimeUnit;

import com.jihuiduo.netproxy.cache.ImAllSession;


public class Thread1 implements Runnable{

	@Override
	public void run() {
		System.out.println("****enter thread1");
		ImAllSession tc = ImAllSession.getInstance();
		tc.put("abc", "123");
	}

}
