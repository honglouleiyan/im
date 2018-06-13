package com.jihuiduo.msgserver.protocol.im;

import java.util.List;
import java.util.Map;

/**
 * IM消息数据结构
 */
public class IMMessage {
	/**
	 * 消息ID
	 */
	private String msg_id;
	/**
	 * 发送者USER_ID
	 */
	private String user_id;
	/**
	 * 接收者USER_ID列表
	 * 
	 * 支持@用户
	 * 当@多个用户时, 接收者dst_user_id为列表形式
	 * 
	 * 支持硬件设备(发送给指定硬件设备)
	 * 
	 * 单聊时为目标用户USER_ID
	 * 群聊时格式如下 : 
	 * 用户USER_ID@群组GROUP_ID, 形如user_id@group_id
	 */
	private List<String> dst_user_id;
	/**
	 * 消息类型
	 * 
	 * ********1~99 普通消息********
	 * 1 : 好友聊天消息
	 * 2 : 群聊天消息
	 * 
	 * ********100~999 系统消息********
	 * 100 : 系统通知
	 * 101 : 系统强制手机端下线通知
	 * 701 : 评论消息
	 * 
	 * ********1000 辅助消息********
	 * 1000 : 帮小帮消息
	 * 
	 * ********1000+ 业务消息********
	 * 1001 : 帖子状态通知消息
	 */
	private Integer type;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 附件类型(该值为优先级大的附件类型)
	 * 1 文字
	 * 2 图片
	 * 3 位置
	 * 
	 * 11 语音
	 * 12 视频
	 * 13 文件
	 */
	private Integer att_type;
	/**
	 * 附件数组
	 */
	private List<Object> att;
	/**
	 * 消息状态
	 * -1 撤回
	 *  0 正常
	 *  1 送达
	 */
	private Integer state;
	/**
	 * 消息优先级
	 * 默认0
	 */
	private Integer priority;
	/**
	 * 是否是离线消息
	 * 0或null 在线消息
	 * 1 离线消息
	 */
	private Integer offline;
	/**
	 * 消息发送时间戳(毫秒)
	 */
	private Long time;
	/**
	 * 可选参数
	 * 例如:
	 * bang_id	123456
	 */
	private Map<String, Object> options;
	
	public IMMessage() {
		
	}
	
	public IMMessage(String msg_id, String user_id, List<String> dst_user_id, Integer type, 
			String title, String message, Integer att_type, List<Object> att, Integer state, 
			Integer priority, Integer offline, Long time) {
		this.msg_id = msg_id;
		this.user_id = user_id;
		this.dst_user_id = dst_user_id;
		this.type = type;
		this.title = title;
		this.message = message;
		this.att_type = att_type;
		this.att = att;
		this.state = state;
		this.priority = priority;
		this.offline = offline;
		this.time = time;
	}
	
	public IMMessage(String msg_id, String user_id, List<String> dst_user_id, Integer type, 
			String title, String message, Integer att_type, List<Object> att, Integer state, 
			Integer priority, Integer offline, Long time, Map<String, Object> options) {
		this.msg_id = msg_id;
		this.user_id = user_id;
		this.dst_user_id = dst_user_id;
		this.type = type;
		this.title = title;
		this.message = message;
		this.att_type = att_type;
		this.att = att;
		this.state = state;
		this.priority = priority;
		this.offline = offline;
		this.time = time;
		this.options = options;
	}
	
	public String getMsg_id() {
		return msg_id;
	}
	
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public List<String> getDst_user_id() {
		return dst_user_id;
	}
	
	public void setDst_user_id(List<String> dst_user_id) {
		this.dst_user_id = dst_user_id;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Integer getAtt_type() {
		return att_type;
	}

	public void setAtt_type(Integer att_type) {
		this.att_type = att_type;
	}
	
	public List<Object> getAtt() {
		return att;
	}
	
	public void setAtt(List<Object> att) {
		this.att = att;
	}
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getOffline() {
		return offline;
	}

	public void setOffline(Integer offline) {
		this.offline = offline;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
	
}
