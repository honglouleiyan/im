package com.jihuiduo.netproxy.server.online;

import java.util.Map;

/**
 * 用户、设备上下线广播
 *
 */
public class UserOnlineOfflineBroadcast {
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
	 * 通知类型
	 * 1 : 用户上下线通知
	 * 2 : 设备上下线通知
	 */
	private Integer type;
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
	/**
	 * 附加参数
	 * 
	 */
	private Map<String, Object> extras;
	
	public UserOnlineOfflineBroadcast() {
		
	}
	
	public UserOnlineOfflineBroadcast(String user_id, Integer device_type,
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
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Map<String, Object> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}

}
