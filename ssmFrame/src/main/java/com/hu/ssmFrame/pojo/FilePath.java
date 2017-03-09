package com.hu.ssmFrame.pojo;

import java.io.Serializable;

/**
 * 一个附件被下载到文件系统中，会将相关信息存入文件路径表filePaths。
 * 本类用于封装一条附件路径信息
 * @author mudking
 *
 */
public class FilePath implements Serializable{

	private static final long serialVersionUID = -737373390611978849L;
	
	private String idFilePath;//本表主键
	private String item;//事项类型（事项表的表名）
	private String itemPk;//办件事项表的主键
	private String filePath;//文件路径
	private String fileName;//文件名
	private Integer type;//文件类型.默认0:单个附件,1:该办件的所有附件打包
	private String attachId;//对应附件表的主键
	private Integer fileCount;//当本条记录记录的是批量下载包的信息时,这里记录该包中的附件数量.
	
	public FilePath() {
		super();
	}
	
	public FilePath(String idFilePath, String item, String itemPk, String filePath, String fileName, Integer type,
			String attachId) {
		super();
		this.idFilePath = idFilePath;
		this.item = item;
		this.itemPk = itemPk;
		this.filePath = filePath;
		this.fileName = fileName;
		this.type = type;
		this.attachId = attachId;
	}

	public FilePath(String item, String itemPk, String filePath, String fileName, Integer type, String attachId) {
		super();
		this.item = item;
		this.itemPk = itemPk;
		this.filePath = filePath;
		this.fileName = fileName;
		this.type = type;
		this.attachId = attachId;
	}

	
	/**
	 * 记录批量下载包时使用.
	 * @param item
	 * @param itemPk
	 * @param filePath
	 * @param fileName
	 * @param type
	 * @param attachId
	 * @param fileCount
	 */
	public FilePath(String item, String itemPk, String filePath, String fileName, Integer type, String attachId,
			Integer fileCount) {
		super();
		this.item = item;
		this.itemPk = itemPk;
		this.filePath = filePath;
		this.fileName = fileName;
		this.type = type;
		this.attachId = attachId;
		this.fileCount = fileCount;
	}
	
	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	public String getIdFilePath() {
		return idFilePath;
	}

	public void setIdFilePath(String idFilePath) {
		this.idFilePath = idFilePath;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemPk() {
		return itemPk;
	}

	public void setItemPk(String itemPk) {
		this.itemPk = itemPk;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	
}
