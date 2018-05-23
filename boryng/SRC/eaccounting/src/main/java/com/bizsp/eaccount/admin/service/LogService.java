package com.bizsp.eaccount.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.admin.dao.LogDao;
import com.bizsp.eaccount.admin.vo.LogVo;
import com.bizsp.eaccount.dept.vo.DeptVo;
import com.bizsp.eaccount.log.vo.MenuInfo;
import com.bizsp.eaccount.menu.dao.MenuDao;
import com.bizsp.eaccount.menu.vo.MenuVo;
import com.bizsp.eaccount.report.dao.ReportDao;
import com.bizsp.framework.security.vo.LoginUserVO;

@Path("/rest/logApi/")
@Component
public class LogService {
	
	@Autowired
	LogDao logDao;
	
	@Autowired
	ReportDao reportDao;
	
	@Autowired
	MenuDao menuDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("logList/{startDate}/{endDate}")
	public Map<String, Object> getLogList(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		LogVo logVo = new LogVo();
		
		
		List<LogVo> logList = logDao.selectLogList(logVo);
		
		resultMap.put("logList", logList);
		return resultMap;
	}
	
	public List<DeptVo> getDeptList() {
		DeptVo deptVo = new DeptVo();
		deptVo.setCopanyCode(getLoginUserInfo().getCompanyCode());
		deptVo.setDeptCode(getLoginUserInfo().getDeptCode());
		List<DeptVo> deptList = reportDao.getLowDeptList(deptVo);
		
		System.out.println("######## deptList Size : "+deptList.size());
		for(DeptVo tmp : deptList){
			System.out.println("### "+tmp.getDeptCode());
		}
		
		/*DeptListVo deptListVo = new DeptListVo();
		deptListVo.setCompanyCd(getLoginUserInfo().getCompanyCode());
		deptListVo.setDeptList(deptList);
		List<UserVo> userList = reportDao.getLowUserList(deptListVo);
		System.out.println("######## "+userList.size());
		for(UserVo tmp : userList){
			System.out.println("### "+tmp.getUserId() + ", " + tmp.getDeptCode());
		}*/
		return deptList;
	}
	
	public int insertLog(LogVo logVo){

    	/*LoginUserVO loginUserVo = getLoginUserInfo();

    	logVo.setC_CD(loginUserVo.getCompanyCode());
    	logVo.setEMP_ID(loginUserVo.getUserId());
    	logVo.setEMP_NM(loginUserVo.getName());
    	logVo.setHANDPHONE(loginUserVo.getMobileNo());
    	logVo.setDIVISION_CODE(loginUserVo.getDeptCode());
    	logVo.setDIVISION_NAME(loginUserVo.getDeptName());
    	logVo.setPOSITION(loginUserVo.getTitleAliasName());
    	logVo.setCALLNAME(loginUserVo.getDutyName());
    	logVo.setOFFICE(loginUserVo.getTelNo());

    	System.out.println(logVo.toString());*/
    	// 로그 insert
    	return logDao.insertLog(logVo);

	}
	
	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
