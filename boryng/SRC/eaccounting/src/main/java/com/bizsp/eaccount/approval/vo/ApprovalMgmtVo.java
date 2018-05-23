package com.bizsp.eaccount.approval.vo;

import java.util.List;

public class ApprovalMgmtVo extends ApprovalVo {

	private static final long serialVersionUID = 422644327311814327L;

	private List<ApprovalItemVo> itemVoList;
	private List<ApprovalLineVo> lineVoList;

	// view (list)
	private String seq;
	private String lastApDeptNm; // 최종결재자의 부서
	private String lastApUserNm; // 최종결재자
	private String rejApDeptNm; // 반려자의 부서
	private String rejApUserNm; // 반려자
	private String apDeptNm; // 현재결재자의 부서
	private String apUserNm; // 현재결재자
	private String account2Cd; // 세부계정
	private String account2Nm; // 세부계정
	private String details; // 사용내역
	private String itemCount; // 건수
	private String requestAmount; // 총사용액
	private String aprDate; // 결재일
	private String rejDate; // 반려일
	private String aprCd; // 결재코드
	private String rejComment; // 반려사유
	private String productNm; // 제품명
	private String authDate; // 카드사용일
	private String attachId;
	private String approvalAttachTpCd;
	private String approvalAttachTpNm;
	private String pfileNm;
	private String ext;
	private String filePath;
	private String mercNm;

	// search param
	private String sDate;
	private String eDate;
	private String userId;
	private String deptCd;
	private String apDeptId;
	private String apUserId;
	private String productCd;
	private String flag;
	private String accountSpCd;
	private String accountSpNm;

	// 재품의
	private String newApprovalId;

	public List<ApprovalItemVo> getItemVoList() {
		return itemVoList;
	}

	public void setItemVoList(List<ApprovalItemVo> itemVoList) {
		this.itemVoList = itemVoList;
	}

	public List<ApprovalLineVo> getLineVoList() {
		return lineVoList;
	}

	public void setLineVoList(List<ApprovalLineVo> lineVoList) {
		this.lineVoList = lineVoList;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getLastApDeptNm() {
		return lastApDeptNm;
	}

	public void setLastApDeptNm(String lastApDeptNm) {
		this.lastApDeptNm = lastApDeptNm;
	}

	public String getLastApUserNm() {
		return lastApUserNm;
	}

	public void setLastApUserNm(String lastApUserNm) {
		this.lastApUserNm = lastApUserNm;
	}

	public String getRejApDeptNm() {
		return rejApDeptNm;
	}

	public void setRejApDeptNm(String rejApDeptNm) {
		this.rejApDeptNm = rejApDeptNm;
	}

	public String getRejApUserNm() {
		return rejApUserNm;
	}

	public void setRejApUserNm(String rejApUserNm) {
		this.rejApUserNm = rejApUserNm;
	}

	public String getApDeptNm() {
		return apDeptNm;
	}

	public void setApDeptNm(String apDeptNm) {
		this.apDeptNm = apDeptNm;
	}

	public String getApUserNm() {
		return apUserNm;
	}

	public void setApUserNm(String apUserNm) {
		this.apUserNm = apUserNm;
	}

	public String getAccount2Cd() {
		return account2Cd;
	}

	public void setAccount2Cd(String account2Cd) {
		this.account2Cd = account2Cd;
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

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getAprDate() {
		return aprDate;
	}

	public void setAprDate(String aprDate) {
		this.aprDate = aprDate;
	}

	public String getRejDate() {
		return rejDate;
	}

	public void setRejDate(String rejDate) {
		this.rejDate = rejDate;
	}

	public String getAprCd() {
		return aprCd;
	}

	public void setAprCd(String aprCd) {
		this.aprCd = aprCd;
	}

	public String getRejComment() {
		return rejComment;
	}

	public void setRejComment(String rejComment) {
		this.rejComment = rejComment;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getApprovalAttachTpCd() {
		return approvalAttachTpCd;
	}

	public void setApprovalAttachTpCd(String approvalAttachTpCd) {
		this.approvalAttachTpCd = approvalAttachTpCd;
	}

	public String getApprovalAttachTpNm() {
		return approvalAttachTpNm;
	}

	public void setApprovalAttachTpNm(String approvalAttachTpNm) {
		this.approvalAttachTpNm = approvalAttachTpNm;
	}

	public String getPfileNm() {
		return pfileNm;
	}

	public void setPfileNm(String pfileNm) {
		this.pfileNm = pfileNm;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
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

	public String getApDeptId() {
		return apDeptId;
	}

	public void setApDeptId(String apDeptId) {
		this.apDeptId = apDeptId;
	}

	public String getApUserId() {
		return apUserId;
	}

	public void setApUserId(String apUserId) {
		this.apUserId = apUserId;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAccountSpCd() {
		return accountSpCd;
	}

	public void setAccountSpCd(String accountSpCd) {
		this.accountSpCd = accountSpCd;
	}

	public String getAccountSpNm() {
		return accountSpNm;
	}

	public void setAccountSpNm(String accountSpNm) {
		this.accountSpNm = accountSpNm;
	}

	public String getNewApprovalId() {
		return newApprovalId;
	}

	public void setNewApprovalId(String newApprovalId) {
		this.newApprovalId = newApprovalId;
	}	

	public String getMercNm() {
		return mercNm;
	}

	public void setMercNm(String mercNm) {
		this.mercNm = mercNm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}