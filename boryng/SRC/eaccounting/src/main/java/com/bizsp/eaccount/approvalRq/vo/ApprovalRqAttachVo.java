package com.bizsp.eaccount.approvalRq.vo;

import java.util.List;

public class ApprovalRqAttachVo {
	private String 	companyCode;
	private String 	approvalId;
	private Integer seq;
	private String 	attachId;
	private String 	approvalAttachTypeCode;
	private String 	regDatetime;
	private String 	regUserId;
	private String 	pFileName;
	private String 	attachSeq;
	private String 	sApprovalId;
	
	public String getsApprovalId() {
		return sApprovalId;
	}
	public void setsApprovalId(String sApprovalId) {
		this.sApprovalId = sApprovalId;
	}
	public String getAttachSeq() {
		return attachSeq;
	}
	public void setAttachSeq(String attachSeq) {
		this.attachSeq = attachSeq;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getApprovalAttachTypeCode() {
		return approvalAttachTypeCode;
	}
	public void setApprovalAttachTypeCode(String approvalAttachTypeCode) {
		this.approvalAttachTypeCode = approvalAttachTypeCode;
	}
	public String getRegDatetime() {
		return regDatetime;
	}
	public void setRegDatetime(String regDatetime) {
		this.regDatetime = regDatetime;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getpFileName() {
		return pFileName;
	}
	public void setpFileName(String pFileName) {
		this.pFileName = pFileName;
	}
	
}
