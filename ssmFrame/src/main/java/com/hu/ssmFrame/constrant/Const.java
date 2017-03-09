package com.hu.ssmFrame.constrant;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

/**
 * 常量类
 * @author mudking
 *
 */
public class Const {
	/**
	 * 附件下载后，存放的根目录.
	 * 默认："E:/attachmentFiles/",
	 * 可以在appconfig.properties中修改
	 */
	public static final String ROOT_LOCATION;
	/**
	 * 首次执行的延时时间,单位是秒
	 */
	public static final Long START_TIME;
	/**
	 * 定时执行的间隔时间
	 */
	public static final Long INTERVAL;
	
	static{
		Properties prop = null;
		try {
			//使用Resources，加载类路径下的配置文件。
			prop = Resources.getResourceAsProperties("appconfig.properties");
		} catch (IOException e) {
			System.out.println("读取自动扫描时间失败!");
			e.printStackTrace();
		}
		//ROOT_LOCATION=prop.getProperty("dir");
		START_TIME=Long.valueOf(prop.getProperty("start"));
		INTERVAL=Long.valueOf(prop.getProperty("interval"));
	}
	
	static{
		//获取项目路径    D:\git\daotie\daotie
		File directory = new File("");// 参数为空
		String courseFile=null;
		try {
			courseFile=directory.getCanonicalPath();
		} catch (IOException e) {
			System.out.println("读取项目路径失败!");
			e.printStackTrace();
		}
		ROOT_LOCATION = courseFile;
	}
	
}
