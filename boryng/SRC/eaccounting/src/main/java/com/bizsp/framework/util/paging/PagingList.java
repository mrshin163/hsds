package com.bizsp.framework.util.paging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.model.BaseCndModel;
import com.bizsp.framework.util.system.PropUtil;


@SuppressWarnings({ "serial", "rawtypes" })
public class PagingList extends ArrayList
{

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(getClass());
	private int blockFirstPage;			//페이지 시작페이지..번호
	private int blockLastPage;			//페이지 끝페이지 번호.
	private int pageNo;			//현재 페이지 번호..
	private int nextPage;				//다음 페이지 번호..
	private int prePage;				//이전 페이지 번호..
	private int lastPage;				//마지막 페이지 번호..
	private int rowPerPage;				//한페이지에 보여지는 줄수..
	private int pagePerPage;			//페이지 번호 갯수..
	private long totalRow;				//총 데이타수..
	@SuppressWarnings("unused")
	private int indexNo;				//게시번호..

	private int next;					//다음
	private int pre;					//이전

	private PagingInfo pagingInfo;

	private Collection pageNumCol;



	@SuppressWarnings("unchecked")
	public PagingInfo getPagingInfo() {
		pagingInfo = new PagingInfo();
		pagingInfo.setBlockFirstPage(getBlockFirstPage());
		pagingInfo.setBlockLastPage(getBlockLastPage());
		pagingInfo.setPageNo(getPageNo());
		pagingInfo.setNextPage(getNextPage());
		pagingInfo.setPrePage(getPrePage());
		pagingInfo.setLastPage(getLastPage());
		pagingInfo.setRowPerPage(getRowPerPage());
		pagingInfo.setPagePerPage(getPagePerPage());
		pagingInfo.setTotalRow(getTotalRow());
		pagingInfo.setIndexNo(getIndexNo());
		pagingInfo.setNext(getNext());
		pagingInfo.setPre(getPre());
		pagingInfo.setPageNumCol(pageNumCol);
		return pagingInfo;
	}
	public PagingList(){
		this(new ArrayList(),0,0,-1);
	}

	@SuppressWarnings("unchecked")
	public PagingList(List dataList){
		addAll(dataList);
	}

	/**
	 * 검색모델을 받아 페이징을 생성한다.
	 * @param dataList
	 * @param cndModel
	 */
	public PagingList(List dataList,BaseCndModel cndModel){
		this(dataList
				, cndModel.getTotalRow()
				, cndModel.getPageNo()
				, cndModel.getRowPerPage()
				, cndModel.getPagePerPage()
				);
	}
	

	/**
	 * 페이지당 보여지는 ROW수를 따로 지정안하고 기본 값을 사용 한다.
	 * 데이타 LIST, 총 데이타 ROW , 시작ROW
	 * @param dataList
	 * @param totalRow
	 * @param beginRow
	 */
	public PagingList(List dataList, long totalRow, int currentPage)
	{
		this(dataList, totalRow, currentPage
				, StringUtil.parseInt(PropUtil.getValue("common.rowPerPage"))
				, StringUtil.parseInt(PropUtil.getValue("common.pagePerPage")));
	}

	public PagingList(List dataList, long totalRow, int currentPage, int rowPerPage)
	{
		this(dataList
				, totalRow
				, currentPage
				, rowPerPage
				, StringUtil.parseInt(PropUtil.getValue("common.pagePerPage"))
				);
	}

	/**
	 * 페이지당 보여지는 ROW수를 따로 지정한다.
	 * 데이타 LIST, 총 데이타 ROW , 시작ROW, 페이지당 보여지는 ROW수
	 * @param dataList
	 * @param totalRow
	 * @param beginRow
	 * @param rowPerPage
	 */
	@SuppressWarnings("unchecked")
	public PagingList(List dataList, long totalRow, int pageNo, int rowPerPage, int pagePerPage)
	{
		this.rowPerPage =rowPerPage;
		this.pagePerPage= pagePerPage;
		//        RowPerpage를 따로 지정할시 rowPerPage를 설정한다.
		if(rowPerPage > 0)
			this.rowPerPage = rowPerPage;
		this.totalRow = totalRow;
		this.pageNo=pageNo;
		//        dataList를 ListArray에 넣기...
		addAll(dataList);
		if(pageNo==0){
			this.pageNo=1;
		}else{
			this.pageNo=pageNo;
		}
		lastPage = (int)Math.ceil((double)getTotalRow() / (double)getRowPerPage());

		if(lastPage < this.pageNo)
			this.pageNo = lastPage;

		//        이전 페이지 구하기..
		int preTmp=(this.pageNo/pagePerPage);
		if( (this.pageNo % pagePerPage)==0){
			preTmp=preTmp-1;
		}
		if(preTmp==0){
			prePage=1;
		}else{
			prePage=(preTmp*pagePerPage);
		}
		if(prePage<1){
			prePage=0;
		}
		//        다음 페이지 구하기.
		nextPage=(preTmp+1)*pagePerPage+1;
		if(nextPage>lastPage){
			nextPage=lastPage;
		}

		//        페이지 시작 페이지 번호..
		blockFirstPage = preTmp*pagePerPage+1;
		blockLastPage = (preTmp+1)*pagePerPage;

		if(blockLastPage>lastPage){
			blockLastPage=lastPage;
		}

		pageNumCol = new ArrayList();
		if(this.pageNo>0){
			for(int pageNum = blockFirstPage; blockLastPage >= pageNum; pageNum++)
				pageNumCol.add(new Integer(pageNum));
		}
		pre = pageNo-1;
		next = pageNo+1;
		if(pageNo<=1) pre = 1;
		if(pageNo>=lastPage) next = lastPage;
	}

	public long getIndexNo()
	{
		return totalRow - (pageNo - 1) * rowPerPage;
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
	public String getTotal(){
		return StringUtil.format(totalRow);
	}
	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}
	public Collection getPageNumCol() {
		return pageNumCol;
	}
	public void setPageNumCol(Collection pageNumCol) {
		this.pageNumCol = pageNumCol;
	}
	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}

	public int getNext() {
		return next;
	}
	public int getPre() {
		return pre;
	}
}