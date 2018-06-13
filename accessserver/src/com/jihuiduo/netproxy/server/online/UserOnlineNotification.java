package com.jihuiduo.netproxy.server.online;

public class UserOnlineNotification {
	/**
	 * 用户id
	 */
	private String user_id;
	/**
	 * 设备类型
	 * 11 Android
	 * 12 iOS
	 * 默认值为11
	 */
	private Integer device_type;
	/**
	 * 设备标识
	 */
	private String device_uid;
	/**
	 * 登录状态
	 * 0 异常
	 * 1 正常
	 */
	private Integer login_state;
	/**
	 * TCP长连接状态
	 * 0 异常
	 * 1 正常
	 */
	private Integer connection_state;
	/**
	 * 通知时间毫秒数
	 */
	private Long time;
	
	
	public UserOnlineNotification() {
	}
	
	public UserOnlineNotification(String user_id, Integer device_type,
			String device_uid, Integer login_state, Integer connection_state,
			Long time) {
		this.user_id = user_id;
		this.device_type = device_type;
		this.device_uid = device_uid;
		this.login_state = login_state;
		this.connection_state = connection_state;
		this.time = time;
	}

	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public Integer getDevice_type() {
		return device_type;
	}
	
	public void setDevice_type(Integer device_type) {
		this.device_type = device_type;
	}
	
	public String getDevice_uid() {
		return device_uid;
	}
	
	public void setDevice_uid(String device_uid) {
		this.device_uid = device_uid;
	}
	
	public Integer getLogin_state() {
		return login_state;
	}
	
	public void setLogin_state(Integer login_state) {
		this.login_state = login_state;
	}
	
	public Integer getConnection_state() {
		return connection_state;
	}
	public void setConnection_state(Integer connection_state) {
		this.connection_state = connection_state;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}

}
