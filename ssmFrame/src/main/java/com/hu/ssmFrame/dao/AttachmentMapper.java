package com.hu.ssmFrame.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hu.ssmFrame.pojo.Attachment;

public interface AttachmentMapper {
	/**
	 * 根据主键，更新附件表的标志位字段downloaded为1，默认0
	 * @param item
	 * @return
	 */
	public int updateFlag(String aid);
	
	/**
	 * 查询所有没有下载过的附件
	 * @return
	 */
	public abstract List<Attachment> selectNotDownloaded();
	/**
	 * 根据主键查询
	 * @param attachId
	 * @return
	 */
	public Attachment selectById(String attachId);
	
	/**
	 * 根据事项主表名称和事项主键,选用附件表的正确外键,查询内容不为空的附件信息.<br/>
	 * 参数的有效性由控制层保证.
	 * @param tableName 附件表关联的主表名 
	 * @param fkValue 附件表关联表的外键值
	 * @return 所有符合条件的附件
	 */
	public List<Attachment> selectByForeignKey(@Param("tableName")String tableName,@Param("fkValue")String fkValue);
	
	/**
	 * 根据事项主表名称和事项主键,选用附件表的正确外键,查询内容不为空的附件数量.<br/>
	 * @param tableName 附件表关联的主表名 
	 * @param fkValue 附件表关联表的外键值
	 * @return 符合条件的附件数量
	 */
	public Long countByForeignKey(@Param("tableName")String tableName,@Param("fkValue")String fkValue);
}
