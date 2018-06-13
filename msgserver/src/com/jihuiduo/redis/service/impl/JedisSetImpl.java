package com.jihuiduo.redis.service.impl;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisSet;

public class JedisSetImpl implements JedisSet {

	@Override
	public Long sadd(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long srem(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.srem(key, members);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Boolean sismember(String key, String member) {
		Jedis jedis = null;
		Boolean res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.sismember(key, member);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.smembers(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public String srandmember(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.srandmember(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long scard(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.scard(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

}
