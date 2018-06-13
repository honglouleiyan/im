package com.jihuiduo.redis;

import com.jihuiduo.config.SystemConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis缓存工厂类
 */
public class JedisFactory {
	
	private static JedisFactory instance;
	
	/** 
     * 在不同的线程中使用相同的Jedis实例会发生奇怪的错误。但是创建太多的实现也不好因为这意味着会建立很多sokcet连接， 
     * 也会导致奇怪的错误发生。单一Jedis实例不是线程安全的。为了避免这些问题，可以使用JedisPool, 
     * JedisPool是一个线程安全的网络连接池。可以用JedisPool创建一些可靠Jedis实例，可以从池中拿到Jedis的实例。 
     * 这种方式可以解决那些问题并且会实现高效的性能 
     */ 
	private JedisPool jedisPool;
	
	private JedisFactory() {
		
	}
	
	/**
	 * 单例模式
	 */
	public static JedisFactory getInstance() {
		if (instance == null) {
			synchronized (JedisFactory.class) {
				if (instance == null) {
					instance = new JedisFactory();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 初始化
	 */
	public void initialize() throws Exception {
		String host = SystemConfig.getRedisHost();
		int port = SystemConfig.getRedisPort();
		int timeout = SystemConfig.getRedisTimeout();
		String password = SystemConfig.getRedisPassword();
		
		int maxaActive = SystemConfig.getRedisMaxActive();
		int maxIdle = SystemConfig.getRedisMaxIdle();
		int maxWait = SystemConfig.getRedisMaxWait();
		boolean testOnBorrow = SystemConfig.getRedisTestOnBorrow();
		boolean testOnReturn = SystemConfig.getRedisTestOnReturn();
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 控制一个pool最多有多少个状态为idle的jedis实例
		jedisPoolConfig.setMaxActive(maxaActive);
		jedisPoolConfig.setMaxIdle(maxIdle);
		// 超时时间
		jedisPoolConfig.setMaxWait(maxWait);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		jedisPoolConfig.setTestOnBorrow(testOnBorrow); 
		// 在还会给pool时，是否提前进行validate操作
		jedisPoolConfig.setTestOnReturn(testOnReturn);
		
		// 构造连接池
		this.jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
	}
	
	private static JedisPool getJedisPool() {
		return instance.jedisPool;
	}
	
	/**
	 * 从池中获取一个jedis实例
	 * @return
	 */
	public static Jedis getResource(){
		return getJedisPool().getResource();
	}
	
	/**
	 * 销毁对象
	 * @param jedis
	 */
	public static void returnBrokenResource(Jedis jedis){
		if(jedis != null){
			getJedisPool().returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 还会到连接池
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		if(jedis != null){
			getJedisPool().returnResource(jedis);
		}
	}
}
