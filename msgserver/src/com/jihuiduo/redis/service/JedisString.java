package com.jihuiduo.redis.service;

/**
 * String操作接口
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:42:11
 */
public interface JedisString {

	/**
	 * 将字符串值 value 关联到 key 如果 key 已经持有其他值， SET 就覆写旧值
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value);

	/**
	 * 返回 key 所关联的字符串值。 如果 key 不存在那么返回特殊值null 。
	 * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET只能用于处理字符串值
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
	 * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long append(String key, String value);

	/**
	 * 同时设置一个或多个 key-value 对。
	 * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
	 * @param keysvalues
	 * @return
	 */
	public String mset(String... keysvalues);

	/**
	 * 将 key 中储存的数字值增一。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
	 * @param key
	 * @return
	 */
	public Long incr(String key);
}
