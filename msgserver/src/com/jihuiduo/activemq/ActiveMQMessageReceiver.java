package com.jihuiduo.activemq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class ActiveMQMessageReceiver {
    private Collection<String> collection; 

    public void receive(TextMessage message) {
		System.out.println("TextMessage接收信息" + message);
	      try {  
	            if (collection == null) {  
	                this.collection = new ArrayList<String>();  
	            }  
	            collection.add(message.getText());  
	        } catch (JMSException e) {  
	            e.printStackTrace();  
	        } 
	}

	public void handleMessage(TextMessage message) {
		System.out.println("TextMessage接收信息" + message);
	}
	
	public void handleMessage(String message) {
		System.out.println("String接收信息" + message);
	}


	public void handleMessage(byte[] message) {
		System.out.println("byte[]接收信息" + message);
	}

	public void handleMessage(Serializable message) {
		System.out.println("Serializable接收信息" + message);
	}

	public void handleMessage(Map<String, Object> message) {
		System.out.println("Map[]接收信息" + message);
		   Set<String> keySet = message.keySet();  
	        Iterator<String> keys = keySet.iterator();  
	        while (keys.hasNext()) {  
	            String key = keys.next();  
	            System.out.println(message.get(key));  
	        } 
		
	}
    public Collection<String> getCollection() {  
        return collection;  
    }  
  
    public void setCollection(Collection<String> collection) {  
        this.collection = collection;  
    }  
}
