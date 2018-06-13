package com.jihuiduo.redis.service.impl;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisJsonObj;
import com.jihuiduo.util.JsonUtil;

public class JedisJsonObjImpl implements JedisJsonObj{

	@Override
	public void set(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getResource();
			jedis.set(key, JsonUtil.getGson().toJson(value));
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}


	@Override
	public Long remove(String key) {
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
	public Object get(String key, Class<?> object) {
		Jedis jedis = null;
		Object res = null;
		try {
			jedis = JedisFactory.getResource();
			String json = jedis.get(key);
			res = JsonUtil.getGson().fromJson(json, object);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

}
