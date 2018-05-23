package com.bizsp.eaccount.admin.vo;

public class LogVo {
	private String SEQ             ;
	private String C_CD            ;
	private String EMP_ID          ;
	private String EMP_NM          ;
	private String MENU_DEPTH1     ;
	private String MENU_DEPTH2     ;
	private String MENU_NAME       ;
	private String LOG_URL         ;
	private String LOG_IP          ;
	private String LOG_REGDATE     ;
	private String LOG_USERAGENT   ;
	private String DIVISION_CODE   ;
	private String DIVISION_NAME   ;
	private String POSITION        ;
	private String CALLNAME        ;
	private String OFFICE          ;
	private String HANDPHONE       ;	
	
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
	public String getC_CD() {
		return C_CD;
	}
	public void setC_CD(String c_CD) {
		C_CD = c_CD;
	}
	public String getEMP_ID() {
		return EMP_ID;
	}
	public void setEMP_ID(String eMP_ID) {
		EMP_ID = eMP_ID;
	}
	public String getEMP_NM() {
		return EMP_NM;
	}
	public void setEMP_NM(String eMP_NM) {
		EMP_NM = eMP_NM;
	}
	public String getMENU_DEPTH1() {
		return MENU_DEPTH1;
	}
	public void setMENU_DEPTH1(String mENU_DEPTH1) {
		MENU_DEPTH1 = mENU_DEPTH1;
	}
	public String getMENU_DEPTH2() {
		return MENU_DEPTH2;
	}
	public void setMENU_DEPTH2(String mENU_DEPTH2) {
		MENU_DEPTH2 = mENU_DEPTH2;
	}
	public String getMENU_NAME() {
		return MENU_NAME;
	}
	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
	}
	public String getLOG_URL() {
		return LOG_URL;
	}
	public void setLOG_URL(String lOG_URL) {
		LOG_URL = lOG_URL;
	}
	public String getLOG_IP() {
		return LOG_IP;
	}
	public void setLOG_IP(String lOG_IP) {
		LOG_IP = lOG_IP;
	}
	public String getLOG_REGDATE() {
		return LOG_REGDATE;
	}
	public void setLOG_REGDATE(String lOG_REGDATE) {
		LOG_REGDATE = lOG_REGDATE;
	}
	public String getLOG_USERAGENT() {
		return LOG_USERAGENT;
	}
	public void setLOG_USERAGENT(String lOG_USERAGENT) {
		LOG_USERAGENT = lOG_USERAGENT;
	}
	public String getDIVISION_CODE() {
		return DIVISION_CODE;
	}
	public void setDIVISION_CODE(String dIVISION_CODE) {
		DIVISION_CODE = dIVISION_CODE;
	}
	public String getDIVISION_NAME() {
		return DIVISION_NAME;
	}
	public void setDIVISION_NAME(String dIVISION_NAME) {
		DIVISION_NAME = dIVISION_NAME;
	}
	public String getPOSITION() {
		return POSITION;
	}
	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}
	public String getCALLNAME() {
		return CALLNAME;
	}
	public void setCALLNAME(String cALLNAME) {
		CALLNAME = cALLNAME;
	}
	public String getOFFICE() {
		return OFFICE;
	}
	public void setOFFICE(String oFFICE) {
		OFFICE = oFFICE;
	}
	public String getHANDPHONE() {
		return HANDPHONE;
	}
	public void setHANDPHONE(String hANDPHONE) {
		HANDPHONE = hANDPHONE;
	}
	@Override
	public String toString() {
		return "LogVo [SEQ=" + SEQ + ", C_CD=" + C_CD + ", EMP_ID=" + EMP_ID
				+ ", EMP_NM=" + EMP_NM + ", MENU_DEPTH1=" + MENU_DEPTH1
				+ ", MENU_DEPTH2=" + MENU_DEPTH2 + ", MENU_NAME=" + MENU_NAME
				+ ", LOG_URL=" + LOG_URL + ", LOG_IP=" + LOG_IP
				+ ", LOG_REGDATE=" + LOG_REGDATE + ", LOG_USERAGENT="
				+ LOG_USERAGENT + ", DIVISION_CODE=" + DIVISION_CODE
				+ ", DIVISION_NAME=" + DIVISION_NAME + ", POSITION=" + POSITION
				+ ", CALLNAME=" + CALLNAME + ", OFFICE=" + OFFICE
				+ ", HANDPHONE=" + HANDPHONE + "]";
	}
}
