package com.bizsp.eaccount.uploadFile.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class AttachVo extends BaseCndModel{

	private static final long serialVersionUID = -1817661004196856784L;
	private String companyCode;
	private String attachId;
	private String bizTpCode;
	private String bizKey;
	private String useUn;
	private String delYn;
	private String regDate;
	private String regUserId;
	private String updDate;
	private String updUserId;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getBizTpCode() {
		return bizTpCode;
	}
	public void setBizTpCode(String bizTpCode) {
		this.bizTpCode = bizTpCode;
	}
	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	public String getUseUn() {
		return useUn;
	}
	public void setUseUn(String useUn) {
		this.useUn = useUn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getUpdUserId() {
		return updUserId;
	}
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	
	
}

