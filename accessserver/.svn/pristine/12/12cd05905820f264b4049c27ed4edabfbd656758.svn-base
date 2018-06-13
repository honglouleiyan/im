package com.jihuiduo.netproxy.clientim;


import java.util.Date;
import org.apache.mina.core.session.IoSession;

public class HeartBeat implements Runnable{
	private IoSession session;
	public HeartBeat(IoSession session){
		this.session=session;
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(new Date().getTime()-ClientHanlder.heartTime.getTime() >= 5000) {
				session.write("");
			}
			if(session == null) {
				break;
			}
		}
	}
}
