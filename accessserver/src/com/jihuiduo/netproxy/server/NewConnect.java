/*===================================================================
 * 深圳市机会多数据移动互联有限公司
 * 日期：2015年9月22日 上午9:56:04
 * 作者：龙     伟
 * 版本：1.0.0
 * 版权：All rights reserved.
 *===================================================================
 * 修订日期           修订人               描述
 * 2015年9月22日     龙  伟	      创建
 */
package com.jihuiduo.netproxy.server;

import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.server.filter.BasicOutgoingPacket;

/**
 * @ClassName: NewConnect
 * @Description: 新的连接管理，用device绑定设备
 */

public class NewConnect {
    public static final String CONNECTION = "CONNECTION";
	private String device;
	private IoSession session;
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	public void write(BasicOutgoingPacket data) {
		this.session.write(data);
	}
}
