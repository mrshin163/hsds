package com.bizsp.eaccount.downloadFile.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class AttachVo extends BaseCndModel {

	private static final long serialVersionUID = 422644327311814327L;

	private String companyCd;
	private String attachId;
	private String bizTpCd;
	private String bizKey;
	private String useYn;
	private String delYn;
	private String regDttm;
	private String regUserId;
	private String updDttm;
	private String updUserId;

	public String getCompanyCd() {
		return companyCd;
	}

	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getBizTpCd() {
		return bizTpCd;
	}

	public void setBizTpCd(String bizTpCd) {
		this.bizTpCd = bizTpCd;
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRegDttm() {
		return regDttm;
	}

	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}

	public String getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public String getUpdDttm() {
		return updDttm;
	}

	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}