package com.jihuiduo.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


public class ActiveMQMessageSender {
    private JmsTemplate jmsTemplate; 
    private Destination destination; 
    
	public void sendMessageData(final String message) {
//		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.send(destination,new MessageCreator(){  
            @Override  
            public TextMessage createMessage(Session session) throws JMSException {  
            	return session.createTextMessage(message);
//                MapMessage message=session.createMapMessage();  
//                message.setString("1", "2");
//                return message;
                
            }  
        }); 
	}
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}


}
