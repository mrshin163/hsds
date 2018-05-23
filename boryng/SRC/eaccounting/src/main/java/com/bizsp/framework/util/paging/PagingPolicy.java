package com.bizsp.framework.util.paging;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.bizsp.framework.util.exception.BaseException;
import com.bizsp.framework.util.system.PropUtil;
import com.bizsp.framework.util.web.ContextHolder;
import com.bizsp.framework.util.web.XmlUtil;


public class PagingPolicy {

	private static PagingPolicy instance;

	private Map<String,PagingPolicyModel> policyMap;

	public Map<String, PagingPolicyModel> getPolicyMap() {
		return policyMap;
	}

	public void setPolicyMap(Map<String, PagingPolicyModel> policyMap) {
		this.policyMap = policyMap;
	}

	/**
	 * 페이징정책을 반환한다.
	 * @return
	 */
	public static PagingPolicy getInstance(){
		if(instance==null){
			instance = new PagingPolicy();
			instance.setPolicyMap(instance.getPolicyModelMap());
		}
		return instance;
	}


	@SuppressWarnings("unchecked")
	public Map<String,PagingPolicyModel> getPolicyModelMap(){
		
		
		
		
		String policyXmlPath = PropUtil.getValue("common.paging.policy.path");
		
		
		
//		System.out.println("policyXmlPath :::::: " + policyXmlPath);
		Map<String,PagingPolicyModel> map = new HashMap<String,PagingPolicyModel>();
		
		try{
			org.springframework.core.io.Resource resource = ContextHolder.getContext().getResource(policyXmlPath);
			if (resource == null) {
				throw new BaseException("Exception Code :: UPLOAD PAGING POLICY XML");
			}
			File xmlFile = resource.getFile();
			// XML 문서를 가지고 온다.
			Document doc = XmlUtil.getDocument(xmlFile);
			Element root = doc.getRootElement();
			List<Element> itemList = root.getChildren();
			for (Element attrbElement : itemList) {
				String policyName = attrbElement.getAttributeValue("name");
				String type = attrbElement.getAttributeValue("type");
				PagingPolicyModel policyModel = new PagingPolicyModel();
				policyModel.setType(type);
				policyModel.setPrefix(attrbElement.getChildTextTrim("prefix"));
				policyModel.setSuffix(attrbElement.getChildTextTrim("suffix"));
				policyModel.setPre(attrbElement.getChildTextTrim("pre"));
				policyModel.setNext(attrbElement.getChildTextTrim("next"));
				policyModel.setFirst(attrbElement.getChildTextTrim("first"));
				policyModel.setLast(attrbElement.getChildTextTrim("last"));
				policyModel.setPageNo(attrbElement.getChildTextTrim("pageNo"));
				policyModel.setSelected(attrbElement.getChildTextTrim("selected"));
				map.put(policyName, policyModel);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
