package com.hu.ssmFrame.service;


public interface IAttachmentService {
	/**
	 * 从数据库读取所有没下载的附件,并写入文件系统,然后将附件信息存入附件信息表,最后更新附件下载状态.
	 */
	public abstract void readAndDownAttach();
	

}
