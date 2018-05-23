package com.bizsp.framework.util.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.system.PropUtil;



public class BaseCndModel implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = -3080534432342991815L;
	/**
	 * 페이징여부
	 */
	private String pagingYn;
	/**
	 * 페이지번호
	 */
	private int pageNo;
	/**
	 * 검색어
	 */
	private String searchValue;
	/**
	 * 검색타입
	 */
	private String searchCode;
	/**
	 * 조회기간 - 시작일
	 */
	private String startDt;
	/**
	 * 조회기간 - 종료일
	 */
	private String endDt;

	/**
	 * 한페이지에 출력하는 라인수
	 */
	private int rowPerPage;

	private int pagePerPage;	// 페이지 갯수   << 1 2 3 4 5 6 7 8 9 10 >>

	/**
	 * 검색된 총row수
	 */
	private long totalRow;



	public long getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}
	public int getStartIdx() {
		if(this.pageNo==0){
			return 0;
		}
		return (getPageNo()-1)*getRowPerPage();
	}
	public int getEndIdx() {
		return getRowPerPage();
	}

	public int getPageNo() {
		return pageNo==0?1:pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getPagingYn() {
		return pagingYn;
	}
	public void setPagingYn(String pagingYn) {
		this.pagingYn = pagingYn;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public String getStartDt() {
		if(StringUtil.isNotEmpty(startDt)){
			return startDt.replaceAll("\\D", "");
		}
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		if(StringUtil.isNotEmpty(getStartDt())){
//			종료날짜가 없으면 시작날짜로 설정한다.
			if(StringUtil.isEmpty(endDt)){
				return getStartDt();
			}
			int sInt = Integer.parseInt(getStartDt());
			int eInt = Integer.parseInt(endDt.replaceAll("\\D", ""));
//			시작날짜가 종료날짜보다 크면 시작날짜로 설정한다.
			if(sInt>eInt){
				return getStartDt();
			}
		}
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}


	public int getRowPerPage() {
		/*return rowPerPage==0?StringUtil.parseInt(PropUtil.getValue("common.rowPerPage")):rowPerPage;*/
		return rowPerPage==0?StringUtil.parseInt(10):rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	public int getPagePerPage() {
		/*return pagePerPage==0?StringUtil.parseInt(PropUtil.getValue("common.pagePerPage")):pagePerPage;*/
		return pagePerPage==0?StringUtil.parseInt(10):pagePerPage;
	}
	public void setPagePerPage(int pagePerPage) {
		this.pagePerPage = pagePerPage;
	}
	/**
	 * <strong>title</strong> : describe  <BR/>
	 * <strong>description</strong> : 빈즈를 Map으로 변환한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 10.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map describe(){
		try {
			return BeanUtils.describe(this);
		} catch (IllegalAccessException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
}
