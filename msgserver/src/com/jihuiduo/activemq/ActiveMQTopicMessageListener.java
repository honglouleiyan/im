package com.jihuiduo.activemq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.biz.message.BusinessServerMessageHandler;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;

/**
 * ActiveMQ广播消息监听器
 * 
 */
public class ActiveMQTopicMessageListener extends ActiveMQMessageHandler implements MessageListener {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQTopicMessageListener.class);
	
	private static final Logger activeMQLogger = LoggerFactory.getLogger("activemq");
	
	@Override
	public void onMessage(Message message) {
		activeMQLogger.debug("收到ActiveMQTopic消息 : " + message);
		if (message instanceof TextMessage) {
			activeMQLogger.debug("收到ActiveMQTopic消息 : TextMessage");
			try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			activeMQLogger.info("收到ActiveMQTopic消息 : " + text);
			logger.info("收到ActiveMQTopic消息 : " + text);
			if (text == null) {
				return;
			}
			// 业务处理
			doReceiveAndExecute(text);
			} catch (Exception e) {
				activeMQLogger.error("收到ActiveMQTopic消息-TextMessage时发生错误, " + e.getMessage(), e);
				logger.error("收到ActiveMQTopic消息-TextMessage时发生错误, " + e.getMessage(), e);
			}
		} else if(message instanceof MapMessage) {
			activeMQLogger.debug("收到ActiveMQTopic消息 : MapMessage");
		} else {
			activeMQLogger.debug("收到ActiveMQTopic消息 : UnKnown");
		}
	}
	
	/**
	 * 业务处理
	 * @param text
	 * @throws Exception
	 */
	private void doReceiveAndExecute(String text) throws Exception {
		HttpPacket packet = createInternalNotification(text);
		BusinessServerMessageHandler.getInstance().handle(packet, false);
	}
	
	/**
	 * 创建服务器内部错误回应
	 * 
	 */
	private HttpPacket createInternalNotification(String text) {
		HttpPacket packet = createUserOnlineNotification(text);
		return packet;
	}
	
	private HttpPacket createExternalNotification() {
		return null;
	}

}
