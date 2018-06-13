package com.jihuiduo.netproxy.test;

import java.text.SimpleDateFormat;
import java.util.Date;




import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;



public class ServerMinaClientHanlder extends IoHandlerAdapter {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("I'm Client &&  I coming!");
        System.out.println(df.format(new Date()));
        System.out.println(session);
    }
 
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println(df.format(new Date()));
        System.out.println("I'm Client &&  I closed!");
    }
 
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    	String msg = (String) message;
    	System.out.println(message);
    }
    
    @Override
    public void messageSent(IoSession session, Object message) {
    	  System.out.println("信息已发送!" + (String)message);
    }
}
