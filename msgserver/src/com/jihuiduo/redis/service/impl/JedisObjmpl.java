package com.jihuiduo.redis.service.impl;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisObj;
import com.jihuiduo.util.SerializeUtil;

public class JedisObjmpl implements JedisObj {

	@Override
	public void set(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getResource();
			jedis.set(key.getBytes(), SerializeUtil.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}

	@Override
	public Object get(String key) {
		Jedis jedis = null;
		Object res = null;
		try {
			jedis = JedisFactory.getResource();
			byte [] vlaue = jedis.get(key.getBytes());
			res =  SerializeUtil.unserialize(vlaue);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long remove(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.del(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
		
	}



}
