package com.hu.ssmFrame.web;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hu.ssmFrame.pojo.FilePath;
import com.hu.ssmFrame.service.IAttachmentService;
import com.hu.ssmFrame.service.IFileDownloadService;
import com.hu.ssmFrame.service.IFilePathService;

/**
 * 文件下载控制器。
 * 1。提供文件下载的页面
 * 2.用户点击下载链接时，提供文件的下载
 * @author mudking
 *
 */
@Controller
@RequestMapping("/fileService")
public class FileDownloadController {
	@Autowired
	IFilePathService iFilePathService;
	@Autowired
	IAttachmentService iAttachmentService;
	@Autowired
	IFileDownloadService iFileDownloadService;
	
	private static final Logger logger=LoggerFactory.getLogger(FileDownloadController.class);
	/**
	 * 用户访问时，根据参数，准备相应的视图数据并跳转
	 * @param tableName
	 * @param primarykey
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="listUI",method=RequestMethod.GET)
	public String listUI(String tableName,String primarykey,Model model) throws Exception{
		/*//1.根据tableName确定是查哪个事项的附件
		if(StringUtils.isBlank(tableName)||StringUtils.isBlank(primarykey)){
			throw new Exception("参数有误");
		}
		FilePath queryFilePath=null;//封装查询参数
		List<FilePath> pathsList =null;//封装查到的路径信息
		
		//封装参数
		queryFilePath=new FilePath();
		queryFilePath.setItem(tableName);
		queryFilePath.setItemPk(primarykey);
		
		//2.调用相应的模块处理
		//工业产品
		if(ItemName.gycp.gettName().equals(tableName)){
			//3.判断是否附件已经下载下来了
			int count = iFilePathService.countByCondition(queryFilePath);
			if(count>0){
				//4.如果有，则查询路径信息，传给视图
				pathsList = iFilePathService.selectByCondition(queryFilePath);
			}else{
				if(iFileDownloadService.downloadFileAndSavePath(tableName, primarykey)){
					pathsList = iFilePathService.selectByCondition(queryFilePath);
				}
			}
		}
		
		if(ItemName.jdc.gettName().equals(tableName)){
			//机动车
		}
		
		//将路径信息存入域对象
		model.addAttribute("paths", pathsList);
		
		return "listUI";*/
		return null;
	}
	
	/**
	 * 用户在下载页面点击文件下载链接时。提供文件下载
	 * @param id_file_paths
	 * @throws Exception 抛出异常让spring报错
	 */
	@RequestMapping(value="fetchFile",method=RequestMethod.GET)
	public void fetchFile(String attachId,HttpServletResponse response) throws Exception{
		if(StringUtils.isBlank(attachId)){
			throw new RuntimeException("附件id不能为空");
		}
		
		try {
			//1.根据主键查找文件的路径
			FilePath filePath = iFilePathService.selectByAttachId(attachId);
			//2.读取文件
			File file=new File(filePath.getFilePath());
			
			//3.设置响应头
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			//response.setContentLengthLong( file.length());这个方法用不了?
			response.setHeader("Content-Length", file.length()+"");
			//response.addHeader( "Cache-Control", "no-cache" );//浏览器和缓存服务器都不应该缓存页面信息
			//response.addHeader( "Cache-Control", "no-store" );//请求和响应的信息都不应该被存储在对方的磁盘系统中；
			//response.addHeader( "Casche-Control", "must-revalidate" );*///于客户机的每次请求，代理服务器必须想服务器验证缓存是否过时；
			//response.setHeader( "Pragma", "no-cache" ); 
			
			//4.提供下载
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
			
		} catch (Exception e) {
			if(logger.isWarnEnabled()){
				logger.warn("下载文件时报错", e);
			}
			throw new Exception(e);
		}
	}
	
	/**
	 * 用户点击批量下载时,提供该办件所有附件的打包下载.
	 * @param tableName 事项的主表名
	 * @param primarykey 事项的办件主键
	 * @param response
	 */
	@RequestMapping(value="fetchZip",method=RequestMethod.GET)
	public void fetchZip(String tableName,String primarykey,HttpServletResponse response){
		try {
			//1.数据检查
			if(StringUtils.isBlank(tableName)||StringUtils.isBlank(primarykey)){
				throw new RuntimeException("表名和主键不能为空!");
			}
			
			//2.封装参数
			FilePath filePath = new FilePath();
			filePath.setItem(tableName);
			filePath.setItemPk(primarykey);
			
			//3.调用模型层
			FilePath zipPath = iFilePathService.findZipByItemInfo(filePath);
			
			if(zipPath==null){
				throw new RuntimeException("没找到附件路径信息");
			}
			//4.提供下载
			//a.读取文件
			File file=new File(zipPath.getFilePath());
			
			//b.设置响应头
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			response.setHeader("Content-Length", file.length()+"");
			
			//c.提供下载
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
