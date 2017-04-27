package com.hu.ssmFrame.core.vo;

import java.io.Serializable;

/**
 * 需要以json形式返回数据给视图时,可以使用此类封装.
 * @author mudking
 *
 */
public class JsonResult implements Serializable{
	private static final long serialVersionUID = 1323040274406739612L;
	/**
	 * 可用于返回消息
	 */
	private String message;
	/**
	 * 操作是否成功
	 */
	private Boolean isSuccess;
	/**
	 * 封装返回的数据
	 */
	private Object data;
	/**
	 * 获取message
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置message
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取isSuccess
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	/**
	 * 设置isSuccess
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * 获取data
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * 设置data
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
