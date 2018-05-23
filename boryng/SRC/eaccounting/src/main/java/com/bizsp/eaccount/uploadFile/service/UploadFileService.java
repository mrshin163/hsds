package com.bizsp.eaccount.uploadFile.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.uploadFile.dao.UploadFileDao;
import com.bizsp.eaccount.uploadFile.vo.AttachFileVo;
import com.bizsp.eaccount.uploadFile.vo.AttachVo;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.security.SecurityUtils;
import com.bizsp.framework.util.system.PropUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/rest/uploadApi/")
@Component
public class UploadFileService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private SecurityUtils secu;

	@Autowired
	private UploadFileDao uploadDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getFileList/{attachId}")
	public synchronized Map<String, Object> getFileList(@PathParam("attachId") String attachId) {

		logger.debug("::::::::::::::::::: getFileList::::::::::::::::");
		AttachFileVo attachFileVo = new AttachFileVo();
		attachFileVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachFileVo.setAttachId(attachId);
		Map<String, Object> notice = new HashMap<String, Object>();
		List<AttachFileVo> fileList = uploadDao.getFileList(attachFileVo);
		notice.put("fileList", fileList);
		return notice;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAttachId")
	public Map<String, Object> getAttachId() {

		logger.debug("::::::::::::::::::: getAttachId::::::::::::::::");

		Map<String, Object> notice = new HashMap<String, Object>();
		String attachId = (String) uploadDao.getAttachId();
		// TO-DO 마스터 인서트
		notice.put("attachId", attachId);
		return notice;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteFile/{attachId}/{seq}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> deleteFile(@PathParam("attachId") String attachId, @PathParam("seq") String seq) {

		logger.debug("::::::::::::::::::: modifyNotice::::::::::::::::");
		Map<String, Object> notice = new HashMap<String, Object>();
		AttachFileVo attachFileVo = new AttachFileVo();
		attachFileVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachFileVo.setAttachId(attachId);
		attachFileVo.setSeq(seq);
		attachFileVo.setUpdUserId(secu.getAuthenticatedUser().getUserId());

		int result = uploadDao.deleteAttachFile(attachFileVo);

		if (result > 0) {
			notice.put("result", "S");
			notice.put("message", "삭제 되었습니다.");
		} else {
			notice.put("result", "F");
			notice.put("message", "삭제 실패하였습니다.");
		}

		return notice;
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("downloadFile/{attachId}/{seq}")
	public Response downloadFile(@PathParam("attachId") String attachId, @PathParam("seq") String seq) {
		logger.debug("::::::::::::::::::: modifyNotice::::::::::::::::");

		AttachFileVo attachFileVo = new AttachFileVo();

		attachFileVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachFileVo.setAttachId(attachId);
		attachFileVo.setSeq(seq);
		//System.out.println(attachId);
		List<AttachFileVo> result = uploadDao.getFileList(attachFileVo);

		File file = null;
		String filePath = "";
		String fileName = "";
		if (result != null && !result.isEmpty()) {
			attachFileVo = result.get(0);
			fileName = attachFileVo.getlFileName() + "." + attachFileVo.getExt();
			//System.out.println(attachFileVo.getFilePath());
			filePath = attachFileVo.getFilePath() + "\\" + attachFileVo.getpFileName();
			file = new File(filePath);

			// file.renameTo(new
			// File(attachFileVo.getlFileName()+"."+attachFileVo.getExt()));
		}
		//System.out.println(attachFileVo.getlFileName());
		//System.out.println(file.getName());

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		return response.build();
	}

	@SuppressWarnings("rawtypes")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("uploadNoticeFile/{type}/{attachId}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public synchronized Map<String, Object> uploadNoticeFile(@FormDataParam("file") FormDataContentDisposition fileInfo, @FormDataParam("file") InputStream inputStream, @PathParam("type") String type, @PathParam("attachId") String attachId) {
		logger.debug("::::::::::::::::::: uploadNoticeFile::::::::::::::::");

		String filePath = PropUtil.getValue("file.uploadPolicy.path") + type + "\\" + DateUtil.getToday("yyyyMMdd");
		AttachVo attachVo = new AttachVo();
		AttachFileVo attachFileVo = new AttachFileVo();
		File desti = new File(filePath);
		
		//System.out.println("#######  !desti.exists() : "+!desti.exists());
		// //// 해당 디렉토리의 존재여부를 확인
		if (!desti.exists()) {			
			if(desti.mkdirs()){
				//System.out.println("#######  desti.mkdirs() Success !!!");
			}else{
				//System.out.println("#######  desti.mkdirs() Fail !!!");
				try {
					Files.createDirectory(desti.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//System.out.println("#######  !desti.exists() : "+!desti.exists());

		attachVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachVo.setAttachId(attachId);
		attachVo.setBizTpCode(type);
		attachVo.setRegUserId(secu.getAuthenticatedUser().getUserId());

		// //// 첨부파일마스터 조회하여 없으면 insert
		List tmp = uploadDao.getAttachList(attachVo);
		//System.out.println(tmp == null ? "null Y" : "null N");
		//System.out.println(tmp.isEmpty());
		//System.out.println(fileInfo.getFileName());
		if (tmp == null || tmp.isEmpty()) {
			uploadDao.insertAttach(attachVo);
		}

		String[] fileName = fileInfo.getFileName().split("[.]");
		String ext = fileName[1];
		String lFileName = fileName[0];
		String pFileName = RandomStringUtils.random(15, true, true);

		attachFileVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachFileVo.setAttachId(attachId);
		attachFileVo.setlFileName(lFileName);
		attachFileVo.setpFileName(pFileName);
		attachFileVo.setFilePath(filePath);
		attachFileVo.setExt(ext);
		attachFileVo.setRegUserId(secu.getAuthenticatedUser().getUserId());

		filePath += "\\" + pFileName;
		//System.out.println(filePath);

		// writeToFile(inputStream, filePath);
		int read = 0;
		long fileSize = 0;
		try {
			OutputStream out = new FileOutputStream(new File(filePath));
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
				if (read != -1)
					fileSize += read;
			}
			out.flush();
			out.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// //// 파일이 실제로 만들어지면 데이터베이스에 정보 입력
		attachFileVo.setFileSize(Long.toString(fileSize));
		uploadDao.insertAttachFile(attachFileVo);

		Map<String, Object> notice = new HashMap<String, Object>();
		notice.put("result", "S");
		notice.put("message", "파일 업로드 되었습니다.");

		return notice;
	}

	@SuppressWarnings("rawtypes")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("uploadFile/{type}/{attachId}/{bizKey}/{typeCode}/{accountCode}/{seq}")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public synchronized Map<String, Object> uploadFile(@FormDataParam("file") FormDataContentDisposition fileInfo, 
			@FormDataParam("file") InputStream inputStream, @PathParam("type") String type, @PathParam("attachId") String attachId, 
			@PathParam("bizKey") String bizKey, @PathParam("typeCode") String typeCode,  @PathParam("accountCode") String accountCode,
			@PathParam("seq") String seq) {
		logger.debug("::::::::::::::::::: uploadFile ::::::::::::::::");

		Map<String, String> typeCodeMap = new HashMap<String, String>();
		typeCodeMap.put("1", "seminar");
		typeCodeMap.put("2", "symposium");
		typeCodeMap.put("3", "sales");
		typeCodeMap.put("4", "other");
		typeCodeMap.put("5", "general");

		String filePath = PropUtil.getValue("file.uploadPolicy.path") + typeCodeMap.get(accountCode) + "\\" + DateUtil.getToday("yyyyMMdd");
		AttachVo attachVo = new AttachVo();
		AttachFileVo attachFileVo = new AttachFileVo();
		File desti = new File(filePath);

		// //// 해당 디렉토리의 존재여부를 확인
		if (!desti.exists()) {
			desti.mkdirs();
		}
		
		attachVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachVo.setAttachId(attachId);
		attachVo.setBizTpCode(type);
		attachVo.setBizKey(secu.getAuthenticatedUser().getCompanyCode() + "|" + bizKey + "|" + seq);
		attachVo.setRegUserId(secu.getAuthenticatedUser().getUserId());

		// //// 첨부파일마스터 조회하여 없으면 insert
		AttachVo tmp = uploadDao.getAttach(attachVo);
		//System.out.println("#################### "+tmp == null ? "null Y" : "null N");
		//System.out.println("#################### "+fileInfo.getFileName());
		if (tmp == null) {
			uploadDao.insertAttach(attachVo);
		}		

		String[] fileName = null;
		try {
			fileName = new String(fileInfo.getFileName().getBytes("8859_1"),"utf-8").split("[.]");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(fileName==null) 
			throw new ServiceException(makeMap("파일 이름이 정의되지 않았습니다."));
		
		String ext = fileName[1];
		String lFileName = fileName[0];
		String pFileName = RandomStringUtils.random(15, true, true);
		String path = typeCodeMap.get(accountCode) + "\\" + DateUtil.getToday("yyyyMMdd");

		attachFileVo.setCompanyCode(secu.getAuthenticatedUser().getCompanyCode());
		attachFileVo.setAttachId(attachId);
		attachFileVo.setlFileName(lFileName);
		attachFileVo.setpFileName(pFileName);
		attachFileVo.setFilePath(path);
		attachFileVo.setRegUserId(secu.getAuthenticatedUser().getUserId());

		filePath += "\\" + pFileName;
		//System.out.println("filePath :::::::::::::::::::::" + filePath);

		// writeToFile(inputStream, filePath);
		int read = 0;
		long fileSize = 0;
		try {
			OutputStream out = new FileOutputStream(new File(filePath));
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
				if (read != -1)
					fileSize += read;
			}
			out.flush();
			out.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (isImageFile(new File(filePath))) {
			writeFile(new File(filePath), filePath);
			attachFileVo.setExt("jpg");
		} else {
			attachFileVo.setExt(ext);
		}
		

		// //// 파일이 실제로 만들어지면 데이터베이스에 정보 입력				
		attachFileVo.setFileSize(Long.toString(fileSize));
		uploadDao.insertAttachFile(attachFileVo);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "S");
		result.put("message", "파일 업로드 되었습니다.");

		return result;
	}

	private boolean isImageFile(File file) {
		boolean result = false;

		String[] arr = { "image/bmp", "image/x-ms-bmp", "image/gif", "image/big-gif", "image/png", "image/jpeg" };

		String mimeType = "";
		try {
			Tika tika = new Tika();
			mimeType = tika.detect(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(mimeType)) {
				result = true;
				break;
			}
		}

		return result;
	}

	private boolean writeFile(File inFile, String outFilePath) {
		boolean result = false;

		BufferedImage inputImage = null;
		try {
			inputImage = ImageIO.read(inFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int width = inputImage.getWidth();
		int height = inputImage.getHeight();

		while (height > 750) {
			width = calculateSize(width);
			height = calculateSize(height);
		}

		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = outputImage.createGraphics();
		g.drawImage(inputImage, 0, 0, width, height, null);

		FileOutputStream fos = null;
		try {
			File outFile = new File(outFilePath);
			fos = new FileOutputStream(outFile);
			ImageIO.write(outputImage, "jpg", fos);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		return result;
	}

	private int calculateSize(int size) {
		int rate = 9;
		return size * rate / 10;
	}
	
	private Map<String, String> makeMap(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "F");
		map.put("msg", message);

		return map;
	}
}