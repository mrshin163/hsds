package com.bizsp.eaccount.downloadFile.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class AttachFileVo extends BaseCndModel {

	private static final long serialVersionUID = 422644327311814327L;

	private String companyCd;
	private String attachId;
	private String seq;
	private String lfileNm;
	private String pfileNm;
	private String filePath;
	private String ext;
	private String fileSize;
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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getLfileNm() {
		return lfileNm;
	}

	public void setLfileNm(String lfileNm) {
		this.lfileNm = lfileNm;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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