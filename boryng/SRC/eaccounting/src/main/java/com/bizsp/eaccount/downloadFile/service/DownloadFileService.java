package com.bizsp.eaccount.downloadFile.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bizsp.eaccount.downloadFile.dao.DownloadFileDao;
import com.bizsp.eaccount.downloadFile.vo.AttachFileVo;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.system.PropUtil;

@Path("/rest/downloadApi/")
@Component
public class DownloadFileService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DownloadFileDao downloadFileDao;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("checkFile/{attachId}/{seq}")
	public Response checkFile(@PathParam("attachId") String attachId, @PathParam("seq") String seq) throws Exception {

		logger.debug(":::::::::: checkFile ::::::::::");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (attachId != null && !"".equals(attachId) && seq != null && !"".equals(seq)) {
			AttachFileVo paramVo = new AttachFileVo();
			paramVo.setAttachId(attachId);
			paramVo.setSeq(seq);
			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());

			AttachFileVo attachFileVo = downloadFileDao.selectAttachFile(paramVo);

			if (attachFileVo != null) {
				File file = new File(PropUtil.getValue("file.uploadPolicy.path") + attachFileVo.getFilePath() + "\\" + attachFileVo.getPfileNm() + "." + attachFileVo.getExt());

				if (file.exists()) {
					resultMap.put("code", "S");
				} else {
					resultMap.put("code", "F");
					resultMap.put("msg", "파일이 존재하지 않습니다.");
				}
			} else {
				resultMap.put("code", "F");
				resultMap.put("msg", "파일이 존재하지 않습니다.");
			}
		} else {
			resultMap.put("code", "F");
			resultMap.put("msg", "파일이 존재하지 않습니다.");
		}

		return Response.status(200).entity(resultMap).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("downloadFile/{attachId}/{seq}")
	public Response downloadFile(@PathParam("attachId") String attachId, @PathParam("seq") String seq) throws Exception {

		logger.debug(":::::::::: downloadFile ::::::::::");

		ResponseBuilder res = null;

		if (attachId != null && !"".equals(attachId) && seq != null && !"".equals(seq)) {
			AttachFileVo paramVo = new AttachFileVo();
			paramVo.setAttachId(attachId);
			paramVo.setSeq(seq);
			paramVo.setCompanyCd(getLoginUserInfo().getCompanyCode());

			AttachFileVo attachFileVo = downloadFileDao.selectAttachFile(paramVo);

			if (attachFileVo != null) {
				File file = new File(PropUtil.getValue("file.uploadPolicy.path") + attachFileVo.getFilePath() + "\\" + attachFileVo.getPfileNm() + "." + attachFileVo.getExt());

				if (file.exists()) {
					res = Response.ok((Object) file);
					res.header("Content-Disposition", "attachment; filename=" + attachFileVo.getPfileNm() + "." + attachFileVo.getExt());
				}
			}
		}

		return res.build();
	}

	private LoginUserVO getLoginUserInfo() {
		return (LoginUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}