package com.bizsp.eaccount.report.vo;

import com.bizsp.framework.util.model.BaseCndModel;


public class CardProcessVo extends BaseCndModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 42264234411814327L;
	private String companyCd;
	private String userId;
	private String userNm;
	private String cardNum;
	private String cardName;
	private String mercName;
	private String requestAmount;
	private String georaeColl;
	private String authTime;
	private String authNum;
	private String mercAddr;
	private String mccName;
	private String mercSaupNo;
	private String rqUserNm;
	private String rqDeptNm;
	private String approvalId;
	private String approvalStatusCd;
	private String accountNm;
	private String account2Nm;
	private String details;
	private String financeNo;
	private String junpyoStCd;
	private String junpyoStNm;
	private String authDate;
	private String startAuthDate;
	private String endAuthDate;
	private String deptCd;
	private String deptNm;
	private String ownUserId;
	private String ownUserNm;
	private String cardUserCode;
	private String sumRequestAmount;

	

	public String getOwnUserNm() {
		return ownUserNm;
	}
	public void setOwnUserNm(String ownUserNm) {
		this.ownUserNm = ownUserNm;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getSumRequestAmount() {
		return sumRequestAmount;
	}
	public void setSumRequestAmount(String sumRequestAmount) {
		this.sumRequestAmount = sumRequestAmount;
	}
	public String getCardUserCode() {
		return cardUserCode;
	}
	public void setCardUserCode(String cardUserCode) {
		this.cardUserCode = cardUserCode;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getOwnUserId() {
		return ownUserId;
	}
	public void setOwnUserId(String ownUserId) {
		this.ownUserId = ownUserId;
	}
	public String getStartAuthDate() {
		return startAuthDate;
	}
	public void setStartAuthDate(String startAuthDate) {
		this.startAuthDate = startAuthDate;
	}
	public String getEndAuthDate() {
		return endAuthDate;
	}
	public void setEndAuthDate(String endAuthDate) {
		this.endAuthDate = endAuthDate;
	}
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
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
	public String getGeoraeColl() {
		return georaeColl;
	}
	public void setGeoraeColl(String georaeColl) {
		this.georaeColl = georaeColl;
	}
	public String getAuthTime() {
		return authTime;
	}
	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}
	public String getAuthNum() {
		return authNum;
	}
	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}
	public String getMercAddr() {
		return mercAddr;
	}
	public void setMercAddr(String mercAddr) {
		this.mercAddr = mercAddr;
	}
	public String getMccName() {
		return mccName;
	}
	public void setMccName(String mccName) {
		this.mccName = mccName;
	}
	public String getMercSaupNo() {
		return mercSaupNo;
	}
	public void setMercSaupNo(String mercSaupNo) {
		this.mercSaupNo = mercSaupNo;
	}
	public String getRqUserNm() {
		return rqUserNm;
	}
	public void setRqUserNm(String rqUserNm) {
		this.rqUserNm = rqUserNm;
	}
	public String getRqDeptNm() {
		return rqDeptNm;
	}
	public void setRqDeptNm(String rqDeptNm) {
		this.rqDeptNm = rqDeptNm;
	}
	public String getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	public String getApprovalStatusCd() {
		return approvalStatusCd;
	}
	public void setApprovalStatusCd(String approvalStatusCd) {
		this.approvalStatusCd = approvalStatusCd;
	}
	public String getAccountNm() {
		return accountNm;
	}
	public void setAccountNm(String accountNm) {
		this.accountNm = accountNm;
	}
	public String getAccount2Nm() {
		return account2Nm;
	}
	public void setAccount2Nm(String account2Nm) {
		this.account2Nm = account2Nm;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getFinanceNo() {
		return financeNo;
	}
	public void setFinanceNo(String financeNo) {
		this.financeNo = financeNo;
	}
	public String getJunpyoStCd() {
		return junpyoStCd;
	}
	public void setJunpyoStCd(String junpyoStCd) {
		this.junpyoStCd = junpyoStCd;
	}
	public String getJunpyoStNm() {
		return junpyoStNm;
	}
	public void setJunpyoStNm(String junpyoStNm) {
		this.junpyoStNm = junpyoStNm;
	}
	@Override
	public String toString() {
		return "CardProcessVo [companyCd=" + companyCd + ", userId=" + userId
				+ ", userNm=" + userNm + ", cardNum=" + cardNum + ", cardName="
				+ cardName + ", mercName=" + mercName + ", requestAmount="
				+ requestAmount + ", georaeColl=" + georaeColl + ", authTime="
				+ authTime + ", authNum=" + authNum + ", mercAddr=" + mercAddr
				+ ", mccName=" + mccName + ", mercSaupNo=" + mercSaupNo
				+ ", rqUserNm=" + rqUserNm + ", rqDeptNm=" + rqDeptNm
				+ ", approvalId=" + approvalId + ", approvalStatusCd="
				+ approvalStatusCd + ", accountNm=" + accountNm
				+ ", account2Nm=" + account2Nm + ", details=" + details
				+ ", financeNo=" + financeNo + ", junpyoStCd=" + junpyoStCd
				+ ", junpyoStNm=" + junpyoStNm + ", authDate=" + authDate
				+ ", startAuthDate=" + startAuthDate + ", endAuthDate="
				+ endAuthDate + ", deptCd=" + deptCd + ", deptNm=" + deptNm
				+ ", ownUserId=" + ownUserId + ", ownUserNm=" + ownUserNm
				+ ", cardUserCode=" + cardUserCode + ", sumRequestAmount="
				+ sumRequestAmount + "]";
	}	
	
	
}
