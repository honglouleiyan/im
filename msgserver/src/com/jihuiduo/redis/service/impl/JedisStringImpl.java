package com.jihuiduo.redis.service.impl;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisString;

public class JedisStringImpl implements JedisString{

	@Override
	public String set(String key, String value) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public String get(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long append(String key, String value) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.append(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public String mset(String... keysvalues) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.mset(keysvalues);
		} catch (Exception e) {
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.incr(key);
		} catch (Exception e) {
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
		
}
