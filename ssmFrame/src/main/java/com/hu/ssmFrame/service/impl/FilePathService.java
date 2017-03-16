package com.hu.ssmFrame.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.hu.ssmFrame.constrant.Const;
import com.hu.ssmFrame.dao.AttachmentMapper;
import com.hu.ssmFrame.dao.FilePathMapper;
import com.hu.ssmFrame.pojo.Attachment;
import com.hu.ssmFrame.pojo.FilePath;
import com.hu.ssmFrame.service.IFilePathService;

@Service("IFilePathService")
public class FilePathService implements IFilePathService {
	Logger logger=LoggerFactory.getLogger(FilePathService.class);
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	FilePathMapper filePathMapper;
	@Autowired
	AttachmentMapper attachmentMapper;

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	@Override
	public FilePath selectByAttachId(String attachId) {
		FilePath filePath = null;
		
		FilePathMapper pathMapper = sqlSessionFactory.openSession().getMapper(FilePathMapper.class);
		filePath=pathMapper.selectByAttachmentId(attachId);
		
		//如果没有查到路径信息,则去附件表下载生成.
		if(filePath==null){
			filePath=dealAttachByAttid(attachId);
		}
		
		return filePath;
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	@Override
	public FilePath dealAttachByAttid(String attId) {
		FilePath filePath=null;//封装附件文件路径信息
		
		try {
			Attachment attach = attachmentMapper.selectById(attId);
			
			//1.创建附件存放的抽象路径名对象
			File file = createFileFromAttach(attach);
			
			//b.创建IO流
			BufferedInputStream bis=new BufferedInputStream(attach.getSourceFile());
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
			
			//c.写入
			FileCopyUtils.copy(bis, bos);
			
			//3.存入成功后，讲路径信息写入数据库
			filePath=new FilePath(null,null,file.getCanonicalPath(),attach.getSourceFilename(),0,attach.getAttachmentId());
			filePathMapper.insert(filePath);
			
			//更新附件表标志字段,下次不再自动扫描这个附件
			attachmentMapper.updateFlag(attach.getAttachmentId());
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("根据附件id,处理附件时异常",e);
			}
			throw new RuntimeException(e);
		}
			
		return filePath;
	}

	/**
	 * 根据附件对象中的信息,创建附件在文件系统中存放位置的File对象.
	 * @param attach 附件对象
	 * @return 附件在文件系统中存放位置的File对象.
	 */
	public File createFileFromAttach(Attachment attach) {
		String dirName=createDirName();//文件名
		String filename=null;//存文件的目录名
		File file=null;

		//2.目标文件命名
		//attachmentFiles/2017/2/办件主键xxxx/附件主键xxxxx/123.doc
		//附件主键+目录名+原文件名(使用收件文件名的话没有后缀)+,防止文件名重复
		filename=attach.getAttachmentId()+"@"+attach.getSourceFilename();
		
		file=new File(dirName,filename);
		return file;
	}
	
	/**
	 * 创建附件存放在文件系统中的目录名.<br/>
	 * 如果该目录不存在,则会创建.
	 * @return 目录名
	 */
	public static String createDirName() {
		String dirName=null;//文件名
		File file=null;
		
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		//1.拼接附件文件存放的目录,如:"D:\workspace\zjj_approve\ssmFrame2017\3\12"
		dirName=Const.ROOT_LOCATION+"/"+cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE);
		
		file=new File(dirName);
		//如果目录不存在,则创建
		if(!(file.exists())){
			file.mkdirs();
			file=null;
		}
		
		return dirName;
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	@Override
	public FilePath findZipByItemInfo(FilePath filePath) throws Exception {
		FilePath zipInfo=null;
		
		//1.查询file_paths表,查询该表名和主键对应的路径信息,有则1.a.没有则2
		zipInfo = filePathMapper.selectZipPathByItemInfo(filePath);
		
		if(zipInfo!=null && zipInfo.getFileCount() > 0){
			if(logger.isDebugEnabled()){
				logger.debug("找到批量下载包信息,开始检查附件个数有无变化");
			}
			//1.a.查询附件表中该办件的附件数量,与路径信息对比.相同则3,不同则1.b
			Long attCount = attachmentMapper.countByForeignKey(filePath.getItem(), filePath.getItemPk());
			if(attCount.intValue()==zipInfo.getFileCount()){
				if(logger.isDebugEnabled()){
					logger.debug("附件数量一致,返回路径信息");
				}
				//返回找到的批量下载的压缩包
				return zipInfo;
			}else{
				if(logger.isDebugEnabled()){
					logger.debug("附件个数有变化,删除现有附件和路径信息.重新下载.");
				}
				//1.b.删除现有的文件和路径信息,转2
				new File(zipInfo.getFilePath()).delete();
				filePathMapper.deleteById(zipInfo.getIdFilePath());
				
				//转2
				zipInfo = findAttachsAndPackageThenSave(filePath);
			}
		}else{//转2
			zipInfo = findAttachsAndPackageThenSave(filePath);
		}
		
		return zipInfo;
	}


	/**
	 * 根据附件表的外键查找附件,打包后存入文件系统.并将路径信息存入file_paths表,最后返回路径信息.
	 * @param filePath 封装了事项主表名,和主表的主键.
	 * @return 封装得到的压缩包的文件路径信息
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	public FilePath findAttachsAndPackageThenSave(FilePath filePath) throws Exception{
		FilePath zipInfo=null;
		File destFile=null;//封装压缩包的路径信息
		BufferedInputStream bis=null;
		ZipOutputStream zipOutputStream=null;
		
		if(logger.isDebugEnabled()){
			logger.debug("开始从数据库批量下载和处理办件的附件");
		}
		try {
			//2.根据表名和主键,使用相应的条件查询附件表的数据.
			List<Attachment> attachList = attachmentMapper.selectByForeignKey(filePath.getItem(), filePath.getItemPk());
			
			//2.a.处理附件表数据,压缩成zip包存入文件系统
			if(attachList!=null && attachList.size()>0){
				//目标文件名.如:"D:\workspace\zjj_approve\ssmFrame2017\3\主表主键xxxx@主表名@附件包.zip"
				destFile=new File(createDirName(), filePath.getItemPk()+"@"+filePath.getItem()+"@附件包.zip");
				zipOutputStream=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)));
				//为了提高处理速度,压缩方法为仅打包归档存储,压缩级别为最低的0.
				//zipOutputStream.setLevel(0);
				//zipOutputStream.setMethod(ZipOutputStream.STORED);
							
				
				for(Attachment attach:attachList){
					try {
						//创建ZIP实体，并添加进压缩包
						//2017.03.14 由于存在企业的附件SourceFilename重名,导致压缩时报错dumplicate entry,增加标志
						ZipEntry zipEntry=new ZipEntry(System.currentTimeMillis()+"@"+attach.getSourceFilename());
						//zipEntry.setMethod(ZipEntry.STORED);
						zipOutputStream.putNextEntry(zipEntry);
						bis=new BufferedInputStream(attach.getSourceFile());
						
						byte[] byt=new byte[8192];
						int len=-1;//存放实际读取的长度	
						
						//读取待压缩的文件并写进压缩包里
						while((len=bis.read(byt))!=-1){
							zipOutputStream.write(byt, 0, len);
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}finally{
						if(bis!=null){
							bis.close();
						}
					}
				}
				if(logger.isDebugEnabled()){
					logger.debug("文件打包完成!路径:"+destFile.getCanonicalPath());
				}
				//2.b 并插入路径表,然后返回路径信息.
				zipInfo=new FilePath(filePath.getItem(), filePath.getItemPk(), destFile.getCanonicalPath(), destFile.getName(),
						1, null, attachList.size());
				filePathMapper.insert(zipInfo);
				if(logger.isDebugEnabled()){
					logger.debug("路径信息存入完成!");
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}finally{
			if(zipOutputStream!=null){
				zipOutputStream.close();
			}
		}
		return zipInfo;
	}
	
}
