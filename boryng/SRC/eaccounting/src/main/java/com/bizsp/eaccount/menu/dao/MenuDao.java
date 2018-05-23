package com.bizsp.eaccount.menu.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.menu.vo.MenuVo;
import com.bizsp.framework.extend.DaoSupport;
import com.bizsp.framework.util.security.SecurityUtils;


@Repository
public class MenuDao extends DaoSupport{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SqlSessionTemplate sqlSession;

	private static SecurityUtils secu;

	/**
	 * 메뉴 리스트
	 * @return
	 */
	public List<Map<String, Object>> getMenuList(MenuVo menuVo){
		logger.info("::::::::::::::: getMenuList :::::::::::::::::");
		List<Map<String, Object>> menuMap = sqlSession.selectList("MenuMapper.selectMenuList", menuVo);
		return menuMap;
	}
	
	/**
	 * 단일 메뉴 
	 * @param menuVo
	 * @return
	 */
	public Map<String, Object> getMenu(MenuVo menuVo){
		logger.info("::::::::::::::: getMenu :::::::::::::::::");
		return sqlSession.selectOne("MenuMapper.selectMenuList", menuVo);
	}
	
	/**
	 * 메뉴 추가
	 * @param menuVo
	 * @return
	 */
	public int insertMenu(MenuVo menuVo){
		logger.info("::::::::::::::: updateMenu :::::::::::::::::");
		return (Integer) insert("MenuMapper.insertMenu", menuVo);
	}
	
	/**
	 * 메뉴 업데이트
	 * @param menuVo
	 * @return
	 */
	public int updateMenu(MenuVo menuVo){
		logger.info("::::::::::::::: updateMenu :::::::::::::::::");
		return update("MenuMapper.updateMenu", menuVo);
	}
	
	/**
	 * 메뉴 삭제
	 * @param menuVo
	 * @return
	 */
	public int deleteMenu(MenuVo menuVo){
		logger.info("::::::::::::::: updateMenu :::::::::::::::::");
		return update("MenuMapper.deleteMenu", menuVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUerMenuList(){
		logger.debug("::::::::::::::: getUserMenuList :::::::::::::::::");
		HashMap<String,String> userInfo = new HashMap<String, String>();
		
		userInfo.put("userId", secu.getAuthenticatedUser().getUserId());
		userInfo.put("companyCd", secu.getAuthenticatedUser().getCompanyCode());

		return selectList("MenuMapper.selectUserMenuList", userInfo);
	}
	
	/**
	 * 자식 메뉴 
	 * @param menuVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MenuVo> getChildMenu(MenuVo menuVo){
		logger.info("::::::::::::::: getChildMenu :::::::::::::::::");
		return selectList("MenuMapper.selectChildMenu", menuVo);
		
	}
	
	/**
	 * 부모 메뉴 
	 * @param menuVo
	 * @return
	 */
	public MenuVo getParentMenu(MenuVo menuVo){
		logger.info("::::::::::::::: getMenu :::::::::::::::::");
		return sqlSession.selectOne("MenuMapper.selectParentMenu", menuVo);
	}
	
}
