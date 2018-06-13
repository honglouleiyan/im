package com.jihuiduo.netproxy.server;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.cache.ClientCache;
import com.jihuiduo.netproxy.cache.DeviceServerCacheThread;
import com.jihuiduo.netproxy.cache.PreparatorySessionCache;
import com.jihuiduo.netproxy.cache.DeviceServerCache;
import com.jihuiduo.netproxy.cache.UserServerCache;
import com.jihuiduo.netproxy.clientim.ImAllServer;
import com.jihuiduo.netproxy.constant.ProtocolMean;
import com.jihuiduo.netproxy.http.HttpResponse;
import com.jihuiduo.netproxy.server.filter.BasicIncomingPacket;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;
import com.jihuiduo.netproxy.server.online.DeviceOnline;
import com.jihuiduo.netproxy.server.online.UserOnlineOfflineBroadcast;
import com.jihuiduo.netproxy.start.MqConnection;
import com.jihuiduo.netproxy.start.RedisConnection;
import com.jihuiduo.netproxy.utils.IllegalMessage;
import com.jihuiduo.netproxy.utils.ProtocolHanlder;
import com.jihuiduo.netproxy.utils.ProtocolHeader;
import com.jihuiduo.netproxy.utils.ProtocolNode;
import com.jihuiduo.netproxy.utils.SequenceUtil;
import com.jihuiduo.redis.cache.impl.CacheTypeEnum;

public class ServerMsgHandler {
	private static final Logger logger = Logger.getLogger(ServerMsgHandler.class);
	public static void messageReceived(IoSession session, Object message) {
		try {
			BasicIncomingPacket msg = (BasicIncomingPacket) message;
			String hearder = msg.getHeaders();
			String datafromapp = msg.getData();
			logger.debug("收到APP的消息:" + hearder + datafromapp);
			ProtocolHeader protocolHeader = ProtocolHanlder.getHeader(hearder);
			ProtocolNode node = ProtocolHanlder.getNode(protocolHeader.getAgreement());
			switch (protocolHeader.getRequestMethod()) {
			//GET HTTP请求
			case "GET":
				logger.debug("HTTP请求   GET提交");
				switch (node.getSecondNode()) {
				case ProtocolMean.REGISTRATION:
					logger.debug("用户预注册1");
					break;
				}
				
				session.write(HttpResponse.createAuth("111"));
				break;
			
			//POST HTTP请求
			case "POST":
				logger.debug("HTTP请求   POST提交");
				switch (node.getSecondNode()) {
				case ProtocolMean.IM:
					if(node.getThirdNode() == null) {
						//IM消息
						logger.debug("IM消息");
						if(StringUtils.isBlank(datafromapp)) {
							logger.error("post提交的body为空");
							IllegalMessage.saveMessage("post提交的body为空:", hearder + datafromapp);
							return;
						}
						String userId = GetInfo.getUserIdForImpost(datafromapp);
						if(StringUtils.isBlank(userId)) {
							logger.error("userId为空");
							IllegalMessage.saveMessage("userId为空:", hearder + datafromapp);
							return;
						}
						imMsgHandler(session, hearder, datafromapp, userId);
					} else if(node.getThirdNode().equals(ProtocolMean.HEARTBEAT)) {
						//心跳
						logger.debug("心跳，不处理业务:" + session);
					}
					break;
				case ProtocolMean.REGISTRATION:
					logger.debug("用户预注册2");
					break;
					
				case ProtocolMean.DEVICE:
					if(node.getThirdNode().equals(ProtocolMean.INDENTITY_AUTH)) {
						logger.debug("TCP长连接的建立，身份验证");
						if(StringUtils.isBlank(datafromapp)) {
							logger.error("post提交的body为空");
							IllegalMessage.saveMessage("post提交的body为空:", hearder + datafromapp);
							return;
						}
						String deviceUid = GetInfo.getDeviceIdForAuth(datafromapp);
						if(StringUtils.isBlank(deviceUid)) {
							logger.error("deviceUid为空");
							IllegalMessage.saveMessage("deviceUid为空:", hearder + datafromapp);
							return;
						}
						authHandler(session, hearder, datafromapp, deviceUid,node);
					} else {
						logger.error("TCP长连接的建立，身份验证--第三级节点协议错误");
					}
					break;
					
				default:
					logger.error("非法协议");
					break;
				}
				
				break;
			//DELETE HTTP请求	
			case "DELETE":
	
				break;
			
			//PUT HTTP请求
			case "PUT":
	
				break;
			
			// HTTP响应
			case "HTTP/1.1":
				logger.debug("响应消息");
				if(StringUtils.isBlank(datafromapp)) {
					logger.error("post提交的body为空");
					IllegalMessage.saveMessage("post提交的body为空:", hearder + datafromapp);
					return;
				}
				String userId = GetInfo.getUserIdForRespone(datafromapp);
				if(StringUtils.isBlank(userId)) {
					logger.error("userId为空");
					IllegalMessage.saveMessage("userId为空:", hearder + datafromapp);
					return;
				}
				responseHandler(session, hearder, datafromapp,userId);
				break;
				
			default:
				logger.error("APP消息,无法解析消息类型");
				IllegalMessage.saveMessage("APP消息,无法解析消息类型:", hearder + datafromapp);
				break;
			}
			
		} catch (Exception e) {
			IllegalMessage.saveMessage("非法消息", message.toString());
 			logger.error("消息处理异常："+e.getMessage(),e);
		}
	}
	
	/*
	 * 处理接收到的用于“回执”业务
	 */
	@SuppressWarnings({ "unchecked"})
	public static void responseHandler(IoSession session, String hearder,String datafromapp,String userId) {
		IoSession ioSession = (IoSession) ClientCache.getInstance().get(userId);
		if(ioSession != null) {
			Connect connect = (Connect) ioSession.getAttribute(Connect.CONNECTION);
			connect.write(new BasicOutgoingPacket(hearder,datafromapp));
		} else {
			ClientInfo client = ImAllServer.getInstance().getBestServer();
			IoSession clientSession = client.getSession();
			Connect  connectClient = (Connect) clientSession.getAttribute(Connect.CONNECTION);
			connectClient.write(new BasicOutgoingPacket(hearder, datafromapp));
		}
	}
	
	public static void imHandler(IoSession session, String hearder,String datafromapp) {
		
	}
	
	
	/*
	 * 处理接收到的用于“IM消息”业务
	 */
	@SuppressWarnings("unchecked")
	public static void imMsgHandler(IoSession session, String hearder,String datafromapp,String userId) throws InterruptedException {
		NewConnect connect = (NewConnect) session.getAttribute(Connect.CONNECTION);
		String device = connect.getDevice();
		if(StringUtils.isBlank(device)) {
			IllegalMessage.saveMessage("未进行身份验证就发消息：", hearder + datafromapp);
			return;
		}
		String deviceCache = (String) UserServerCache.getInstance().get(userId);
		//若servercache里面保存的缓存和当前缓存不同，则表明账号已登录   踢掉上一个登录状态
		if(deviceCache == null) {
			UserServerCache.getInstance().putSingle(userId, device);
		} else {
			//若UserServerCache里面保存的缓存和当前缓存不同，则表明设备被另外一个账号登录
			try {
				if (!deviceCache.equals(device)) {
//					serverSession.write(HttpResponse.createOffline(SequenceUtil.createSequence(SequenceUtil.getCount()),userId.toString()));
//					Thread.sleep(3000);
//					serverSession.close(false);
//					Thread.sleep(2000);
					UserServerCache.getInstance().putSingle(userId, device);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		logger.debug("userId对于设备：userId=" + userId + ",device=" + device);
		IoSession clientSession = (IoSession) ClientCache.getInstance().get(userId.toString());
		if(clientSession != null) {
			Connect  connectClient = (Connect) clientSession.getAttribute(Connect.CONNECTION);
			connectClient.write(new BasicOutgoingPacket(hearder, datafromapp));
			return;
		}
		//选择新IM服务器
		ImAllServer imAllServer = ImAllServer.getInstance();
		List<ClientInfo> clientInfos = imAllServer.getServerList();
		if(clientInfos == null || clientInfos.size() <= 0){
			//暂无正常的IM服务器 
			//新启动线程   在超时时间前再次连接判断能否转发该消息，若到时能无法连接到服务器，则丢弃该消息
//			CachedThreadPool.getInstance().getCachedThreadPool().execute(new RequestHttpForIm(userId.toString(), hearder, datafromapp));
			return;
		} else {
			ClientInfo client = ImAllServer.getInstance().getBestServer();
			ClientCache.getInstance().put(userId.toString(), client.getSession());
			clientSession = client.getSession();
			Connect  connectClient = (Connect) clientSession.getAttribute(Connect.CONNECTION);
			connectClient.write(new BasicOutgoingPacket(hearder, datafromapp));
		}
	}
	
	/*
	 * 处理接收到的用于“心跳”业务
	 */
	@SuppressWarnings("unchecked")
	public static void heartHandler(IoSession session, String hearder,String datafromapp,Long userId) {
		Connect connect = (Connect) session.getAttribute(Connect.CONNECTION);
		if(connect.getUserId() == null) {
			connect.setUserId(userId.toString());
		}
		IoSession serverSession = (IoSession) DeviceServerCache.getInstance().get(userId.toString());
		if(serverSession == null) {
			DeviceServerCache.getInstance().put(userId.toString(), session);
		} else {
			try {
			//若servercache里面保存的缓存和当前缓存不同，则表明账号已登录   踢掉上一个登录状态
			if (!serverSession.equals(session)) {
				logger.debug("旧session推送：" + serverSession + " ip:"+ serverSession.getRemoteAddress());
				serverSession.write(HttpResponse.createOffline(SequenceUtil.createSequence(SequenceUtil.getCount()),userId.toString()));
				logger.debug("旧session关闭：" + serverSession + " ip:"+ serverSession.getRemoteAddress());
				Thread.sleep(3000);
				serverSession.close(false);
				logger.debug("新session替换：" + session + " ip:"+ session.getRemoteAddress());
				Thread.sleep(2000);
				DeviceServerCache.getInstance().put(userId.toString(), session);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//缓存心跳标示的状态
//		UserOnline.setUserInfo(session,userId.toString());
	}
	
	/*
	 * 处理接收到的用于“身份验证”业务
	 */
	@SuppressWarnings("unchecked")
	public static void authHandler(IoSession session, String hearder,String datafromapp,String deviceUid,ProtocolNode node) {
		Map<String, String> map=  RedisConnection.redisStringCache.getHashAll(CacheTypeEnum.DEVICE, deviceUid);
		if(map == null) {
			logger.error("非法的设备   连接关闭");
			session.close(true);
			PreparatorySessionCache.getInstance().remove(session);
			return;
		}
		NewConnect connect = (NewConnect) session.getAttribute(Connect.CONNECTION);
		if(connect.getDevice() == null) {
			connect.setDevice(deviceUid);
		}
		IoSession serverSession = (IoSession) DeviceServerCache.getInstance().get(deviceUid);
		if(serverSession == null) {
			DeviceServerCache.getInstance().put(deviceUid, session);
			DeviceOnline.setUserInfo(session, deviceUid);
		} else {
			//若servercache里面保存的缓存和当前缓存不同，则表明账号已登录   踢说掉上一个登录状态
			if (!serverSession.equals(session)) {
				/**
				 * 一个APP出现两个连接情形处理   
				 * T掉上一个    将DeviceServerCache设置为最新
				 */
				logger.error("旧连接：" + serverSession);
				logger.error("新连接：" + session);
				serverSession.close(true);//异步   下一步用线程来执行
//				DeviceServerCache.getInstance().put(deviceUid, session);
				new Thread(new DeviceServerCacheThread(session,deviceUid)).start();
			}
		}
		//接口返回通知
		String seq = node.getParameter().get("seq");
		connect.write(HttpResponse.createAuth(seq));
		//移除预验证缓存
		PreparatorySessionCache.getInstance().remove(session);
		//查询混成获取用户邓登录记录
		String userId = "";
		if(map.get(DeviceOnline.LOGIN_STATE)!=null&&map.get(DeviceOnline.LOGIN_STATE).equals(DeviceOnline.STATEONE)) {
			userId = map.get(DeviceOnline.USER_ID);
			if(StringUtils.isNotBlank(userId)) {
				UserServerCache.getInstance().putSingle(userId, deviceUid);
			
			}
		}
		UserOnlineOfflineBroadcast notification = new UserOnlineOfflineBroadcast();
		notification.setUser_id(userId);
		notification.setDevice_type(map.get(DeviceOnline.DEVICE_TYPE)!=null?Integer.valueOf(map.get(DeviceOnline.DEVICE_TYPE)):0);
		notification.setConnection_state(DeviceOnline.NOTIFICATION);
		notification.setDevice_uid(deviceUid);
		notification.setType(2);
		notification.setLogin_state(map.get(DeviceOnline.LOGIN_STATE)!=null?Integer.valueOf(map.get(DeviceOnline.LOGIN_STATE)):0);
		notification.setTime(new Date().getTime());
		JSONObject object = JSONObject.fromObject(notification);
		MqConnection.jmsSenderService.sendTopicData(object.toString());
		logger.debug("设备：" + deviceUid + ",session:" + session);
	}

}
