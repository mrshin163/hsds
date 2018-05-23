package com.bizsp.eaccount.approvalLine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.approvalLine.dao.ApprovalLineDao;
import com.bizsp.eaccount.approvalLine.vo.ApprovalPolicyDtlVo;
import com.bizsp.eaccount.approvalLine.vo.ApprovalPolicyVo;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.security.SecurityUtils;

@Component
@Path("/rest/policyApi/")
public class ApprovalLineService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private SecurityUtils secu;
	
	@Autowired
	ApprovalLineDao approvalLineDao;
	
	
	/**
	 * 결재라인 정책을 조회한다. 
	 * @param policyNm
	 * @param pageNo
	 * @return
	 * @throws ServiceException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getApprovalPolicy/{policyNm}/{accountCd}/{deptNm}/{pageNo}")
	public Map<String, Object> getApprovalPolicy(
							@PathParam("policyNm") 	String policyNm,
							@PathParam("accountCd") String accountCd,
							@PathParam("deptNm") 	String deptNm,
							@PathParam("pageNo") 	int pageNo ) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ApprovalPolicyVo approvalPolicyVo = new ApprovalPolicyVo();
		
		approvalPolicyVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalPolicyVo.setPolicyNm(policyNm);
		approvalPolicyVo.setAccountCd(accountCd);
		approvalPolicyVo.setDeptNm(deptNm);
		approvalPolicyVo.setPageNo(pageNo);
		
		if(pageNo > 0 ){
			approvalPolicyVo.setPagingYn("Y");
		} else {
			approvalPolicyVo.setPagingYn("N");
		}
		
		List<ApprovalPolicyVo> list = approvalLineDao.selectPolicy(approvalPolicyVo);
		
		map.put("approvalPolicy", list);
		map.put("totalRow", approvalPolicyVo.getTotalRow());		
		
		return map;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getApprovalPolicyOne/{policyId}")
	public Map<String, Object> getApprovalPolicyOne(@PathParam("policyId") 	String policyId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		ApprovalPolicyVo approvalPolicyVo = new ApprovalPolicyVo();		
		approvalPolicyVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalPolicyVo.setPolicyId(policyId);
		
		ApprovalPolicyVo result = approvalLineDao.selectPolicyOne(approvalPolicyVo);
		//System.out.println(approvalPolicyVo.getPolicyNm());
		if(result==null){
			map.put("result", "F");
			map.put("message", "정보를 불러오는데 실패하였습니다.");
		}else{
			map.put("result", "S");
			map.put("policy", result);
		}
		return map;
	}
	
	/**
	 * 결재라인을 조회한다. 
	 * @param policyNm
	 * @param pageNo
	 * @return
	 * @throws ServiceException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getApprovalLine/{policyId}")
	public Map<String, Object> getApprovalLine(@PathParam("policyId") String policyId) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ApprovalPolicyDtlVo approvalPolicyDtlVo = new ApprovalPolicyDtlVo();
		
		approvalPolicyDtlVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalPolicyDtlVo.setPolicyId(policyId);
		
		List<ApprovalPolicyDtlVo> list = approvalLineDao.selectLine(approvalPolicyDtlVo);
		
		map.put("approvalLine", list);
		map.put("totalRow", approvalPolicyDtlVo.getTotalRow());		
		
		return map;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addPolicy")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addPolicy(ApprovalPolicyVo approvalPolicyVo) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		approvalPolicyVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalPolicyVo.setRegUserId(secu.getAuthenticatedUser().getUserId());
		
		ApprovalPolicyVo resultVo = approvalLineDao.selectPolicyOne(approvalPolicyVo);
		if(resultVo!=null){
			map.put("result", "F");
			map.put("message", "이미 존재하는 결재선 ID가 있습니다.");
			return map;
		}
		
		int result = approvalLineDao.insertPolicy(approvalPolicyVo);
		
		if(result == 1){
			map.put("result", "S");
			map.put("message", "저장에 성공 하였습니다.");	
		}else{
			map.put("result", "F");
			map.put("message", "저장에 실패 하였습니다.");	
		}		
		return map;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updatePolicy")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> updatePolicy(ApprovalPolicyVo approvalPolicyVo) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		approvalPolicyVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalPolicyVo.setUpdUserId(secu.getAuthenticatedUser().getUserId());
		
		int result = approvalLineDao.updatePolicy(approvalPolicyVo);
		
		if(result == 1){
			map.put("result", "S");
			map.put("message", "수정에 성공 하였습니다.");	
		}else{
			map.put("result", "F");
			map.put("message", "수정에 실패 하였습니다.");	
		}		
		return map;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("modifyLine")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response modifyLine(List<ApprovalPolicyDtlVo> pList) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();		
		String msg = "F";
		// 2015-03-24
		// 주석 추가
		//System.out.println(pList.size());
		for(ApprovalPolicyDtlVo approvalPolicyDtlVo : pList){
			approvalPolicyDtlVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());			
			
			int result = 0;
			ApprovalPolicyDtlVo resultVo = approvalLineDao.selectLineOne(approvalPolicyDtlVo);
			if(resultVo!=null){
				approvalPolicyDtlVo.setUpdUserId(secu.getAuthenticatedUser().getUserId());
				result = approvalLineDao.updateLine(approvalPolicyDtlVo);
			}else{
				approvalPolicyDtlVo.setRegUserId(secu.getAuthenticatedUser().getUserId());
				result = approvalLineDao.insertLine(approvalPolicyDtlVo);
			}
			
			if(result == 1){
				msg = "S";	
			}else{
				//throw new ServiceException("F");
			}
		}
		map.put("result", msg);
		if(msg.equals("S")){
			map.put("message", "저장에 성공하였습니다.");	
		}else{
			map.put("message", "저장에 실패하였습니다.");
		}

		return Response.status(200).entity(map).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deletePolicy")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> deletePolicy(ApprovalPolicyVo approvalPolicyVo) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		approvalPolicyVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		approvalPolicyVo.setUpdUserId(secu.getAuthenticatedUser().getUserId());
		approvalPolicyVo.setDelYn("Y");
		
		int result = approvalLineDao.deletePolicy(approvalPolicyVo);
		
		if(result > 0){
			map.put("result", "S");
			map.put("message", "삭제에 성공 하였습니다.");	
		}else{
			map.put("result", "F");
			map.put("message", "삭제에 실패 하였습니다.");	
		}		
		return map;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteLine")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> deleteLine(ApprovalPolicyDtlVo approvalPolicyDtlVo) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		approvalPolicyDtlVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		
		int result = 0;
		ApprovalPolicyDtlVo resultVo = approvalLineDao.selectLineOne(approvalPolicyDtlVo);
		if(resultVo == null){
			result = 1;
		}else{
			result = approvalLineDao.deleteLine(approvalPolicyDtlVo);
		}		
		
		if(result == 1){
			map.put("result", "S");
			map.put("message", "삭제에 성공 하였습니다.");	
		}else{
			map.put("result", "F");
			map.put("message", "삭제에 실패 하였습니다.");	
		}		
		
		return map;
	}
	
	
}
