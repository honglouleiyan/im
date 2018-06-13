package com.jihuiduo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库连接类
 */
public class DbConnectionManager {
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DbConnectionManager.class);
	
	private static DbConnectionManager instance;
	private Properties properties;
	private String alias;
	
	private DbConnectionManager() {
		
	}

	/**
	 * 单例模式
	 */
	public static DbConnectionManager getInstance() {
		if (null == instance) {
			synchronized (DbConnectionManager.class) {
				if (null == instance) {
					instance = new DbConnectionManager();
				}
			}
		}
		return instance;
	}
	
	public void initialize(String path, String dbAlias) throws Exception {
		loadProperties(path);
		this.properties = new Properties();
		this.alias = dbAlias;
	}
	
	/**
	 * 加载proxool配置文件
	 */
	private void loadProperties(String path) throws Exception {
		// false表示不验证xml
		JAXPConfigurator.configure(path, false);
	}

	/**
	 * 获取数据库连接
	 */
	public static Connection getConnection() {
		return instance.getConnection("proxool." + instance.alias);
	}
	
	/**
	 * 获取数据库连接
	 */
	private Connection getConnection(String proxoolAlias) {
		Connection con = null;
		for(int tryCount = 0;tryCount < 5; tryCount++) {
			try {
				con = DriverManager.getConnection(proxoolAlias);
				break;
			} catch (SQLException e) {
				logger.error("获取数据库连接异常 : " + e.getMessage(), e);			
				if (tryCount >= 5) {
					logger.error("尝试重连数据库超过5次, 重置连接池. ");
					resetConnectionPool();
					break;
				} else {
					logger.error("Unable to connect to database. Retrying in 5 seconds...");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException iex) {
						logger.error(iex.getMessage(), iex);
					}
				}
			} catch (Exception e) {
				logger.error("获取数据库连接异常 : " + e.getMessage(), e);
				break;
			}
		}
		return con;
	}

	/**
	 * 关闭数据库连接
	 */
	public static void close(Connection con) {
		try {
			if (null != con && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			con = null;
			logger.error("关闭数据库连接发生异常 : " + e.getMessage(), e);
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                    rs.close();
                }
            catch (SQLException e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }       
            catch (Exception e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }
    
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
               con.close();
            }
            catch (Exception e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }
    
    public static void closeStatement(ResultSet rs, Statement stmt) {
        closeResultSet(rs);
        closeStatement(stmt);
    }
    
    public static void fastcloseStmt(PreparedStatement pstmt) throws SQLException
    {
        pstmt.close();
    }
    
    public static void fastcloseStmt(ResultSet rs, PreparedStatement pstmt) throws SQLException
    {
        rs.close();
        pstmt.close();
    }
    
    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(con);
    }
    
    public static void closeConnection(Statement stmt, Connection con) {
        closeStatement(stmt);
        closeConnection(con);
    }

	/**
	 * 获得数据库的当前连接数
	 */
	public static String fetchConnCount() {
		StringBuffer connCount = new StringBuffer();
		try {
			SnapshotIF snapshot = ProxoolFacade.getSnapshot("sql", true);
			int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数
			int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数
			int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数

			connCount.append("---------------数据库连接池状态-------------------");
			connCount.append(curActiveCount + "(active)  " + availableCount
					+ "(available)  " + maxCount + "(max)");
			connCount.append("-------------------------------------------");
		} catch (ProxoolException e) {
			logger.error("数据库异常",e);
		}
		return connCount.toString();
	}

	/**
	 * 重置连接池
	 */
	private void resetConnectionPool() {
		try {
			ProxoolFacade.killAllConnections(alias, "");
			logger.info("************rest DB Connection pool*********************");
		} catch (Exception e) {
			logger.error("DbConnection.resetConnectionPool() close Exception "+ e);
		}
		try {
			ProxoolFacade.updateConnectionPool("proxool."+alias, properties);
			logger.info("************rest DB Connection pool*********************");
		} catch (Exception e) {
			logger.error("DbConnection.resetConnectionPool() updateConnectionPool Exception "+ e);
		}
	}

}

