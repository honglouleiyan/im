/*===================================================================
 * 深圳市机会多数据移动互联有限公司
 * 日期：2015年10月12日 下午2:25:09
 * 作者：龙     伟
 * 版本：1.0.0
 * 版权：All rights reserved.
 *===================================================================
 * 修订日期           修订人               描述
 * 2015年10月12日     龙  伟	      创建
 */
package com.jihuiduo.netproxy.cache;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.server.online.DeviceOnline;

/**
 * @ClassName: DeviceServerCacheThread
 * @Description: 描述
 */

public class DeviceServerCacheThread implements Runnable{
	private static final Logger logger = Logger.getLogger(DeviceServerCacheThread.class);
	/*
	  * <p>Title: 一个客户端发来两个连接，由于mina 关闭上一个连接是异步操作，导致新DeviceServerCache数据：device_uid和session被旧连接异常
	  *     所以新建线程，5秒内循环添加新的device_uid和session</p>
	  * <p>Description: </p>
	  * @see java.lang.Runnable#run()
	  */
	private IoSession session;
	private String deviceUid;
	public DeviceServerCacheThread(IoSession session,String deviceUid){
		this.session=session;
		this.deviceUid=deviceUid;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		for(int i = 0; i < 5; i++) {
			logger.debug("一个APP出现了两个连接:" + DeviceServerCache.getInstance().get(deviceUid));
			DeviceServerCache.getInstance().put(deviceUid, session);
			DeviceOnline.setUserInfo(session, deviceUid);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
