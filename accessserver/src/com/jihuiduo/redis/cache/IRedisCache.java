package com.jihuiduo.redis.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;

import com.jihuiduo.redis.cache.impl.CacheTypeEnum;



/**
 * redis缓存接口
 * @author Zhongquan Xia
 * @date 2015年4月1日 上午10:28:08 
 *
 */
public interface IRedisCache<K,V> {
	
    /**
     * 根据Key获取缓存数据
     * @param cacheTypeEnum
     * @param key
     * @return Object
     */
    V get(CacheTypeEnum cacheTypeEnum,K key);
    
    /**
     * 添加缓存数据带时效
     * @param cacheTypeEnum
     * @param key
     * @param value
     * @param time
     */
    void put(CacheTypeEnum cacheTypeEnum, K key, V value,
			long time);

	/**
	 * 添加缓存数据
	 * @param cacheTypeEnum
	 * @param key
	 * @param value 
	 */
	void put(CacheTypeEnum cacheTypeEnum,K key, V value);

	/**
	 * 根据Key删除缓存数据
	 * @param cacheTypeEnum
	 * @param key 
	 */
	void remove(CacheTypeEnum cacheTypeEnum, K key);
	
	/**
	 * 更新缓存数据
	 * @param cacheTypeEnum
	 * @param key
	 * @param value 
	 */
	void update(CacheTypeEnum cacheTypeEnum, K key, V value);

	/**
	 * 获取多个缓存数据
	 * @param cacheTypeEnum
	 * @param keys
	 * @return List<Object>
	 */
	List<V> multiGet(CacheTypeEnum cacheTypeEnum, Set<K> keys);

	/**
	 * 获取hash数据
	 * @param cacheTypeEnum
	 * @param key
	 * @param hashKey
	 * @return Object
	 */
	V getHash(CacheTypeEnum cacheTypeEnum, K key, K hashKey);

	/**
	 * 获取所有hash数据
	 * @param cacheTypeEnum
	 * @param key
	 * @return Map<Object,Object>
	 */
	Map<K, V> getHashAll(CacheTypeEnum cacheTypeEnum, K key);

	
	/**
	 * 删除缓存类型下所有数据
	 * @param cacheTypeEnum 
	 */
	void removeAll(CacheTypeEnum cacheTypeEnum);

	/**
	 * 添加hash数据
	 * @param cacheTypeEnum
	 * @param key
	 * @param hashKey
	 * @param value 
	 */
	void putHash(CacheTypeEnum cacheTypeEnum, K key, K hashKey,
			V value);
	
	
	/**
	 *	删除hash数据
	 * @param cacheTypeEnum
	 * @param key
	 * @param hashKey
	 */
	void removeHash(CacheTypeEnum cacheTypeEnum,K key, K hashKey);
	
	/**
	 * 为给定key设置生存时间
	 * @param cacheTypeEnum
	 * @param key
	 * @param time
	 */
	void expire(CacheTypeEnum cacheTypeEnum, K key, long time);


	/**
	 * 添加hash数据
	 * @param cacheTypeEnum
	 * @param key
	 * @param hash
	 */
	void putAllHash(CacheTypeEnum cacheTypeEnum, K key, Map<K, V> hash);
	
	RedisTemplate<K, V> getRedisTemplate();
}
