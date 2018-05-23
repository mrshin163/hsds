package com.bizsp.eaccount.auth.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.auth.vo.AuthVo;
import com.bizsp.eaccount.auth.vo.MenuAuthVo;
import com.bizsp.eaccount.auth.vo.UserAuthVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class AuthDao extends DaoSupport {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public List<AuthVo> selectAuthList(AuthVo param) {
		logger.info(":::::::::: selectAuthList ::::::::::");

		return selectList("AuthMapper.selectAuthList", param);
	}

	public AuthVo selectAuth(AuthVo param) {
		logger.info(":::::::::: selectAuth ::::::::::");

		return (AuthVo) select("AuthMapper.selectAuth", param);
	}

	public int validAuthId(AuthVo param) {
		logger.info(":::::::::: validAuthId ::::::::::");

		return (Integer) select("AuthMapper.validAuthId", param);
	}

	public int insertAuth(AuthVo param) {
		logger.info(":::::::::: insertAuth ::::::::::");

		return (Integer) insert("AuthMapper.insertAuth", param);
	}

	public int updateAuth(AuthVo param) {
		logger.info(":::::::::: updateAuth ::::::::::");

		return update("AuthMapper.updateAuth", param);
	}

	@SuppressWarnings("unchecked")
	public List<Object> selectUserAuthList(UserAuthVo param) {
		logger.info(":::::::::: selectUserAuthList ::::::::::");

		return pagingList("AuthMapper.selectUserAuthList", param, "AuthMapper.userAuthCount");
	}

	public int insertUserAuth(UserAuthVo param) {
		logger.info(":::::::::: insertUserAuth ::::::::::");

		return (Integer) insert("AuthMapper.insertUserAuth", param);
	}

	public int deleteUserAuth(UserAuthVo param) {
		logger.info(":::::::::: deleteUserAuth ::::::::::");

		return delete("AuthMapper.deleteUserAuth", param);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getMenuAuthList(MenuAuthVo menuAuthVo) {
		logger.info("::::::::::::::: getMenuAuthList :::::::::::::::::");

		return pagingList("AuthMapper.selectMenuAuthList", menuAuthVo);
	}

	public MenuAuthVo getMenuAuth(MenuAuthVo menuAuthVo) {
		logger.info("::::::::::::::: getMenuAuth :::::::::::::::::");
		return getSqlSession().selectOne("selectMenuAuth", menuAuthVo);
	}

	public int insertMenuAuth(MenuAuthVo menuAuthVo) {
		logger.info("::::::::::::::: insertMenuAuth :::::::::::::::::");
		return update("insertMenuAuth", menuAuthVo);
	}

	public int deleteMenuAuth(MenuAuthVo menuAuthVo) {
		logger.info("::::::::::::::: deleteMenuAuth :::::::::::::::::");
		return update("deleteMenuAuth", menuAuthVo);
	}
}