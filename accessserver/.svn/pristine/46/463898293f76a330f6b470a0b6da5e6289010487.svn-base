package com.jihuiduo.netproxy.server;

import java.util.Date;

import org.apache.mina.core.session.IoSession;

/*
 * 需要你连接的服务器属性
 */
public class ClientInfo {
	 public String ip;
	 public int weight;
	 public IoSession session;
	 public int effectiveWeight;//有效权重
	 public int currentWeight;//当前权重
	 public boolean down = false;
	 public Date checkedDate;
	 public ClientInfo(String ip, int weight,IoSession session) {
		 super();
		 this.ip = ip;
		 this.weight = weight;
		 this.session = session;
		 this.effectiveWeight = this.weight;
		 this.currentWeight = 0;
		 if(this.weight < 0){
			 this.down = true;
		 }else{
			 this.down = false;
		 }
	 }
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getEffectiveWeight() {
		return effectiveWeight;
	}
	public void setEffectiveWeight(int effectiveWeight) {
		this.effectiveWeight = effectiveWeight;
	}
	public int getCurrentWeight() {
		return currentWeight;
	}
	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public Date getCheckedDate() {
		return checkedDate;
	}
	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	} 
	
}
