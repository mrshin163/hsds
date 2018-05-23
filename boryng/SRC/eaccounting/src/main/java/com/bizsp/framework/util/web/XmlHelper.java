package com.bizsp.framework.util.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;



/**
 *  XML 형식의 웹문서를 파싱한다.
 * @author <a href='devkim@devkim.co.kr'><b>KIM JIN HYUG</b></a>
 * @version XmlHelper.java - 2009. 5. 25
 * @project childFund-admin
 */
public class XmlHelper {


	/**
	 * @param argUrl : xml URL
	 * @param parent : 상위로드 레벨이름 Ex> String parent[]={"channel","item"};
	 * @return
	 * @date 2009. 5. 25
	 * @author KIM JIN HYUG
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getXMLData(String argUrl,String parent[]){
		 try {
			 URL url = new URL(argUrl);
			 HttpURLConnection con = (HttpURLConnection) url.openConnection();
		      InputStream is = con.getInputStream();
		      Document doc = readXmlFile(is);
		      Element element = doc.getRootElement();
		      List<Element> li=new ArrayList<Element>();
		      int size=parent.length;
		      if(parent.length!=0){
			      for(int i=0;i<size;i++){
			    	  if(i==size-1){
			    		  li=element.getChildren(parent[i]);
			    	  }else{
			    		  element=element.getChild(parent[i]);
			    	  }
			      }
		      }else{
		    	  li = element.getChildren();
		      }
		      Map<String,Object> mapTmp=new HashMap<String,Object>();
		      for (Element tmpE : li) {
		    	  String name=tmpE.getName();
		    	  if(tmpE.getChildren().size()>0){
		    		  List<Map<String,String>> tmp = null;
		    		  if(mapTmp.get(name)!=null){
		    			  tmp = (List<Map<String,String>>)mapTmp.get(name);
		    		  }else{
		    			  tmp = new ArrayList<Map<String,String>>();
		    		  }
		    		  mapTmp.put(name, convertEleToList(tmp,tmpE));
		    	  }else{
		    		  String val = tmpE.getText();
		    		  mapTmp.put(name, val);
		    	  }
		      }
		      return mapTmp;
		}catch (JDOMException jdoe) {
//		      System.out.println(jdoe);
		      return null;
	    }
	    catch (IOException ie) {
//	      System.out.println(ie);
	      return null;
	    }
	 }

	@SuppressWarnings("unchecked")
	public static Map<String,Object> getXMLDataForFile(String filePath,String parent[]){
		try{
			File f = new File(filePath);
			FileInputStream fis = new FileInputStream(f);
		    Document doc = readXmlFile(fis);
		    Element element = doc.getRootElement();
		    List<Element> li=new ArrayList<Element>();
		    int size=parent.length;
	    	if(parent.length!=0){
	    		for(int i=0;i<size;i++){
	    			if(i==size-1){
	    				li=element.getChildren(parent[i]);
	    			}else{
	    				element=element.getChild(parent[i]);
	    			}
	    		}
	    	}else{
	    		li = element.getChildren();
	    	}
	    	Map<String,Object> mapTmp=new HashMap<String,Object>();
	    	for (Element tmpE : li) {
	    		String name=tmpE.getName();
	    		if(tmpE.getChildren().size()>0){
	    			List<Map<String,String>> tmp = null;
	    			if(mapTmp.get(name)!=null){
	    				tmp = (List<Map<String,String>>)mapTmp.get(name);
	    			}else{
	    				tmp = new ArrayList<Map<String,String>>();
		    		}
	    			mapTmp.put(name, convertEleToList(tmp,tmpE));
	    		}else{
	    			String val = tmpE.getText();
	    			mapTmp.put(name, val);
	    		}
	    	}
	    	return mapTmp;
		}catch (JDOMException jdoe) {
//			System.out.println(jdoe);
			return null;
	    }
	    catch (IOException ie) {
//	      System.out.println(ie);
	      return null;
	    }
	}

//	public static void main(String args[]){
//		String node[] = new String[]{} ;
//		XmlHelper.getXMLDataForFile("E:\\source\\paran.com\\randPud.xml", node);
//	}


	 /**
	 * <strong>description</strong> : Element에 자식이 있을경우 리스트를 반환한다. <BR/>
	 * @param parent
	 * @param ele
	 * @return <BR/>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> convertEleToList(List<Map<String,String>> parent, Element ele)
	{
		List<Element> li=new ArrayList<Element>();
		li = ele.getChildren();
		Map<String,String> mapTmp=new HashMap<String,String>();
		for (Element tmpE : li) {
	    	  String name=tmpE.getName();
	    	  mapTmp.put(name, tmpE.getText());
	      }
		parent.add(mapTmp);
		return parent;

	}


//	public static void main(String args[]){
//		String url = "http://search10.paran.com/support/pudding/pudding.php?Query=&pg=1&list_cnt=2&listby=BoxSearch&userno=620000152845";
//		String node[] = new String[]{} ;
//		Map<String,Object> tmp = XmlHelper.getXMLData(url, node);
//		SearchInfo beans = new SearchInfo();
//		try {
//			BeanUtils.populate(beans, tmp);
//			List<Map<String,String>> tmpaa = beans.getItem();
//			for(Map<String,String> tt : tmpaa){
//				System.out.println(tt.get("DOCID"));
//			}
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.print(">>");
//	}


	/**
	 *
	    JAXBContext context = JAXBContext.newInstance(Vct.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal( vct, response.getWriter());

	 * XML을 읽어 온다.
	 * @param file
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @date 2009. 5. 25
	 * @author KIM JIN HYUG
	 */
	private static Document readXmlFile(InputStream file) throws JDOMException,  IOException {
		SAXBuilder builder = new SAXBuilder();
	    Document doc = builder.build(file);
	    return doc;
	 }

	@SuppressWarnings("unchecked")
	public static void createXMLToObject(HttpServletResponse res,Object obj) throws Exception{
		try{
			Document doc = new Document();
			Element listData = new Element("LIST");
			if(obj instanceof ArrayList){
				List<Object> list = (ArrayList<Object>)obj;
				for(Object tmpObj : list){
					Element data = getElement(tmpObj);
					listData.addContent(data);
				}
			}else{
				if(obj!=null){
					Element data = getElement(obj);
					listData.addContent(data);
				}
			}
			doc.addContent(listData);
			XMLOutputter outputter = new XMLOutputter();
			res.setContentType("text/xml");
            res.setCharacterEncoding("UTF-8");
		    outputter.output(doc,res.getOutputStream() );
		}catch(Exception e){}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Element getElement(Object beans) throws Exception{
		try{
			Map beansMap = new HashMap();
			if(beans instanceof HashMap){
				beansMap = (HashMap)beans;
			}else{
				beansMap = BeanUtils.describe(beans);
			}
			Set<String> set = beansMap.keySet();
			Iterator<String> iter = set.iterator();
			Element data = new Element("DATA");
			while(iter.hasNext()){
				String key = iter.next();
				Object val = beansMap.get(key);
				if(key.equals("class")) continue;
				Element element = new Element(key);
				if(val instanceof ArrayList){
					List<Object> list = (ArrayList<Object>)val;
					for(Object tmpObj : list){
						element = getElementByBeans(element, tmpObj);
					}
				}else if(val instanceof String[]){

				}else if(val instanceof HashMap){

				}else{
					element.setText(String.valueOf(val));
				}
				data.addContent(element);
			}
			return data;
		}catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Element getElementByBeans(Element root, Object beans) throws Exception{
		try{
			Map beansMap = BeanUtils.describe(beans);
			Set<String> set = beansMap.keySet();
			Iterator<String> iter = set.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				Object val = beansMap.get(key);
				if(key.equals("class")) continue;
				Element element = new Element(key);
				element.setText(String.valueOf(val));
				root.addContent(element);
			}

			return root;
		}catch(Exception e){
			return root;
		}
	}
}