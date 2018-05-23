package com.bizsp.eaccount.code.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.code.dao.CodeDao;
import com.bizsp.eaccount.code.vo.CommCodeVo;
import com.bizsp.eaccount.code.vo.CommGrpCodeVo;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.exception.ServiceException;

@Path("/rest/codeApi/")
@Component
public class CodeService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CodeDao codeDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("commGrpCodeList/{grpCodeName}/{pageNo}")
	public Map<String, Object> getCommGrpCodeList(@PathParam("grpCodeName") String grpCodeName, @PathParam("pageNo") int pageNo) {

		logger.debug("::::::::::::::::::: getGroupcodeList::::::::::::::::");

		Map<String, Object> commGrpCodeMap = new HashMap<String, Object>();

		CommGrpCodeVo commGrpCodeVo = new CommGrpCodeVo();

		if (pageNo > 0)
			commGrpCodeVo.setPagingYn("Y");
		else
			commGrpCodeVo.setPagingYn("N");

		commGrpCodeVo.setGrpCodeName(grpCodeName);
		commGrpCodeVo.setPageNo(pageNo);

		List<CommGrpCodeVo> commGrpCodeList = codeDao.getCommGrpCodeList(commGrpCodeVo);

		commGrpCodeMap.put("codeList", commGrpCodeList);
		commGrpCodeMap.put("totalRow", commGrpCodeVo.getTotalRow());

		return commGrpCodeMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("commCodeList/{grpCodeId}/{pageNo}")
	public Map<String, Object> getCommCodeList(@PathParam("grpCodeId") String grpCodeId, @PathParam("pageNo") int pageNo) {
		logger.info("::::::::::::::::::: getCommCodeList ::::::::::::::::");

		Map<String, Object> commCodeMap = new HashMap<String, Object>();
		CommCodeVo commCodeVo = new CommCodeVo();
		// CommGrpCodeVo commGrpCodeVo = new CommGrpCodeVo();

		if (pageNo > 0)
			commCodeVo.setPagingYn("Y");
		else
			commCodeVo.setPagingYn("N");

		commCodeVo.setGrpCodeId(grpCodeId);
		commCodeVo.setPageNo(pageNo);

		List<CommCodeVo> commCodeList = codeDao.getCommCodeList(commCodeVo);
		// List<CommGrpCodeVo> commGrpCodeList =
		// codeDao.getCommGrpCodeList(commGrpCodeVo);

		commCodeMap.put("codeList", commCodeList);
		commCodeMap.put("totalRow", commCodeVo.getTotalRow());
		// commCodeMap.put("commGrpCodeList", commGrpCodeList);

		return commCodeMap;
	}

	/**
	 * 코드 이름 조회 (for Ajax Autocomplete)
	 * 
	 * @param deptName
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("codeNameSearch/{grpCodeId}/{codeName}")
	public List<String> getCodeNameList(@PathParam("grpCodeId") String grpCodeId, @PathParam("codeName") String codeName) {
		logger.debug("::::::::::::::::::: getCodeNameList ::::::::::::::::");

		CommCodeVo commCodeVo = new CommCodeVo();

		commCodeVo.setGrpCodeId(grpCodeId);
		commCodeVo.setCodeName(codeName);

		List<CommCodeVo> codeList = codeDao.getCommCodeList(commCodeVo);
		List<String> codeNameList = new ArrayList<String>();

		for (CommCodeVo vo : codeList) {
			codeNameList.add(vo.getCodeName());
		}

		return codeNameList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("codeSearchList/{grpCodeId}/{codeName}")
	public List<CommCodeVo> getCodeSerchList(@PathParam("grpCodeId") String grpCodeId, @PathParam("codeName") String codeName) {
		CommCodeVo commCodeVo = new CommCodeVo();

		String code = codeName.replace("`", "/");

		commCodeVo.setGrpCodeId(grpCodeId);
		commCodeVo.setCodeName(code);
		return codeDao.getCommCodeList(commCodeVo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCommGrpCode/{grpCodeId}")
	public CommGrpCodeVo getCommGrpCode(@PathParam("grpCodeId") String grpCodeId) {
		CommGrpCodeVo commGrpCodeVo = new CommGrpCodeVo();
		commGrpCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commGrpCodeVo.setGrpCodeId(grpCodeId);
		return codeDao.getCommGrpCodeOne(commGrpCodeVo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCommCode/{grpCodeId}/{code}")
	public CommCodeVo getCommCode(@PathParam("grpCodeId") String grpCodeId, @PathParam("code") String code) {
		CommCodeVo commCodeVo = new CommCodeVo();
		commCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commCodeVo.setGrpCodeId(grpCodeId);
		commCodeVo.setCode(code);
		return codeDao.getCommCodeOne(commCodeVo);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insertCommGrpCode")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response insertCommGrpCode(CommGrpCodeVo commGrpCodeVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "F";
		commGrpCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commGrpCodeVo.setRegUser(getLoginUserInfo().getUserId());

		int result = codeDao.insertCommGrpCode(commGrpCodeVo);
		if (result == 1) {
			msg = "S";
		} else {
			throw new ServiceException(makeMap(msg));
		}
		resultMap.put("msg", msg);
		return Response.status(200).entity(resultMap).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insertCommCode")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response insertCommCode(CommCodeVo commCodeVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "F";
		commCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commCodeVo.setRegUser(getLoginUserInfo().getUserId());

		int result = codeDao.insertCommCode(commCodeVo);
		if (result == 1) {
			msg = "S";
		} else {
			throw new ServiceException(makeMap(msg));
		}
		resultMap.put("msg", msg);
		return Response.status(200).entity(resultMap).build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("updateCommGrpCode")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response updateCommGrpCode(CommGrpCodeVo commGrpCodeVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "F";
		commGrpCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commGrpCodeVo.setUpdtUser(getLoginUserInfo().getUserId());

		int result = codeDao.updateCommGrpCode(commGrpCodeVo);
		if (result == 1) {
			msg = "S";
		} else {
			throw new ServiceException(makeMap(msg));
		}
		resultMap.put("msg", msg);
		return Response.status(200).entity(resultMap).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateCommCode")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response updateCommCode(CommCodeVo commCodeVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "F";
		commCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commCodeVo.setUpdtUser(getLoginUserInfo().getUserId());

		int result = codeDao.updateCommCode(commCodeVo);
		if (result == 1) {
			msg = "S";
		} else {
			throw new ServiceException(makeMap(msg));
		}
		resultMap.put("msg", msg);
		return Response.status(200).entity(resultMap).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteCommGrpCode")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response deleteCommGrpCode(CommGrpCodeVo commGrpCodeVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "F";
		commGrpCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commGrpCodeVo.setUpdtUser(getLoginUserInfo().getUserId());

		int result = codeDao.deleteCommGrpCode(commGrpCodeVo);
		if (result == 1) {
			msg = "S";
		} else {
			throw new ServiceException(makeMap(msg));
		}
		resultMap.put("msg", msg);
		return Response.status(200).entity(resultMap).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteCommCode")
	@Transactional(propagation = Propagation.REQUIRED)
	public Response deleteCommCode(CommCodeVo commCodeVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "F";
		commCodeVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		commCodeVo.setUpdtUser(getLoginUserInfo().getUserId());

		int result = codeDao.deleteCommCode(commCodeVo);
		if (result == 1) {
			msg = "S";
		} else {
			throw new ServiceException(makeMap(msg));
		}
		resultMap.put("msg", msg);
		return Response.status(200).entity(resultMap).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("accountFav/{code}")
	public Map<String, Object> accountFav(@PathParam("code") String code) throws Exception {

		logger.debug(":::::::::: accountFav 세부계정 선행 데이터 조회 ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (code != null && !"".equals(code)) {
			CommCodeVo commCodeVo = new CommCodeVo();
			commCodeVo.setCode(code);

			LoginUserVO loginUserVO = (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			commCodeVo.setCompanyCode(loginUserVO.getCompanyCode());

			List<CommCodeVo> list = codeDao.getAccountFavList(commCodeVo);

			if (list != null && list.size() > 0) {
				resultMap.put("list", list);
				resultMap.put("code", "S");
			}
		} else {
			throw new ServiceException(makeMap("잘못된 요청입니다."));
		}

		return resultMap;
	}

	private Map<String, String> makeMap(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "F");
		map.put("msg", message);

		return map;
	}

	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}