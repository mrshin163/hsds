package com.bizsp.eaccount.downloadFile.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bizsp.eaccount.downloadFile.vo.AttachFileVo;
import com.bizsp.framework.extend.DaoSupport;

@Repository
public class DownloadFileDao extends DaoSupport {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AttachFileVo selectAttachFile(AttachFileVo param) {
		logger.info(":::::::::: selectAttachFile ::::::::::");

		return (AttachFileVo) getSqlSession().selectOne("DownloadFileMapper.selectAttachFile", param);
	}
}