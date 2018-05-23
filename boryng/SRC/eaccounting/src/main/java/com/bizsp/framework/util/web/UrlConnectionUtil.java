package com.bizsp.framework.util.web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlConnectionUtil {
	private static Logger logger = LoggerFactory.getLogger(UrlConnectionUtil.class);

	/**
	 * @param pm_sUrl
	 * @param pm_sParam
	 * @param method
	 * @return
	 */
	public static InputStream requestByPost(String pm_sUrl, String pm_sParam, String method) {
		//			StringBuilder lm_oResult = new StringBuilder(); //응답받은 문자열
		String lm_sParam = pm_sParam;                   //요청할 파라미터

		//URL이 null이면 공백문자열을 리턴한다.
		if(pm_sUrl == null) {
			return null;
		}

		if(pm_sParam == null) {
			lm_sParam = "";
		}

		HttpURLConnection lm_oURLConnection = null;
		InputStream in = null;
		PrintWriter lm_oPrintWriter = null;

		try{
			//해당 URL로 요청을 한다.
			URL lm_oUrl = new URL(pm_sUrl);
			lm_oURLConnection = (HttpURLConnection)lm_oUrl.openConnection();
			lm_oURLConnection.setRequestMethod(method);
			lm_oURLConnection.setDoOutput(true);
			lm_oURLConnection.setDoInput(true);
			lm_oURLConnection.setUseCaches(false);
			lm_oURLConnection.setDefaultUseCaches(false);
			lm_oURLConnection.setConnectTimeout(2000);
			lm_oURLConnection.setReadTimeout(2000);
			lm_oPrintWriter = new PrintWriter(lm_oURLConnection.getOutputStream());
			lm_oPrintWriter.println(lm_sParam);
			lm_oPrintWriter.close();
			int lm_sResponseCode = lm_oURLConnection.getResponseCode();
			if(lm_sResponseCode == 200) {
				in = lm_oURLConnection.getInputStream();
			}

		} catch(Exception e) {
			logger.error("ERROR :\n", e);
		} finally {
			if(lm_oPrintWriter != null) {
				try{lm_oPrintWriter.close();}catch(Exception e){
					e.getMessage();
				}
			}
		}
		return in;
	}

	/**
	 * URL과 파라미터를 넘겨받아 해당 URL로 POST방식 http 요청 후 응답결과를 반환하는 메소드.
	 *
	 * @param String pm_sUrl   : 호출할 URL
	 * @param String pm_sParam : 요청할 파라미터
	 * @return String          : 응답받은 문자열
	 */
	public static String requestByString(String pm_sUrl, String pm_sParam,String method,String charset) {

		StringBuffer lm_oResult = new StringBuffer(); //응답받은 문자열
		//		   	String lm_sParam = pm_sParam;                   //요청할 파라미터
		//URL이 null이면 공백문자열을 리턴한다.
		if(pm_sUrl == null) {
			return "";
		}

		//	    	if(pm_sParam == null) {
		//	    		lm_sParam = "";
		//	    	}


		BufferedReader lm_oReader = null;
		InputStream in = null;

		try {
			in = requestByPost(pm_sUrl, pm_sParam,method);
			if(in != null){
				lm_oReader = new BufferedReader(new InputStreamReader(in,charset));
				String lm_sReadLine = null;
				while((lm_sReadLine = lm_oReader.readLine()) != null) {
					lm_oResult.append(lm_sReadLine);
				}
				lm_oReader.close();
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
		} finally {
			if(lm_oReader != null) {
				try{lm_oReader.close();}catch(Exception e){e.getMessage();}
			}
			if(in != null) {
				try{in.close();}catch(Exception e){ e.getMessage(); }
			}
		}
		return lm_oResult.toString();
	}


	/**
	 *
	 * @param ip
	 * @param port
	 * @param packet
	 * @param timeOut
	 * @return {@link InputStream}
	 */
	public static InputStream getSocketToInputStream(String ip, int port, String packet, int timeOut){
		Socket socket = null;
		DataOutputStream 	dout = null;
		DataInputStream		din = null;

		String result	= "";

		int size = 4096;
		byte buf[] = new byte[size];

		try {
			socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(ip, port);
			socket.connect(socketAddress, timeOut);
			socket.setSoTimeout(timeOut);

			dout = new DataOutputStream(socket.getOutputStream());
			din	= new DataInputStream(socket.getInputStream());

			dout.writeBytes(packet);

			dout.flush();
			din.read(buf);

		} catch (IOException e) {
			logger.debug("에러가 발생하였습니다.{}" , "ip : " + ip + " / port :" + port + " / packet : " +  packet);
			logger.debug("e : {}",e);
		} finally {
			if(dout != null){
				try {
					dout.close();
				} catch (IOException e) {
					logger.debug("에러가 발생하였습니다.{}" , "ip : " + ip + " / port :" + port + " / packet : " +  packet);
					logger.debug("e : {}",e);
				}
			}
			if(din != null){
				try {
					din.close();
				} catch (IOException e) {
					logger.debug("에러가 발생하였습니다.{}" , "ip : " + ip + " / port :" + port + " / packet : " +  packet);
					logger.debug("e : {}",e);
				}
			}

			if(socket != null){
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						logger.debug("에러가 발생하였습니다.{}" , "ip : " + ip + " / port :" + port + " / packet : " +  packet);
						logger.debug("e : {}",e);
					}
				}
			}

		}
		return din;
	}

	/**
	 * 소켓에 접속하여 결과값을 리턴한다.
	 * @param ip
	 * @param port
	 * @param packet
	 * @param timeOut
	 * @return {@link String}
	 */
	public static String getSocketToString(String ip, int port, String packet, int timeOut){

		DataInputStream		din = null;
		String result	= "";

		try {
			din = (DataInputStream) getSocketToInputStream(ip, port, packet, timeOut);
			result = din.toString();
		} finally {
			if(din != null){
				try {
					din.close();
				} catch (IOException e) {
					logger.debug("에러가 발생하였습니다.{}" , "ip : " + ip + " / port :" + port + " / packet : " +  packet);
					logger.debug("e : {}",e);
				}
			}

		}
		return result;
	}

	/**
	 * URL을 호출한다.
	 * @param address
	 * @return
	 */
	public static String callUrl(String address){
		HttpURLConnection con = null;
		URL url = null;
		String html = "";
		try {
			url = new URL(address);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine = "";
			while((inputLine = br.readLine()) != null){
				html+=inputLine;
			}//while
		}catch(Exception e){
			return "";
		}
		return html;
	}
}
