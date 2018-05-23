package com.bizsp.eaccount.approvalSupport.ruleDocument.vo;

public class RuleDocumentVo {
	private String munseoNo;
	private String munseoDate;
	private String pummok;
	private String georaeName;
	private String jangso;
	private int amount;
	private String sabun;
	private String damdangName;
	private String month;

	public String getPummok() {
		return pummok;
	}

	public void setPummok(String pummok) {
		this.pummok = pummok;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMunseoNo() {
		return munseoNo;
	}

	public void setMunseoNo(String munseoNo) {
		this.munseoNo = munseoNo;
	}

	public String getMunseoDate() {
		return munseoDate;
	}

	public void setMunseoDate(String munsetDate) {
		this.munseoDate = munsetDate;
	}

	public String getGeoraeName() {
		return georaeName;
	}

	public void setGeoraeName(String georaeName) {
		this.georaeName = georaeName;
	}

	public String getJangso() {
		return jangso;
	}

	public void setJangso(String jangso) {
		this.jangso = jangso;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getSabun() {
		return sabun;
	}

	public void setSabun(String sabun) {
		this.sabun = sabun;
	}

	public String getDamdangName() {
		return damdangName;
	}

	public void setDamdangName(String damdangName) {
		this.damdangName = damdangName;
	}
}