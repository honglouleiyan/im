package com.jihuiduo.biz.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.internal.StringMap;
import com.jihuiduo.biz.MessageHandler;
import com.jihuiduo.database.DbConnectionManager;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;
import com.jihuiduo.msgserver.protocol.im.HistoryMessage;
import com.jihuiduo.msgserver.protocol.im.IMMessage;
import com.jihuiduo.util.JsonUtil;

public class HistoryMessageHandler extends MessageHandler {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(HistoryMessageHandler.class);
	/**
	 * 新增一条历史消息
	 */
	private static final String ADD_HISTORY = "INSERT INTO t_im_message_history(`user_id`, `dst_user_id`, `message_type`, `message_id`, "
			+ "`message_size`, `message`, `create_time`, `is_delete`, `bang_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * 单例模式
	 */
	private static HistoryMessageHandler instance;
	/**
	 * Object锁
	 */
	private static final Object LOCK = new Object();
	
	private HistoryMessageHandler() {
		
	}
	
	/**
	 * 单例模式
	 */
	public static HistoryMessageHandler getInstance() {
		if (instance == null) {
			synchronized (LOCK) {
				if (instance == null) {
					instance = new HistoryMessageHandler();
				}
			}
		}
		return instance;
	}
	
	public boolean addIMMessage(String headers, IMMessage message) {
		boolean isSuccess = true;
		String user_id = message.getUser_id();
		List<String> dst_user_id = message.getDst_user_id();
		int message_type = message.getType();
		String message_id = message.getMsg_id();
		// 默认值为0
		Integer offline = message.getOffline();
		boolean isOffline = (offline == null || offline == 0) ? false : true;
		if (isOffline) {
			// 离线消息不再存储
			return false;
		}
		
		for (String dst : dst_user_id) {
			// 入库消息需要重构
			List<String> newDstUserId = new ArrayList<String>();
			newDstUserId.add(dst);
			message.setDst_user_id(newDstUserId);
			String text = JsonUtil.getGson().toJson(message);
			HttpPacket httpPacket = rebuildHeadersAndData(headers, text);
			
			HistoryMessage history = new HistoryMessage();
			user_id = getBareUserId(user_id);
			dst = getBareUserId(dst);
			history.setUser_id(user_id);
			history.setDst_user_id(dst);
			history.setMessage_id(message_id);
			history.setMessage_type(message_type);
			history.setMessage(httpPacket.toString());
			history.setMessage_size((long)text.length());
			history.setIs_delete(0);
			Date date = new Date();
			history.setCreate_time(date);
			
			Long bang_id = null;
			if (message_type > 1000 && message_type < 1010) {
				Map<String, Object> options = message.getOptions();
				if (options != null && options.size() > 0) {
					Object object = options.get("bang_info");
					if (object != null) {
						// JsonUtil.getGson().fromJson(s, new TypeToken<Map<String, Object>>(){}.getType());
						StringMap<Object> map = (StringMap<Object>)object;
						Object o = map.get("bang_id");
						if (o != null) {
							String str = o.toString();
							try {
								bang_id = (long) Double.parseDouble(str);
							} catch (Exception e) {
								bang_id = null;
							}
						}
					}
				}	
			}
			
			long id = addHistory(history, bang_id);
			if (id > 0) {
				isSuccess &= true;
			} else {
				return false;
			}
		}
		return isSuccess;
	}
	
	public long addHistory(HistoryMessage history, Long bang_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
	        con = DbConnectionManager.getConnection();
	    	pstmt = con.prepareStatement(ADD_HISTORY, Statement.RETURN_GENERATED_KEYS);
	    	
	    	String user_id = history.getUser_id();
	    	if (user_id == null || user_id.matches("\\s*")) {
				pstmt.setNull(1, Types.VARCHAR);
			} else {
				pstmt.setString(1, user_id);
			}
	    	String dst_user_id = history.getDst_user_id();
	    	if (dst_user_id == null || dst_user_id.matches("\\s*")) {
				pstmt.setNull(2, Types.VARCHAR);
			} else {
				pstmt.setString(2, dst_user_id);
			}
	    	Integer message_type = history.getMessage_type();
	    	if (message_type == null) {
				pstmt.setNull(3, Types.INTEGER);
			} else {
				pstmt.setInt(3, message_type);
			}
	    	String message_id = history.getMessage_id();
	    	if (message_id == null || message_id.matches("\\s*")) {
				pstmt.setNull(4, Types.VARCHAR);
			} else {
				pstmt.setString(4, message_id);
			}
	    	Long message_size = history.getMessage_size();
	    	if (message_size == null) {
	    		// 默认值为0
				message_size = 0L;
			}
	    	pstmt.setLong(5, message_size);
	    	String message = history.getMessage();
	    	if (message == null || message.matches("\\s*")) {
				pstmt.setNull(6, Types.VARCHAR);
			} else {
				pstmt.setString(6, message);
			}
	    	long time = new java.util.Date().getTime();
	    	pstmt.setTimestamp(7, new Timestamp(time));
	    	Integer is_delete = history.getIs_delete();
	    	if (is_delete == null) {
				is_delete = 0;
			}
	    	pstmt.setInt(8, is_delete);
	    	if (bang_id == null) {
	    		pstmt.setNull(9, Types.BIGINT);
			} else {
				pstmt.setLong(9, bang_id);
			}
	    	
	    	pstmt.executeUpdate();
	    	
	    	// 获取插入数据时自动产生的ID
	    	rs = pstmt.getGeneratedKeys();
	    	if (rs.next()) {
				return rs.getLong(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			logger.error("新增HistoryMessage时发生错误 : "+e.getMessage(), e);
	    	return -1;
	    }
	    finally {
	        DbConnectionManager.closeConnection(rs, pstmt, con);
	    }
	}
	
}
