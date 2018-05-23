package com.bizsp.framework.util.paging;

import com.bizsp.framework.util.model.BaseModel;



/**
 * @author devkim
 *
 */
public class PagingPolicyModel extends BaseModel {
	private static final long serialVersionUID = 2288986570582487016L;

	private String type;

	/**
	 * 페이징 랩퍼시작.
	 */
	private String prefix;
	/**
	 * 페이징 랩퍼끝.
	 */
	private String suffix;
	/**
	 * 페이징처음으로.
	 */
	private String first;
	/**
	 * 페이징마지막.
	 */
	private String last;
	/**
	 * 페이징이전.
	 */
	private String pre;
	/**
	 * 페이징다음.
	 */
	private String next;
	/**
	 * 페이징표시.
	 */
	private String pageNo;
	/**
	 * 현재페이지.
	 */
	private String selected;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getPre() {
		return pre;
	}
	public void setPre(String pre) {
		this.pre = pre;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}


}
