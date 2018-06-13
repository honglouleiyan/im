package com.jihuiduo.redis.service;



/**
 * 对象操作接口传入对象需要实现序列化
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:41:58
 */
public interface JedisObj {
	/**
	 * 保存对象  
	 * @param key
	 * @param value  需要实现序列化
	 */
	public void set(String key, Object value);
	
	/**
	 * 获取对象
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 删除对象
	 * @param key
	 * @return
	 */
	public Long remove(String key);
}
