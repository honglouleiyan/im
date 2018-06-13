package com.jihuiduo.netproxy.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;



public class JmsSenderServiceImpl implements JmsSenderService {
    private JmsTemplate jmsTemplate; 
    private Destination destinationQueue; 
    private Destination destinationTopic; 
	@Override
	/*
	  * <p>Title: sendTopicData</p>
	  * <p>Description:发送队列 </p>
	  * @param message
	  * @see com.jihuiduo.netproxy.mq.JmsSenderService#sendTopicData(java.lang.String)
	  */
	public void sendMessageData(final String message) {
//		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.send(destinationQueue,new MessageCreator(){  
            @Override  
            public TextMessage createMessage(Session session) throws JMSException {  
            	return session.createTextMessage(message);
            }  
        }); 
	}
	
	/*
	  * <p>Title: sendTopicData</p>
	  * <p>Description: 发送广播</p>
	  * @param message
	  * @see com.jihuiduo.netproxy.mq.JmsSenderService#sendTopicData(java.lang.String)
	  */
	
	@Override
	public void sendTopicData(final String message) {
        jmsTemplate.send(destinationTopic,new MessageCreator(){  
            @Override  
            public TextMessage createMessage(Session session) throws JMSException {  
            	return session.createTextMessage(message);
            }  
        }); 
	}
	
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getDestinationQueue() {
		return destinationQueue;
	}

	public void setDestinationQueue(Destination destinationQueue) {
		this.destinationQueue = destinationQueue;
	}

	public Destination getDestinationTopic() {
		return destinationTopic;
	}

	public void setDestinationTopic(Destination destinationTopic) {
		this.destinationTopic = destinationTopic;
	}

}
