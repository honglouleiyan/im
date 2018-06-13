package com.jihuiduo.msgserver.protocol.im;

/**
 * 消息类型
 */
public enum MessageType {
	/**
	 * 默认值
	 */
	normal(0),
	/**
	 * 普通单聊消息
	 */
	chat(1),
	/**
	 * 普通群聊消息
	 */
	groupchat(2),
	/**
	 * 错误消息
	 */
	error(-1);
	
	private int messageType;
	
	private MessageType(int messageType) {
		this.messageType = messageType;
	}
	
	public static MessageType fromInteger(int messageType) {
		if (messageType == normal.messageType) {
			return normal;
		} else if (messageType == chat.messageType) {
			return chat;
		} else if (messageType == groupchat.messageType) {
			return groupchat;
		} else if (messageType == error.messageType) {
			return error;
		}
		
		return null;
	}
	
	public int toInteger() {
		return messageType;
	}
	
}
