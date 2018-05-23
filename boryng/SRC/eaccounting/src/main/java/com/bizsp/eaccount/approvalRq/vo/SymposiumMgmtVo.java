package com.bizsp.eaccount.approvalRq.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class SymposiumMgmtVo extends BaseCndModel {

	private static final long serialVersionUID = 422644327311814327L;

	private String companyCd;
	private String approvalId;
	private String authDate;
	private String mercName;
	private String requestAmount;
	private String userNm;
	private String budgetDeptNm;
	private String account2Nm;
	private String productNm;
	private String customerNm;
	private String details;

	private String attachId;
	private String filePath;
	private String pfileNm;
	private String ext;
	private String seq;

	private String rqUserId;
	private String emailAddr;
	private String mobileNo;

	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCompanyCd() {
		return companyCd;
	}

	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getMercName() {
		return mercName;
	}

	public void setMercName(String mercName) {
		this.mercName = mercName;
	}

	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getBudgetDeptNm() {
		return budgetDeptNm;
	}

	public void setBudgetDeptNm(String budgetDeptNm) {
		this.budgetDeptNm = budgetDeptNm;
	}

	public String getAccount2Nm() {
		return account2Nm;
	}

	public void setAccount2Nm(String account2Nm) {
		this.account2Nm = account2Nm;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPfileNm() {
		return pfileNm;
	}

	public void setPfileNm(String pfileNm) {
		this.pfileNm = pfileNm;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getRqUserId() {
		return rqUserId;
	}

	public void setRqUserId(String rqUserId) {
		this.rqUserId = rqUserId;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}