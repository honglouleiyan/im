package com.jihuiduo.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hash操作接口
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:41:54
 */
public interface JedisHash {

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
	 * 此命令会覆盖哈希表中已存在的域。
	 * @param key
	 * @param map
	 * @return
	 */
	public String hmset(String key, Map<String, String> map);

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。
	 * 如果给定的域不存在于哈希表，那么返回一个 null 值。
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<String> hmget(String key, String... fields);

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hdel(String key, String... fields);

	/**
	 * 返回哈希表 key 中域的数量
	 * @param key
	 * @return
	 */
	public Long hlen(String key);

	/**
	 * 返回哈希表 key 中的所有域
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key);

	/**
	 * 返回哈希表 key 中所有域的值
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key);

	/**
	 * 返回哈希表 key 中所有field-value (域-值)对
	 * @param key
	 * @return
	 */
	public Map<String, String> hmget(String key);

}
