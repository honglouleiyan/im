package com.jihuiduo.redis.service;

import java.util.Set;

/**
 * key操作接口
 * @author Zhongquan Xia
 * @date 2015年4月17日 下午5:41:45
 */
public interface JedisKey {
	/**
	 * 查询所有key
	 * @return
	 */
	public Set<String> queryAllKey();
	
	/**
	 * 判断Key是否存在
	 * @param key
	 * @return
	 */
	public Boolean exists(String key);
	
	/**
	 * 删除Key
	 * @param key
	 * @return
	 */
	public Long delKey(String key);
	
	/**
	 * 将 key 改名为 newkey 
	 * @param oldkey
	 * @param newkey
	 * @return
	 */
	public String renameKey(String oldkey, String newkey);
}
