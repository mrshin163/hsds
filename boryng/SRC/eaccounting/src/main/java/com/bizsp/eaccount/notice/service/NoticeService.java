package com.bizsp.eaccount.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.notice.dao.NoticeDao;
import com.bizsp.eaccount.notice.vo.NoticeVo;
import com.bizsp.eaccount.uploadFile.dao.UploadFileDao;
import com.bizsp.eaccount.uploadFile.vo.AttachFileVo;
import com.bizsp.eaccount.uploadFile.vo.AttachVo;
import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.security.SecurityUtils;

@Path("/rest/noticeApi/")
@Component
public class NoticeService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private SecurityUtils secu;
	
	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private UploadFileDao uploadDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("noticeList/{pageNo}")
	public Map<String, Object> getNotice(@PathParam("pageNo") int pageNo) {		
		logger.debug("::::::::::::::::::: getNotice::::::::::::::::");
		Map<String, Object> notice = new HashMap<String, Object>();
		
		NoticeVo noticeVo = new NoticeVo();		
		noticeVo.setPageNo(pageNo);
		noticeVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		
		if(pageNo > 0 ){
			noticeVo.setPagingYn("Y");
		} else {
			noticeVo.setPagingYn("N");
		}
		
		List<NoticeVo> 		noticeList = noticeDao.getNoticeList(noticeVo);
		notice.put("notice", noticeList);
		notice.put("totalRow", noticeVo.getTotalRow());
		
		return notice;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insertNotice")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> insertNotice(NoticeVo noticeVo) {
		logger.debug("::::::::::::::::::: insertNotice::::::::::::::::");
		
		Map<String, Object> notice = new HashMap<String, Object>();
		
		//공지사항 insert
		noticeVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		noticeVo.setRegUserId(secu.getAuthenticatedUser().getUserId());
		noticeVo.setSeq(noticeDao.getNoticeSeq());
		int result = noticeDao.insertNotice(noticeVo);
		
		if(result>0){
			if(noticeVo.getAttachId()!=null || !noticeVo.getAttachId().equals("")){
//				System.out.println("####################### noticeVo.getAttachId() : " + noticeVo.getAttachId());
				AttachVo attachVo = new AttachVo();
				attachVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
				attachVo.setAttachId(noticeVo.getAttachId());
							
				boolean isNull = uploadDao.getAttach(attachVo)==null?true:false;
				
				if(!isNull){
					String bizKey = secu.getAuthenticatedUser().getCompanyCode() + "|" + noticeVo.getSeq();
					attachVo.setBizKey(bizKey);
					uploadDao.updateAttach(attachVo);
				}
			}
			
			notice.put("result", "S");
			notice.put("message", "저장 되었습니다.");
		}else{
			notice.put("result", "F");
			notice.put("message", "저장 실패하였습니다.");
		}
		
		return notice;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateNotice")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> updateNotice(NoticeVo noticeVo) {
		logger.debug("::::::::::::::::::: updateNotice::::::::::::::::");
		Map<String, Object> notice = new HashMap<String, Object>();
		noticeVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		noticeVo.setUpdtUserId(secu.getAuthenticatedUser().getUserId());
		int result = noticeDao.updateNotice(noticeVo);
//		System.out.println(result);
		if(result==1){
			notice.put("result", "S");
			notice.put("message", "수정 되었습니다.");
		}else{
			notice.put("result", "F");
			notice.put("message", "수정 실패하였습니다.");
		}
		
		return notice;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteNotice")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> deleteNotice(NoticeVo noticeVo) {
		logger.debug("::::::::::::::::::: deleteNotice::::::::::::::::");
		Map<String, Object> notice = new HashMap<String, Object>();
		noticeVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		noticeVo.setUpdtUserId(secu.getAuthenticatedUser().getUserId());
		int result = noticeDao.deleteNotice(noticeVo);
		
		if(result>0){
			notice.put("result", "S");
			notice.put("message", "삭제 되었습니다.");
		}else{
			notice.put("result", "F");
			notice.put("message", "삭제 실패하였습니다.");
		}		
		return notice;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addCount/{seq}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addCount(@PathParam("seq") String seq) {
		logger.debug("::::::::::::::::::: noticeAddCount::::::::::::::::");
		Map<String, Object> map = new HashMap<String, Object>();
		
		//조회수 추가 및 첨부파일 조회한다.
		NoticeVo noticeVo = new NoticeVo();
		noticeVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		noticeVo.setSeq(seq);
		
		noticeDao.addNoticeCnt(noticeVo);
		NoticeVo resultNoticeVo = noticeDao.getNoticeOne(noticeVo);
		
		if(resultNoticeVo!=null){
			map.put("result", "S");
			map.put("notice", resultNoticeVo);
			
			if(resultNoticeVo.getAttachId()!=null){
				//////  첨부파일 리스트 조회
				AttachFileVo attachFileVo = new AttachFileVo();
				attachFileVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
				attachFileVo.setAttachId(resultNoticeVo.getAttachId());				
				map.put("fileList", uploadDao.getFileList(attachFileVo));
			}
		}else{
			map.put("result", "F");
		}
		
		return map;
	}
}
