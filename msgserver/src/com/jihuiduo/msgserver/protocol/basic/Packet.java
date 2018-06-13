package com.jihuiduo.msgserver.protocol.basic;

/**
 * (文本协议)基础数据包
 *
 */
public abstract class Packet {
	/**
	 * 文本内容
	 */
	protected String packet;
	
	protected Packet() {
		
	}
	
	public Packet(String packet) {
		this.packet = packet;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}
	
	@Override
	public String toString() {
		return packet;
	}
	
}
