package com.bizsp.framework.util.model;

import java.io.File;
import java.io.Serializable;

public class UploadFileModel  implements Serializable{

	private static final long serialVersionUID = 7981877839201325836L;
	/**
	 * 파일명
	 */
	private String fileName;
	/**
	 * 파일경로
	 */
	private String filePath;
	/**
	 * 원본파일명
	 */
	private String originalFileNm;
	/**
	 * 파일크기 KB
	 */
	private long fileSize;
	/**
	 * 파일확장자
	 */
	private String fileExt;
	/**
	 * 파일타입 IMG , DOC
	 */
	private String contentType;
	/**
	 * 파일업로드 input 이름
	 */
	private String inputName;

	/**
	 * 파일
	 */
	private File srcFile;

	public File getSrcFile() {
		return srcFile;
	}
	public void setSrcFile(File srcFile) {
		this.srcFile = srcFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOriginalFileNm() {
		return originalFileNm;
	}
	public void setOriginalFileNm(String originalFileNm) {
		this.originalFileNm = originalFileNm;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}


}
