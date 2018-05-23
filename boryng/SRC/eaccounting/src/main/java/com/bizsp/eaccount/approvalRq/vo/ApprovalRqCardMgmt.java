package com.bizsp.eaccount.approvalRq.vo;

public class ApprovalRqCardMgmt {
	private String cardNum;
	private String authDate;
	private String authNum;
	private String georaeStatus;
	private Integer requestAmount;
	private String georaeColl;
	private String cardUsingTypeCode;
	
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
	public String getGeoraeStatus() {
		return georaeStatus;
	}
	public void setGeoraeStatus(String georaeStatus) {
		this.georaeStatus = georaeStatus;
	}
	public Integer getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(Integer requestAmount) {
		this.requestAmount = requestAmount;
	}
	public String getGeoraeColl() {
		return georaeColl;
	}
	public void setGeoraeColl(String georaeColl) {
		this.georaeColl = georaeColl;
	}
	public String getCardUsingTypeCode() {
		return cardUsingTypeCode;
	}
	public void setCardUsingTypeCode(String cardUsingTypeCode) {
		this.cardUsingTypeCode = cardUsingTypeCode;
	}
}
