package com.jihuiduo.redis.cache.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.jihuiduo.redis.cache.IRedisCache;


/**
 * redis缓存实现类
 * @author Zhongquan Xia
 * @date 2015年4月1日 上午10:27:10 
 *
 */
@Component("redisStringCache")
public class RedisStringCacheImpl implements IRedisCache<String,String> {
	
	@Resource(name = "strRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public String get(CacheTypeEnum cacheTypeEnum,String key) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void put(CacheTypeEnum cacheTypeEnum,String key, String value,
			long time) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void put(CacheTypeEnum cacheTypeEnum,String key, String value
			) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void remove(CacheTypeEnum cacheTypeEnum,String key) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(CacheTypeEnum cacheTypeEnum,String key, String value) {
		this.put(cacheTypeEnum, key, value);
	}
	
	
	@Override
	public List<String> multiGet(CacheTypeEnum cacheTypeEnum,Set<String> keys) {
		if(keys==null){
			return null;
		}
		Set<String> wrapKeys = new HashSet<String>(keys.size());
		for(String key:keys){
			wrapKeys.add(wrapKey(cacheTypeEnum,key));
		}
		try {
			return redisTemplate.opsForValue().multiGet(wrapKeys);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void putHash(CacheTypeEnum cacheTypeEnum, String key,
			String hashKey, String value) {
		key = wrapKey(cacheTypeEnum, key);
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getHash(CacheTypeEnum cacheTypeEnum,String key, String hashKey) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			return (String) redisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void removeAll(CacheTypeEnum cacheTypeEnum) {
		Set<String> keySet = null;
		try {
			keySet = redisTemplate.keys("*"+ cacheTypeEnum.name() +"*");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(keySet != null && !keySet.isEmpty()){
				redisTemplate.delete(keySet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void expire(CacheTypeEnum cacheTypeEnum, String key, long time) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			redisTemplate.expire(key, time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void putAllHash(CacheTypeEnum cacheTypeEnum, String key,
			Map<String, String> hash) {
		key = wrapKey(cacheTypeEnum, key);
		try {
			redisTemplate.opsForHash().putAll(key, hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeHash(CacheTypeEnum cacheTypeEnum, String key,
			String hashKey) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			redisTemplate.opsForHash().delete(key, hashKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String wrapKey(CacheTypeEnum cacheTypeEnum, String key) {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(cacheTypeEnum).append(":").append(key);
		return keyBuilder.toString();
	}

	@Override
	public Map<String, String> getHashAll(CacheTypeEnum cacheTypeEnum,
			String key) {
		key = wrapKey(cacheTypeEnum,key);
		try {
			Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
			if(map == null || map.isEmpty()){
				return null;
			}
			Map<String, String> hashMap = new HashMap<String, String>();
			for (Entry<Object, Object> entry : map.entrySet()) {
				hashMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
			return hashMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	

}
