package com.bizsp.eaccount.dept.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.dept.vo.DeptVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class DeptDao extends DaoSupport{
	
	/**
	 * 부서 조회 
	 * @param deptVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DeptVo> getDeptList(DeptVo deptVo){
		return pagingList("DeptMapper.selectDeptList", deptVo);
	}
}
