package com.bizsp.eaccount.user.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;




import com.bizsp.eaccount.user.vo.UserVo;
import com.bizsp.framework.extend.DaoSupport;


@Repository
public class UserDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	public List<UserVo> getUserList(UserVo userVo){
		logger.debug("::::::::::::::: getUserList :::::::::::::::::");
		return pagingList("UserMapper.selectUserList", userVo, "UserMapper.userCount");
	}

}
