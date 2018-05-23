package com.bizsp.eaccount.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.admin.vo.LogVo;
import com.bizsp.framework.extend.DaoSupport;
@Repository
public class LogDao extends DaoSupport{
	
	@SuppressWarnings("unchecked")
	public List<LogVo> selectLogList(LogVo logVo){
		return selectList("",logVo);
	}
	
	public int insertLog(LogVo logVo){
		return (Integer) insert("insertLog",logVo);
	}
}
