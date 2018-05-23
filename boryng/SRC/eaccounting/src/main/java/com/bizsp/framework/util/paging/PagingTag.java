/**
 *
 */
package com.bizsp.framework.util.paging;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.system.PropUtil;

public class PagingTag  extends TagSupport{
	private static final long serialVersionUID = -1400550117230970506L;
	/**
	 * 스크립트.
	 */
	private String script;
	/**
	 * 페이징정책명.
	 */
	private String policyName;

	@SuppressWarnings("rawtypes")
	private List list;

	/**
	 * 목록총 갯수.
	 * @param pagingList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static long getTotalRow(List pagingList){
		if(pagingList instanceof PagingList){
			PagingList pageList = (PagingList)pagingList;
			return pageList.getTotalRow();
		}else{
			return pagingList.size();
		}
	}

	/**
	 * 목록번호
	 * @param pagingList
	 * @param idx
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static long getIdx(List pagingList,int idx){
		if(pagingList instanceof PagingList){
			PagingList pageList = (PagingList)pagingList;
			return pageList.getIndexNo()-idx;
		}else{
			return pagingList.size()-idx;
		}
	}

	@Override
	public int doStartTag() throws JspException{
		PagingPolicyModel model = PagingPolicy.getInstance().getPolicyMap().get(getPolicyName());
		if(model==null){
			try {
				pageContext.getOut().print("");
			}catch (IOException e) {
				throw new JspException();
			}
			return SKIP_BODY;
		}
		if(list instanceof PagingList){
			PagingList pageList = (PagingList)list;
			PagingInfo pagingInfo = pageList.getPagingInfo();
			StringBuffer sb = new StringBuffer("");
			if(pagingInfo.getTotalRow()!=0
					&& pagingInfo.getRowPerPage()!=0){
//				이전다음 버튼일 경우.
				if(StringUtil.isEquals(model.getType(),"twoButton")){
					try {
//						prefix
						if(StringUtil.isNotEmpty(model.getPrefix())){
							sb.append(convertScript(model.getPrefix(),0));
						}
						//pre 이전페이지.
						if(StringUtil.isNotEmpty(model.getPre())
								&& pagingInfo.getPre()!=pagingInfo.getPageNo()
						){
							sb.append(convertScript(model.getPre(),pagingInfo.getPre()));
						}
//						next
						if(StringUtil.isNotEmpty(model.getNext())
								&& pagingInfo.getNext()!=pagingInfo.getPageNo()
						){
							sb.append(convertScript(model.getNext(),pagingInfo.getNext()));
						}
//						suffix
						if(StringUtil.isNotEmpty(model.getSuffix())){
							sb.append(convertScript(model.getSuffix(),0));
						}
						pageContext.getOut().print(sb.toString());
					}catch (IOException e) {
						throw new JspException();
					}
					return SKIP_BODY;
				}

				//				prefix
				if(StringUtil.isNotEmpty(model.getPrefix())){
					sb.append(convertScript(model.getPrefix(),0));
				}
				//				first
				if(StringUtil.isNotEmpty(model.getFirst())){
					sb.append(convertScript(model.getFirst(),1));
				}
				//pre 이전페이지.
				if(StringUtil.isNotEmpty(model.getPre())){
					sb.append(convertScript(model.getPre(),pagingInfo.getPrePage()));
				}

				for(int i:pagingInfo.getPageNumCol()){
					if(pagingInfo.getPageNo()==i){
						if(StringUtil.isNotEmpty(model.getSelected())){
							sb.append(convertScript(model.getSelected(), i));
						}
						continue;
					}
					if(StringUtil.isNotEmpty(model.getPageNo())){
						sb.append(convertScript(model.getPageNo(), i));
					}
				}

				//				next
				if(StringUtil.isNotEmpty(model.getNext())){
					sb.append(convertScript(model.getNext(),pagingInfo.getNextPage()));
				}
				//				last
				if(StringUtil.isNotEmpty(model.getLast())){
					sb.append(convertScript(model.getLast(),pagingInfo.getLastPage()));
				}
				//				suffix
				if(StringUtil.isNotEmpty(model.getSuffix())){
					sb.append(convertScript(model.getSuffix(),0));
				}
			}
			try {
				pageContext.getOut().print(sb.toString());
			}catch (IOException e) {
				throw new JspException();
			}
		}
		return SKIP_BODY;
	}

	public String convertScript(String tag,int page){
		if(StringUtil.isEmpty(tag)){
			return "";
		}
		if(tag.indexOf("#script#")>=0){
			tag = StringUtil.change(tag, "#script#", getScript()+"('"+page+"')");
		}
		if(tag.indexOf("#pageNo#")>=0){
			tag = StringUtil.change(tag, "#pageNo#", String.valueOf(page));
		}
		return tag;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

	public String getScript() {
		return StringUtil.nvl(script,PropUtil.getValue("common.paging.script"));
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getPolicyName() {
		return StringUtil.nvl(policyName,PropUtil.getValue("common.paging.policy.name"));
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

}
