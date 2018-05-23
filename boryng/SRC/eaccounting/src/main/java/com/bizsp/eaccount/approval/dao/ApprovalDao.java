package com.bizsp.eaccount.approval.dao;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.approval.vo.ApprovalItemVo;
import com.bizsp.eaccount.approval.vo.ApprovalLineVo;
import com.bizsp.eaccount.approval.vo.ApprovalMgmtVo;
import com.bizsp.eaccount.approval.vo.ApprovalVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class ApprovalDao extends DaoSupport {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectApprovalList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalList ::::::::::");

		return pagingList("ApprovalMapper.selectApprovalList", param, "ApprovalMapper.approvalCount");
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectApprovalListForAdmin(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalListForAdmin ::::::::::");

		return pagingList("ApprovalMapper.selectApprovalListForAdmin", param, "ApprovalMapper.approvalForAdminCount");
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectApprovalProgressList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalProgressList ::::::::::");

		return pagingList("ApprovalMapper.selectApprovalProgressList", param, "ApprovalMapper.approvalProgressCount");
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectApprovalCompleteList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalCompleteList ::::::::::");

		return pagingList("ApprovalMapper.selectApprovalCompleteList", param, "ApprovalMapper.approvalCompleteCount");
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectProgressList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectProgressList ::::::::::");

		return pagingList("ApprovalMapper.selectProgressList", param, "ApprovalMapper.progressCount");
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectCompleteList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectCompleteList ::::::::::");

		return pagingList("ApprovalMapper.selectCompleteList", param, "ApprovalMapper.completeCount");
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectRestoreList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectRestoreList ::::::::::");

		return pagingList("ApprovalMapper.selectRestoreList", param, "ApprovalMapper.restoreCount");
	}

	public int updateApprovalCode(ApprovalLineVo param) {
		logger.info(":::::::::: updateApprovalCode ::::::::::");

		return update("ApprovalMapper.updateApprovalCode", param);
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalLineVo> selectApprovalLineList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalLineList ::::::::::");

		return selectList("ApprovalMapper.selectApprovalLineList", param);
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalItemVo> selectApprovalItemList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalItemList ::::::::::");

		return selectList("ApprovalMapper.selectApprovalItemList", param);
	}
	
	public ApprovalItemVo selectApprovalItem(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApprovalItemList ::::::::::");

		return getSqlSession().selectOne("ApprovalMapper.selectApprovalItemList", param);
	}

	public int validFirstApprovalCode(ApprovalMgmtVo param) {
		logger.info(":::::::::: validFirstApprovalCode ::::::::::");

		return (Integer) select("ApprovalMapper.validFirstApprovalCode", param);
	}

	public int validMyApproval(ApprovalMgmtVo param) {
		logger.info(":::::::::: validMyApproval ::::::::::");

		return (Integer) select("ApprovalMapper.validMyApproval", param);
	}

	public int updateApprovalStatusCode(ApprovalMgmtVo param) {
		logger.info(":::::::::: updateApprovalStatusCode ::::::::::");

		return update("ApprovalMapper.updateApprovalStatusCode", param);
	}

	public int validAccountCode(ApprovalMgmtVo param) {
		logger.info(":::::::::: validAccountCode ::::::::::");

		return (Integer) select("ApprovalMapper.validAccountCode", param);
	}

	public int insertApproval(ApprovalMgmtVo param) {
		logger.info(":::::::::: insertApproval ::::::::::");

		return (Integer) insert("ApprovalMapper.insertApproval", param);
	}

	public int insertApprovalItem(ApprovalMgmtVo param) {
		logger.info(":::::::::: insertApprovalItem ::::::::::");

		return (Integer) insert("ApprovalMapper.insertApprovalItem", param);
	}

	public int insertApprovalAttach(ApprovalMgmtVo param) {
		logger.info(":::::::::: insertApprovalAttach ::::::::::");

		return (Integer) insert("ApprovalMapper.insertApprovalAttach", param);
	}

	public int validApprovalStatusCode(ApprovalMgmtVo param) {
		logger.info(":::::::::: validApprovalStatusCode ::::::::::");

		return (Integer) select("ApprovalMapper.validApprovalStatusCode", param);
	}

	public int validLastApprovalUser(ApprovalMgmtVo param) {
		logger.info(":::::::::: validLastApprovalUser ::::::::::");

		return (Integer) select("ApprovalMapper.validLastApprovalUser", param);
	}

	public int updateForSymposium(ApprovalMgmtVo param) {
		logger.info(":::::::::: updateForSymposium ::::::::::");

		return update("ApprovalMapper.updateForSymposium", param);
	}

	public int validSymposiumApproval(ApprovalMgmtVo param) {
		logger.info(":::::::::: validSymposiumApproval ::::::::::");

		return (Integer) select("ApprovalMapper.validSymposiumApproval", param);
	}

	public int validFirstApUserId(ApprovalMgmtVo param) {
		logger.info(":::::::::: validFirstApUserId ::::::::::");

		return (Integer) select("ApprovalMapper.validFirstApUserId", param);
	}

	public int validAccountCd(ApprovalMgmtVo param) {
		logger.info(":::::::::: validAccountCd ::::::::::");

		return (Integer) select("ApprovalMapper.validAccountCd", param);
	}

	public String selectAccountCd(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectAccountCd ::::::::::");

		return (String) select("ApprovalMapper.selectAccountCd", param);
	}

	public int updateAccountSpCd(ApprovalMgmtVo param) {
		logger.info(":::::::::: updateAccountSpCd ::::::::::");

		return update("ApprovalMapper.updateAccountSpCd", param);
	}

	public int updateCancelApprovalCode(ApprovalLineVo param) {
		logger.info(":::::::::: updateCancelApprovalCode ::::::::::");

		return update("ApprovalMapper.updateCancelApprovalCode", param);
	}

	public int updateCancelApprovalStatusCode(ApprovalMgmtVo param) {
		logger.info(":::::::::: updateCancelApprovalStatusCode ::::::::::");

		return update("ApprovalMapper.updateCancelApprovalStatusCode", param);
	}

	public int deleteInterfaceData(ApprovalMgmtVo param) {
		logger.info(":::::::::: deleteInterfaceData ::::::::::");

		return delete("ApprovalMapper.deleteInterfaceData", param);
	}

	public ApprovalVo selectApproval(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectApproval ::::::::::");

		return (ApprovalVo) select("ApprovalMapper.selectApproval", param);
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selectUserListForSendMail(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectUserListForSendMail ::::::::::");

		return selectList("ApprovalMapper.selectUserListForSendMail", param);
	}

	public int updateAccount2Cd(ApprovalMgmtVo param) {
		logger.info(":::::::::: updateAccount2Cd ::::::::::");

		return update("ApprovalMapper.updateAccount2Cd", param);
	}

	@SuppressWarnings("unchecked")
	public List<ApprovalMgmtVo> selectAttachFileList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectAttachFileList ::::::::::");

		return selectList("ApprovalMapper.selectAttachFileList", param);
	}

	public String selectSeqOfLastApprovalUser(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectSeqOfLastApprovalUser ::::::::::");

		return (String) select("ApprovalMapper.selectSeqOfLastApprovalUser", param);
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> selectInterfaceList(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectInterfaceList ::::::::::");

		return selectList("ApprovalMapper.selectInterfaceList", param);
	}

	public int insertInterface(HashMap<String, String> map) {
		logger.info(":::::::::: insertInterface ::::::::::");

		return (Integer) insert("ApprovalMapper.insertInterface", map);
	}

	public ApprovalMgmtVo selectRestoreData(ApprovalMgmtVo param) {
		logger.info(":::::::::: selectRestoreData ::::::::::");

		return (ApprovalMgmtVo) select("ApprovalMapper.selectRestoreData", param);
	}
}