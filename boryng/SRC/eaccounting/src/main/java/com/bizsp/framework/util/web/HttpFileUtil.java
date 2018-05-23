package com.bizsp.framework.util.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFileUtil {

	private static Logger log = LoggerFactory.getLogger(HttpFileUtil.class);

	/**
	 * 연결할 URL
	 */
	private final URL targetURL;
	public static final String CRLF = "\r\n";

	/**
	 * 파라미터 목록을 저장하고 있다.
	 * 파라미터 이름과 값이 차례대로 저장된다.
	 */
	@SuppressWarnings("rawtypes")
	private final ArrayList list;

	public HttpFileUtil(URL target) {
		this(target, 20);
	}

	/**
	 * HttpRequest를 생성한다.
	 *
	 * @param target HTTP 메시지를 전송할 대상 URL
	 */
	@SuppressWarnings("rawtypes")
	public HttpFileUtil(URL target, int initialCapicity) {
		this.targetURL = target;
		this.list = new ArrayList(initialCapicity);
	}
	@SuppressWarnings("unchecked")
	public void addParameter(String parameterName, String parameterValue) {
		if (parameterValue == null)
			throw new IllegalArgumentException("parameterValue can't be null!");

		list.add(parameterName);
		list.add(parameterValue);
	}

	public void addFile(String parameterName, File parameterValue) {
		// paramterValue가 null일 경우 NullFile을 삽입한다.
		if (parameterValue == null) {
			list.add(parameterName);
			list.add(new NullFile());
		} else {
			list.add(parameterName);
			list.add(parameterValue);
		}
	}
	public InputStream sendMultipartPost() throws IOException {
		HttpURLConnection conn = (HttpURLConnection)targetURL.openConnection();

		// Delimeter 생성
		String delimeter = makeDelimeter();

		byte[] newLineBytes = CRLF.getBytes();
		byte[] delimeterBytes = delimeter.getBytes();
		byte[] dispositionBytes = "Content-Disposition: form-data; name=".getBytes();
		byte[] quotationBytes = "\"".getBytes();
		byte[] contentTypeBytes = "Content-Type: application/octet-stream".getBytes();
		byte[] fileNameBytes = "; filename=".getBytes();
		byte[] twoDashBytes = "--".getBytes();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary="+delimeter);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);

		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(conn.getOutputStream());

			Object[] obj = new Object[list.size()];
			list.toArray(obj);

			for (int i = 0 ; i < obj.length ; i += 2) {
				// Delimeter 전송
				out.write(twoDashBytes);
				out.write(delimeterBytes);
				out.write(newLineBytes);
				// 파라미터 이름 출력
				out.write(dispositionBytes);
				out.write(quotationBytes);
				out.write( ((String)obj[i]).getBytes() );
				out.write(quotationBytes);
				if ( obj[i+1] instanceof String) {
					// String 이라면
					out.write(newLineBytes);
					out.write(newLineBytes);
					// 값 출력
					out.write( ((String)obj[i+1]).getBytes() );
					out.write(newLineBytes);
				} else {
					// 파라미터의 값이 File 이나 NullFile인 경우
					if ( obj[i+1] instanceof File) {
						File file = (File)obj[i+1];
						// File이 존재하는 지 검사한다.
						out.write(fileNameBytes);
						out.write(quotationBytes);
						out.write(file.getAbsolutePath().getBytes() );
						out.write(quotationBytes);
					} else {
						// NullFile 인 경우
						out.write(fileNameBytes);
						out.write(quotationBytes);
						out.write(quotationBytes);
					}
					out.write(newLineBytes);
					out.write(contentTypeBytes);
					out.write(newLineBytes);
					out.write(newLineBytes);
					// File 데이터를 전송한다.
					if (obj[i+1] instanceof File) {
						File file = (File)obj[i+1];
						// file에 있는 내용을 전송한다.
						BufferedInputStream is = null;
						try {
							is = new BufferedInputStream(
									new FileInputStream(file));
							byte[] fileBuffer = new byte[1024 * 8]; // 8k
							int len = -1;
							while ( (len = is.read(fileBuffer)) != -1) {
								out.write(fileBuffer, 0, len);
							}
						} finally {
							if (is != null) try { is.close(); } catch(IOException ex) {}
						}
					}
					out.write(newLineBytes);
				} // 파일 데이터의 전송 블럭 끝
				if ( i + 2 == obj.length ) {
					// 마지막 Delimeter 전송
					out.write(twoDashBytes);
					out.write(delimeterBytes);
					out.write(twoDashBytes);
					out.write(newLineBytes);
				}
			} // for 루프의 끝

			out.flush();
		} finally {
			if (out != null) out.close();
		}
		return conn.getInputStream();
	}
	private static String makeDelimeter() {
		return "---------------------------7d115d2a20060c";
	}
	private class NullFile {
		NullFile() {
		}
		@Override
		public String toString() {
			return "";
		}
	}



}
