package com.jihuiduo.msgserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session管理
 * 
 */
public class SessionManager {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
	/**
	 * 单例模式
	 */
	private static SessionManager instance;
	/**
	 * 接入服务器sessions
	 */
	private Map<String, Session> accessServerSessions;
	/**
	 * 业务服务器sessions
	 */
	private Map<String, Session> businessServerSessions;
	
	/**
	 * 单例模式
	 */
	public static SessionManager getInstance() {
		if (null == instance) {
			synchronized(SessionManager.class){
				if(null == instance){
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}
	
	private SessionManager() {
		// 初始化sessions
		accessServerSessions = new ConcurrentHashMap<String, Session>();
		businessServerSessions = new ConcurrentHashMap<String, Session>();
	}
	
	public void addAccessServerSession(String key, Session session) {
		logger.debug("SessionManager addAccessServerSession : " + key);
		accessServerSessions.put(key, session);
	}
	
	public void addBusinessServerSession(String key, Session session) {
		logger.debug("SessionManager addBusinessServerSession : " + key);
		businessServerSessions.put(key, session);
	}
	
	public Session getAccessServerSession(String key) {
		return accessServerSessions.get(key);
	}
	
	public Session getBusinessServerSession(String key) {
		return businessServerSessions.get(key);
	}
	
	public List<Session> getAccessServerSessions() {
		List<Session> list = new ArrayList<Session>();
		Set<String> keys = accessServerSessions.keySet();
		if (keys == null) {
			return null;
		}
		for (String key : keys) {
			list.add(accessServerSessions.get(key));
		}
		return list;
	}
	
	public List<Session> getBusinessServerSessions() {
		List<Session> list = new ArrayList<Session>();
		Set<String> keys = businessServerSessions.keySet();
		if (keys == null) {
			return null;
		}
		for (String key : keys) {
			list.add(businessServerSessions.get(key));
		}
		return list;
	}
	
	public void removeAccessServerSession(String key) {
		logger.debug("SessionManager removeAccessServerSession : " + key);
		accessServerSessions.remove(key);
	}
	
	public void removeBusinessServerSession(String key) {
		logger.debug("SessionManager removeBusinessServerSession : " + key);
		businessServerSessions.remove(key);
	}
	
	public void clearAccessServerSessions() {
		accessServerSessions.clear();
	}
	
	public void clearBusinessServerSessions() {
		businessServerSessions.clear();
	}
	
	/**
	 * 创建服务器Session
	 * 
	 * @param connection
	 * @return
	 */
	public Session createServerSession(Connection connection) {
		Session session = new Session(connection);
		connection.init(session);
		return session;
	}
	
}
