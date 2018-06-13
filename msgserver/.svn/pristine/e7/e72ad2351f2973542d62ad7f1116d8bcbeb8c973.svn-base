package com.jihuiduo.biz.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.jihuiduo.biz.notification.android.AndroidJPushService;
import com.jihuiduo.biz.notification.apple.ApplePushNotificationService;
import com.jihuiduo.redis.JedisFactory;
import com.jihuiduo.util.SequenceUtil;

/**
 * 推送通知业务处理类
 * 
 */
public class NotificationMessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(NotificationMessageHandler.class);
	/**
	 * 一台设备上存储的用户信息(DEVICE_PREFIX_KEY + 设备标识)
	 */
	private static final String DEVICE_PREFIX_KEY = "DEVICE:";
	/**
	 * 用户使用的设备信息
	 */
	private static final String DEVICE_USER_PREFIX_KEY = "DEVICE_USER:";
	
	/**
	 * Android推送服务
	 */
	private AndroidJPushService androidService;
	/**
	 * IOS推送服务
	 */
	private ApplePushNotificationService appleService;
	/**
	 * 单例
	 */
	private static NotificationMessageHandler instance;
	/**
	 * 生产者-消费者队列
	 */
	private BlockingQueue<String> blockingQueue;
	/**
	 * 推送消息集合
	 * 
	 * key值为:
	 * msg_id
	 * 
	 * value值为:
	 * PayloadMessage
	 */
	private Map<String, Object> pushMessages = new ConcurrentHashMap<String, Object>();
	/**
	 * 用户还未推送的消息列表
	 * 
	 * key值为：
	 * 用户ID
	 * 
	 * value值为：
	 * 还未推送的msg_id列表
	 */
	private Map<String, Set<String>> waitingForPushMessages = new ConcurrentHashMap<String, Set<String>>();
	/**
	 * 队列大小
	 */
	private static final int allowedQueueSize = 5000;
	
	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	public static NotificationMessageHandler getInstance() {
		if (instance == null) {
			synchronized (NotificationMessageHandler.class) {
				if (instance == null) {
					instance = new NotificationMessageHandler();
				}
			}
		}
		return instance;
	}
	
	private NotificationMessageHandler() {
		initialize();
	}
	
	private void initialize() {
		androidService = AndroidJPushService.getInstance();
		appleService = ApplePushNotificationService.getInstance();
		
		blockingQueue=new ArrayBlockingQueue<String>(allowedQueueSize);
		initThread();
		
		// IOS测试数据
		// PayloadMessage message = new PayloadMessage();
		// List<String> deviceTokens = new ArrayList<String>();
		// deviceTokens.add("d3f0418b216aeab20c25bd7aa0d6e97ced32878c8666dbc2f0925caf6e4d4faa");
		// String alert = "123456789!!!这是测试数据!!!";
		// message.setAlert(alert);
		// message.setDevice_tokens(deviceTokens);
		// pushIOSNotifications(message);
	}
	
	/**
	 * 推送Android客户端通知消息
	 */
	private void pushAndroidNotifications(PayloadMessage message) {
		if (message == null) {
			return;
		}
		List<String> deviceTokens = message.getDevice_tokens();
		if (deviceTokens == null || deviceTokens.size() == 0) {
			return;
		}
		String alert = message.getAlert();
		for (String deviceToken : deviceTokens) {
			logger.info("PushNotificationHandler pushAndroidNotifications, deviceToken : " + deviceToken + " alert : " + alert);
			androidService.sendPush_all_alias_alert(deviceToken, alert);
		}
	}
	
	/**
	 * 推送IOS客户端通知消息
	 */
	private void pushIOSNotifications(PayloadMessage message) {
		if (message == null) {
			return;
		}
		List<String> deviceTokens = message.getDevice_tokens();
		if (deviceTokens == null || deviceTokens.size() == 0) {
			return;
		}
		String alert = message.getAlert();
		int badge = (message.getBadge() == null) ? 1: message.getBadge();
		
		// String deviceToken = "26f4c2253a878dc52af47ebd431d5684cf67491efd2c08ebacf0f0e87fced87e";
		for (String deviceToken : deviceTokens) {
			if (deviceToken != null && !deviceToken.matches("\\s*")) {
				// 替换device_token中可能出现的空格
				deviceToken = deviceToken.replaceAll(" ", "");
				logger.info("PushNotificationHandler pushIOSNotifications, deviceToken : " + deviceToken + " alert : " + alert);
				List<String> tokens = new ArrayList<String>();
				tokens.add(deviceToken);
				appleService.pushNotification(tokens, alert, badge);
			}
		}
	}
	
	/**
	 * 添加任务到推送队列
	 */
	public boolean push(String userId, PayloadMessage message) {
		int size = blockingQueue.size();
		logger.info("消息推送 : 当前等待发送队列数     " + size);
		if (size >= allowedQueueSize) {
			logger.info("消息推送 : 队列达到最大限制数, 请等待!");
			return false;
		}

		// 阻塞方式, 若队列中插入的元素超过最大限制, 则阻塞直至可以插入
		// try {
		// blockingQueue.put(object);
		// } catch (InterruptedException e) {
		//
		// }
		// 非阻塞方式, 若队列中插入的元素超过最大限制, 则返回false
		String key = SequenceUtil.createSequence();
		boolean success = blockingQueue.offer(key);
		pushMessages.put(key, message);
		
		Set<String> keyList = waitingForPushMessages.get(userId);
		if (keyList == null) {
			keyList = new HashSet<String>();
		}
		keyList.add(key);
		waitingForPushMessages.put(userId, keyList);
		return success;
	}
	
	/**
	 * 移除指定用户还未完成推送的消息
	 * 
	 * @param userId
	 */
	public void removeUserNotification(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return;
		}
		Set<String> sets = waitingForPushMessages.remove(userId);
		if (sets != null && sets.size() > 0) {
			for (String s : sets) {
				pushMessages.remove(s);
			}
		}
	}
	
	/**
	 * 外部推送
	 */
	private void pushNotifications(PayloadMessage message) {
		if (message == null) {
			return;
		}
		List<String> deviceTokens = message.getDevice_tokens();
		List<Integer> deviceTypes = message.getDevice_types();
		
		int tokensSum = deviceTokens.size();
		int typesSum = deviceTypes.size();
		
		if (deviceTokens == null || tokensSum == 0) {
			return;
		}
		if (deviceTypes == null || typesSum == 0) {
			return;
		}
		if (tokensSum != typesSum) {
			return;
		}
		
		for (int i = 0; i < tokensSum; i++) {
			String deviceToken = deviceTokens.get(i);
			Integer deviceType = deviceTypes.get(i);
			
			PayloadMessage payload = new PayloadMessage();
			List<String> tokens = new ArrayList<String>();
			tokens.add(deviceToken);
			String title = message.getTitle();
			String alert = message.getAlert();
			payload.setDevice_tokens(tokens);
			payload.setTitle(title);
			payload.setAlert(alert);
			
			if (deviceToken != null && deviceType == 12) {
				// IOS客户端
				pushIOSNotifications(payload);
			} else {
				// 默认Android客户端
				pushAndroidNotifications(payload);
			}
		}
	}
	
	
	/**
	 * 初始化推送线程
	 */
	private void initThread() {
		// int count = Runtime.getRuntime().availableProcessors() + 1;
		int count = 1;
		logger.info("消息推送 ：当前启动线程池数量    " + count);
		for(int i = 0; i < count; i++) {
			Thread thread = new Thread(new Runnable(){
				public void run() {
					while(true) {
						try {
							// 队列中没有数据时, 不会堵塞, 直接返回null
							// blockingQueue.poll();
							// 队列中没有数据时, 会堵塞, 直至有新数据
							String key = blockingQueue.take();
							Object message = pushMessages.get(key);
							if (message != null) {
								// 业务处理
								pushNotifications((PayloadMessage)message);
							}
							
							// 休眠
							Thread.sleep(500);
						} catch (InterruptedException e) {
							logger.error("消息推送 ： 队列出错", e);
						}
					}
				}
			}, "PushNotification_" + i);
			thread.start();
		}
	}
	
	
	
	/**
	 * 获取用户的设备token
	 */
	public String getDeviceToken(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该用户为系统用户或特殊用户, 返回默认值: null
			return null;
		}
		
		userId = getBareUserId(userId);
		// 默认返回null
		String deviceToken = null;
		// 查询userId用户登录设备token
		String deviceUid = getDeviceUid(userId);
		if (deviceUid != null) {
			// 查询userId用户登录设备类型
			int deviceType = 11;
			String key = DEVICE_PREFIX_KEY + deviceUid;
			List<String> res = hmget(key, "device_type");
			if (res != null && res.size() > 0) {
				String temp = res.get(0);
				if (temp != null) {
					deviceType = temp.equalsIgnoreCase("12") ? 12 : 11;
				}
			}
			if (deviceType == 12) {
				List<String> res2 = hmget(key, "device_token");
				if (res2 != null && res2.size() > 0) {
					String temp2 = res2.get(0);
					if (temp2 != null) {
						deviceToken = temp2.equalsIgnoreCase("") ? null : temp2;
					}
				}
			} else {
				deviceToken = deviceUid;
			}
		}
		
		return deviceToken;
	}
	
	/**
	 * 获取用户的设备类型
	 */
	public int getDeviceType(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该用户为系统用户或特殊用户, 返回默认值: -1
			return -1;
		}
		
		userId = getBareUserId(userId);
		int deviceType = 11;
		// 查询userId用户登录设备token
		String deviceUid = getDeviceUid(userId);
		if (deviceUid != null) {
			// 查询userId用户登录设备类型
			String key = DEVICE_PREFIX_KEY + deviceUid;
			List<String> res = hmget(key, "device_type");
			if (res != null && res.size() > 0) {
				String temp = res.get(0);
				if (temp != null) {
					deviceType = temp.equalsIgnoreCase("12") ? 12 : 11;
				}
			}
		}
		
		return deviceType;
	}
	
	/**
	 * 查询该用户使用的设备标识
	 * 
	 * @param userId
	 * @return
	 */
	public String getDeviceUid(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			return null;
		}
		userId = getBareUserId(userId);
		String deviceUid = null;
		String key = DEVICE_USER_PREFIX_KEY + userId;
		List<String> res = hmget(key, "device_uid");
		if (res != null && res.size() > 0) {
			String temp = res.get(0);
			if (temp != null && !temp.matches("\\s*")) {
				deviceUid = temp;
			}
		}
		return deviceUid;
	}
	
	public String getBareUserId(String userId) {
		if (userId == null || userId.matches("\\s*")) {
			// 该userId非法
			return null;
		}
		// userId有四种可能性
		// 10000
		// 10000/11
		// 10000@888888
		// 10000@888888/11
		String temp = null;
		int atIndex = userId.indexOf("@");
		int slashIndex = userId.indexOf("/");
		if (atIndex > 0) {
			temp = userId.substring(0, atIndex);
		} else {
			if (slashIndex > 0) {
				temp = userId.substring(0, slashIndex);
			} else {
				temp = userId;
			}
		}
		return temp;
	}
	
	private static List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = JedisFactory.getResource();
			res = jedis.hmget(key, fields);
		} catch (Exception e) {
			logger.error("ModulesMessageHandler redis hmget error, " + e.getMessage(), e);
			JedisFactory.returnBrokenResource(jedis);
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return res;
	}
	
}
