package com.bizsp.eaccount.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.auth.dao.AuthDao;
import com.bizsp.eaccount.auth.vo.AuthVo;
import com.bizsp.eaccount.auth.vo.MenuAuthVo;
import com.bizsp.eaccount.auth.vo.UserAuthVo;
import com.bizsp.eaccount.menu.dao.MenuDao;
import com.bizsp.eaccount.menu.vo.MenuVo;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.web.HtmlUtil;

@Path("/rest/authApi/")
@Component
public class AuthService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AuthDao authDao;

	@Autowired
	MenuDao menuDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("authList")
	public Map<String, Object> getAuthList() {

		logger.debug(":::::::::: getAuthList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		AuthVo authVo = new AuthVo();
		authVo.setCompanyCode(getLoginUserInfo().getCompanyCode());

		List<AuthVo> authList = authDao.selectAuthList(authVo);

		resultMap.put("authList", authList);
		resultMap.put("totalRow", authList.size());

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("auth/{authId}")
	public Map<String, Object> getAuth(@PathParam("authId") String authId) throws Exception {

		logger.debug(":::::::::: getAuth ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		AuthVo paramVo = new AuthVo();
		paramVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		paramVo.setAuthId(authId);

		AuthVo authVo = authDao.selectAuth(paramVo);

		if (authVo != null) {
			resultMap.put("authVo", authVo);
			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("권한 정보 조회 실패했습니다."));
		}

		return resultMap;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("auth/{authId}/{authName}/{description}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> insertAuth(@PathParam("authId") String authId, @PathParam("authName") String authName, @PathParam("description") String description) throws Exception {

		logger.debug(":::::::::: insertAuth ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		AuthVo paramVo = new AuthVo();
		paramVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		paramVo.setAuthId(authId);

		int validCnt1 = authDao.validAuthId(paramVo);

		if (validCnt1 == 1) {
			throw new ServiceException(makeMap("중복된 권한 ID 입니다."));
		}

		paramVo.setAuthName(authName);
		paramVo.setDescription(description.replaceAll("`", "/"));
		paramVo.setRegUserId(getLoginUserInfo().getUserId());

		int validCnt2 = authDao.insertAuth(paramVo);

		if (validCnt2 == 1) {
			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("권한 등록 실패했습니다."));
		}

		return resultMap;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("auth/{authId}/{authName}/{description}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> updateAuth(@PathParam("authId") String authId, @PathParam("authName") String authName, @PathParam("description") String description) throws Exception {

		logger.debug(":::::::::: updateAuth ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		AuthVo paramVo = new AuthVo();
		paramVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		paramVo.setAuthId(authId);
		paramVo.setAuthName(authName);
		paramVo.setDescription(description.replaceAll("`", "/"));
		paramVo.setUpdtUserId(getLoginUserInfo().getUserId());

		int validCnt = authDao.updateAuth(paramVo);

		if (validCnt == 1) {
			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("권한 수정 실패했습니다."));
		}

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("userAuthList/{authId}/{pageNo}")
	public Map<String, Object> getUserAuthList(@PathParam("authId") String authId, @PathParam("pageNo") int pageNo) {

		logger.debug(":::::::::: getUserAuthList ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		AuthVo authVo = new AuthVo();
		authVo.setCompanyCode(getLoginUserInfo().getCompanyCode());

		List<AuthVo> authList = authDao.selectAuthList(authVo);

		resultMap.put("authList", authList);

		UserAuthVo paramVo = new UserAuthVo();
		if (pageNo > 0) {
			paramVo.setPagingYn("Y");
		} else {
			paramVo.setPagingYn("N");
		}
		paramVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		paramVo.setAuthId(authId);
		paramVo.setPageNo(pageNo);

		List<Object> userAuthList = authDao.selectUserAuthList(paramVo);

		resultMap.put("html", HtmlUtil.makeHtmlForList(userAuthList, HtmlUtil.USER_AUTH_NAME, HtmlUtil.USER_AUTH_TYPE));
		resultMap.put("totalRow", paramVo.getTotalRow());

		return resultMap;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("userAuth/{authId}/{userId}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> insertUserAuth(@PathParam("authId") String authId, @PathParam("userId") String userId) throws Exception {

		logger.debug(":::::::::: insertUserAuth ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserAuthVo paramVo = new UserAuthVo();
		paramVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		paramVo.setAuthId(authId);
		paramVo.setUserId(userId);
		paramVo.setRegUserId(getLoginUserInfo().getUserId());

		int validCnt = authDao.insertUserAuth(paramVo);

		if (validCnt == 1) {
			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("사용자 권한 등록 실패했습니다."));
		}

		return resultMap;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("userAuth/{authId}/{userId}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> deleteUserAuth(@PathParam("authId") String authId, @PathParam("userId") String userId) throws Exception {

		logger.debug(":::::::::: deleteUserAuth ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserAuthVo paramVo = new UserAuthVo();
		paramVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		paramVo.setAuthId(authId);
		paramVo.setUserId(userId);
		paramVo.setRegUserId(getLoginUserInfo().getUserId());

		int validCnt = authDao.deleteUserAuth(paramVo);

		if (validCnt == 1) {
			resultMap.put("code", "S");
		} else {
			throw new ServiceException(makeMap("사용자 권한 삭제 실패했습니다."));
		}

		return resultMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("menuAuthList/{authId}")
	public Map<String, Object> getMenuAuthList(@PathParam("authId") String authId) {

		logger.info("::::::::::::::::: getMenuAuthList :::::::::::::::::::::");

		AuthVo authVo = new AuthVo();
		MenuAuthVo menuAuthVo = new MenuAuthVo();
		menuAuthVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuAuthVo.setAuthId(authId);

		Map<String, Object> menuAuthMap = new HashMap<String, Object>();
		List<Object> menuAuthList = authDao.getMenuAuthList(menuAuthVo);
		// List<AuthVo> authList = authDao.getAuthList(authVo);

		menuAuthMap.put("menuAuthList", menuAuthList);
		// menuAuthMap.put("authList", authList);

		return menuAuthMap;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insertMenuAuth")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> insertMenuAuth(MenuAuthVo menuAuthVo) {

		logger.info("::::::::::::::::: insertMenuAuth :::::::::::::::::::::");
		Map<String, Object> menuAuthMap = new HashMap<String, Object>();

		menuAuthVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuAuthVo.setRegUserId(getLoginUserInfo().getUserId());

		MenuVo menuVo = new MenuVo();
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuVo.setMenuId(menuAuthVo.getMenuId());
		// //// 최상위부모부터 자신까지 권한 INSERT
		insertParentMenuAuth(menuVo, menuAuthVo);

		// //// 자식노드 조회하여 있으면 권한 INSERT
		List<MenuVo> child = menuDao.getChildMenu(menuVo);
		if (child != null && !child.isEmpty()) {
			insertChildMenuAuth(child, menuAuthVo);
		}
		return menuAuthMap;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteMenuAuth")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> deleteMenuAuth(MenuAuthVo menuAuthVo) {

		logger.info("::::::::::::::::: insertMenuAuth :::::::::::::::::::::");
		Map<String, Object> menuAuthMap = new HashMap<String, Object>();

		menuAuthVo.setCompanyCode(getLoginUserInfo().getCompanyCode());

		MenuVo menuVo = new MenuVo();
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuVo.setMenuId(menuAuthVo.getMenuId());

		// //// 권한 조회하여
		MenuAuthVo result = authDao.getMenuAuth(menuAuthVo);
		// //// 있으면 권한 DELETE
		if (result != null) {
			authDao.deleteMenuAuth(menuAuthVo);
			// //// 자식노드 조회하여 있으면 권한 DELETE
			List<MenuVo> child = menuDao.getChildMenu(menuVo);
			if (child != null && !child.isEmpty()) {
				deleteChildMenuAuth(child, menuAuthVo);
			}
		}

		return menuAuthMap;
	}

	/**
	 * 자기자신부터 부모 메뉴 끝까지 체크하여 전부 권한 INSERT 최상위 부모부터 자신까지 권한 INSERT 한다.
	 * 
	 * @param menuVo
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public boolean insertParentMenuAuth(MenuVo menuVo, MenuAuthVo menuAuthVo) {

		// //// 현재 값으로 메뉴조회
		Map now = menuDao.getMenu(menuVo);
		// //// 현재 메뉴가 최상위 부모메뉴가 아니면
		if (now != null && !now.get("UPMENUID").equals("0")) {
			MenuVo parent = menuDao.getParentMenu(menuVo);
			// //// 다시조회
			insertParentMenuAuth(parent, menuAuthVo);
		}
		// //// menuVo의 menuId 값으로 권한조회
		menuAuthVo.setMenuId(menuVo.getMenuId());
		MenuAuthVo result = authDao.getMenuAuth(menuAuthVo);
		int resultCnt = 0;
		// //// 권한이 없다면
		if (result == null) {
			resultCnt = authDao.insertMenuAuth(menuAuthVo);
		}
		return resultCnt == 1 ? true : false;
	}

	/**
	 * child 권한 INSERT
	 * 
	 * @param menuVo
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public boolean insertChildMenuAuth(List<MenuVo> list, MenuAuthVo menuAuthVo) {
		int resultCnt = 0;
		for (MenuVo menuVo : list) {
			menuAuthVo.setMenuId(menuVo.getMenuId());
			MenuAuthVo result = authDao.getMenuAuth(menuAuthVo);
			if (result == null) {
				resultCnt = authDao.insertMenuAuth(menuAuthVo);
			}

		}
		return resultCnt > 0 ? true : false;
	}

	/**
	 * child 권한 DELETE
	 * 
	 * @param menuVo
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public boolean deleteChildMenuAuth(List<MenuVo> list, MenuAuthVo menuAuthVo) {
		int resultCnt = 0;
		for (MenuVo menuVo : list) {
			menuAuthVo.setMenuId(menuVo.getMenuId());
			MenuAuthVo result = authDao.getMenuAuth(menuAuthVo);
			if (result != null) {
				resultCnt = authDao.deleteMenuAuth(menuAuthVo);
			}
		}
		return resultCnt > 0 ? true : false;
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