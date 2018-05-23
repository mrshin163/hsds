package com.bizsp.framework.util.model;

import java.io.Serializable;

import com.bizsp.framework.util.lang.StringUtil;

public class JsonModel implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6459766902134032665L;

	public final String TAGS = "tags";

	/**
	 * 성공여부
	 */
	private String successYn;
	/**
	 * 전송코드
	 */
	private String jsonCode;
	/**
	 * 전송메시지
	 */
	private String jsonMessage;
	/**
	 * 전송데이타
	 */
	private Object data;

	public String getSuccessYn() {
		return StringUtil.nvl(successYn,"Y");
	}
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getJsonCode() {
		return jsonCode;
	}
	public void setJsonCode(String jsonCode) {
		this.jsonCode = jsonCode;
	}
	public String getJsonMessage() {
		return jsonMessage;
	}
	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}



}
