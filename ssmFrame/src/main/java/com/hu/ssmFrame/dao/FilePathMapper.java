package com.hu.ssmFrame.dao;




import java.util.List;

import com.hu.ssmFrame.pojo.FilePath;

public interface FilePathMapper {
	/**
	 * 插入数据
	 */
	public int insert(FilePath item);
	
	/**
	 * 根据主键删除数据
	 * @param item
	 * @return
	 */
	public int deleteById(String idFilePath);
	
	/**
	 * 根据附件的主键查找
	 * @param id
	 * @return
	 */
	public FilePath selectByAttachmentId(String attachId);
	
	/**
	 * 根据事项主表名称和事项主键,查询办件的批量下载包信息
	 * @param filePath
	 * @return
	 */
	public FilePath selectZipPathByItemInfo(FilePath filePath);
	
	
}
