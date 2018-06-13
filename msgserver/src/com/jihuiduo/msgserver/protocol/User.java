package com.jihuiduo.msgserver.protocol;

/**
 * 用户基本信息
 * 
 */
public class User {
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 昵称
	 */
	private String nick;
	/**
	 * 手机号码
	 */
	private String mobile_phone;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 头像
	 */
	private String head;
	/**
	 * 性别
	 * 0 : 未知
	 * 1 : 男
	 * 2 : 女
	 */
	private Integer gender;
	/**
	 * 出生日期
	 */
	private Long birthday;
	/**
	 * 现居地
	 */
	private String current_domicile;
	/**
	 * 设备唯一标识符
	 */
	private String device_uid;
	/**
	 * 是否删除
	 * 0 : 正常
	 * 1 : 删除
	 */
	private Integer is_delete;
	/**
	 * 用户状态
	 * 0 : 正常
	 * 1 : 黑名单
	 * 2 : 禁言
	 */
	private Integer user_status;
	/**
	 * 最后登录时间
	 */
	private Long login_time;
	/**
	 * 最后登录类型
	 */
	private Integer login_type;
	/**
	 * 注册时间
	 */
	private Long create_time;
	/**
	 * 更新时间
	 */
	private Long update_time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public String getCurrent_domicile() {
		return current_domicile;
	}
	public void setCurrent_domicile(String current_domicile) {
		this.current_domicile = current_domicile;
	}
	public String getDevice_uid() {
		return device_uid;
	}
	public void setDevice_uid(String device_uid) {
		this.device_uid = device_uid;
	}
	public Integer getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}
	public Integer getUser_status() {
		return user_status;
	}
	public void setUser_status(Integer user_status) {
		this.user_status = user_status;
	}
	public Long getLogin_time() {
		return login_time;
	}
	public void setLogin_time(Long login_time) {
		this.login_time = login_time;
	}
	public Integer getLogin_type() {
		return login_type;
	}
	public void setLogin_type(Integer login_type) {
		this.login_type = login_type;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	
}
