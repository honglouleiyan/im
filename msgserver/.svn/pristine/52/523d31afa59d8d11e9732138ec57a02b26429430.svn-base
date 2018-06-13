package com.jihuiduo.redis.service;

import java.util.List;

/**
 * List操作接口
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:41:49
 */
public interface JedisList {
	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 * @param key
	 * @param strings
	 * @return
	 */
	public Long lpush(String key, String... strings);

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾
	 * @param key
	 * @param strings
	 * @return
	 */
	public Long rpush(String key, String... strings);

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end);

	/**
	 * 返回列表 key 的长度。
	 * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
	 * 如果 key 不是列表类型，返回一个错误
	 * @param key
	 * @return
	 */
	public Long llen(String key);

	/**
	 * 返回列表 key 中，下标为 index 的元素
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index);

}
