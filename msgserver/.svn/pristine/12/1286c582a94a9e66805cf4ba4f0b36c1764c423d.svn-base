package com.jihuiduo.redis.service;

/**
 * 对象操作接口ByJson
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:41:22
 */
public interface JedisJsonObj {
	/**
	 * 保存对象
	 * @param key
	 * @param object
	 */
	public void set(String key, Object object);
	/**
	 * 获取对象
	 * @param key
	 * @param typeReference  指定返回对象类型如：new TypeReference<List<Group>>(){}
	 * List<Group>为返回对象类型
	 * @return
	 */
	public Object get(String key, Class<?> object);
	
	/**
	 * 删除对象
	 * @param key
	 * @return
	 */
	public Long remove(String key);

}
