package com.bizsp.eaccount.approvalRq.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.approval.vo.ApprovalMgmtVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqAttachVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqCardMgmt;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqInfoVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqItemVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqLineVo;
import com.bizsp.eaccount.approvalRq.vo.ApprovalRqVo;
import com.bizsp.eaccount.approvalRq.vo.SymposiumMgmtVo;
import com.bizsp.eaccount.approvalRq.vo.SymposiumRqVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class ApprovalRqDao extends DaoSupport {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 미상신 내역 리스트
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getApprovalUpList(ApprovalRqVo approvalRqVo) {

		logger.info("::::::::::::::: getApprovalUpList :::::::::::::::::");
		return pagingList("ApprovalRqMapper.selectApprovalUpList", approvalRqVo, "ApprovalRqMapper.approvalUpCount");
	}

	/**
	 * 대체자 미상신 리스트
	 * 
	 * @param approvalRqVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getApprovalRrqList(ApprovalRqVo approvalRqVo) {

		logger.info("::::::::::::::: getApprovalRrqList :::::::::::::::::");
		return pagingList("ApprovalRqMapper.selectApprovalRrqList", approvalRqVo, "ApprovalRqMapper.selectApprovalRrqCount");
	}

	/**
	 * 결재선 리스트
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getApprovalLineList(ApprovalRqVo approvalRqVo) {

		logger.info("::::::::::::::: getApprovalLineList :::::::::::::::::");
		return pagingList("ApprovalRqMapper.selectApprovalLineList", approvalRqVo);
	}

	/**
	 * 품의서 저장
	 * 
	 * @param approvalInfo
	 */
	public void insertApproval(ApprovalRqInfoVo approvalInfoVo) {
		logger.info("::::::::::::::: insertApproval :::::::::::::::::");
		insert("ApprovalRqMapper.insertApproval", approvalInfoVo);
	}

	/**
	 * 품의서 상신
	 * 
	 * @param approvalRqInfoVo
	 */
	public void updateApprovalRq(ApprovalRqInfoVo approvalRqInfoVo) {
		logger.info("::::::::::::::: updateApprovalRq :::::::::::::::::");
		update("ApprovalRqMapper.updateApprovalRq", approvalRqInfoVo);
	}

	/**
	 * 심포지엄 건 상태 코드 변경
	 * 
	 * @param approvalRqInfoVo
	 */
	public void updateApprovalStaus(String sApprovalId) {
		logger.info("::::::::::::::: updateApprovalStaus :::::::::::::::::");
		update("ApprovalRqMapper.updateApprovalStaus", sApprovalId);
	}

	/**
	 * 품의 내역 기초데이터 저장
	 * 
	 * @param approvalItemVo
	 */
	public void insertApprovalItem(ApprovalRqInfoVo approvalInfoVo) {
		logger.info("::::::::::::::: insertApprovalItem :::::::::::::::::");
		insert("ApprovalRqMapper.insertApprovalItem", approvalInfoVo);
	}

	/**
	 * 품의서 세부 사항 저장
	 * 
	 * @param approvalRqItemVo
	 */
	public void updateApprovalItem(ApprovalRqItemVo approvalRqItemVo) {
		logger.info("::::::::::::::: updateApprovalItem :::::::::::::::::");
		update("ApprovalRqMapper.updateApprovalRqItem", approvalRqItemVo);

	}

	/**
	 * 품의서 저장시 결재 라인 저장
	 * 
	 * @param approvalRqLineVo
	 */
	public void insertApprovlRqLine(List<ApprovalRqLineVo> approvalRqLineVo) {
		insert("ApprovalRqMapper.insertApprovalRqLine", approvalRqLineVo);
	}

	/**
	 * 품의서 내역 가져오기
	 * 
	 * @param approvalItemVo
	 */
	public List<ApprovalRqItemVo> getApprovalItem(String approvalId) {
		logger.info("::::::::::::::: getApprovalItem :::::::::::::::::");
		return selectList("ApprovalRqMapper.selectApprovalItem", approvalId);
	}

	/**
	 * approvalId 시퀀스 생성
	 * 
	 * @return
	 */
	public String getApprovalId() {
		return (String) select("ApprovalRqMapper.getApprovalId");
	}

	/**
	 * 미상신 내역 카드용도 변경
	 * 
	 * @param approvalRqCardMgmts
	 */
	public void updateCardUsingTypeStatus(ApprovalRqCardMgmt approvalRqCardMgmts) {
		update("ApprovalRqMapper.updateCardUseStatus", approvalRqCardMgmts);
	}

	/**
	 * 심포지움요청대상 리스트 조회
	 * 
	 * @param symposiumVo
	 *            검색조건
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSymposiumRqList(SymposiumRqVo symposiumVo) {
		logger.info("::::::::::::::: getSymposiumRqList :::::::::::::::::");
		return pagingList("ApprovalRqMapper.selectSymposiumList", symposiumVo, "ApprovalRqMapper.SymposiumListCount");
	}

	/**
	 * 심포지움 품의서 저장
	 * 
	 * @param approvalInfo
	 */
	public void insertSymposiumApproval(ApprovalRqInfoVo approvalInfoVo) {
		logger.info("::::::::::::::: insertSymposiumApproval :::::::::::::::::");
		insert("ApprovalRqMapper.insertSymposiumApproval", approvalInfoVo);
	}

	/**
	 * 심포지움 품의 내역 저장
	 * 
	 * @param approvalItemVo
	 */
	public void insertSymposiumApprovalItem(ApprovalRqInfoVo approvalInfoVo) {
		logger.info("::::::::::::::: insertSymposiumApprovalItem :::::::::::::::::");
		insert("ApprovalRqMapper.insertSymposiumApprovalItem", approvalInfoVo);
	}

	/**
	 * 품의서 첨부 내역 저장
	 * 
	 * @param approvalRqAttachs
	 */
	public void insertApprovalRqAttach(List<ApprovalRqAttachVo> approvalRqAttachs) {
		logger.info("::::::::::::::: insertApprovalRqAttach :::::::::::::::::");
		insert("ApprovalRqMapper.insertApprovalRqAttach", approvalRqAttachs);
	}

	public void updateApprovalRqAttach(ApprovalRqAttachVo approvalRqAttach) {
		logger.info("::::::::::::::: updateApprovalRqAttach :::::::::::::::::");
		insert("ApprovalRqMapper.updateApprovalRqAttach", approvalRqAttach);
	}

	/**
	 * 첨부파일 조회
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectAttachFileList(ApprovalMgmtVo approvalMgmtVo) {
		logger.info(":::::::::: selectAttachFileList ::::::::::");

		return selectList("ApprovalMapper.selectAttachFileList", approvalMgmtVo);
	}

	/**
	 * 심포지움 상신시 첨부파일 복사
	 * 
	 * @param approvalRqAttach
	 */
	public void insertApprovalSymposiumRqAttach(ApprovalRqAttachVo approvalRqAttach) {
		logger.info("::::::::::::::: insertApprovalSymposiumRqAttach :::::::::::::::::");
		insert("ApprovalRqMapper.insertApprovalSymposiumRqAttach", approvalRqAttach);
	}

	public int validRqUser(ApprovalRqInfoVo param) {
		logger.info(":::::::::: validRqUser ::::::::::");

		return (Integer) select("ApprovalRqMapper.validRqUser", param);
	}

	public String validLastItemSeq(ApprovalRqInfoVo param) {
		logger.info(":::::::::: validLastItemSeq ::::::::::");

		return (String) select("ApprovalRqMapper.validLastItemSeq", param);
	}

	public int validLastApprovalUser(ApprovalRqInfoVo param) {
		logger.info(":::::::::: validLastApprovalUser ::::::::::");

		return (Integer) select("ApprovalRqMapper.validLastApprovalUser", param);
	}

	@SuppressWarnings("unchecked")
	public List<SymposiumMgmtVo> selectCancelSymposiumList(SymposiumMgmtVo param) {
		logger.info(":::::::::: selectCancelSymposiumList ::::::::::");

		return pagingList("ApprovalRqMapper.selectCancelSymposiumList", param, "ApprovalRqMapper.cancelSymposiumCount");
	}

	public int validCancelSymposium(SymposiumMgmtVo param) {
		logger.info(":::::::::: validCancelSymposium ::::::::::");

		return (Integer) select("ApprovalRqMapper.validCancelSymposium", param);
	}

	public int deleteSymposiumApprovalItem(SymposiumMgmtVo param) {
		logger.info(":::::::::: deleteSymposiumApprovalItem ::::::::::");

		return delete("ApprovalRqMapper.deleteSymposiumApprovalItem", param);
	}

	@SuppressWarnings("unchecked")
	public List<String> selectSymposiumApprovalAttach(SymposiumMgmtVo param) {
		logger.info(":::::::::: selectSymposiumApprovalAttach ::::::::::");

		return selectList("ApprovalRqMapper.selectSymposiumApprovalAttach", param);
	}

	public SymposiumMgmtVo selectSymposiumAttachFile(SymposiumMgmtVo param) {
		logger.info(":::::::::: selectSymposiumAttachFile ::::::::::");

		return (SymposiumMgmtVo) select("ApprovalRqMapper.selectSymposiumAttachFile", param);
	}

	public int deleteSymposiumAttachFile(SymposiumMgmtVo param) {
		logger.info(":::::::::: deleteSymposiumAttachFile ::::::::::");

		return delete("ApprovalRqMapper.deleteSymposiumAttachFile", param);
	}

	public int deleteSymposiumAttach(SymposiumMgmtVo param) {
		logger.info(":::::::::: deleteSymposiumAttach ::::::::::");

		return delete("ApprovalRqMapper.deleteSymposiumAttach", param);
	}

	public int deleteSymposiumApprovalAttach(SymposiumMgmtVo param) {
		logger.info(":::::::::: deleteSymposiumApprovalAttach ::::::::::");

		return delete("ApprovalRqMapper.deleteSymposiumApprovalAttach", param);
	}

	public SymposiumMgmtVo selectSymposiumRestoreData(SymposiumMgmtVo param) {
		logger.info(":::::::::: selectSymposiumRestoreData ::::::::::");

		return (SymposiumMgmtVo) select("ApprovalRqMapper.selectSymposiumRestoreData", param);
	}

	public int deleteApproval(SymposiumMgmtVo paramVo) {
		logger.info(":::::::::: deleteApproval ::::::::::");

		return delete("ApprovalRqMapper.deleteApproval", paramVo);		
	}
	
	public int deleteApprovalLine(SymposiumMgmtVo paramVo) {
		logger.info(":::::::::: deleteApprovalLine ::::::::::");

		return delete("ApprovalRqMapper.deleteApprovalLine", paramVo);		
	}
	
	@SuppressWarnings("unchecked")
	public List<SymposiumMgmtVo> selectApprovalStatusT(SymposiumMgmtVo paramVo) {
		logger.info(":::::::::: selectApprovalStatusT ::::::::::");

		return selectList("ApprovalRqMapper.selectApprovalStatusT", paramVo);		
	}
}