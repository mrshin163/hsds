package com.bizsp.eaccount.notice.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.notice.vo.NoticeVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class NoticeDao extends DaoSupport{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 공지사항리스트 조회
	 * @param NoticeVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NoticeVo> getNoticeList(NoticeVo noticeVo){
		logger.info("::::::::::::::: getNotice :::::::::::::::::");		
		return pagingList("NoticeMapper.selectNoticeList", noticeVo, "NoticeMapper.noticeCount");
	}
	
	/**
	 * 공지사항 조회
	 * @param NoticeVo
	 * @return
	 */
	public NoticeVo getNoticeOne(NoticeVo noticeVo){
		logger.info("::::::::::::::: getNotice :::::::::::::::::");		
		return getSqlSession().selectOne("NoticeMapper.selectNoticeOne", noticeVo);
	}
	
	/**
	 * 공지사항 seq 채번
	 * @param AttachFileVo
	 * @return
	 */
	public String getNoticeSeq(){
		logger.info("::::::::::::::: getNoticeSeq :::::::::::::::::");
		return (String) select("NoticeMapper.getNoticeSeq");
	}
		
	/**
	 * 공지사항 저장
	 * @param NoticeVo
	 * @return
	 */
	public int insertNotice(NoticeVo noticeVo){
		logger.info("::::::::::::::: insertNotice :::::::::::::::::");
		return (Integer) insert("NoticeMapper.insertNotice", noticeVo);
	}
	
	/**
	 * 공지사항 수정
	 * @param NoticeVo
	 * @return
	 */
	public int updateNotice(NoticeVo noticeVo){
		logger.info("::::::::::::::: updateNotice :::::::::::::::::");
		return update("NoticeMapper.updateNotice", noticeVo);
	}
	
	/**
	 * 공지사항 조회수 추가
	 * @param NoticeVo
	 * @return
	 */
	public int addNoticeCnt(NoticeVo noticeVo){
		logger.info("::::::::::::::: addNoticeCnt :::::::::::::::::");
		return update("NoticeMapper.addNoticeCnt", noticeVo);
	}
	
	/**
	 * 공지사항 삭제
	 * @param NoticeVo
	 * @return
	 */
	public int deleteNotice(NoticeVo noticeVo){
		logger.info("::::::::::::::: deleteNotice :::::::::::::::::");
		return delete("NoticeMapper.deleteNotice", noticeVo);
	}
	
	
	/**
	 * 첨부파일 마스터ID 채번
	 * @param NoticeVo
	 * @return
	 */
	public String getAttachId() {
		// TODO Auto-generated method stub
		return (String) select("NoticeMapper.getAttachId");
	}

	/**
	 * 메인화면용 공지사항 TOP5 조회
	 * @param NoticeVo
	 * @return
	 */
	public List<NoticeVo> getNoticeListTop5(){
		logger.info("::::::::::::::: getNoticeListTop5 :::::::::::::::::");		
		return getSqlSession().selectList("NoticeMapper.getNoticeListTop5");
	}
		
}
