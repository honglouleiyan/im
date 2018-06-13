package com.jihuiduo.netproxy.server.filter;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.jihuiduo.netproxy.constant.Constant;
import com.jihuiduo.netproxy.server.ServerManage;


/**
 * TCP服务器编码过滤器
 */
public class TcpServerCodecEncoder extends ProtocolEncoderAdapter {
	
	/**
	 * 日志
	 */
	private static final Logger logger = Logger.getLogger(TcpServerCodecEncoder.class);
	
	/**
	 * 编码过滤器
	 * */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		BasicOutgoingPacket outgoingPacket = (BasicOutgoingPacket) message;
		String headers = outgoingPacket.getHeaders();
		String data = outgoingPacket.getData();
		if (null == data) {
			data = "";
		}
		// String.length()得到的长度是在Unicode编码下
		int length = headers.length() + data.length();
		
		String msg = headers + data;
		byte[] bytes = msg.getBytes(Constant.CHARSET);
		// 该处得到的长度是在UTF-8编码下
		int len = bytes.length;
		
		IoBuffer buffer = IoBuffer.allocate(len);
		buffer.put(bytes);
		buffer.flip();
		out.write(buffer);
		// 记录发送的数据包
		logger.debug("当前发送的数据包属性, length : " + length + " byte[]长度 : " + len);
		logger.debug("当前发送的数据包内容 : \r\n" + msg);
	}
}
