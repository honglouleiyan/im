package com.jihuiduo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作封装类
 */
public class DbAccessManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DbAccessManager.class);

	/**
	 * 执行不带参数查询语句
	 * @param con
	 * @param sql
	 * @return
	 */
	public static ResultSet exeQuery(Connection con, String sql) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, stmt, null);
		}
		return rs;
	}

	/**
	 * 执行带参数的sql查询语句
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet exeQuery(Connection con, String sql, Object[] params)throws Exception {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			//设置参数
			for(int i=0; i<params.length; i++) {
				pstmt.setObject(i+1, params[i]);			
			}
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			close(null, pstmt, null);
		}
		return rs;
	}
	
	/**
	 * 执行带参数的更新操作
	 * @param con
	 * @param sql
	 * @return
	 */
	public static int exeUpdate(Connection con, String sql, Object[] params)throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			//设置参数
			for(int i=0; i<params.length; i++) {
				pstmt.setObject(i+1, params[i]);			
			}
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			close(null, pstmt, null);
		}
		return result;
	}
	
	/**
	 * 执行带参数的新增操作
	 * @param con
	 * @param sql
	 * @return
	 */
	public static boolean exeInsert(Connection con, String sql, Object[] params) throws Exception{
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			pstmt = con.prepareStatement(sql);
			//设置参数
			for(int i=0; i<params.length; i++) {
				pstmt.setObject(i+1, params[i]);			
			}
			pstmt.execute();
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			close(null, pstmt, null);
		}
		return result;
	}
	
	/**
	 * 执行带参数的新增操作, 返回新增主键ID值
	 * @param con
	 * @param sql
	 * @return
	 */
	public static long exeInsertBackPk(Connection con, String sql, Object[] params) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			//设置参数
			for(int i=0; i<params.length; i++) {
				pstmt.setObject(i+1, params[i]);			
			}
			pstmt.execute();
			rs = pstmt.getGeneratedKeys(); 
            if (rs.next()) { 
                Long id = rs.getLong(1); 
                return id;
            } 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			close(null, pstmt, rs);
		}
		return -1;
	}
	
	/**
	 * 执行带参数的批量新增操作
	 * @param con
	 * @param sql
	 * @return
	 */
	public static boolean exeBatch(Connection con, String sql, List<Object[]> paramsList) throws Exception{
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			pstmt = con.prepareStatement(sql);
			//设置参数
			for(Object[] params : paramsList){
				for(int i=0; i<params.length; i++) {
					pstmt.setObject(i+1, params[i]);			
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			result = true;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}finally {
			close(null, pstmt, null);
		}
		return result;
	}
	/**
	 * 关闭db连接
	 * @param con
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.getStatement().close();
				rs.close();				
			} catch(SQLException e) {
				logger.error(e.getMessage(),e);
			}
			rs = null;
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch(SQLException e) {
				logger.error(e.getMessage(),e);
			}
			stmt = null;
		}
		if(con != null) {
			try {
				con.close();
			} catch(SQLException e) {
				logger.error(e.getMessage(),e);
			}
			con = null;
		}
	}
}

