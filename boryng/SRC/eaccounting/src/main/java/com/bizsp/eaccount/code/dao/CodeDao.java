package com.bizsp.eaccount.code.dao;

import java.util.List;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.code.vo.CommCodeVo;
import com.bizsp.eaccount.code.vo.CommGrpCodeVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class CodeDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 공통 코드 그룹 리스트 조회
	 * @param commGrpCodeVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommGrpCodeVo> getCommGrpCodeList(CommGrpCodeVo commGrpCodeVo){
		logger.info("::::::::::::::: getGrpCodeList :::::::::::::::::");
		
		return pagingList("CodeMapper.selectCommGrpCodeList", commGrpCodeVo, "CodeMapper.commGrpCodeCount");
	}
	
	/**
	 * 공통 코드 리스트 조회
	 * @param commCodeVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommCodeVo> getCommCodeList(CommCodeVo commCodeVo){
		logger.info("::::::::::::::: getCodeList :::::::::::::::::");
		return pagingList("CodeMapper.selectCommCodeList", commCodeVo, "CodeMapper.commCodeCount");
	}	

	/**
	 * 공통 코드 그룹 조회
	 * @param commCodeVo
	 * @return
	 */
	public CommGrpCodeVo getCommGrpCodeOne(CommGrpCodeVo commGrpCodeVo){
		logger.info("::::::::::::::: getCommGrpCodeOne :::::::::::::::::");
		return getSqlSession().selectOne("CodeMapper.selectCommGrpCodeList", commGrpCodeVo);
	}
	
	/**
	 * 공통 코드 조회
	 * @param commCodeVo
	 * @return
	 */
	public CommCodeVo getCommCodeOne(CommCodeVo commCodeVo){
		logger.info("::::::::::::::: getCommCodeOne :::::::::::::::::");
		return getSqlSession().selectOne("CodeMapper.selectCommCodeList", commCodeVo);
	}
	
	/**
	 * 공통 코드 그룹 추가
	 * @param commCodeVo
	 * @return
	 */
	public int insertCommGrpCode(CommGrpCodeVo commGrpCodeVo){
		logger.info("::::::::::::::: insertCommGrpCode :::::::::::::::::");
		return (Integer) insert("CodeMapper.insertCommGrpCode", commGrpCodeVo);
	}
	
	/**
	 * 공통 코드 추가
	 * @param commCodeVo
	 * @return
	 */
	public int insertCommCode(CommCodeVo commCodeVo){
		logger.info("::::::::::::::: insertCommCode :::::::::::::::::");
		return (Integer) insert("CodeMapper.insertCommCode", commCodeVo);
	}
	
	/**
	 * 공통 코드 그룹 수정
	 * @param commCodeVo
	 * @return
	 */
	public int updateCommGrpCode(CommGrpCodeVo commGrpCodeVo){
		logger.info("::::::::::::::: updateCommGrpCode :::::::::::::::::");
		return update("CodeMapper.updateCommGrpCode", commGrpCodeVo);
	}
	
	/**
	 * 공통 코드 수정
	 * @param commCodeVo
	 * @return
	 */
	public int updateCommCode(CommCodeVo commCodeVo){
		logger.info("::::::::::::::: updateCommCode :::::::::::::::::");
		return update("CodeMapper.updateCommCode", commCodeVo);
	}
	
	/**
	 * 공통 코드 그룹 삭제
	 * @param commCodeVo
	 * @return
	 */
	public int deleteCommGrpCode(CommGrpCodeVo commGrpCodeVo){
		logger.info("::::::::::::::: deleteCommGrpCode :::::::::::::::::");
		return update("CodeMapper.deleteCommGrpCode", commGrpCodeVo);
	}
	
	/**
	 * 공통 코드 삭제
	 * @param commCodeVo
	 * @return
	 */
	public int deleteCommCode(CommCodeVo commCodeVo){
		logger.info("::::::::::::::: deleteCommCode :::::::::::::::::");
		return update("CodeMapper.deleteCommCode", commCodeVo);
	}
	

	public List<CommCodeVo> getAccountFavList(CommCodeVo param) {
		logger.info(":::::::::: getAccountFavList ::::::::::");
		return getSqlSession().selectList("CodeMapper.getAccountFavList", param);
	}
}
