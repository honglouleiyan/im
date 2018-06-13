package com.jihuiduo.biz.notification.apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.config.SystemConfig;
import com.jihuiduo.msgserver.starter.SystemInitService;

/**
 * 苹果手机APNS推送业务
 */
public class ApplePushNotificationService {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApplePushNotificationService.class);
	/**
	 * 单例模式
	 */
	private static ApplePushNotificationService instance;

	/**
	 * APNS变量
	 */
	private String host;
	private String testHost;
	private int port;
	private int testPort;
	private String certificateName;
	private String testCertificateName;
	private String certificatePassword;
	private String testCertificatePassword;
	private String certificatePath;
	private boolean testOn;
	
	public static void main(String[] args) {
		// String token1 = "f1c29ff6 77a6e43d 7109b668 d11ef4af cb105ece 2d4c0ea3 2b889ff2 c79a48a8";
		// String token2 = "9c787992 5839ea77 1093b1af 42cd264a 01fb0a7e 9f90d59d c8ea449f d5d3c4a5";	
	}

	/**
	 * 单例模式
	 */
	public static ApplePushNotificationService getInstance() {
		if (instance == null) {
			synchronized (ApplePushNotificationService.class) {
				if (instance == null) {
					instance = new ApplePushNotificationService();
				}
			}
		}
		return instance;
	}

	private ApplePushNotificationService() {
		init();
	}

	/**
	 * 初始化参数
	 */
	private void init() {
		host = SystemConfig.getApnsHost();
		testHost = SystemConfig.getApnsTestHost();
		port = SystemConfig.getApnsPort();
		testPort = SystemConfig.getApnsTestPort();
		certificateName = SystemConfig.getApnsCertificateName();
		testCertificateName = SystemConfig.getApnsTestCertificateName();
		certificatePassword = SystemConfig.getApnsCertificatePassword();
		testCertificatePassword = SystemConfig.getApnsTestCertificatePassword();
		certificatePath = SystemInitService.getConfDir();
		testOn = SystemConfig.getApnsTestOn();
	}

	/**
	 * 推送通知
	 */
	public void pushNotification(List<String> deviceTokens, String message, int badge) {
		logger.info("ApplePushNotificationService pushNotification");
		try {
			PushNotificationPayload payload = new PushNotificationPayload();
			// 推送内容过多, 仅截取前50个字
			if (message != null && message.length() > 50) {
				message = message.substring(0, 50) + "...";
			}
			payload.addAlert(message);
			payload.addBadge(badge);
			payload.addSound("default");
			
			PushNotificationManager pushManager = new PushNotificationManager();

			// 连接APNS服务的*.p12文件位置
			String targetPath;
			String password;
			if (testOn) {
				targetPath = certificatePath + testCertificateName;
				password = testCertificatePassword;
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(targetPath, password, false));
			} else {
				targetPath = certificatePath + certificateName;
				password = certificatePassword;
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(targetPath, password, true));
			}

			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (deviceTokens == null || deviceTokens.size() == 0) {
				logger.info("ApplePushNotificationService pushNotification deviceTokens == null || deviceTokens.size() == 0");
				return;
			}
			boolean isGroupSending = false;
			int size = deviceTokens.size();
			if (size == 1) {
				isGroupSending = false;
			} else {
				isGroupSending = true;
			}
			logger.info("deviceTokens的个数 :" + size + " message : " + message + " badge : " + badge);
			if (!isGroupSending) {
				Device device = new BasicDevice();
				device.setToken(deviceTokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payload);
				notifications.add(notification);
			} else {
				List<Device> device = new ArrayList<Device>();
				for (String token : deviceTokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payload, device);
			}
			// 统计成功、失败次数
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			logger.info("ApplePushNotificationService pushNotification failed : " + failed);
			int successful = successfulNotifications.size();
			logger.info("ApplePushNotificationService pushNotification successful : " + successful);
			
			pushManager.stopConnection();
		} catch (Exception e) {
			logger.error("ApplePushNotificationService pushNotification 发生错误, " + e.getMessage(), e);
		}
	}

	/**
	 * 推送通知
	 */
	public void pushNotificationWithExtras(List<String> deviceTokens, String message, int badge, Map<String, String> extras) {
		logger.info("ApplePushNotificationService pushNotificationWithExtras");
		try {
			// message={"aps":{"alert":"iphone推送测试"}}
			// PushNotificationPayload payLoad = PushNotificationPayload.fromJSON(message);
			
			PushNotificationPayload payload = new PushNotificationPayload();
			// 推送内容过多, 仅截取前50个字
			if (message != null && message.length() > 50) {
				message = message.substring(0, 50) + "...";
			}
			payload.addAlert(message);
			payload.addBadge(badge);
			payload.addSound("default");
			if (extras != null && extras.size() > 0) {
				Set<String> keys = extras.keySet();
				for (String key : keys) {
					String value = extras.get(key);
					payload.addCustomDictionary(key, value);
				}
			}
			
			PushNotificationManager pushManager = new PushNotificationManager();

			// 连接APNS服务的*.p12文件位置
			String targetPath;
			String password;
			// AppleNotificationServerBasicImpl中的参数boolean production
			// true:产品发布推送服务
			// false:产品测试推送服务
			if (testOn) {
				targetPath = certificatePath + testCertificateName;
				password = testCertificatePassword;
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(targetPath, password, false));
			} else {
				targetPath = certificatePath + certificateName;
				password = certificatePassword;
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(targetPath, password, true));
			}
			
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (deviceTokens == null || deviceTokens.size() == 0) {
				logger.info("ApplePushNotificationService pushNotificationWithExtras deviceTokens == null || deviceTokens.size() == 0");
				return;
			}
			
			boolean isGroupSending = false;
			int size = deviceTokens.size();
			if (size == 1) {
				isGroupSending = false;
			} else {
				isGroupSending = true;
			}
			logger.info("deviceTokens的个数 :" + size + " message : " + message + " badge : " + badge);
			if (!isGroupSending) {
				Device device = new BasicDevice();
				device.setToken(deviceTokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payload);
				notifications.add(notification);
			} else {
				List<Device> device = new ArrayList<Device>();
				for (String token : deviceTokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payload, device);
			}
			// 统计成功、失败次数
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			logger.info("ApplePushNotificationService pushNotificationWithExtras failed : " + failed);
			int successful = successfulNotifications.size();
			logger.info("ApplePushNotificationService pushNotificationWithExtras successful : " + successful);
			
			pushManager.stopConnection();
		} catch (Exception e) {
			logger.error("ApplePushNotificationService pushNotificationWithExtras 发生错误, " + e.getMessage(), e);
		}

	}

}
