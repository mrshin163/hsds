package com.bizsp.framework.util.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {

	private static Log log = LogFactory.getLog(XmlUtil.class);

	/**
	 * InputStream 을 받아서 <br>
	 * jdom 의Document 객체를 return 한다 .
	 * @param in
	 * @return document
	 */
	public static Document getDocument(InputStream in){
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc =  builder.build(in);
		} catch (JDOMException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return doc;
	}

	/**
	 * InputStream 을 받아서 <br>
	 * jdom 의Document 객체를 return 한다 .
	 * @param in
	 * @return document
	 */
	public static Document getDocument(InputStream in, String encoding){
		Document doc = null;
		InputSource source = new InputSource(in);
		source.setEncoding(encoding);
		SAXBuilder builder = new SAXBuilder();
		try {
			doc =  builder.build(in);
		} catch (JDOMException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return doc;
	}

	/*
	 * 파일을 받아서
	 */
	public static Document getDocument(File file){
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc =  builder.build(file);
		} catch (JDOMException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		}
		return doc;
	}

	/*
	 * reader 를 받아서
	 */
	public static Document getDocument(Reader reader){
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc =  builder.build(reader);

		} catch (JDOMException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error(e);
			}
		} finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return doc;
	}



	/**
	 * element list 를 받아서 각각의 element 이름과 element item 을 return 한다 .<br>
	 *
	 * @param list
	 * @param map
	 * @return Map
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getTotalElement(List list,Map<String, String> map){

		for(int i = 0 ; i < list.size() ; i++){
			if( (( (Element) list.get(i) ).getChildren()).isEmpty() ){
				map.put( ((Element)list.get(i)).getName() , ((Element)list.get(i)).getTextTrim() );
			}else{
				getTotalElement(((Element)list.get(i)).getChildren(),map);
			}
		}
		return map;
	}

	public static String toXml(Object obj){
		StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n ");
		String result = null;
		XStream xstream = new XStream();

		xstream.alias(obj.getClass().getName().toLowerCase(), obj.getClass());
		builder.append(xstream.toXML(obj));

		try {
			result = new String(builder.toString().getBytes(), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			log.error("{}" , e);
		}
		return result;
//		return builder.toString();
	}

}
