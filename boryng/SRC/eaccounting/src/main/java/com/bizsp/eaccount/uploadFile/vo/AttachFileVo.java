package com.bizsp.eaccount.uploadFile.vo;

import com.bizsp.framework.util.model.BaseCndModel;

public class AttachFileVo extends BaseCndModel{

	private static final long serialVersionUID = -1817661004196856784L;
	private String companyCode;
	private String attachId;
	private String seq;
	private String lFileName;
	private String pFileName;
	private String filePath;
	private String ext;
	private String fileSize;
	private String useUn;
	private String delYn;
	private String regDate;
	private String regUserId;
	private String updDate;
	private String updUserId;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	public String getlFileName() {
		return lFileName;
	}
	public void setlFileName(String lFileName) {
		this.lFileName = lFileName;
	}
	public String getpFileName() {
		return pFileName;
	}
	public void setpFileName(String pFileName) {
		this.pFileName = pFileName;
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
	public String getUseUn() {
		return useUn;
	}
	public void setUseUn(String useUn) {
		this.useUn = useUn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getUpdUserId() {
		return updUserId;
	}
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	
	
}

