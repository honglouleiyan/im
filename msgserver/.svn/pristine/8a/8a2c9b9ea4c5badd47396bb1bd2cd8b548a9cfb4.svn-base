package com.jihuiduo.redis.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.redis.service.JedisHash;

public class JedisHashImpl implements JedisHash {

	@Override
	public String hmset(String key, Map<String, String> map) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hmset(key, map);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long hdel(String key, String... fields) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hdel(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Long hlen(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hlen(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hkeys(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public List<String> hvals(String key) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hvals(key);
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

	@Override
	public Map<String, String> hmget(String key) {
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = JedisFactory.getResource();
			Set<String> keySet = this.hkeys(key);
			if(keySet != null && keySet.size() > 0){
				res = new HashMap<String, String>();
			}
	        Iterator<String> iter = keySet.iterator();  
	        while (iter.hasNext()){  
	            String field = iter.next();  
	            List<String> values = this.hmget(key, field);
	            if(values != null && values.size() > 0){
	            	res.put(field, values.get(0));
	            }
	        }  
		} catch (Exception e) {
			e.printStackTrace();
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}

}
