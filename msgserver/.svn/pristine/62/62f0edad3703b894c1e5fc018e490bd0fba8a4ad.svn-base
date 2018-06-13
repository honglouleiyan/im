package com.jihuiduo.msgserver.protocol.im;

import java.util.Date;

/**
 * 离线消息数据结构
 *
 */
public class OfflineMessage {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 离线消息生产者
	 */
	private String user_id;
	/**
	 * 离线消息消费者
	 */
	private String dst_user_id;
	/**
	 * 消息类型
	 * 同 IMMessage Type
	 */
	private Integer message_type;
	/**
	 * 消息id
	 */
	private String message_id;
	/**
	 * 消息(需要支持多种消息类型, 例如聊天消息、好友订阅消息、群组信息推送等)
	 */
	private String message;
	/**
	 * 消息大小(单位: 字节byte)
	 */
	private Long message_size;
	/**
	 * 消息有效截止日期
	 */
	private Date deadline;
	/**
	 * 离线消息生产时间
	 */
	private Date create_time;
	
	public OfflineMessage() {
		
	}
	
	public OfflineMessage(Long id, String user_id, String dst_user_id, Integer message_type,
			String message_id, String message, Long message_size,
			Date deadline, Date create_time) {
		this.id = id;
		this.user_id = user_id;
		this.dst_user_id = dst_user_id;
		this.message_type = message_type;
		this.message_id = message_id;
		this.message = message;
		this.message_size = message_size;
		this.deadline = deadline;
		this.create_time = create_time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getDst_user_id() {
		return dst_user_id;
	}

	public void setDst_user_id(String dst_user_id) {
		this.dst_user_id = dst_user_id;
	}

	public Integer getMessage_type() {
		return message_type;
	}

	public void setMessage_type(Integer message_type) {
		this.message_type = message_type;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getMessage_size() {
		return message_size;
	}

	public void setMessage_size(Long message_size) {
		this.message_size = message_size;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
