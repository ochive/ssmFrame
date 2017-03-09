package com.hu.ssmFrame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 自动扫描数据库中没有下载的附件,下载到文件系统.
 * @author mudking
 *
 */
@Component
public class AutoReadAndDownloadAttach implements Runnable{
	@Autowired
	IAttachmentService iAttachmentService;
	
	@Override
	public void run() {
		iAttachmentService.readAndDownAttach();
	}
	
}
