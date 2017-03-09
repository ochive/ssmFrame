package com.hu.ssmFrame.pojo;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 封装附件表关键字段
 * 
 * @author mudking
 *
 */
public class Attachment implements Serializable {
	private static final long serialVersionUID = -4075554841542154889L;
	
	private String attachmentId;// 主键
	private String ord;// 附件顺序。一个办件的附件是有顺序的
	private String sjFilename;// 收件材料名称，附件名
	private InputStream sourceFile;// 二进制文件
	private String sourceFilename;// 原始附件名（带扩展名的）
	private Boolean downloaded;// 是否被下载到文件系统过。默认0，已下载则为1

	public Attachment() {
	}

	public Attachment(String attachmentId, String ord, String sjFilename, InputStream sourceFile, String sourceFilename,
			Boolean downloaded) {
		super();
		this.attachmentId = attachmentId;
		this.ord = ord;
		this.sjFilename = sjFilename;
		this.sourceFile = sourceFile;
		this.sourceFilename = sourceFilename;
		this.downloaded = downloaded;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}
	
	public String getSjFilename() {
		return sjFilename;
	}

	public void setSjFilename(String sjFilename) {
		this.sjFilename = sjFilename;
	}
	
	public InputStream getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(InputStream sourceFile) {
		this.sourceFile = sourceFile;
	}
	/**
	 * 原始附件名（带扩展名的）
	 * @return
	 */
	public String getSourceFilename() {
		return sourceFilename;
	}
	/**
	 * 原始附件名（带扩展名的）
	 * @param sourceFilename
	 */
	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public Boolean getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded) {
		this.downloaded = downloaded;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
}
