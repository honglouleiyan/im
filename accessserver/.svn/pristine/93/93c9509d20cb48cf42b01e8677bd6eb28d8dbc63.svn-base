package com.jihuiduo.netproxy.mq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.netproxy.cache.DeviceServerCache;
import com.jihuiduo.netproxy.cache.UserServerCache;
import com.jihuiduo.netproxy.http.HttpResponse;
import com.jihuiduo.netproxy.server.Connect;
import com.jihuiduo.netproxy.server.NewConnect;
import com.jihuiduo.netproxy.server.online.DeviceOnline;
import com.jihuiduo.netproxy.utils.SequenceUtil;
public class TopicListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(TopicListener.class);
	@Override
	public void onMessage(Message message) {
//		logger.info("recive topic message:" + message);
		if (message instanceof TextMessage) { 
			try {
				logger.info("收到ActiveMQQueue消息 : TextMessage");
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				logger.info("收到ActiveMQQueue消息 :text " + text);
				// 业务处理
				doReceiveAndExecute(text);
			} catch (Exception e) {
				logger.error("收到ActiveTopic消息-TextMessage时发生错误, " + e.getMessage(), e);
			}
		} else if(message instanceof MapMessage) {
			System.out.println("MapMessage");
		}
	}  

	/**
	 * 业务处理
	 * @param text
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void doReceiveAndExecute(String text) {
		JSONObject jsonObject = JSONObject.fromObject(text);
		if(jsonObject.get(DeviceOnline.LOGIN_STATE).equals(1)) {
			logger.info("登录通知");
			String deviceUid = jsonObject.get(DeviceOnline.DEVICE_UID).toString();
			String userId = jsonObject.get(DeviceOnline.USER_ID).toString();
			if(StringUtils.isNotBlank(deviceUid) && StringUtils.isNotBlank(userId)) {
				UserServerCache.getInstance().putSingle(userId, deviceUid);
			}
		} else {
			logger.info("退出登录通知");
			String userId = jsonObject.get(DeviceOnline.USER_ID).toString();
			if( StringUtils.isNotBlank(userId)) {
				UserServerCache.getInstance().remove(userId);
			}
		}
	}
	
}
