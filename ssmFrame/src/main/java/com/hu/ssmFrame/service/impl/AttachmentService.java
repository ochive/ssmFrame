package com.hu.ssmFrame.service.impl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.hu.ssmFrame.dao.AttachmentMapper;
import com.hu.ssmFrame.dao.FilePathMapper;
import com.hu.ssmFrame.pojo.Attachment;
import com.hu.ssmFrame.pojo.FilePath;
import com.hu.ssmFrame.service.IAttachmentService;

@Service("IAttachmentService")
public class AttachmentService implements IAttachmentService{
	private Logger logger=LoggerFactory.getLogger(AttachmentService.class);
	
	@Autowired
	IAttachmentService iAttachmentService;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	FilePathMapper filePathMapper;

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void readAndDownAttach() {
		try {
			AttachmentMapper attMapper = sqlSessionTemplate.getMapper(AttachmentMapper.class);
			//查询外网系统中所有没被下载的附件
			List<Attachment> attList = attMapper.selectNotDownloaded();
			
			if(attList!=null && attList.size()>0){
				String filename=null;
				File file=null;
				FilePath filePath=null;
				String dirName = FilePathService.createDirName();
				
				//循环处理，保存文件
				for(Attachment attach:attList){
						//c.目标文件命名
						//attachmentFiles/2017/2/办件主键xxxx/附件主键xxxxx/123.doc
						//附件主键+目录名+原文件名(使用收件文件名的话没有后缀)+,防止文件名重复
						filename=attach.getAttachmentId()+"@"+attach.getSourceFilename();
						
						file=new File(dirName,filename);
						
						//a.获取输入流
						BufferedInputStream bis=new BufferedInputStream(attach.getSourceFile());
						//b.获取输出流
						BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
	
						
						//d.写入
						FileCopyUtils.copy(bis, bos);
						
						//7.存入成功后，讲路径信息写入数据库
						filePath=new FilePath(null,null,file.getCanonicalPath(),attach.getSourceFilename(),0,attach.getAttachmentId());
						filePathMapper.insert(filePath);
						
						//更新附件表标志字段,下次不再自动扫描这个附件
						attMapper.updateFlag(attach.getAttachmentId());
					
				}
				//能到这，说明所有文件都存完了。开始打包
				//FileToZip.fileToZip(dirName, dirName, pkName);
			}
			if(logger.isDebugEnabled()){
				logger.debug("本次附件下载完成");
			}
		} catch (Exception e) {
			if(logger.isInfoEnabled()){
				logger.info("自动下载附件时报错", e);
			}
			throw new RuntimeException(e);
		}
	}

	
	


	
}
