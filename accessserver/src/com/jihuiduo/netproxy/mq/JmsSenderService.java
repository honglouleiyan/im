package com.jihuiduo.netproxy.mq;


public interface JmsSenderService {
	
	public void sendMessageData(String message);
	public void sendTopicData(String message);
}
