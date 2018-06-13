package com.jihuiduo.netproxy.server;

/*
 * 配置文件所示需要连接的服务器数据项
 */
public class ImInfo {
	 public String ip;
	 public int port;
	 public int weight;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	 
}
