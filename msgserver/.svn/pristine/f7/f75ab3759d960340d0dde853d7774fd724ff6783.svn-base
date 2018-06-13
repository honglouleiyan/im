package com.jihuiduo.biz.notification.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.config.SystemConfig;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送
 */
public class AndroidJPushService {
	/**
	 * 日志
	 */
    private static final Logger logger = LoggerFactory.getLogger(AndroidJPushService.class);
    /**
     * 单例模式
     */
    private static AndroidJPushService instance;
    
	private String appKey;
	private String masterSecret;
	private String testAppKey;
	private String testMasterSecret;
	private boolean testOn;
	
    public static AndroidJPushService getInstance() {
		if (instance == null) {
			synchronized (AndroidJPushService.class) {
				if (instance == null) {
					instance = new AndroidJPushService();
				}
			}
		}
		return instance;
	}
	
	private AndroidJPushService() {
		init();
	}
	
	/**
	 * 初始化参数
	 */
	private void init() {
		appKey = SystemConfig.getJPushAppKey();
		testAppKey = SystemConfig.getJPushTestAppKey();
		masterSecret = SystemConfig.getJPushMasterSecret();
		testMasterSecret = SystemConfig.getJPushTestMasterSecret();
		testOn = SystemConfig.getJPushTestOn();
	}

	/**
	 * 推送给所有的客户端通知消息
	 */
	public void sendPush_all_all_alert(String alert) {
	    PushPayload payload = PushPayload.alertAll(alert);
	    sendPush(payload);
	}
	
	/**
	 * 推送给user_id的客户端通知消息
	 */
    public void sendPush_all_alias_alert(String user_id, String alert) {
    	PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(user_id))
                .setNotification(Notification.alert(alert))
                .build();
    	sendPush(payload);
    }
    
    /**
     * 推送给标签为tag的客户端通知消息
     */
    public void sendPush_android_tag_alertWithTitle(String tag, String alert, String title) {
    	PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(alert, title, null))
                .build();
    	sendPush(payload);
    }
    
    public void sendPush_android_and_ios() {
    	PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                		.setAlert("alert content")
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle("Android Title").build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.addExtra("extra_key", "extra_value").build())
                		.build())
                .build();
    	sendPush(payload);
    }
    
    public void sendPush_ios_tagAnd_alertWithExtrasAndMessage() {
    	PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("alert")
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content("msg_content"))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    	sendPush(payload);
    }
    
    public void sendPush_ios_audienceMore_messageWithExtras() {
    	PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("msg_content")
                        .addExtra("from", "JPush")
                        .build())
                .build();
    	sendPush(payload);
    }
    
    /**
     * 推送消息
     */
    private void sendPush(PushPayload payload) {
	    // HttpProxy proxy = new HttpProxy("localhost", 3128);
	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        JPushClient jpushClient;
        if (!testOn) {
        	jpushClient = new JPushClient(masterSecret, appKey, 3);
		} else {
			jpushClient = new JPushClient(testMasterSecret, testAppKey, 3);
		}
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("AndroidJPushService result - " + result);
            logger.info("JPush推送结果 : " + result.isResultOK());
        } catch (APIConnectionException e) {
        	logger.error("AndroidJPushService Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
        	logger.error("AndroidJPushService Error response from JPush server. Should review and fix it. ", e);
        	logger.info("HTTP Status: " + e.getStatus());
        	logger.info("Error Code: " + e.getErrorCode());
        	logger.info("Error Message: " + e.getErrorMessage());
        	logger.info("Msg ID: " + e.getMsgId());
        }
	}
    
    public static void main(String[] args) {
		
	}
    
}

