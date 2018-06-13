package com.jihuiduo.netproxy.clientim;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import com.jihuiduo.netproxy.cache.ClientCache;
import com.jihuiduo.netproxy.cache.DeviceServerCache;
import com.jihuiduo.netproxy.cache.UserServerCache;
import com.jihuiduo.netproxy.constant.ProtocolMean;
import com.jihuiduo.netproxy.server.GetInfo;
import com.jihuiduo.netproxy.server.Connect;
import com.jihuiduo.netproxy.server.NewConnect;
import com.jihuiduo.netproxy.server.filter.BasicIncomingPacket;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;
import com.jihuiduo.netproxy.server.online.DeviceOnline;
import com.jihuiduo.netproxy.start.RedisConnection;
import com.jihuiduo.netproxy.utils.IllegalMessage;
import com.jihuiduo.netproxy.utils.ProtocolHanlder;
import com.jihuiduo.netproxy.utils.ProtocolHeader;
import com.jihuiduo.netproxy.utils.ProtocolNode;
import com.jihuiduo.redis.cache.impl.CacheTypeEnum;

public class ImMsgHanlder {
	private static final Logger logger = Logger.getLogger(ImMsgHanlder.class);
	public static void messageReceived(IoSession session, BasicIncomingPacket message) {
		String hearder = message.getHeaders();
		String datafromapp = message.getData();
    	logger.debug("收到IM服务器消息：" + hearder + datafromapp);
		try {
			
			ProtocolHeader protocolHeader = ProtocolHanlder.getHeader(hearder);
			switch (protocolHeader.getRequestMethod()) {
			//GET HTTP请求
			case "GET":
				
				break;
				
			//POST HTTP请求
			case "POST":
				postHanlder(datafromapp, hearder, protocolHeader,session);
				break;
				
			// HTTP响应
			case "HTTP/1.1":
				responseHanlder(datafromapp, hearder, protocolHeader);
				break;
				
			default:
				logger.error("IM消息,无法解析消息类型");
				IllegalMessage.saveMessage("IM消息,无法解析消息类型:", hearder + datafromapp);
				break;
			}
			
	    } catch (Exception e) {
			logger.error("消息处理异常："+e.getMessage(),e);
			IllegalMessage.saveMessage("消息处理异常:", hearder + datafromapp);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void postHanlder(String datafromapp,String hearder,ProtocolHeader protocolHeader,IoSession session) {
		logger.debug("POST消息");
		if(StringUtils.isBlank(datafromapp)) {
			logger.error("IM消息,body为空");
			IllegalMessage.saveMessage("IM消息,body为空:", hearder + datafromapp);
			return;
		}
		JSONObject data = JSONObject.fromObject(datafromapp);
		if(data == null) {
			logger.error("IM消息,body转换成JSONObject数据为空");
			IllegalMessage.saveMessage("IM消息,body转换成JSONObject数据为空:", hearder + datafromapp);
			return;
		}
		ProtocolNode node = ProtocolHanlder.getNode(protocolHeader.getAgreement());
		//IM消息
		if (node.getSecondNode().equals(ProtocolMean.IM) && node.getThirdNode() == null) {
			logger.debug("IM消息");
			JSONArray messageInfos = (JSONArray) data.get(ProtocolMean.MESSAGEINFO);
			if(messageInfos == null || messageInfos.size() <= 0) {
				logger.error("非法的聊天消息，无法解析消息体");
				IllegalMessage.saveMessage("IM消息,非法的消息推送，无法找到接收用户:", hearder + datafromapp);
				return;
			}
			JSONObject messageInfo = (JSONObject) messageInfos.get(0);
			JSONArray array = (JSONArray) messageInfo.get(ProtocolMean.DSTUSERID);
			if(array == null || array.size() <= 0) {
				logger.error("非法的聊天消息，消息体无法找到接收用户");
				IllegalMessage.saveMessage("IM消息,非法的聊天消息，消息体无法找到接收用户:", hearder + datafromapp);
				return;
			}
			//如果是74@0000000格式数据，取得@前面的数据
			String dstUserId;
			String groupInfo = array.get(0).toString();
			int dex = groupInfo.indexOf("@");
			if(dex == -1) {
				dstUserId = groupInfo;
			} else {
				dstUserId = groupInfo.substring(0, dex);
			}
			logger.debug("目标用户："+dstUserId);
			if(StringUtils.isBlank(dstUserId)) {
				logger.error("IM消息,body体无dstUserId节点");
				IllegalMessage.saveMessage("IM消息,body体无result节点:", hearder + datafromapp);
				return;
			}
			String device = "";
			logger.debug("消息类型："+messageInfo.get(ProtocolMean.TYPE));
			int type = Integer.valueOf(messageInfo.get(ProtocolMean.TYPE).toString());
			if(type == ProtocolMean.TYPE_101) {
				device = dstUserId;
			} else {
				device = (String) UserServerCache.getInstance().get(dstUserId);
			}
			logger.debug("目标用户："+dstUserId + ",目标设备：" + device);
			if(StringUtils.isBlank(device)) {
				logger.error("用户找不到登录的设备");
				device= RedisConnection.redisStringCache.getHash(CacheTypeEnum.DEVICE_USER, dstUserId,DeviceOnline.DEVICE_UID);
				if(StringUtils.isBlank(device)) {
					IllegalMessage.saveMessage("用户找不到登录的设备,未登录：", hearder + datafromapp);
					return;
				}
			}
			IoSession ioSession = (IoSession) DeviceServerCache.getInstance().get(device);
			logger.debug("目标用户："+dstUserId + ",目标设备：" + device + ",目标session:" + ioSession);
			if(ioSession != null) {
				NewConnect  connect = (NewConnect) ioSession.getAttribute(Connect.CONNECTION);
				if(ClientCache.getInstance().get(dstUserId) == null) {
        			ClientCache.getInstance().put(dstUserId, session);
        		}
				connect.write(new BasicOutgoingPacket(hearder,datafromapp));
				return;
			} else {
				logger.error(dstUserId + "断线");
			    IllegalMessage.saveMessage("IM消息," + dstUserId + "断线\n", hearder + datafromapp);
				return;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void responseHanlder(String datafromapp,String hearder,ProtocolHeader protocolHeader) {
		logger.debug("回执消息");
		if(StringUtils.isBlank(datafromapp)) {
			logger.error("IM消息,body为空");
			IllegalMessage.saveMessage("IM消息 body为空:", hearder + datafromapp);
			return;
		}
		JSONObject responseDate = JSONObject.fromObject(datafromapp);
		if(responseDate == null) {
			logger.error("IM消息,body转换成JSONObject数据为空");
			IllegalMessage.saveMessage("IM消息,body转换成JSONObject数据为空:", hearder + datafromapp);
			return;
		}
		//回执消息
		String userId = GetInfo.getUserIdForRespone(datafromapp);
		logger.debug("目标用户："+userId);
		if(StringUtils.isBlank(userId)) {
			logger.error("IM消息,body体无result节点");
			IllegalMessage.saveMessage("IM消息,body体无result节点:", hearder + datafromapp);
			return;
		}
		String device = (String) UserServerCache.getInstance().get(userId);
		if(StringUtils.isBlank(device)) {
			device= RedisConnection.redisStringCache.getHash(CacheTypeEnum.DEVICE_USER, userId,DeviceOnline.DEVICE_UID);
			if(StringUtils.isBlank(device)) {
				logger.error("用户找不到登录的设备");
				IllegalMessage.saveMessage("用户找不到登录的设备,未登录：", hearder + datafromapp);
				return;
			}
		}
		logger.debug("目标用户："+userId + ",目标设备：" + device);
		if(StringUtils.isBlank(device)) {
			logger.error("用户找不到登录的设备,估计未登录：");
			IllegalMessage.saveMessage("用户找不到登录的设备,估计未登录：", hearder + datafromapp);
			return;
		}
        IoSession ioSession = (IoSession) DeviceServerCache.getInstance().get(device);
		logger.debug("目标用户："+userId + ",目标设备：" + device + ",目标session:" + ioSession);
        if(ioSession != null) {
        	NewConnect  connect = (NewConnect) ioSession.getAttribute(Connect.CONNECTION);
        	connect.write(new BasicOutgoingPacket(hearder,datafromapp));
        	return;
        } else {
        	logger.error(userId + "断线");
        	IllegalMessage.saveMessage(userId + "断线\n", hearder + datafromapp);
        	return;
        }
	}
}
