package com.jihuiduo.redis.service.impl;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisKey;

public class JedisKeyImpl implements JedisKey {

	@Override
	public Set<String> queryAllKey() {
		Jedis jedis = null;
		Set<String> keys = null;
		try {
			jedis = JedisFactory.getResource();
			keys = jedis.keys("*");
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return keys;
	}
	
	@Override
	public Boolean exists(String key) {
		Jedis jedis = null;
		Boolean res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long delKey(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public String renameKey(String oldkey, String newkey) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.rename(oldkey, newkey);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}


}
