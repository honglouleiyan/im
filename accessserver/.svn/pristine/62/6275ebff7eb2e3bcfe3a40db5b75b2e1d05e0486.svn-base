package com.jihuiduo.netproxy.utils;

import java.util.HashMap;
import java.util.Map;


public class ProtocolHanlder {
	public static ProtocolHeader getHeader(String header) {
		ProtocolHeader protocolHeader = new ProtocolHeader();
		int dex = header.indexOf(" ");
		int second = header.indexOf(" ", dex+1);
		protocolHeader.setRequestMethod(header.substring(0, dex));
		protocolHeader.setAgreement(header.substring(dex+2,second));
		return protocolHeader;
	}
	public static ProtocolNode getNode(String protocolInfo) {
		ProtocolNode node = new ProtocolNode();
		Map<String, String> map = new HashMap<String, String>();
		//遇到问好  取问号前数据为协议节点   问号后面的数据为参数
		int cut = protocolInfo.indexOf("?");
		String paras = null;
		String protocol = null;
		if(cut != -1) {
			protocol = protocolInfo.substring(0,cut);
			paras = protocolInfo.substring(cut+1);
		}
		if(paras != null) {
			String[] parameters = paras.split("&");
			for(String parameter : parameters) {
				String[] v = parameter.split("=");
				map.put(v[0], v[1]);
			}
		}
		if(protocol == null) {
			protocol = protocolInfo;
		}
		node.setParameter(map);
		int firstDex = protocol.indexOf("/");
		if(firstDex == -1) {
			node.setFirstNode(protocol);
			return node;
		}
		//取第一个节点
		node.setFirstNode(protocol.substring(0,firstDex));
		int secondDex = protocol.indexOf("/",firstDex+1);
		if(secondDex == -1) {
			node.setSecondNode(protocol.substring(firstDex+1));
			return node;
		}
		//取第二个节点
		node.setSecondNode(protocol.substring(firstDex+1,secondDex));
		int threeDex =  protocol.indexOf("/",secondDex+1);
		if(threeDex == -1) {
			node.setThirdNode(protocol.substring(secondDex+1));
			return node;
		}
		//取第三个节点
		node.setThirdNode(protocol.substring(secondDex+1,threeDex));
		int fourDex =  protocol.indexOf("/",threeDex+1);
		if(fourDex == -1) {
			node.setFourthNode(protocol.substring(threeDex+1));
			return node;
		}
		//取第四个节点
		node.setFourthNode(protocol.substring(threeDex+1,fourDex));
		int fiveDex =  protocol.indexOf("/",fourDex+1);
		if(fiveDex == -1) {
			node.setFifthNode(protocol.substring(fourDex+1));
			return node;
		}
		//取第五个节点
		node.setFifthNode(protocol.substring(fourDex+1,fiveDex));
		
		return node;
	}
}
