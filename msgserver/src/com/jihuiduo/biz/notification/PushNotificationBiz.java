package com.jihuiduo.biz.notification;

import java.util.ArrayList;
import java.util.List;

import com.jihuiduo.msgserver.protocol.http.HttpPacket;

import javapns.devices.Device;
import javapns.notification.PushNotificationManager;

public class PushNotificationBiz {
	
//	public void pushNotification(BasicPacket packet) {
//    String to = "";
//    String from = "";
//    
//    // 如果用户名没有node节点，则不推送
//    // 例如服务器返回的回执信息，from为msgserver，则不经过APNS
//    if (to == null || "".equalsIgnoreCase(to) || from == null || "".equalsIgnoreCase(from)) {
//		return;
//	}
//   
//    // 获取device_token
//    String device_token = "";
//    boolean canPush = (device_token != null);
//    if (!canPush) {
//    	// 该消息接收用户没有绑定苹果设备, 不再处理
//		return;
//	}
//    // 发送者名字
//    String senderName = "";
//    if (senderName == null || senderName.length() == 0) {
//		senderName = "";
//	}
//    
//	// 读取7天之内的未读消息
//	// 7*24*60*60*1000
//	// long deadline = System.currentTimeMillis() - 604800000L;
//	// 接收者的未读消息条数
//	// int unreadCount = 0;
//    
//    // 通过内存数据读取未读消息条数
//    String key = to;
//    // AppRunningState.getInstance().increaseAppUnreadMessagesCount(key);
//    // int unreadCount = AppRunningState.getInstance().getAppUnreadMessagesCount(key);
//    
//    // 推送给苹果设备的通知消息集合
//    List<AppleMessage> ams = new ArrayList<AppleMessage>();
//    
//    if (ic == 206) {
//    	// 该消息是客服消息
//    	
//    	PMessageModel pm = JsonUtil.getJsonUtil().getGson().fromJson(data, PMessageModel.class);
//    	// 客服消息集合
//    	List<PMessageStyle> psList = pm.getBody();
//    	for (PMessageStyle ps : psList) {
//    		AppleMessage am206 = new AppleMessage();
//    		am206.setSenderName(senderName);
//    		am206.setUnreadCount(unreadCount);
//    		am206.setDeviceToken(token.getDevice_token());
//    		
//    		int pStyle = ps.getStyle();
//        	switch (pStyle) {
//			case 1:
//				// 文字
//				String target = JsonUtil.getJsonUtil().getGson().toJson(ps);
//				PMessageStyle1 style1 = JsonUtil.getJsonUtil().getGson().fromJson(target, PMessageStyle1.class);
//				String content1 = style1.getContent();
//				am206.setContent(content1);
//				break;
//			case 2:
//				// [图片]
//				String content2 = "[图片]";
//				am206.setContent(content2);
//				break;
//			case 3:
//				// [语音]
//				String content3 = "[语音]";
//				am206.setContent(content3);
//				break;
//			case 4:
//				// [视频]
//				String content4 = "[视频]";
//				am206.setContent(content4);
//				break;
//			default:
//				String defaultContent = "[未知消息]";
//				am206.setContent(defaultContent);
//				break;
//			}
//        	ams.add(am206);
//    	}
//    	
//	} else if (ic == 202) {
//		// 该消息是公众消息
//		EMessageModel em = JsonUtil.getJsonUtil().getGson().fromJson(data, EMessageModel.class);
//		List<EMessageStyle> esList = em.getBody();
//		for (EMessageStyle es : esList) {
//			AppleMessage am202 = new AppleMessage();
//    		am202.setSenderName(senderName);
//    		am202.setUnreadCount(unreadCount);
//    		am202.setDeviceToken(token.getDevice_token());
//    		
//			int eStyle = es.getStyle();
//			if (eStyle == 1) {
//				// [标题]
//				String content1 = "[标题]";
//				am202.setContent(content1);
//			} else if (eStyle >1 && eStyle <9) {
//				// [图片]
//				String content2 = "[图片]";
//				am202.setContent(content2);
//			} else if (eStyle == 9) {
//				// [语音]
//				String content9 = "[语音]";
//				am202.setContent(content9);
//			} else if (eStyle == 10) {
//				// [视频]
//				String content10 = "[视频]";
//				am202.setContent(content10);
//			} else {
//				// [未知消息]
//				String content11 = "[未知消息]";
//				am202.setContent(content11);
//			}
//			ams.add(am202);
//		}
//		
//	} else {
//		// 不做处理
//	}
//    
//    // 推送苹果设备通知消息
//    if (ams != null && ams.size() > 0) {
//		sendAppleMessages(ams);
//	}
//}

	
	
//	public void sendAppleMessage(AppleMessage message) {
//		try {
//			// 定义消息模式
//			PayLoad payLoad = new PayLoad();
//			String contents = message.getContent();
//			// 推送内容过多, 仅截取前50个字
//			if (contents.length() > 50) {
//				contents = contents.substring(0, 50) + "...";
//			}
//			payLoad.addAlert("[" + message.getSenderName() + "]:" + contents);
//			// 消息推送标记数, 小红圈中显示的数字
//			payLoad.addBadge(message.getUnreadCount());
//			payLoad.addSound("default");
//			// 注册deviceToken
//			PushNotificationManager pushManager = PushNotificationManager.getInstance();
//			pushManager.addDevice("iPhone", message.getDeviceToken());
//			
//			// 用于JAVA后台连接APNS服务的*.p12文件位置
//			String targetPath;
//			if (testOn) {
//				targetPath = certificatePath + testCertificateName;
//				pushManager.initializeConnection(testHost, testPort, targetPath, testCertificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//			} else {
//				targetPath = certificatePath + certificateName;
//				pushManager.initializeConnection(host, port, targetPath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//			}
//			
//			// 发送推送
//			Device client = pushManager.getDevice("iPhone");
//			pushManager.sendNotification(client, payLoad);
//			
//			// 删除deviceToken
//			pushManager.removeDevice("iPhone");
//			
//			// 停止连接APNS
//			pushManager.stopConnection();
//		} catch (Exception e) {
//			log.error("推送苹果通知消息时发生错误, "+e.getMessage(), e);
//		} finally {
//			
//		}
//	}
//	
//	public void sendAppleMessages(List<AppleMessage> messages) {
//		if (messages == null || messages.size() == 0) {
//			return;
//		}
//		try {
//			PushNotificationManager pushManager = PushNotificationManager.getInstance();
//			int size = messages.size();
//			List<PayLoad> payLoads = new ArrayList<PayLoad>();
//			for (int i = 0; i < size; i++) {
//				AppleMessage message = messages.get(i);
//				PayLoad payLoad = new PayLoad();
//				String contents = message.getContent();
//				// 推送内容过多, 仅截取前50个字
//				if (contents.length() > 50) {
//					contents = contents.substring(0, 50) + "...";
//				}
//				payLoad.addAlert("[" + message.getSenderName() + "]:" + contents);
//				// 消息推送记数, 小红圈中显示的数字
//				payLoad.addBadge(message.getUnreadCount());
//				payLoad.addSound("default");
//				// 添加集合
//				payLoads.add(payLoad);
//				// 注册deviceToken
//				pushManager.addDevice("iPhone" + i, message.getDeviceToken());
//			}
//			
//			// 连接APNS服务的*.p12文件位置
//			String targetPath;
//			if (testOn) {
//				targetPath = certificatePath + testCertificateName;
//				pushManager.initializeConnection(testHost, testPort, targetPath, testCertificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//			} else {
//				targetPath = certificatePath + certificateName;
//				pushManager.initializeConnection(host, port, targetPath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//			}
//			
//			// 发送推送
//			int s = payLoads.size();
//			for (int j = 0; j < s; j++) {
//				Device client = pushManager.getDevice("iPhone" + j);
//				pushManager.sendNotification(client, payLoads.get(j));
//				// 删除deviceToken
//				pushManager.removeDevice("iPhone" + j);
//			}
//			
//			// 停止连接APNS
//			pushManager.stopConnection();
//			
//		} catch (Exception e) {
//			log.error("推送苹果通知消息集合时发生错误, "+e.getMessage(), e);
//		} finally {
//			
//		}
//	}
	
}
