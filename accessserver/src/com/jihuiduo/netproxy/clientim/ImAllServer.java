package com.jihuiduo.netproxy.clientim;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.session.IoSession;

import com.jihuiduo.netproxy.server.ClientInfo;

/*
 * 管理所哟逇IM服务器 选择最优服务器
 */
public class ImAllServer {
    private static ImAllServer single=null;  
    public static ImAllServer getInstance() {  
         if (single == null) {    
        	 synchronized(ImAllServer.class){
        		 single = new ImAllServer();  
        	 }
         }    
        return single;
    } 

	private List<ClientInfo> serverList; //服务器集合
	public ClientInfo getBestServer() {
		ClientInfo server = null;
		ClientInfo best = null;
		int total = 0;
		for(int i=0,len=serverList.size();i<len;i++){
			//当前服务器对象
			server = serverList.get(i);
			if(server.down){
				 continue;
			}
			server.currentWeight += server.effectiveWeight;
			total += server.effectiveWeight;
			if(server.effectiveWeight < server.weight){
				 server.effectiveWeight++;
			}
			if(best == null || server.currentWeight>best.currentWeight){
				 best = server;
			}
		}
		if (best == null) {
				return null;
		}
		best.currentWeight -= total;
		best.checkedDate = new Date();
		return best;
	}
	
	public synchronized void add(ClientInfo info) {
		serverList.add(info);
	}
	
	public ClientInfo getServer(String address) {
		if(serverList !=null && serverList.size() > 0) {
			for(ClientInfo clientInfo : serverList) {
				if(clientInfo.getIp().equals(address) ) {
					return clientInfo;
				}
			}
		}
		return null;
	}
	
	public void setDown(IoSession session) {
		if(serverList !=null && serverList.size() > 0) {
			for(ClientInfo clientInfo : serverList) {
				if(session.equals(clientInfo.getSession())) {
					clientInfo.setDown(true);
				}
			}
		}
	}
	
	
	public void init(List<ClientInfo> servers) {
		serverList = new ArrayList<ClientInfo>(); 
		for (ClientInfo server : servers) {
			serverList.add(server);
		}
	}
	
	
	public   static   void  main(String[] args)  {
		ImAllServer obj = ImAllServer.getInstance();
		IoSession ioSession = null;
		ClientInfo s1 = new ClientInfo("192.168.0.100", 3,ioSession);
		ClientInfo s2 = new ClientInfo("192.168.0.101", 2,ioSession);
		ClientInfo s3 = new ClientInfo("192.168.0.102", 2,ioSession);
		List<ClientInfo> servers = new ArrayList<ClientInfo>();
		servers.add(s1);
		servers.add(s2);
		servers.add(s3);
		obj.init(servers);
		Map<String,Integer> countResult = new HashMap<String,Integer>();
		for (int i = 0; i < 10; i++) {
			 ClientInfo s = obj.getBestServer();
			 String log = "ip:"+s.ip+";weight:"+s.weight;
			 if(countResult.containsKey(log)){
				 countResult.put(log,countResult.get(log)+1);
			 }else{
				 countResult.put(log,1);
			 }
			 System.out.println(log);
			 if(i == 3) {
				ClientInfo s4 = new ClientInfo("192.168.0.103", 1,ioSession);
				ImAllServer.getInstance().add(s4);
			 }
		}
		for(Entry<String, Integer> map : countResult.entrySet()){
			System.out.println("服务器 "+map.getKey()+" 请求次数： "+map.getValue());
		}
	}


	public List<ClientInfo> getServerList() {
		return serverList;
	}

	public void setServerList(List<ClientInfo> serverList) {
		this.serverList = serverList;
	}
	
}
