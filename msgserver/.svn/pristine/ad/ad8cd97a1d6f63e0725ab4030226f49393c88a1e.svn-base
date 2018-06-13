package com.jihuiduo.redis.service;

import java.util.Set;

/**
 * Set操作接口
 * 
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:42:03
 */
public interface JedisSet {
	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，
	 * 已经存在于集合的 member 元素将被忽略。
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * 当 key 不是集合类型时，返回一个错误
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(String key, String... members);

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key, String... members);

	/**
	 * 判断 member 元素是否集合 key 的成员
	 * @param key
	 * @param member
	 * @return
	 */
	public Boolean sismember(String key, String member);

	/**
	 * 返回集合 key 中的所有成员。
	 * 不存在的 key 被视为空集合
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key);

	/**
	 * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
	 * @param key
	 * @return
	 */
	public String srandmember(String key);

	/**
	 * 返回集合 key 的基数(集合中元素的数量)
	 * @param key
	 * @return
	 */
	public Long scard(String key);

}
