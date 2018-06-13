package com.jihuiduo.redis.service.impl;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisList;

public class JedisListImpl implements JedisList {

	@Override
	public Long lpush(String key, String... strings) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.lpush(key, strings);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long rpush(String key, String... strings) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.rpush(key, strings);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long llen(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public String lindex(String key, long index) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.lindex(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

}
