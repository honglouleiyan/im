package com.jihuiduo.test;

import redis.clients.jedis.Jedis;

import com.jihuiduo.msgserver.starter.SystemInitService;
import com.jihuiduo.redis.JedisFactory;

public class CacheTest {
	
	public static void main(String[] args) throws Exception {
		
		// 测试Redis缓存和内存 存取数据速度差异
		
		SystemInitService service = SystemInitService.getInstance();
		service.initialize();
		
		System.out.println(System.currentTimeMillis());
		new CacheTest().get("USER:10000");
		System.out.println(System.currentTimeMillis());
		
		
		
		for (int i = 0; i < 10; i++) {
			System.out.println("i : " + i + " : " + System.currentTimeMillis());
			System.out.println("i : " + i + " : " + new CacheTest().get("USER:10000"));
			System.out.println("i : " + i + " : " + System.currentTimeMillis());
		}
		
		
	}
	
	
	public String get(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.get(key);
		} catch (Exception e) {
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

}
