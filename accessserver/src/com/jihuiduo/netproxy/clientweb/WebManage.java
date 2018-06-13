package com.jihuiduo.netproxy.clientweb;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jihuiduo.netproxy.server.ClientInfo;
import com.jihuiduo.netproxy.server.ImInfo;
import com.jihuiduo.netproxy.utils.ReadXml;

public class WebManage implements Runnable{
	private static Logger logger  =  Logger.getLogger(WebManage.class );

	@Override
	public void run() {
		/*
		 * 读取需要连接的web服务器ip/port   新建连接
		 */
		List<ImInfo> infos = ReadXml.getWebInfos();
		List<ClientInfo> servers = new ArrayList<ClientInfo>();
		WebAllServer.getInstance().init(servers);
		int size = infos.size();
		if(infos != null && size > 0) {
			for(ImInfo info : infos) {
				new Thread(new WebConnect(info.getIp(),info.getPort(),info.getWeight())).start();
			}
		}

	}

}
