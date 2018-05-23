package com.bizsp.eaccount.card.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class CardMgmtVo extends BaseCndModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5811483517668622552L;
	String companyCd;
	String cardNo;
	String repCardYn;
	String repUserId;
	String comCardYn;
	String comCardMgrId;
	String secCardYn;
	String sec1UserId;
	String sec2UserId;

	String regDttm;
	String regUserId;
	String updDttm;
	String updUserId;
	
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRepCardYn() {
		return repCardYn;
	}
	public void setRepCardYn(String repCardYn) {
		this.repCardYn = repCardYn;
	}
	public String getRepUserId() {
		return repUserId;
	}
	public void setRepUserId(String repUserId) {
		this.repUserId = repUserId;
	}
	public String getComCardYn() {
		return comCardYn;
	}
	public void setComCardYn(String comCardYn) {
		this.comCardYn = comCardYn;
	}
	public String getComCardMgrId() {
		return comCardMgrId;
	}
	public void setComCardMgrId(String comCardMgrId) {
		this.comCardMgrId = comCardMgrId;
	}
	public String getSecCardYn() {
		return secCardYn;
	}
	public void setSecCardYn(String secCardYn) {
		this.secCardYn = secCardYn;
	}
	public String getSec1UserId() {
		return sec1UserId;
	}
	public void setSec1UserId(String sec1UserId) {
		this.sec1UserId = sec1UserId;
	}
	public String getSec2UserId() {
		return sec2UserId;
	}
	public void setSec2UserId(String sec2UserId) {
		this.sec2UserId = sec2UserId;
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

}
