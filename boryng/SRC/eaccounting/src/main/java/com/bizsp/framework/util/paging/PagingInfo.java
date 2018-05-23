package com.bizsp.framework.util.paging;

import java.util.Collection;

import com.bizsp.framework.util.model.BaseModel;



public class PagingInfo extends BaseModel{
	
	private static final long serialVersionUID = -4587388408941473823L;
	
	private int blockFirstPage;			//페이지 시작페이지..번호
	private int blockLastPage;			//페이지 끝페이지 번호.
	private int pageNo;			//현재 페이지 번호..
	private int nextPage;				//다음 페이지 번호..
	private int prePage;				//이전 페이지 번호..
	private int lastPage;				//마지막 페이지 번호..
	private int rowPerPage;				//한페이지에 보여지는 줄수..
	private int pagePerPage;			//페이지 번호 갯수..
	private long totalRow;				//총 데이타수..
	private long indexNo;				//게시번호..
	private int next;					//다음
	private int pre;					//이전

	private Collection<Integer> pageNumCol;


	public Collection<Integer> getPageNumCol() {
		return pageNumCol;
	}
	public void setPageNumCol(Collection<Integer> pageNumCol) {
		this.pageNumCol = pageNumCol;
	}
	public int getBlockFirstPage() {
		return blockFirstPage;
	}
	public void setBlockFirstPage(int blockFirstPage) {
		this.blockFirstPage = blockFirstPage;
	}
	public int getBlockLastPage() {
		return blockLastPage;
	}
	public void setBlockLastPage(int blockLastPage) {
		this.blockLastPage = blockLastPage;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	public int getPagePerPage() {
		return pagePerPage;
	}
	public void setPagePerPage(int pagePerPage) {
		this.pagePerPage = pagePerPage;
	}
	public long getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}
	public long getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(long indexNo) {
		this.indexNo = indexNo;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getPre() {
		return pre;
	}
	public void setPre(int pre) {
		this.pre = pre;
	}

}
