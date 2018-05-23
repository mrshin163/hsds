package com.bizsp.eaccount.paging.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.code.vo.CommGrpCodeVo;
import com.bizsp.framework.extend.DaoSupport;


@Repository
public class PagingDao extends DaoSupport{
	@SuppressWarnings("unchecked")
	public List<CommGrpCodeVo> getPaging(CommGrpCodeVo commGrpCodeVo){
		
		
		commGrpCodeVo.setPagingYn("Y");
		return pagingList("CodeMapper.selectCommGrpCodeList", commGrpCodeVo, "CodeMapper.commGrpCodeCount");
	}
}
