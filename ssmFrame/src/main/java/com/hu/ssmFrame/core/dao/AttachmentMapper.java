package com.hu.ssmFrame.core.dao;



import org.apache.ibatis.annotations.Param;


public interface AttachmentMapper {

	
	/**
	 * 根据事项主表名称和事项主键,选用附件表的正确外键,查询内容不为空的附件数量.<br/>
	 * @param tableName 附件表关联的主表名 
	 * @param fkValue 附件表关联表的外键值
	 * @return 符合条件的附件数量
	 */
	public Long countByForeignKey(@Param("tableName")String tableName,@Param("fkValue")String fkValue);
}
