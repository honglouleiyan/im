package com.jihuiduo.netproxy.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jihuiduo.netproxy.start.SystemInitService;


public class IllegalMessage {
	private static final Logger logger = Logger.getLogger(IllegalMessage.class);
	public static void saveMessage(String describe,String data) {
		try { 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			File myFilePath = new File(SystemInitService.getBaseDir() + "/logs/" + "message.log." + formatter.format(new Date()));
			if (!myFilePath.exists()) {   
				myFilePath.createNewFile();   
			} 
			FileWriter fileWritter = new FileWriter(myFilePath,true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(matter.format(new Date())+ " " + describe + "\n" + data + "\n");
            bufferWritter.close();
            fileWritter.close();
		}catch (Exception e) {   
		   logger.info("新建或写入文件出错"); 
		} 
	}
}
