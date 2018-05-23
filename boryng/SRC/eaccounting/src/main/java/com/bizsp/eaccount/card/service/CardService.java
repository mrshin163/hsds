package com.bizsp.eaccount.card.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bizsp.eaccount.card.dao.CardDao;
import com.bizsp.eaccount.card.vo.CardMgmtVo;
import com.bizsp.eaccount.card.vo.CardScheduleVo;
import com.bizsp.eaccount.card.vo.CardVo;
import com.bizsp.framework.util.exception.ServiceException;
import com.bizsp.framework.util.lang.DateUtil;
import com.bizsp.framework.util.security.SecurityUtils;

@Component
@Path("/rest/cardApi/")
public class CardService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private SecurityUtils secu;
	
	
	@Autowired
	private CardDao cardDao;	
	
	/**
	 * 카드 목록을 조회한다. 
	 * @param cardOwnCd
	 * @param cardNo
	 * @param cardOwnUser
	 * @param cardStCd
	 * @param searchTypeCd
	 * @param pageNo
	 * @return
	 * @throws ServiceException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCardList/{cardOwnCd}/{cardNo}/{cardOwnUser}/{cardStCd}/{searchTypeCd}/{pageNo}")
	public Map<String, Object> getCardAQList(@PathParam("cardOwnCd") String cardOwnCd
			, @PathParam("cardNo") 			String cardNo
			, @PathParam("cardOwnUser") 	String cardOwnUser			
			, @PathParam("cardStCd") 		String cardStCd			
			, @PathParam("searchTypeCd") 	String searchTypeCd		
			, @PathParam("pageNo") int pageNo ) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("###,###");	
		try {
			CardVo cardVo = new CardVo();
			
			cardVo.setPageNo(pageNo);
			cardVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
			cardVo.setCardOwnCd(cardOwnCd);
			cardVo.setCardNo(cardNo);
			cardVo.setCardOwnUser(cardOwnUser);
			cardVo.setCardStCd(cardStCd);
			cardVo.setSearchTypeCd(searchTypeCd);
			
			if(pageNo > 0 ){
				cardVo.setPagingYn("Y");
			} else {
				cardVo.setPagingYn("N");
			}
			List<CardVo> list = cardDao.selectCardList(cardVo);
			for(CardVo card : list){
				String date = card.getJoinDate();
				card.setJoinDate(DateUtil.dateFormat(date, "yyyy-MM-dd"));
				date = card.getExpireDate();
				card.setExpireDate(DateUtil.dateFormat(date, "yyyy-MM-dd"));
			}
			map.put("cardList", list);
			map.put("totalRow",  cardVo.getTotalRow());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return map;
	}
	
	/**
	 * 카드스케줄 목록을 조회한다. 
	 * @param cardOwnCd
	 * @param cardNo
	 * @param cardOwnUser
	 * @param cardStCd
	 * @param searchTypeCd
	 * @param pageNo
	 * @return
	 * @throws ServiceException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCardScheduleList/{cardOwnCd}/{cardNo}/{cardOwnUser}/{cardStCd}/{searchTypeCd}/{pageNo}")
	public Map<String, Object> getCardScheduleList(@PathParam("cardOwnCd") String cardOwnCd
			, @PathParam("cardNo") 			String cardNo
			, @PathParam("cardOwnUser") 	String cardOwnUser			
			, @PathParam("cardStCd") 		String cardStCd			
			, @PathParam("searchTypeCd") 	String searchTypeCd		
			, @PathParam("pageNo") int pageNo ) throws ServiceException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("###,###");	
		try {
			CardVo cardVo = new CardVo();
			
			cardVo.setPageNo(pageNo);
			cardVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
			cardVo.setCardOwnCd(cardOwnCd);
			cardVo.setCardNo(cardNo);
			cardVo.setCardOwnUser(cardOwnUser);
			cardVo.setCardStCd(cardStCd);
			cardVo.setSearchTypeCd(searchTypeCd);
			
			if(pageNo > 0 ){
				cardVo.setPagingYn("Y");
			} else {
				cardVo.setPagingYn("N");
			}
			List<CardVo> list = cardDao.selectCardScheduleList(cardVo);
			for(CardVo card : list){
				String date = card.getJoinDate();
				card.setJoinDate(DateUtil.dateFormat(date, "yyyy-MM-dd"));
				date = card.getExpireDate();
				card.setExpireDate(DateUtil.dateFormat(date, "yyyy-MM-dd"));
				date = card.getsStartDay();
				card.setsStartDay(DateUtil.dateFormat(date, "yyyy-MM-dd"));
				date = card.getsEndDay();
				card.setsEndDay(DateUtil.dateFormat(date, "yyyy-MM-dd"));
			}
			map.put("cardList", list);
			map.put("totalRow",  cardVo.getTotalRow());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return map;
	}

	/**
	 * 삭제한다.
	 * @param p
	 * @return
	 * @throws ServiceException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setInitCardMgmt")
	@Transactional(propagation = Propagation.REQUIRED)
	public int setInitCardMgmt(CardMgmtVo p) throws ServiceException {
		try {
			p.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
			p.setUpdUserId(secu.getAuthenticatedUser().getUserId());
			return cardDao.updateCardMgmt(p);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 대체사용자를 지정한다.
	 * @param p
	 * @return
	 * @throws ServiceException 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("setRepUser")
	@Transactional(propagation = Propagation.REQUIRED)
	public int setRepUser(CardMgmtVo p) throws ServiceException {
		try {
			//-------------------------------------
			// 1. 건이 없으면 우선 만든다.
			//-------------------------------------
			initCardMgmt(p);
			
			//-------------------------------------
			// 2. 대체자로 지정한다.
			//-------------------------------------

			p.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
			p.setUpdUserId(secu.getAuthenticatedUser().getUserId());
			p.setRepCardYn("Y");
		
//			디버그용 System.out.println 주석 처리
//			System.out.println(p.getCardNo());
//			System.out.println(p.getRepUserId());
//			System.out.println(p.getUpdUserId());
//			System.out.println(p.getRepCardYn());
			
			return cardDao.updateCardMgmt(p);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	/**
	 * 공용카드관리자를 지정한다.
	 * @param p
	 * @return
	 * @throws ServiceException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("setComCardMgr")
	@Transactional(propagation = Propagation.REQUIRED)
	public int setComCardMgr(CardMgmtVo p) throws ServiceException {
		try {
			//-------------------------------------
			// 1. 건이 없으면 우선 만든다.
			//-------------------------------------
			initCardMgmt(p);
			
			//-------------------------------------
			// 2. 공용카드관리자를 지정한다.
			//-------------------------------------
			p.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
			p.setUpdUserId(secu.getAuthenticatedUser().getUserId());
			p.setComCardYn("Y");
			
			return cardDao.updateCardMgmt(p);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 비서를 지정한다. 비서 2명중 1명은 반드시 지정되어야 한다.
	 * @param p
	 * @return
	 * @throws ServiceException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("setSecCardMgr")
	@Transactional(propagation = Propagation.REQUIRED)
	public int setSecCardMgr(CardMgmtVo p) throws ServiceException {
		try {
			//-------------------------------------
			// 1. 건이 없으면 우선 만든다.
			//-------------------------------------
			initCardMgmt(p);
			
			//-------------------------------------
			// 2. 비서를 지정한다.
			//-------------------------------------
			p.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
			p.setUpdUserId(secu.getAuthenticatedUser().getUserId());
			p.setSecCardYn("Y");
			
			return cardDao.updateCardMgmt(p);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 카드관리정보가 없으면 기본으로 만든다. 
	 * @param p
	 * @throws Exception 
	 */
	private void initCardMgmt(CardMgmtVo p) throws Exception {
		p.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		p.setRegUserId(secu.getAuthenticatedUser().getUserId());
		Map<String, CardMgmtVo> ret = cardDao.selectCardMgmt(p);
		
		if(ret == null) {
			int r = cardDao.insertCardMgmt(p);
			if (r == 0)
				throw new Exception("카드관리정보 초기화 도중 에러가 발생했습니다.");
		}
	}

	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("getCardSchedule/{cardNo}")
	public Map<String, Object> getCardSchedule(@PathParam("cardNo") String cardNo) throws ServiceException  {
		Map<String, Object> map = new HashMap<String, Object>();
		CardScheduleVo cardSchedule = new CardScheduleVo();
		cardSchedule.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		cardSchedule.setCardNo(cardNo);
		List<CardScheduleVo> list = cardDao.selectCardSchedule(cardSchedule);
		for(CardScheduleVo schedule : list){
			String date = schedule.getStartDay();
			schedule.setStartDay(DateUtil.dateFormat(date, "yyyy-MM-dd"));
			date = schedule.getEndDay();
			schedule.setEndDay(DateUtil.dateFormat(date, "yyyy-MM-dd"));
		}
		
		map.put("cardSchedule", list);
		return map;
	}
	
	
	/**
	 * 자금팅 공용카드 사용스케줄을 등록한다. 
	 * 기간이 겹치는 부분이 있으면 안된다.
	 * 과거날짜는 넣을 수 없다. 오늘 이후만 넣을 수 있다.
	 * @param p
	 * @return
	 * @throws ServiceException 
	 * @throws ParseException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("addCardSchedule")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addCardSchedule(List<CardScheduleVo> p) throws ServiceException  {
		Map<String, Object> map = new HashMap<String, Object>();
		int ret = 0;
		try {
			// --------------------------------
			// 1. Validation
			// --------------------------------
			if(p==null || p.isEmpty()){
				map.put("result", "F");
				map.put("message", "처리중 오류가 발생하였습니다.");
			}
//			System.out.println(p.get(0).getStartDay());
//			System.out.println(p.get(0).getEndDay());
//			System.out.println(p.get(0).getUserId());
//			System.out.println(p.get(0).getCardNo());
			for(CardScheduleVo cardSchedule : p){
			// 1.1 시작일 종료일 체크 -- VIEW 단에서 이미 한다.
			// 1.2 이미 존재하는지 체크
			// 1.3 오버램 스케줄 체크 -- VIEW 단에서 이미 한다.
				if(checkAleadyCardSchedule(cardSchedule)){
					cardSchedule.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
					cardSchedule.setRegUserId(secu.getAuthenticatedUser().getUserId());
					
//					System.out.println(cardSchedule.getCompanyCd());
//					System.out.println(cardSchedule.getRegUserId());
//					System.out.println(cardSchedule.getStartDay());
//					System.out.println(cardSchedule.getEndDay());
//					System.out.println(cardSchedule.getUserId());
//					System.out.println(cardSchedule.getCardNo());
					
					ret = cardDao.insertCardSchedule(cardSchedule);		

					if (ret == 1){
						map.put("result", "S");
						map.put("message", "스케줄 등록에 성공하였습니다..");
					}
				}
			}

		} catch (ParseException e) {
			map.put("result", "F");
			map.put("message", e);
		} catch (Exception e) {
			map.put("result", "F");
			map.put("message", e);
		}
		
		return map;
	}

	/**
	 * 카드사용스케줄을 삭제한다.
	 * 삭제하고자 하는 기간으로 이미 매입건이 들어와 있으면 삭제할 수 없다.
	 * @param p
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("removeCardSchedule")
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeCardSchedule(Map<String,String> p) {
		// --------------------------------
		// 1. Validation
		// --------------------------------
		
		// --------------------------------
		// 2. 처리
		// --------------------------------
		CardScheduleVo cardScheduleVo = new CardScheduleVo();

		cardScheduleVo.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		cardScheduleVo.setCardNo(p.get("cardNo"));
		cardScheduleVo.setStartDay(p.get("startDay"));
		cardScheduleVo.setEndDay(p.get("endDay"));
		cardScheduleVo.setUserId(p.get("userId"));
		
		return cardDao.deleteCardSchedule(cardScheduleVo);
	}



	/**
	 * 시작일, 종료일 구간 체크. 오늘날짜 이상이며 시작날짜는 종료날짜 보다 뒤 일 수 없다.
	 * @param pStartDay
	 * @param pEndDay
	 * @param pFormat
	 * @return
	 * @throws Exception
	 */
	private boolean checkDuration(String pStartDay, String pEndDay, String pFormat) throws Exception {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pFormat);

		Calendar current = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		current.setTime(new Date());
		start.setTime(format.parse(pStartDay));
		end.setTime(format.parse(pEndDay));
		
		int startDiff 	= (int)((current.getTime().getTime() - start.getTime().getTime()) / (1000*60*60*24));
		int endDiff 	= (int)((current.getTime().getTime() - end.getTime().getTime()) / (1000*60*60*24));
		int diff 		= (int)((end.getTime().getTime() - start.getTime().getTime()) / (1000*60*60*24));

		if (startDiff>0)
			return false;
		if (diff<0) 
			return false;

		return true;
	}

	/**
	 * 카드스케줄이 이미 있는지 체크한다. 
	 * @param p
	 * @throws Exception 
	 */
	private boolean checkAleadyCardSchedule(CardScheduleVo v) throws Exception {

		v.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		List<CardScheduleVo> ret = cardDao.selectCardScheduleVali(v);
		
		if(ret!= null && ret.size() > 0)
			return false;
		else
			return true;
	}

	/**
	 * 카드 기준으로, 저장하고자 하는 스케줄이  겹치는 구간이 있는지 체크 
	 * @param p
	 * @throws Exception 
	 */
	private boolean checkCardOverlapedSchedule(CardScheduleVo v) throws Exception {
		v.setCompanyCd(secu.getAuthenticatedUser().getCompanyCode());
		
		int ret =0;	
		
		// TODO 오버랩되는지 체크 구현할 것. 오버랩되면 throw할 것
		return true;
		
	}
	
}
