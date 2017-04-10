package com.hu.ssmFrame.core.vo;

import java.io.Serializable;

/**
 * JSON模型
 * 封装了 操作结果,消息,查询条件的封装对象.
 * 用于像页面传递操作的结果.
 * @author 胡振华
 * @version 创建时间：2016-7-8 下午02:03:31 
 *
 */
public class ResultForAjax implements Serializable {
	private static final long serialVersionUID = -7802581705039052139L;
	//操作结果.
	private boolean success = false;
	//消息
	private String message = "";
	//可用于封装回显的条件
	private Object obj = null;
	/**
	 *  消息
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	/**
	 *  消息
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 操作结果.默认false
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 操作结果.默认false
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * 可用于封装回显的条件
	 * @return
	 */
	public Object getObj() {
		return obj;
	}
	/**
	 * 可用于封装回显的条件
	 * @param obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		return "ResultForAjax [message=" + message + ", obj=" + obj
				+ ", success=" + success + "]";
	}
}
