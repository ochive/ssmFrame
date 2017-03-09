package com.hu.ssmFrame.service;

import java.util.List;

import com.hu.ssmFrame.pojo.FilePath;

public interface IFilePathService {
	/**
	 * 插入数据
	 */
	//public boolean insert(FilePath item);
	
	/**
	 * 根据主键删除数据
	 * @param idFilePath
	 * @return
	 */
	//public boolean deletePath(String idFilePath);
	
	
	/**
	 * 根据附件主键查找路径信息
	 * @param attachId 附件的主键id,由控制层保证非空
	 * @return 
	 */
	public FilePath selectByAttachId(String attachId);
	
	
	/**
	 * 根据附件主键寻找附件,下载附件到文件系统,更新附件表标志位,最后插入附件路径信息表.
	 * 返回路径信息对象.
	 * @param attId 附件的主键
	 * @return 附件路径信息.
	 */
	public abstract FilePath dealAttachByAttid(String attId);
	
	/**
	 * 根据事项主表名称和事项主键,返回对应的批量下载包信息
	 * @param filePath 封装事项主表名称和事项主键
	 * @return
	 * @throws Exception 
	 */
	public abstract FilePath findZipByItemInfo(FilePath filePath) throws Exception;
}
