package com.hu.ssmFrame.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hu.ssmFrame.service.IAttachmentService;
import com.hu.ssmFrame.service.IFileDownloadService;
import com.hu.ssmFrame.service.IFilePathService;

@Service("IFileDownloadService")
public class FileDownloadService implements IFileDownloadService {
	@Autowired
	IFilePathService iFilePathService;
	@Autowired
	IAttachmentService iAttachmentService;
	
	@Override
	public boolean downloadFileAndSavePath(String tName, String pkName) {
		/*
			boolean flag=false;
			
			try {
				//5.如果没有，则查询附件表
				List<Attachment> attachList = iAttachmentService.selectByCondition(tName,pkName);
				//6.如果查到，则将附件存入文件系统
				if(attachList!=null && attachList.size()>0){
					String filename=null;
					File file=null;
					FilePath filePath=null;
					String dirName=null;
					
					Calendar cal=Calendar.getInstance();
					cal.setTimeInMillis(System.currentTimeMillis());
					
					dirName=Const.ROOT_LOCATION+cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+pkName;
					
					//循环处理，保存文件
					for(Attachment attach:attachList){
						try {
							//a.获取输入流
							InputStream binaryStream = attach.getSourceFile().getBinaryStream();
							//b.获取输出流
							//c.文件命名
							//attachmentFiles/2017/2/办件主键xxxx/附件主键xxxxx/123.doc
							filename=dirName+"/"+attach.getSourceFile();
							
							file=new File(filename);
							BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));

							//d.写入
							FileCopyUtils.copy(binaryStream, bos);
							
							//7.存入成功后，讲路径信息写入数据库
							filePath=new FilePath(UUID.randomUUID().toString().replace("-", "")
									, tName, pkName, file.getCanonicalPath(), attach.getSjFilename(), 0);
							iFilePathService.insert(filePath);
							
							iAttachmentService.updateFlag(attach.getAttachmentId());
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					//能到这，说明所有文件都存完了。开始打包
					FileToZip.fileToZip(dirName, dirName, pkName);
					
					flag=true;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		
		
		return flag;
		*/
		return false;
	}
	
	
	
	
}
