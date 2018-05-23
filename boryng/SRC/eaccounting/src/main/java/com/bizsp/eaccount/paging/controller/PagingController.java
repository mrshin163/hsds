package com.bizsp.eaccount.paging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import com.bizsp.eaccount.code.vo.CommGrpCodeVo;
import com.bizsp.eaccount.paging.dao.PagingDao;
import com.bizsp.framework.extend.DaoSupport;
import com.bizsp.framework.util.system.PropUtil;


@Controller

public class PagingController extends DaoSupport{
	
	@Autowired
	PagingDao pageDao;
	
	@RequestMapping(value="/paging")
	public String scrnPaging(CommGrpCodeVo commGrpCodeVo, Model model){
		List<CommGrpCodeVo> dataList;
		
		dataList = pageDao.getPaging(commGrpCodeVo);
		model.addAttribute("codeList", dataList);
		
		return "paging";
	}
}
