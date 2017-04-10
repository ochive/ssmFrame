package com.hu.ssmFrame.core.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class MyWebUtils {
	
	/**
	 * 提供文件下载时,文件名可能会出现乱码.
	 * 根据不同的浏览器对文件名进行处理.
	 * @param request
	 * @param originalName 待处理的原始文件名
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertToDownloadName(HttpServletRequest request,String originalName) throws UnsupportedEncodingException{
		//解决iE8下,下载的文件名乱码问题
		//获取浏览器类型
		String userAgent = request.getHeader("User-Agent"); 
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			originalName = java.net.URLEncoder.encode(originalName, "UTF-8");
		} else {
		//非IE浏览器的处理：
			originalName = new String(originalName.getBytes("UTF-8"),"ISO-8859-1");
		}
		
		return originalName;
	}
	
	
}
