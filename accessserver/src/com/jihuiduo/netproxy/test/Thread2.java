package com.jihuiduo.netproxy.test;


import com.jihuiduo.netproxy.cache.ImAllSession;


public class Thread2 implements Runnable{

	@Override
	public void run() {
		System.out.println("****enter thread2");
		ImAllSession tc = ImAllSession.getInstance();
		tc.put("def", "456");
	}

}
