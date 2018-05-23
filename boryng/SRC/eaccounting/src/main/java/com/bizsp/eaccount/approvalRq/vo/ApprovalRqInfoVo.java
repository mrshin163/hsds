package com.bizsp.eaccount.approvalRq.vo;

import java.util.Date;
import java.util.List;

public class ApprovalRqInfoVo {

	private String companyCode;
	private String approvalId;
	private Date rqDate;
	private String rqOpinion;
	private String approvalStatusCode;
	private Date approvalDate;
	private String accountCode;
	private String accountName;
	private String budgetDeptCode;
	private String budgetDeptName;
	private String rqUserId;
	private String rqUserName;
	private String rqDeptCode;
	private String rqDeptName;
	private String rqTitleCode;
	private String rqTitleName;
	private String rqDutyCode;
	private String rqDutyName;
	private String rrqUserId;
	private String rrqUserName;
	private String rrqDeptCode;
	private String rrqDeptName;
	private String rrqTitleCode;
	private String rrqTitleName;
	private String rrqDutyCode;
	private String rrqDutyName;
	private String channelTypeCode;
	private String delYn;
	private String regDateTime;
	private String regUserId;
	private String updtDateTime;
	private String updtUserId;
	private String cardNum;
	private String authDate;
	private String authNum;
	private String georaeStat;
	private String requestAmount;
	private String georaeColl;
	private String ownerId;
	private String nreqUserId;
	
	private String sApprovalId; // 심포지움 결재요청건의 ID
	private String sApprovalSeq; // 심포지움 품의서 내역 일련번호
	
	private List<String> sApprovalIds;
	
	public List<String> getsApprovalIds() {
		return sApprovalIds;
	}
	public void setsApprovalIds(List<String> sApprovalIds) {
		this.sApprovalIds = sApprovalIds;
	}
	public String getNreqUserId() {
		return nreqUserId;
	}
	public void setNreqUserId(String nreqUserId) {
		this.nreqUserId = nreqUserId;
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
	public Date getRqDate() {
		return rqDate;
	}
	public void setRqDate(Date rqDate) {
		this.rqDate = rqDate;
	}
	public String getRqOpinion() {
		return rqOpinion;
	}
	public void setRqOpinion(String rqOpinion) {
		this.rqOpinion = rqOpinion;
	}
	public String getApprovalStatusCode() {
		return approvalStatusCode;
	}
	public void setApprovalStatusCode(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBudgetDeptCode() {
		return budgetDeptCode;
	}
	public void setBudgetDeptCode(String budgetDeptCode) {
		this.budgetDeptCode = budgetDeptCode;
	}
	public String getBudgetDeptName() {
		return budgetDeptName;
	}
	public void setBudgetDeptName(String budgetDeptName) {
		this.budgetDeptName = budgetDeptName;
	}
	public String getRqUserId() {
		return rqUserId;
	}
	public void setRqUserId(String rqUserId) {
		this.rqUserId = rqUserId;
	}
	public String getRqUserName() {
		return rqUserName;
	}
	public void setRqUserName(String rqUserName) {
		this.rqUserName = rqUserName;
	}
	public String getRqDeptCode() {
		return rqDeptCode;
	}
	public void setRqDeptCode(String rqDeptCode) {
		this.rqDeptCode = rqDeptCode;
	}
	public String getRqDeptName() {
		return rqDeptName;
	}
	public void setRqDeptName(String rqDeptName) {
		this.rqDeptName = rqDeptName;
	}
	public String getRqTitleCode() {
		return rqTitleCode;
	}
	public void setRqTitleCode(String rqTitleCode) {
		this.rqTitleCode = rqTitleCode;
	}
	public String getRqTitleName() {
		return rqTitleName;
	}
	public void setRqTitleName(String rqTitleName) {
		this.rqTitleName = rqTitleName;
	}
	public String getRqDutyCode() {
		return rqDutyCode;
	}
	public void setRqDutyCode(String rqDutyCode) {
		this.rqDutyCode = rqDutyCode;
	}
	public String getRqDutyName() {
		return rqDutyName;
	}
	public void setRqDutyName(String rqDutyName) {
		this.rqDutyName = rqDutyName;
	}
	public String getRrqUserId() {
		return rrqUserId;
	}
	public void setRrqUserId(String rrqUserId) {
		this.rrqUserId = rrqUserId;
	}
	public String getRrqUserName() {
		return rrqUserName;
	}
	public void setRrqUserName(String rrqUserName) {
		this.rrqUserName = rrqUserName;
	}
	public String getRrqDeptCode() {
		return rrqDeptCode;
	}
	public void setRrqDeptCode(String rrqDeptCode) {
		this.rrqDeptCode = rrqDeptCode;
	}
	public String getRrqDeptName() {
		return rrqDeptName;
	}
	public void setRrqDeptName(String rrqDeptName) {
		this.rrqDeptName = rrqDeptName;
	}
	public String getRrqTitleCode() {
		return rrqTitleCode;
	}
	public void setRrqTitleCode(String rrqTitleCode) {
		this.rrqTitleCode = rrqTitleCode;
	}
	public String getRrqTitleName() {
		return rrqTitleName;
	}
	public void setRrqTitleName(String rrqTitleName) {
		this.rrqTitleName = rrqTitleName;
	}
	public String getRrqDutyCode() {
		return rrqDutyCode;
	}
	public void setRrqDutyCode(String rrqDutyCode) {
		this.rrqDutyCode = rrqDutyCode;
	}
	public String getRrqDutyName() {
		return rrqDutyName;
	}
	public void setRrqDutyName(String rrqDutyName) {
		this.rrqDutyName = rrqDutyName;
	}
	public String getChannelTypeCode() {
		return channelTypeCode;
	}
	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegDateTime() {
		return regDateTime;
	}
	public void setRegDateTime(String regDateTime) {
		this.regDateTime = regDateTime;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getUpdtDateTime() {
		return updtDateTime;
	}
	public void setUpdtDateTime(String updtDateTime) {
		this.updtDateTime = updtDateTime;
	}
	public String getUpdtUserId() {
		return updtUserId;
	}
	public void setUpdtUserId(String updtUserId) {
		this.updtUserId = updtUserId;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getAuthNum() {
		return authNum;
	}
	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}
	public String getGeoraeStat() {
		return georaeStat;
	}
	public void setGeoraeStat(String georaeStat) {
		this.georaeStat = georaeStat;
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
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getsApprovalId() {
		return sApprovalId;
	}
	public void setsApprovalId(String sApprovalId) {
		this.sApprovalId = sApprovalId;
	}
	public String getsApprovalSeq() {
		return sApprovalSeq;
	}
	public void setsApprovalSeq(String sApprovalSeq) {
		this.sApprovalSeq = sApprovalSeq;
	}  
	
	
	
}
