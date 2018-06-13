package com.jihuiduo.netproxy.server.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * TCP服务器编解码过滤器类
 */
public class TcpServerCodecFactory implements ProtocolCodecFactory{

	
	private  TcpServerCodecDecoder decoder;
	private  TcpServerCodecEncoder encoder;
	
	public TcpServerCodecFactory(){
		decoder = new TcpServerCodecDecoder();
		encoder = new TcpServerCodecEncoder();
	}
	
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
