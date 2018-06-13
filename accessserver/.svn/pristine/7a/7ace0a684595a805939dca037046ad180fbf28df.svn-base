package com.jihuiduo.netproxy.clientweb;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.cache.CryptonymCache;
import com.jihuiduo.netproxy.cache.DeviceServerCache;
import com.jihuiduo.netproxy.cache.WebCache;
import com.jihuiduo.netproxy.constant.ProtocolMean;
import com.jihuiduo.netproxy.server.Connect;
import com.jihuiduo.netproxy.server.filter.BasicIncomingPacket;
import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;
import com.jihuiduo.netproxy.utils.IllegalMessage;

public class WebMsgHanlder {
	private static final Logger logger = Logger.getLogger(WebMsgHanlder.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void messageReceived(IoSession session, BasicIncomingPacket message) {
		String hearder = message.getHeaders();
		String datafromapp = message.getData();
		try {
			//NP到web映射缓存
		    WebCache webCache = WebCache.getInstance(); 
		    //APP到NP映射混成
		    DeviceServerCache serverCache = DeviceServerCache.getInstance();
		    //匿名用户缓存
		    CryptonymCache cryptonymCache = CryptonymCache.getInstance();
		    if(StringUtils.isBlank(hearder) || StringUtils.isBlank(datafromapp)) {
		    	logger.error("web返回消息,头或者消息体为空");
		    	IllegalMessage.saveMessage("web返回消息头或者消息体为空,", hearder + datafromapp);
		    	return;
		    }
		    JSONObject data = JSONObject.fromObject(datafromapp);
			if(data == null) {
				logger.error("web消息,返回结果无返回值");
		    	IllegalMessage.saveMessage("web消息,返回结果无返回值,", hearder + datafromapp);
			    return;
			}
			JSONObject result = (JSONObject) data.get(ProtocolMean.RESULT);
			if(result == null) {
				logger.error("web消息,返回结果无返回值");
		    	IllegalMessage.saveMessage("web消息,返回结果无返回值,", hearder + datafromapp);
			    return;
			}
			if(result.get(ProtocolMean.USERID) != null && !result.get(ProtocolMean.USERID).equals("null")) {
				logger.error("匿名请求web返回");
				if(result.get(ProtocolMean.SEQ) != null) {
		    		String seq = (String) result.get(ProtocolMean.SEQ);
		    		if(StringUtils.isNotBlank(seq)) {
		    			//根据seq查找匿名用户
		    			String cryptonym = (String) cryptonymCache.get(seq);
		    		    IoSession ioSession2 = (IoSession) serverCache.get(cryptonym);
		    		    if(ioSession2 == null) {
		    		    	logger.error(cryptonym+"断线");
		    		    	IllegalMessage.saveMessage(cryptonym+"断线,", hearder + datafromapp);
		    		    	return;
		    		    }
		    		    //更新app到np的缓存  并转发数据
	    				Connect  connect = (Connect) ioSession2.getAttribute(Connect.CONNECTION);
	    				connect.write(new BasicOutgoingPacket(hearder,datafromapp));
		    			return;
		    		}
		    	}
		    	logger.error("非法web返回，无userId，无seq");
		    	IllegalMessage.saveMessage("非法web返回，无userId，无seq,", hearder + datafromapp);
				return;
			}
		    logger.debug("正常用户返回");
		    String dummyUserId = (String) result.get(ProtocolMean.USERID);
		    IoSession ioSession = (IoSession) serverCache.get(dummyUserId);
		    if(ioSession == null) {
		    	if(result.get(ProtocolMean.SEQ) != null) {
		    		String seq = (String) result.get(ProtocolMean.SEQ);
		    		if(StringUtils.isNotBlank(seq)) {
		    			logger.debug("登录成功标志判断");
		    			//根据seq查找匿名用户
		    			String cryptonym = (String) cryptonymCache.get(seq);
		    			//清楚np到web缓存的该匿名用户
		    			webCache.remove(cryptonym);
		    			//清楚该匿名用户所有的seq
//		    			cryptonymCache.removeAll(cryptonym);
		    			//将正确用户id加入web到np缓存
		    		    webCache.put(dummyUserId, session);
		    		    IoSession ioSession2 = (IoSession) serverCache.get(cryptonym);
		    		    if(ioSession2 == null) {
		    		    	logger.error(cryptonym+"断线");
		    		    	IllegalMessage.saveMessage(cryptonym+"断线,", hearder + datafromapp);
		    		    	return;
		    		    }
		    		    //更新app到np的缓存  并转发数据
		    		    serverCache.remove(cryptonym);
		    		    serverCache.put(dummyUserId, ioSession2);
	    				Connect  connect = (Connect) ioSession2.getAttribute(Connect.CONNECTION);
	    				connect.write(new BasicOutgoingPacket(hearder,datafromapp));
		    			return;
		    		}
		    	}
		    	logger.error(dummyUserId+"断线");
		    	IllegalMessage.saveMessage(dummyUserId+"断线,", hearder + datafromapp);
		    	return;
		    }
    		Connect  connect = (Connect) ioSession.getAttribute(Connect.CONNECTION);
    		connect.write(new BasicOutgoingPacket(hearder,datafromapp));
		    
	    } catch (Exception e) {
	    	logger.error("web返回消息,处理异常："+e.getMessage(),e);
	    	IllegalMessage.saveMessage("web返回消息，处理异常", hearder + datafromapp);
		}
	 }
}
