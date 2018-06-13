package com.jihuiduo.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.basic.Packet;

/**
 * TCP服务器编码过滤器
 */
public class TcpServerCodecEncoder extends ProtocolEncoderAdapter {
	/**
	 * 消息历史记录日志
	 */
	private static final Logger historyLogger = LoggerFactory.getLogger("history");
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(TcpServerCodecEncoder.class);
	
	/**
	 * 编码过滤器
	 * */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		Packet outgoingPacket = (Packet) message;
		String msg = outgoingPacket.getPacket();
		
		// String.length()得到的长度是在Unicode编码下
		// 若存在中文, 可能length和len的值不一致
		int length = msg.length();
		byte[] bytes = msg.getBytes(Constants.CHARSET);
		// 该处得到的长度是在UTF-8编码下
		int len = bytes.length;
		
		IoBuffer buffer = IoBuffer.allocate(len);
		buffer.put(bytes);
		buffer.flip();
		out.write(buffer);
		// 记录发送的数据包
		logger.debug("当前发送的数据包属性, length : " + length + " byte[]长度 : " + len);
		logger.debug("当前发送的数据包内容 : \r\n" + msg);
		historyLogger.info(session.toString() + " 发出的信息 : \r\n" + msg);
	}
	
}
