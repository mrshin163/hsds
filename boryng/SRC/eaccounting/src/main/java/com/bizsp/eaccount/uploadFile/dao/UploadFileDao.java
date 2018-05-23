package com.bizsp.eaccount.uploadFile.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.uploadFile.vo.AttachFileVo;
import com.bizsp.eaccount.uploadFile.vo.AttachVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class UploadFileDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 첨부파일 리스트
	 * @param AttachFileVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AttachFileVo> getFileList(AttachFileVo attachFileVo){
		logger.info("::::::::::::::: getFileList :::::::::::::::::");
//		System.out.println(attachFileVo==null?"Y":"N");
		return selectList("UploadFileMapper.getFileList", attachFileVo);
	}
	
	/**
	 * 첨부파일 마스터 조회
	 * @param AttachFileVo
	 * @return
	 */
	public AttachVo getAttach(AttachVo attachVo){
		logger.info("::::::::::::::: getAttach :::::::::::::::::");
//		System.out.println(attachVo==null?"Y":"N");
		return getSqlSession().selectOne("UploadFileMapper.getAttachList", attachVo);
	}
	
	/**
	 * 첨부파일 마스터ID 채번
	 * @param AttachFileVo
	 * @return
	 */
	public String getAttachId(){
		logger.info("::::::::::::::: getAttachId :::::::::::::::::");
		return (String) select("UploadFileMapper.getAttachId");
	}
	
	/**
	 * 첨부파일 마스터ID 조회
	 * @param AttachFileVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AttachVo> getAttachList(Object object){
		logger.info("::::::::::::::: getAttachList :::::::::::::::::");
		return selectList("UploadFileMapper.getAttachList", object);
	}
		
	/**
	 * 첨부파일마스터 저장
	 * @param AttachFileVo
	 * @return
	 */
	public int insertAttach(AttachVo attachVo){
		logger.info("::::::::::::::: insertAttach :::::::::::::::::");
		return (Integer) insert("UploadFileMapper.insertAttach", attachVo);
	}
	
	/**
	 * 첨부파일마스터 저장
	 * @param AttachFileVo
	 * @return
	 */
	public int updateAttach(AttachVo attachVo){
		logger.info("::::::::::::::: updateAttach :::::::::::::::::");
		return (Integer) insert("UploadFileMapper.updateAttach", attachVo);
	}
	
	
	/**
	 * 첨부파일정보 저장
	 * @param AttachFileVo
	 * @return
	 */
	public int insertAttachFile(AttachFileVo attachFileVo){
		logger.info("::::::::::::::: insertAttachFile :::::::::::::::::");
		return (Integer) insert("UploadFileMapper.insertAttachFile", attachFileVo);
	}
	
	
	/**
	 * 첨부파일 삭제처리
	 * @param AttachFileVo
	 * @return
	 */
	public int deleteAttachFile(AttachFileVo attachFileVo){
		logger.info("::::::::::::::: deleteFile :::::::::::::::::");
		return delete("UploadFileMapper.deleteAttachFile", attachFileVo);
	}

}
