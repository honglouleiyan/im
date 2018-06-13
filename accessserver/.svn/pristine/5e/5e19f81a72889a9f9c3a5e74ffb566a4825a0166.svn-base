package com.jihuiduo.netproxy.clientim;

import java.util.ArrayList;
import java.util.List;
import com.jihuiduo.netproxy.server.ClientInfo;
import com.jihuiduo.netproxy.server.ImInfo;
import com.jihuiduo.netproxy.utils.ReadXml;

public class ClientManage implements Runnable{
	@Override
	public void run() {
		/*
		 * 读取需要连接的IM服务器ip/port   新建连接
		 */
		List<ImInfo> infos = ReadXml.getImInfos();
		List<ClientInfo> servers = new ArrayList<ClientInfo>();
		ImAllServer.getInstance().init(servers);
		int size = infos.size();
		if(infos != null && size > 0) {
			for(ImInfo info : infos) {
				new Thread(new ClientConnect(info.getIp(),info.getPort(),info.getWeight())).start();
			}
		}
	}

}
