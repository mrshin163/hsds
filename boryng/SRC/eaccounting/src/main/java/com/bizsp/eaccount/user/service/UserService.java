package com.bizsp.eaccount.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.user.dao.UserDao;
import com.bizsp.eaccount.user.vo.UserVo;

@Path("/rest/userApi")
@Component
public class UserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserDao userDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("userList/{userName}/{deptName}/{pageNo}")
	public Map<String, Object> getUserList(@PathParam("userName") String userName, @PathParam("deptName") String deptName, @PathParam("pageNo") int pageNo) {
		logger.debug("::::::::::::::::::: getUserList ::::::::::::::::");

		UserVo userVo = new UserVo();
		if (pageNo > 0)
			userVo.setPagingYn("Y");
		else
			userVo.setPagingYn("N");

		userVo.setUserName(userName);
		userVo.setDeptName(deptName.replaceAll("`", "/"));
		userVo.setPageNo(pageNo);

		Map<String, Object> userMap = new HashMap<String, Object>();
		List<UserVo> userList = userDao.getUserList(userVo);

		userMap.put("userList", userList);
		userMap.put("totalRow", userVo.getTotalRow());

		return userMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nameSearch/{userName}")
	public List<String> getUserNameList(@PathParam("userName") String userName) {
		logger.debug("::::::::::::::::::: getUserList ::::::::::::::::");

		UserVo userVo = new UserVo();
		userVo.setPagingYn("Y");
		userVo.setUserName(userName);
		userVo.setPageNo(1);
		List<UserVo> userList = userDao.getUserList(userVo);
		List<String> userNameList = new ArrayList<String>();

		for (UserVo vo : userList) {
			userNameList.add(vo.getUserName());
		}

		return userNameList;
	}
}