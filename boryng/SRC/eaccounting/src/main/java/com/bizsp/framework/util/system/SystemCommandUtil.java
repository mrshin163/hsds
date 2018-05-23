package com.bizsp.framework.util.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * OS 쉘스크립트를 실행한다.
 */
public class SystemCommandUtil {

	/**
	 *  do not instance!!
	 */
	private SystemCommandUtil() {

	}

	/**
	 * OS 의 명령어를 실행 합니다.<br>
	 * @param cmd
	 * @throws IOException
	 */
	public static void command(String cmd) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process process  = runtime.exec(cmd);
	}

	/**
	 * OS 의 명령어를 실행 합니다.<br>
	 * @param cmd
	 * @return process
	 * @throws IOException
	 */
	public static String commandToResult(String cmd) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;

		process = runtime.exec(cmd);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		StringBuffer result=new StringBuffer();

		while((line=br.readLine())!=null){
			result.append(line);
		}

		if(!result.toString().equals("success")) {
			is = process.getErrorStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			result=new StringBuffer();

			while((line=br.readLine())!=null){
				result.append(line);
			}
		}

		return result.toString();
	}
}
