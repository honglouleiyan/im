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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.database.DbConnectionManager;
import com.jihuiduo.msgserver.protocol.im.OfflineMessage;

public class OfflineMessageStore {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(OfflineMessageStore.class);
	/**
	 * 新增一条离线消息
	 */
	private static final String ADD_OFFLINE = "INSERT INTO t_im_message_offline(`user_id`, `dst_user_id`, `message_type`, `message_id`, "
			+ "`message`, `message_size`, `deadline`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	/**
	 * 查询指定用户的离线消息
	 */
	private static final String LOAD_OFFLINE = "SELECT `id`, `user_id`, `message_type`, `message_id`, `message`, `message_size`, "
			+ "`deadline`, `create_time` FROM t_im_message_offline WHERE `dst_user_id` = ?";
	/**
	 * 查询指定用户的离线消息大小
	 */
	private static final String SELECT_SIZE_OFFLINE = "SELECT SUM(message_size) FROM t_im_message_offline WHERE `dst_user_id` = ?";
	/**
	 * 查询所有用户的离线消息大小
	 */
	private static final String SELECT_SIZE_ALL_OFFLINE = "SELECT SUM(message_size) FROM t_im_message_offline";
	/**
	 * 删除指定用户的离线消息
	 */
	private static final String DELETE_OFFLINE = "DELETE FROM t_im_message_offline WHERE `dst_user_id` = ?";
	
	/**
	 * 单例模式
	 */
	private static OfflineMessageStore offlineMessageStore;
	/**
	 * Object锁
	 */
	private static final Object LOCK = new Object();
	
	private OfflineMessageStore() {
	}
	
	/**
	 * 单例模式
	 */
	public static OfflineMessageStore getInstance() {
		if (offlineMessageStore == null) {
			synchronized (LOCK) {
				if (offlineMessageStore == null) {
					offlineMessageStore = new OfflineMessageStore();
				}
			}
		}
		return offlineMessageStore;
	}
	
	public long addOffline(OfflineMessage offline) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
            con = DbConnectionManager.getConnection();
        	pstmt = con.prepareStatement(ADD_OFFLINE, Statement.RETURN_GENERATED_KEYS);
        	
        	String user_id = offline.getUser_id();
        	if (user_id == null || user_id.matches("\\s*")) {
				pstmt.setNull(1, Types.VARCHAR);
			} else {
				user_id = user_id.toLowerCase();
				pstmt.setString(1, user_id);
			}
        	
        	String dst_user_id = offline.getDst_user_id();
        	if (dst_user_id == null || dst_user_id.matches("\\s*")) {
				pstmt.setNull(2, Types.VARCHAR);
			} else {
				dst_user_id = dst_user_id.toLowerCase();
				pstmt.setString(2, dst_user_id);
			}
        	
        	Integer message_type = offline.getMessage_type();
        	if (message_type == null) {
        		message_type = 0;
			}
        	pstmt.setInt(3, message_type);
        	String message_id = offline.getMessage_id();
        	if (message_id == null || message_id.matches("\\s*")) {
				pstmt.setNull(4, Types.VARCHAR);
			} else {
				pstmt.setString(4, message_id);
			}
        	String message = offline.getMessage();
        	if (message == null || message.matches("\\s*")) {
				pstmt.setNull(5, Types.VARCHAR);
			} else {
				pstmt.setString(5, message);
			}
        	Long message_size = offline.getMessage_size();
        	if (message_size == null) {
				message_size = 0L;
			}
        	pstmt.setLong(6, message_size);
        	
        	long time = new java.util.Date().getTime();
        	// 消息有效期
        	Date deadline = offline.getDeadline();
        	if (deadline == null) {
				deadline = new java.util.Date(time + 30*24*60*60*1000L);
			}
        	pstmt.setTimestamp(7, new Timestamp(deadline.getTime()));
        	pstmt.setTimestamp(8, new Timestamp(time));
        	pstmt.executeUpdate();
        	
        	// 获取插入数据时自动产生的ID
        	rs = pstmt.getGeneratedKeys();
        	if (rs.next()) {
    			return rs.getLong(1);
    		} else {
				return 0;
			}
		} catch (SQLException sqle) {
			logger.error("addOffline发生错误 : "+sqle.getMessage(), sqle);
        	return -1;
        }
        finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
	}
	
	public List<OfflineMessage> loadOffline(String dstUserId) {
		List<OfflineMessage> list = new ArrayList<OfflineMessage>();
		if (dstUserId == null || dstUserId.matches("\\s*")) {
			return null;
		}
		dstUserId = dstUserId.toLowerCase();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
            con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(LOAD_OFFLINE);
			pstmt.setString(1, dstUserId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	OfflineMessage offlineMessage = new OfflineMessage();
            	long id = rs.getLong(1);
            	String user_id = rs.getString(2);
            	int message_type = rs.getInt(3);
            	String message_id = rs.getString(4);
            	String message = rs.getString(5);
            	long message_size = rs.getLong(6);
            	
            	long deadline = rs.getTimestamp(7).getTime();
            	long create_time = rs.getTimestamp(8).getTime();
            	
            	offlineMessage.setId(id);
            	offlineMessage.setUser_id(user_id);
            	offlineMessage.setDst_user_id(dstUserId);
            	offlineMessage.setMessage_type(message_type);
            	offlineMessage.setMessage_id(message_id);
            	offlineMessage.setMessage(message);
            	offlineMessage.setMessage_size(message_size);
            	offlineMessage.setDeadline(new Date(deadline));
            	offlineMessage.setDeadline(new Date(create_time));
            	
            	list.add(offlineMessage);
            }
        }
        catch (SQLException sqle) {
        	logger.error("loadOffline发生错误, "+sqle.getMessage(), sqle);
        	return null;
        }
        finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
		return list;
	}
	
	public boolean deleteOffline(String dstUserId) {
		boolean success = false;
		if (dstUserId == null || dstUserId.matches("\\s*")) {
			return false;
		}
		dstUserId = dstUserId.toLowerCase();
		Connection con = null;
		PreparedStatement pstmt = null;
		int r;
		try {
            con = DbConnectionManager.getConnection();
			pstmt = con.prepareStatement(DELETE_OFFLINE);
			pstmt.setString(1, dstUserId);
            r = pstmt.executeUpdate();
            if (r > 0) {
				success = true;
			} else {
				success = false;
			}
            } catch(SQLException sqle) {
            	logger.error("deleteOffline发生错误, "+sqle.getMessage(), sqle);
            	return false;
            } finally {
                DbConnectionManager.closeConnection(pstmt, con);
            }
		return success;
	}
	
	public long getOfflineCount(String dstUserId) {
		if (dstUserId == null || dstUserId.matches("\\s*")) {
			return 0;
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
            con = DbConnectionManager.getConnection();
        	pstmt = con.prepareStatement(SELECT_SIZE_OFFLINE);
			pstmt.setString(1, dstUserId);
        	rs = pstmt.executeQuery();
        	if (rs.next()) {
        		return rs.getLong(1);
        	} else {
				return 0;
			}
		} catch (SQLException sqle) {
			logger.error("getOfflineCount发生错误 : "+sqle.getMessage(), sqle);
        	return -1;
        }
        finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
	}
	
	public long getAllOfflineCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
            con = DbConnectionManager.getConnection();
        	pstmt = con.prepareStatement(SELECT_SIZE_ALL_OFFLINE);
        	rs = pstmt.executeQuery();
        	if (rs.next()) {
        		return rs.getLong(1);
        	} else {
				return 0;
			}
		} catch (SQLException sqle) {
			logger.error("getAllOfflineCount发生错误 : "+sqle.getMessage(), sqle);
        	return -1;
        }
        finally {
            DbConnectionManager.closeConnection(rs, pstmt, con);
        }
	}


}
