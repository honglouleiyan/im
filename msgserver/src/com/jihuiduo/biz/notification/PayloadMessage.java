package com.jihuiduo.biz.notification;

import java.util.List;
import java.util.Map;

/**
 * 手机通知栏推送通知消息
 */
public class PayloadMessage {
	/**
	 * 推送目标设备标识集合
	 */
	private List<String> device_tokens;
	/**
	 * 推送设备类型, 和目标设备标识一一对应
	 * 11:Android
	 * 12:iOS
	 */
	private List<Integer> device_types;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 告警内容
	 */
	private String alert;
	/**
	 * 未读消息条数
	 */
	private Integer badge;
	/**
	 * 提示音乐
	 */
	private String sound;
	/**
	 * 附加参数
	 */
	private Map<String, String> extras;
	
	public PayloadMessage() {
		
	}
	
	public PayloadMessage(List<String> device_tokens,
			List<Integer> device_types, String title, String alert,
			Integer badge, String sound, Map<String, String> extras) {
		this.device_tokens = device_tokens;
		this.device_types = device_types;
		this.title = title;
		this.alert = alert;
		this.badge = badge;
		this.sound = sound;
		this.extras = extras;
	}

	public List<String> getDevice_tokens() {
		return device_tokens;
	}
	
	public void setDevice_tokens(List<String> device_tokens) {
		this.device_tokens = device_tokens;
	}
	
	public List<Integer> getDevice_types() {
		return device_types;
	}
	
	public void setDevice_types(List<Integer> device_types) {
		this.device_types = device_types;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAlert() {
		return alert;
	}
	
	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	public Integer getBadge() {
		return badge;
	}
	
	public void setBadge(Integer badge) {
		this.badge = badge;
	}
	
	public String getSound() {
		return sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	public Map<String, String> getExtras() {
		return extras;
	}
	
	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}
	
}
