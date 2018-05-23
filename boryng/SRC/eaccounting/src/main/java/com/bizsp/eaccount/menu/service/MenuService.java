package com.bizsp.eaccount.menu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bizsp.eaccount.menu.dao.MenuDao;
import com.bizsp.eaccount.menu.vo.MenuVo;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.web.TreeUtil;

@Path("/rest/menuApi")
@Component
public class MenuService {
	
	@Autowired
	MenuDao menuDao;
	
	@Context
	private SecurityContext securityContext;

	/**
	 * menu Tree 구조
	 * @return
	 */
	@SuppressWarnings("static-access")
	@GET
	@Path("menuList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getMenuTree(){
		MenuVo menuVo = new MenuVo();
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		List<Map<String, Object>> menuList = menuDao.getMenuList(menuVo);
		List<Map<String, Object>> menuTree;
		
		TreeUtil treeUtil = new TreeUtil();
		menuTree = treeUtil.convertorTreeMap(menuList, "0", "MENUID", "UPMENUID", "MENUNAME", "MENUORDER");
		return menuTree;
	}
	
	public List<Map<String, Object>> getMenuList(){
		MenuVo menuVo = new MenuVo();
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		List<Map<String, Object>> menuList = menuDao.getMenuList(menuVo);
		return menuList;
	}
	
	/**
	 * 단일 메뉴
	 * @param menuId
	 * @return
	 */
	@GET
	@Path("menuSearch/{menuId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getMenu(@PathParam("menuId") String menuId){
		MenuVo menuVo = new MenuVo();
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuVo.setMenuId(menuId);
		return menuDao.getMenu(menuVo);
	}
	
	/**
	 * 메뉴 관리 등록
	 * @param menuVo
	 * @return
	 */
	@POST
	@Path("insertMenu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED)
	public Response insertMenu(MenuVo menuVo){
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuVo.setRegUserId(getLoginUserInfo().getUserId());
		menuDao.insertMenu(menuVo);
		return Response.status(200).entity(menuVo).build();
	}
	
	/**
	 * 메뉴 관리 업데이트
	 * @param menuVo
	 * @return
	 */
	@POST
	@Path("updateMenu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED)
	public Response updateMenu(MenuVo menuVo){
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuVo.setUpdtUserId(getLoginUserInfo().getUserId());
		menuDao.updateMenu(menuVo);
		return Response.status(200).entity(menuVo).build();
	}
	
	/**
	 * 메뉴 관리 업데이트
	 * @param menuVo
	 * @return
	 */
	@POST
	@Path("deleteMenu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED)
	public Response deleteMenu(MenuVo menuVo){
		menuVo.setCompanyCode(getLoginUserInfo().getCompanyCode());
		menuVo.setUpdtUserId(getLoginUserInfo().getUserId());
		menuDao.deleteMenu(menuVo);
		return Response.status(200).entity(menuVo).build();
	}
	
	/**
	 * 유저 메뉴 권한에 따른 메뉴리스트 
	 * @return
	 */
	@SuppressWarnings("static-access")
	@Path("userMenuList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getUserMenuList(){
		
		List<Map<String, Object>> userMenuList = menuDao.getUerMenuList();
		List<Map<String, Object>> userMenuTree;
		
		TreeUtil treeUtil = new TreeUtil();
		userMenuTree = treeUtil.convertorTreeMap(userMenuList, "0", "MENUID", "UPMENUID", "MENUNAME", "MENUORDER");
		
		return userMenuTree;
	}	

	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	
	
}
