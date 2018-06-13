package com.jihuiduo.msgserver.protocol.im;

import java.util.Date;

/**
 * 历史消息数据结构
 *
 */
public class HistoryMessage {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 消息发送者
	 */
	private String user_id;
	/**
	 * 消息接收者
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
	 * 消息大小
	 */
	private Long message_size;
	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 消息创建时间
	 */
	private Date create_time;
	/**
	 * 是否删除
	 */
	private Integer is_delete;

	public HistoryMessage() {
		
	}
	
	public HistoryMessage(Long id, String user_id, String dst_user_id,
			Integer message_type, String message_id, Long message_size,
			String message, Date create_time, Integer is_delete) {
		this.id = id;
		this.user_id = user_id;
		this.dst_user_id = dst_user_id;
		this.message_type = message_type;
		this.message_id = message_id;
		this.message_size = message_size;
		this.message = message;
		this.create_time = create_time;
		this.is_delete = is_delete;
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

	public Long getMessage_size() {
		return message_size;
	}

	public void setMessage_size(Long message_size) {
		this.message_size = message_size;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}
	
}
