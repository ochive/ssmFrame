package com.hu.ssmFrame.service;

public interface IFileDownloadService {

	/**
	 * 根据表名和主键，去附件表下载附件，将数据保存到文件系统。
	 * @return
	 */
	public boolean downloadFileAndSavePath(String item,String itemPk);
}
