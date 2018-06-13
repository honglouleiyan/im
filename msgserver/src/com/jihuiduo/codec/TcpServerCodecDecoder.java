package com.jihuiduo.codec;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jihuiduo.constant.Constants;
import com.jihuiduo.msgserver.protocol.http.HttpPacket;

/**
 * TCP服务器解码过滤器
 */
public class TcpServerCodecDecoder extends CumulativeProtocolDecoder {
	/**
	 * 消息历史记录日志
	 */
	private static final Logger historyLogger = LoggerFactory.getLogger("history");
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(TcpServerCodecDecoder.class);
	/**
	 * 字符\r
	 */
	private static final byte CR = 0x0D;
	/**
	 * 字符\n
	 */
	private static final byte LF = 0x0A;
	/**
	 * 数字0
	 */
	private static final byte ZERO = 0x30;
	/**
	 * 字符C(CONNECT)
	 */
	private static final byte C = 0x43;
	/**
	 * 字符D(DELETE)
	 */
	private static final byte D = 0x44;
	/**
	 * 字符G(GET)
	 */
	private static final byte G = 0x47;
	/**
	 * 字符H(HTTP, HEAD)
	 */
	private static final byte H = 0x48;
	/**
	 * 字符O(OPTIONS)
	 */
	private static final byte O = 0x4F;
	/**
	 * 字符P(POST, PUT)
	 */
	private static final byte P = 0x50;
	/**
	 * 字符T(TRACE)
	 */
	private static final byte T = 0x54;
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		// 记录远程主机的IP地址和端口号
		InetSocketAddress inetSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		logger.debug("远程主机IP地址和端口号 : " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort());
		// 记录收到的数据包
		logger.debug("当前收到数据包属性, limit : " + buffer.limit() + " remaining : " + buffer.remaining() + " position : " + buffer.position() + " capacity : " + buffer.capacity());
		logger.debug("当前收到的数据包内容 : " + buffer.getHexDump());

		// 指针当前位置
		int position = buffer.position();
		int msgSize = buffer.remaining();
		// IoBuffer有数据, 并且至少超过 \r\n\r\n 的长度时, 才进行解码
		if (msgSize >= 4) {
			// 标记开始位置, 如果一条消息没传输完成(断包)则返回到这个位置
			buffer.mark();
			
			// 截取buffer得到一个新的IoBuffer实例
			// int beginPosition = buffer.position();
			// int endPosition = beginPosition + msgSize;
			// IoBuffer ioBuffer = buffer.getSlice(beginPosition, msgSize);
			// buffer.position(endPosition);
			
			// 得到一条数据
			byte[] bytes = new byte[msgSize];
			buffer.get(bytes);
			try {
				logger.debug("*****对收到的二进制数据进行解码***** : \r\n" + new String(bytes, Constants.CHARSET_NAME));
				// 取得第一个字符
				byte b = bytes[0];
				if (!(b == H || b == G || b == P || b == D || b == O || b == T || b == C)) {
					// 收到的数据不是HTTP协议格式, 直接丢弃(不重置读指针位置)
					return false;
				}
				// 用 \r\n\r\n 切割收到的数据包
				int index = -1;
				for (int i = 0; i < msgSize - 3; i++) {
					if (bytes[i] == CR && bytes[i+1] == LF && bytes[i+2] == CR && bytes[i+3] == LF) {
						index = i;
						break;
					}
				}
				if (index == -1) {
					// 收到的数据包中不包含 \r\n\r\n, HTTP协议头数据不完整
					// 重置读指针位置
					buffer.reset();
					return false;
				}
				int headerLength = index + 4;
				// 截取HTTP协议头
				byte[] headerBytes = new byte[headerLength];
				System.arraycopy(bytes, 0, headerBytes, 0, headerLength);
				String headers = new String(headerBytes, Constants.CHARSET_NAME);
				
				// 判断数据包的完整长度
				int length = 0;
				int contentLength = 0;
				if (headers.startsWith("GET")) {
					// 如果是GET请求，则headers中没有 Content-Length 和 Transfer-Encoding 字段
				} else {
					// 如果是POST, PUT, DELETE请求, 则通过 Content-Length 和 Transfer-Encoding 字段定位长度
					int contentLengthIndex = headers.indexOf("Content-Length: ");
					if (contentLengthIndex > -1) {
						// HTTP协议头有Content-Length字段
						String temp = headers.substring(contentLengthIndex + 16);
						int tempIndex = temp.indexOf("\r\n");
						String contentLengthStr = temp.substring(0, tempIndex);
						// 得到content的byte[]长度
						contentLength = Integer.parseInt(contentLengthStr);
					} else {
						// HTTP协议头中有Transfer-Encoding字段
						int transferEncodingIndex = headers.indexOf("Transfer-Encoding: ");
						if (transferEncodingIndex > -1) {
							String t = headers.substring(transferEncodingIndex + 19);
							int tIndex = t.indexOf("\r\n");
							String transferEncodingStr = t.substring(0, tIndex);
							if ("chunked".equalsIgnoreCase(transferEncodingStr)) {
								// 若Transfer-Encoding为chunked时, 0\r\n\r\n 为结束标志
								// 余下的数据
								int bodySize = msgSize - headerLength;
								byte[] body = new byte[bodySize];
								System.arraycopy(bytes, headerLength, body, 0, bodySize);
								int chunkIndex = -1;
								for (int j = 0; j < bodySize - 4; j++) {
									if (bytes[j] == ZERO && bytes[j+1] == CR && bytes[j+2] == LF && bytes[j+3] == CR && bytes[j+4] == LF) {
										chunkIndex = j;
										break;
									}
								}
								if (chunkIndex == -1) {
									// 收到的数据包中不包含 0\r\n\r\n, chunked数据不完整
									// 重置读指针位置
									buffer.reset();
									return false;
								}
								contentLength = chunkIndex + 5;
								
							} else {
								logger.error("数据无法解析 : 收到的数据包中Transfer-Encoding字段值不是chunked. 协议头 :\r\n" + headers);
								// 不重置读指针位置, 丢弃收到的数据包
								return false;
							}
						} else {
							logger.error("未知协议 : 收到的数据包中没有Content-Length和Transfer-Encoding字段. 协议头 :\r\n" + headers);
							// 不重置读指针位置, 丢弃收到的数据包
							return false;
						}
					}
				}
				
				// 实际的完整数据长度
				length = headerLength + contentLength;
				
				HttpPacket incomingPacket = new HttpPacket();
				String data = null;
				
				// IoBuffer Position回到原来标记的地方
				buffer.reset();
				if (length > msgSize) {
					// 断包
					// 重置IoBuffer, 重新读取
					return false;
				} else {
					// 接收到的数据至少包含一个完整的数据包
					// incomingPacket.setHeaders(headers);
					if (contentLength > 0) {
						byte[] dataBytes = new byte[contentLength];
						System.arraycopy(bytes, headerLength, dataBytes, 0, contentLength);
						data = new String(dataBytes, Constants.CHARSET_NAME);
					}
					// incomingPacket.setData(data);
					incomingPacket.setPacket(headers + data);
					// 设置IoBuffer的Position
					buffer.position(position + length);
				}
				// 写入数据到应用层
				out.write(incomingPacket);
				historyLogger.info(session.toString() + " 收到的信息 : \r\n" + incomingPacket.toString());
				
				// 后面还有数据则继续调用再解析
				if (buffer.remaining() > 0) {
					return true;
				}
			} catch (Exception e) {
				logger.error("服务器接收到无法解析的数据包, 需要核对协议. " + e.getMessage(), e);
				return false;
			}
		}
		return false;
	}

}
