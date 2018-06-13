package com.jihuiduo.netproxy.test;

import com.jihuiduo.netproxy.cache.ImAllSession;


public class TestImAllSessio {
	public static void main(String[] args) {
		TestImALLSession allSession = new TestImALLSession();
		ImAllSession tc = ImAllSession.getInstance();
		new Thread(new Thread1()).start();
		new Thread(new Thread2()).start();
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
}
