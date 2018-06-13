package com.jihuiduo.netproxy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jihuiduo.netproxy.server.ImInfo;


public class ReadXml {
	private static ReadXml readXml = null;
	private String localAddress;
	private int localMonitorPort;
	private int localPort;
	private int idleTime;
	private List<ImInfo> imInfos;
	private List<ImInfo> webInfos;
	private int imReconnectionTime;
	private int webReconnectionTime;
	private int webOuttime;
	private int imOuttime;
	private ReadXml(String fileName){
		initConfig(fileName);
	}
	
	public static ReadXml getInstance(String fileName){
		if(readXml == null){
			synchronized(ReadXml.class){
				if(readXml == null){
					readXml = new ReadXml(fileName);
				}
			}
		}
		return readXml;
	}

	
	public void initConfig(String fileName){
		readConfigFile(fileName);
	}
	
	
	@SuppressWarnings("rawtypes")
	private void readConfigFile(String fileName){
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			File xmlFile = new File(fileName);  
			doc = reader.read(xmlFile);
			if(doc == null) 
				return ;
			Element root = doc.getRootElement();
			localAddress = root.element("localaddress").attributeValue("ip");
			localMonitorPort = Integer.parseInt(root.element("localaddress").attributeValue("port"));
			localPort = Integer.parseInt(root.element("localaddress").attributeValue("localport"));
			idleTime = Integer.parseInt(root.element("localaddress").attributeValue("idleTime"));
			imReconnectionTime = Integer.parseInt(root.element("imaddress").attributeValue("reconnectionTime"))*1000;
			webReconnectionTime = Integer.parseInt(root.element("webaddress").attributeValue("reconnectionTime"))*1000;
			imOuttime = Integer.parseInt(root.element("imaddress").attributeValue("setouttime"))*1000;
			webOuttime = Integer.parseInt(root.element("webaddress").attributeValue("setouttime"))*1000;
			Element ticket=null; 
			Iterator tickets = null;
			List<ImInfo> list = new ArrayList<ImInfo>();
			for (tickets = root.element("imaddress").elementIterator(); tickets.hasNext();) {
				ticket = (Element) tickets.next();
				ImInfo imInfo = new ImInfo();
				imInfo.setIp(ticket.attributeValue("ip"));
				imInfo.setPort(Integer.parseInt(ticket.attributeValue("port")));
				imInfo.setWeight(Integer.parseInt(ticket.attributeValue("weight")));
				list.add(imInfo);
			}
			imInfos = list;
			List<ImInfo> list2 = new ArrayList<ImInfo>();
			for (tickets = root.element("webaddress").elementIterator(); tickets.hasNext();) {
				ticket = (Element) tickets.next();
				ImInfo imInfo = new ImInfo();
				imInfo.setIp(ticket.attributeValue("ip"));
				imInfo.setPort(Integer.parseInt(ticket.attributeValue("port")));
				imInfo.setWeight(Integer.parseInt(ticket.attributeValue("weight")));
				list2.add(imInfo);
			}
			webInfos = list2;
		} catch (Exception e) {
			throw new RuntimeException("config file netproxy.xml error",e);
		}
		
	}
	
	public static String getLocalAddress() {
		return readXml.localAddress;
	}
	public static int getlocalMonitorPort() {
		return readXml.localMonitorPort;
	}
	public static int getlocalPort() {
		return readXml.localPort;
	}
	public static int getIdleTime() {
		return readXml.idleTime;
	}
	public static List<ImInfo> getImInfos() {
		return readXml.imInfos;
	}
	public static List<ImInfo> getWebInfos() {
		return readXml.webInfos;
	}
	public static int getImconnectionTime() {
		return readXml.imReconnectionTime;
	}
	public static int getwebconnectionTime() {
		return readXml.webReconnectionTime;
	}
	public static int getwebouttime() {
		return readXml.webOuttime;
	}
	public static int getimouttime() {
		return readXml.imOuttime;
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		System.out.println(ReadXml.getWebInfos().get(0).getIp());
		
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			File xmlFile = new File(System.getProperty("user.dir") + "/src/conf/netproxy.xml");  
			doc = reader.read(xmlFile);
			if(doc == null) 
				return ;
			Element root = doc.getRootElement();
			Element ticket=null; 
			   
			Iterator tickets = null;
			for (tickets = root.element("imaddress").elementIterator(); tickets.hasNext();) {
				ticket = (Element) tickets.next();
				System.out.print(ticket.attributeValue("ip")+"  ");
				System.out.print(ticket.attributeValue("port")+"  ");
				System.out.println(ticket.attributeValue("weight"));
			 }
			System.out.println(root.element("localaddress").attributeValue("port"));
		} catch (Exception e) {
			throw new RuntimeException("config file netproxy.xml error",e);
		}
	}
}
