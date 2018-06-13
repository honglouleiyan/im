package com.jihuiduo.biz.message;

/**
 * 重发消息次数统计
 *
 */
public class MessageRetryCount {
	
	/**
	 * 消息内容
	 */
	private String packet;
	/**
	 * 目标接收者
	 */
	private String dst_user_id;
	/**
	 * 用来统计消息的重复推送次数，初始默认值为0
	 */
	private int count;
	/**
	 * 最后一次发送消息的时间戳
	 */
	private long time;
	
	public MessageRetryCount(String packet, String dst_user_id, int count, long time) {
		this.packet = packet;
		this.dst_user_id = dst_user_id;
		this.count = count;
		this.time = time;
	}
	
	public String getPacket() {
		return packet;
	}
	
	public void setPacket(String packet) {
		this.packet = packet;
	}
	
	public String getDst_user_id() {
		return dst_user_id;
	}

	public void setDst_user_id(String dst_user_id) {
		this.dst_user_id = dst_user_id;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
