package com.bizsp.framework.util.lang;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * <strong>description</strong> : Apache org.apache.commons.lang.StringUtils 를 상속받아 추가 구현.  <BR/>
 */
public class StringUtil extends StringUtils{

	//	private static Log log = LogFactory.getLog("StringUtil.class");

	final static char[] CHAR_1ST = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
		0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
		0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

	final static char[] CHAR_2ND = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153,
		0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b,
		0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163 };

	final static char[] CHAR_3RD = { 0, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135,
		0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e,
		0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147,
		0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

	/**
	 * do not instance !!
	 */
	//	private StringUtil() {
	//
	//	}

    public static String nvl(Object o) {
        return ((o == null) ? "" : o.toString());
    }

    public static String nvl(Object o,String rtn) {
    	return ((o == null) ? rtn : o.toString());
    }

	/**
	 * null 이거나 공백만으로 이루어졌을 경우 true 반환 <br>
	 * 내부적으로 trim() method 로 공백 제거
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 공백이 아닌지 검사한다. <br>
	 * null 이거나 공백만으로 이루어졌을 경우 false 반환 <br>
	 * 내부적으로 trim() method 로 공백 제거
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * str + concatStr 연산을 시행 한다 <br>
	 * @param str
	 * @param concatStr
	 * @return
	 */
	public static String concat(String str, String concatStr) {
		if (isEmpty(str)) {
			return concatStr;
		}
		if (isEmpty(concatStr)) {
			return str;
		}
		return str.concat(concatStr);
	}

	/**
	 * Array 형식의 String 들을 다 더해서 반환 한다.<br>
	 * concat("일"," 하기 " , "싫어");<br>
	 * return "일하기싫어"<br>
	 * @param strs
	 * @return
	 */
	public static String concat(String... strs) {
		StringBuilder builder = new StringBuilder();
		if (strs != null) {
			for (String s : strs) {
				builder.append(s);
			}
		}
		return null;
	}

	/**
	 * 좌측 공백을 없앤다.
	 * @param str
	 * @return
	 */
	public static String ltrim(String str) {
		String result = "";
		if (str != null) {
			char ch[] = str.toCharArray();
			int size = ch.length;
			boolean isEmptyFirst = true;
			for (int i = 0; i < size; i++) {
				if (Character.isWhitespace(ch[i]) && isEmptyFirst) {
					;
				}
				else {
					result += ch[i];
					isEmptyFirst = false;
				}
			}
		}
		return result;
	}

	/**
	 * 공백을 없앤다.<br>
	 * null 일 경우 null 을 반환 <br>
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 공백을 없앤다. <br>
	 * null 일 경우 공백을 반환 <br>
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	/**
	 * str 원본에서 앞에 부분의 stripChars 를 추출해서 삭제 한다. <br>
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen)
					&& Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		}
		else if (stripChars.length() == 0) {
			return str;
		}
		else {
			while ((start != strLen)
					&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	/**
	 * str 원본에서 뒤에 부분의 stripChars 를 추출해서 삭제 한다. <br>
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		}
		else if (stripChars.length() == 0) {
			return str;
		}
		else {
			while ((end != 0)
					&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	/**
	 * str 원본에서 앞 뒤에 stripChars 를 추출해서 삭제 한다. <br>
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	/**
	 * str 과c comp 를 같은지 비교 한다. <br>
	 * @param str
	 * @param comp
	 * @return
	 */
	public static boolean equals(String str, String comp) {
		return str == null ? comp == null : str.equals(comp);
	}

	/**
	 * 대소문자를 구별을 제외 하고 String 이 같은지 비교 한다. <br>
	 * @param str
	 * @param comp
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str, String comp) {
		return str == null ? comp == null : str.equalsIgnoreCase(comp);
	}

	/**
	 * 앞에서 부터 String 의 위치 값을 반환 한다. <br>
	 * @param str
	 * @param search
	 * @return
	 */
	public static int indexOf(String str, String search) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(search);
	}

	/**
	 * 앞에서 부터  char 의 위치 값을 반환 한다. <br>
	 * @param str
	 * @param searchChar
	 * @return
	 */
	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	/**
	 * 뒤에서 부터 search 값의 위치를 알아 낸다. <br>
	 * @param str
	 * @param search
	 * @return
	 */
	public static int lastIndexOf(String str, String search) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(search);
	}

	/**
	 * 뒤에서 부터 searchChar 값의 위치를 알아 낸다. <br>
	 * @param str
	 * @param search
	 * @return
	 */
	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	/**
	 * str 에 searchStr 이 포함 되어 있는지 확인 한다. <br>
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * 대소문자를 구별 안하며 searchStr 값이 포함 되어 있는지 확인 <br>
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return contains(str.toUpperCase(), searchStr.toUpperCase());
	}

	/**
	 * str 을 start 값부터 자른다 <br>
	 * @param str
	 * @param start
	 * @return
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
	 * str 값을 start 부터 end 까지 자른다. <br>
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * String 을 separatorChars 로  분할 하여 String array 로 반환 <br>
	 * @param str
	 * @param separatorChars
	 * @param max
	 * @param preserveAllTokens
	 * @return
	 */
	private static String[] splitWorker(String str, String separatorChars,
			int max, boolean preserveAllTokens) {

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		else if (separatorChars.length() == 1) {
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		else {
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * String 을 separatorChar 로  분할 하여 String array 로 반환 <br>
	 * @param str
	 * @param separatorChar
	 * @param preserveAllTokens
	 * @return
	 */
	private static String[] splitWorker(String str, char separatorChar,
			boolean preserveAllTokens) {

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			}
			else {
				lastMatch = false;
			}
			match = true;
			i++;
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * String 을 separatorChar 로  분할 하여 String array 로 반환 <br>
	 * 모든 인자 값을 보존 한다.
	 * @param str
	 * @param separatorChar
	 * @return
	 */
	public static String[] splitPreserveAllTokens(String str, char separatorChar) {
		return splitWorker(str, separatorChar, true);
	}

	/**
	 * string 을 잘라서 String array 로 반환 <br>
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	/**
	 * 앞부분 의 str 에 remove 값을 삭제 한다.
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	/**
	 * 뒷부분 의 str 에 remove 값을 삭제 한다
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	/**
	 * str 에 remove 값을 삭제 한다
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String remove(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		return replace(str, remove, "", -1);
	}

	/**
	 * text 에 repl 값을 with 로 max 만큼 교체 한다. <br>
	 * @param text
	 * @param repl
	 * @param with
	 * @param max
	 * @return
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (isEmpty(text) || isEmpty(repl) || with == null || max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(repl, start);
		if (end == -1) {
			return text;
		}
		int replLength = repl.length();
		int increase = with.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(repl, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * text 에 repl 값을 with 로 전체  교체 한다. <br>
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * text 에 repl 값을 with 로 max 만큼 하나만 교체 한다. <br>
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replaceOnce(String text, String repl, String with) {
		return replace(text, repl, with, 1);
	}

	/**
	 * str 을 대문자로 변환 <br>
	 * @param str
	 * @return
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * str 을 소문자로 변환 <br>
	 * @param str
	 * @return
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	/**
	 * str 이 알파벳으로만 되어 있는지 확인 <br>
	 * @param str
	 * @return
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiAlpha(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 알파벳과 공백 만 인지 확인 <br>
	 * @param str
	 * @return
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((isAsciiAlpha(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 알파벳과 숫자 만 인지 확인 <br>
	 * @param str
	 * @return
	 */
	public static boolean isAlphaNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiAlphanumeric(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 알파벳과 공백/숫자 만 인지 확인 <br>
	 * @param str
	 * @return
	 */
	public static boolean isAlphaNnumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((isAsciiAlphanumeric(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 숫자 만 인지 확인 <br>
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiNumeric(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 숫자와 공백 만 인지 확인 <br>
	 * @param str
	 * @return
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((isAsciiNumeric(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 공백 인지 확인
	 * @param str
	 * @return
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str 이 null 일 경우 defaultStr 으로 대체
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String defaultString(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	/**
	 * <strong>title</strong> : 값이 없을 경우 공백으로 처리 <BR/>
	 * <strong>description</strong> :  <BR/>
	 * @param str
	 * @return <BR/>
	 */
	public static String emptyString(String str)
	{
		String rtn = "";
		if( str == null || str.equals("null") )
		{
			rtn = "";
		}else if( str.trim().length() == 0 )
		{
			rtn = "";
		}else
		{
			rtn = str;
		}
		return rtn;
	}

	/**
	 * str 이 null 이거나 공백 일때 defaultStr 으로 대체
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return StringUtil.isEmpty(str) ? defaultStr : str;
	}

	/**
	 * str 을 반대로 만듦
	 * @param str
	 * @return
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * unicode 로 변경
	 * @param ch
	 * @return
	 */
	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		}
		else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		}
		else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}

	/**
     * 문자열을 유니코드로 변환
     * @param text
     * @return
     */
    public static String convertUnicode(String text){
    	char[] chBuffer = text.toCharArray();
 	   	StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < chBuffer.length; i++) {
			if((chBuffer[i] == 32)) {
				buffer.append(" ");
				continue;
			}
			buffer.append("\\u");
			buffer.append(Integer.toHexString(chBuffer[i]));
		}
		return buffer.toString();
    }

	/**
	 * char 가 Ascii 문자 인지 확인
	 * @param ch
	 * @return
	 */
	public static boolean isAscii(char ch) {
		return ch < 128;
	}

	/**
	 * char 가 표현 가능한 Ascii 문자 인지 확인
	 * @param ch
	 * @return
	 */
	public static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}

	/**
	 * char 가 알파벳 인지 확인 ( 대소문자 포함 )
	 * @param ch
	 * @return
	 */
	public static boolean isAsciiAlpha(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}

	/**
	 * char 가 대문자 알파벳 인지 확인
	 * @param ch
	 * @return
	 */
	public static boolean isAsciiAlphaUpper(char ch) {
		return ch >= 'A' && ch <= 'Z';
	}

	/**
	 * char 가 소문자 알파벳인지 확인
	 * @param ch
	 * @return
	 */
	public static boolean isAsciiAlphaLower(char ch) {
		return ch >= 'a' && ch <= 'z';
	}

	/**
	 * char 가 숫자 문자 인지 확인
	 * @param ch
	 * @return
	 */
	public static boolean isAsciiNumeric(char ch) {
		return ch >= '0' && ch <= '9';
	}

	/**
	 * char 가 숫자 / 알파벳 인지 확인
	 * @param ch
	 * @return
	 */
	public static boolean isAsciiAlphanumeric(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
		|| (ch >= '0' && ch <= '9');
	}

	/**
	 * hex 로 변환
	 * @param ch
	 * @return
	 */
	public static String hex(char ch) {
		return Integer.toHexString(ch).toUpperCase();
	}

	/**
	 * StringToken 을 받아 온다.
	 * @param str
	 * @param delimeter
	 * @return
	 */
	public static StringTokenizer getToken(String str, String delimeter) {
		if (str != null) {
			if (delimeter != null) {
				delimeter = "";
			}
			StringTokenizer stringToken = new StringTokenizer(str, delimeter);
			return stringToken;
		}
		return null;
	}

	/**
	 * StringToken 을 받아 온다.
	 * @param str
	 * @return
	 */
	public static StringTokenizer getToken(String str) {
		if (str != null) {
			return new StringTokenizer(str);
		}
		return null;
	}

	/**
	 * String 을 MD5 로 생성 한다.
	 * @param id
	 * @return
	 */
	public static String makeMD5Digest(String id) {

		if (id == null || "".equals(id)) {
			id = Long.toString(System.currentTimeMillis());
		}

		byte[] defaultBytes = id.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte[] messageDigest = algorithm.digest();
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			return hexString.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * String 을 long 으로 변환<br>
	 * 숫자로 변경되지 않을 문자의 경우 NumberFormatException 발생 <br>
	 * @param str
	 * @return
	 */
	public static long stringToLong(String str) {

		if (!isNumeric(str)) {
			throw new NumberFormatException();
		}
		return Long.parseLong(str);

	}

	/**
	 * String 을 int 으로 변환<br>
	 * 숫자로 변경되지 않을 문자의 경우 NumberFormatException 발생 <br>
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str) {
		if(str==null || str.equals("null")) {
			return 0;
		} else {
			if (!isNumeric(str)) {
				throw new NumberFormatException();
			}
			return Integer.parseInt(str);
		}

	}

	/**
	 * String Character Encoding 을 변환 한다.
	 *
	 * @param str
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertCharSet(String str, String preEnc, String enc)
	throws UnsupportedEncodingException {
		String result = null;
		result = new String(str.getBytes(preEnc), enc);
		return result;
	}

	/**
	 * 한글의 자모음을 분리 하여 만들어 준다. <br>
	 * @param str
	 * @return
	 */
	public static String koToChar(String str) {
		int a = 0;
		int b = 0;
		int c = 0; //
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			UnicodeBlock unicode = Character.UnicodeBlock.of(ch);
			//if (ch >= 0xAC00 && ch <= 0xD7A3) { //
			if (unicode == Character.UnicodeBlock.HANGUL_JAMO
					|| unicode == Character.UnicodeBlock.HANGUL_SYLLABLES) {

				c = ch - 0xAC00;
				a = c / (21 * 28);
				c = c % (21 * 28);
				b = c / 28;
				c = c % 28;

				result = result + CHAR_1ST[a] + CHAR_2ND[b];
				if (c != 0)
					result = result + CHAR_3RD[c]; //
			}
			else {
				result = result + ch;
			}
		}
		return result;
	}

	/**
	 * 한글 문자 포함여부를 체크한다.
	 * @param str
	 * @return
	 */
	public static boolean isHangleCheck(String str){
		boolean flag = false;
		int hanLeng = 0;
		if(str == null || str.length() < 1) return flag;

		for(int i=0; i< str.length(); i++){
			if(isHangleChar(str.charAt(i))){
				hanLeng++;
				flag =  true;
				if(hanLeng > 2)
					break;
			}else{
				hanLeng = 0;
			}
		}
		return flag;
	}

	/**
	 * 한글 char인지 체크
	 * @param ch
	 * @return
	 */
	public static boolean isHangleChar(char ch) {
		String block = Character.UnicodeBlock.of(ch).toString();

		if(block.equals("HANGUL_JAMO") || block.equals("HANGUL_SYLLABLES") || block.equals("HANGUL_COMPATIBILITY_JAMO"))
			return true;

		return false;
	}

	/**
	 * null 일 경우 "" 으로 만들어 준다 .
	 *
	 * @param str
	 * @return
	 */
	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	//euckr to uniconde로 변경
	public static String euckrToUnicode(String str) {
		str = str.trim();
		char[] chBuffer = str.toCharArray();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < chBuffer.length; i++) {
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of( ch );

			if( UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)|| (chBuffer[i] == 32)) {
				if((chBuffer[i] == 32)) {
					buffer.append(" ");
					continue;
				}
				buffer.append("\\u");
				buffer.append(Integer.toHexString(chBuffer[i]));
			}
			else {
				buffer.append(chBuffer[i]);
			}

		}

		return buffer.toString();
	}

	public static String utf8ToEuckr(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("\\", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String encodingType(String str)
	{
		str = str.trim();
		char[] chBuffer = str.toCharArray();
		String encodingType = "EUC-KR";

		for (int i = 0; i < chBuffer.length; i++) {
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of( ch );

			if( UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)) {
				encodingType="UTF-8";
				break;
			}
		}

		return encodingType;
	}


	/** 여기서부터 Append by Kim Jin Hyug **/

	/**
	 * <strong>title</strong> : Object를 int로 변환한다. <BR/>
	 * <strong>description</strong> : Object가 null 일 경우 또는 Exception 발생시 0을 반환 <BR/>
	 * @param obj
	 * @return <BR/>
	 */
	public static int parseInt(Object obj) {
		try {
			if(obj==null){
				return 0;
			}
			return Integer.parseInt(String.valueOf(obj));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	public static float parseFloat(Object obj) {
		try {
			if(obj==null){
				return 0;
			}
			return  Float.parseFloat(String.valueOf(obj));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * <strong>title</strong> : Object를 문자열로 변경한다. <BR/>
	 * <strong>description</strong> : Object가 NULL일 경우 empty반환 <BR/>
	 * @param src
	 * @return <BR/>
	 */
	public static String toString(Object src){
		String rst="";
		try{
			rst=String.valueOf(src);
		}catch(Exception e){
			return "";
		}
		return rst;
	}

	/**
	 * <strong>title</strong> : Object NULL 인지 확인 <BR/>
	 * <strong>description</strong> :  <BR/>
	 * @param str
	 * @return <BR/>
	 */
	public static boolean isEmpty(Object str) {
		return str == null || toString(str).trim().length() == 0;
	}


	/**
	 * <strong>title</strong> : long 형식으로 변환  <BR/>
	 * <strong>description</strong> : Object를 long로 변환한다. null 또는 Exception는 0을 반환한다. <BR/>
	 * @param obj
	 * @return <BR/>
	 */
	public static long parseLong(Object obj) {
		try {
			if(obj==null){
				return 0L;
			}
			return Long.parseLong(String.valueOf(obj));
		} catch (NumberFormatException e) {
			return 0L;
		}
	}


	/**
	 * <strong>title</strong> : 3자리마다 콤마를 찍는다. <BR/>
	 * <strong>description</strong> :  <BR/>
	 * @param n
	 * @return <BR/>
	 */
	public static String format(long n)
	{

		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			return nf.format(n);
		} catch(Exception e) {
			return "0";
		}
	}

	/**
	 * <strong>title</strong> : 3자리마다 콤마를 찍는다. <BR/>
	 * <strong>description</strong> :  <BR/>
	 * @param n
	 * @return <BR/>
	 */
	public static String format(Object n)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			long tmp = parseLong(n);
			return nf.format(tmp);
		} catch(Exception e) {
			return "0";
		}
	}
	/**
	 * <strong>title</strong> : 문자를 포맷에 맞게 변경. <BR/>
	 * <strong>description</strong> : format는 #와 0만 허용 <BR>
	 * Ex> formatting("421160","###-###") = 421-160 <BR/>
	 * @param source
	 * @param format
	 * @return <BR/>
	 */
	public static String formatting(Object source,String format){
		String sourceStr = (String)source;
		if(isEmpty(sourceStr)) return "";
		String args[] = removeEmptyArray(format.split("[^#0]"));
		int argsLen[] = new int[args.length];
		int idx=0;
		for(String tmp : args){
			argsLen[idx] = tmp.length();
			idx++;
		}


		String loopRst[] = new String[args.length];
		int curIdx =0;
		for(int i=0;i<args.length;i++){
			String formatArg =  args[i];
			loopRst[i] = formatting(sourceStr,curIdx,argsLen[i],formatArg);
			curIdx += argsLen[i];
		}

		String head = format.substring(0,1);
		String delims[] =  removeEmptyArray(format.split("[#0]"));
		int loopSize = delims.length > loopRst.length?delims.length:loopRst.length;
		List<String> rstList = new ArrayList<String>();
		if(!head.equals("0") && !head.equals("#")){
			for(int i=0;i<loopSize;i++){
				if(i<delims.length) rstList.add(delims[i]);
				if(i<loopRst.length) rstList.add(loopRst[i]);
			}
		}else{
			for(int i=0;i<loopSize;i++){
				if(i<loopRst.length) rstList.add(loopRst[i]);
				if(i<delims.length) rstList.add(delims[i]);
			}
		}
		String rst = "";
		for(String tmp : rstList){
			rst += tmp;
		}

		return rst;
	}

	/**
	 * <strong>title</strong> : 빈배열을 삭제 한다. <BR/>
	 * <strong>description</strong> : 배열에 값이 null 또는 Empry 일경우 배열에서 삭제 한다. <BR/>
	 * @param args
	 * @return <BR/>
	 */
	public static String[] removeEmptyArray(String  args[]){

		int i=0;
		for(String tmp : args){
			if(isNotEmpty(tmp)){
				i++;
			}
		}

		String rst[] = new String[i];
		i=0;
		for(String tmp : args){
			if(isNotEmpty(tmp)){
				rst[i] = tmp;
				i++;
			}
		}

		return rst;
	}

	/**
	 * <strong>title</strong> : 포멧팅에서 사용.. <BR/>
	 * <strong>description</strong> :  <BR/>
	 * @param param
	 * @param start
	 * @param range
	 * @param format
	 * @return <BR/>
	 */
	public static String formatting(String param, int start,int range,String format){
		String substr = substring(param, start, start+range);
		substr = substring(substr,0,format.length());
		if(substr.length()<format.length()){
			substr = format.substring(0,format.length() - substr.length())+substr;
		}
		return substr.replaceAll("#", "");
	}


	/**
	 * 문자열이 서로 같은지 확인한다.
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEquals(String a,String b){
		if(a==null)
			return false;
		if(b==null)
			return false;
		return a.equals(b);
	}


	/**
	 * 같으문자면 지정한 문자열(success) 반환
	 * 문자가 서로 같지 않으면 문자열(fail) 반환
	 * @param a
	 * @param b
	 * @param success
	 * @param fail
	 * @return
	 */
	public static String isEquals(String a,String b,String success,String fail){
		if(StringUtil.isEquals(a, b)){
			return success;
		}else{
			return fail;
		}
	}

	/** 여기서부터 Append by Youn Byong Suk **/

	/**
	 * <strong>title</strong> : 문자열에서 주어진 separator 로 쪼개어 문자배열을 생성한다  <BR/>
	 * <strong>description</strong> :  <BR/>
	 * @param str 원본문자열
	 * @param separator 원하는 token 문자열
	 * @return 스트링배열 <BR/>
	 */
	public static String[] splitVal(String str, String separator)
	{
		StringTokenizer st = new StringTokenizer(str, separator);
		String[] values = new String[st.countTokens()];
		int pos = 0;
		while (st.hasMoreTokens())
		{
			values[pos++] = st.nextToken();
		}

		return values;
	}


	/**
	 * 입력 String에 있는 특정문자를 삭제해준다.
	 * @param strString input String
	 * @param strChar special character
	 * @return String 특정문자를 제거한 문자열
	 */
	public static String deleteChar(String strString, char strChar)
	{
		if ( isBlank(strString) )
			return "";

		strString = strString.trim();
		byte[] source = strString.getBytes();
		byte[] result = new byte[source.length];
		int j = 0;
		for (int i = 0; i < source.length; i++)
		{
			if (source[i] == (byte)strChar )
				i++;

			result[j++] = source[i];
		}

		return new String(result).trim();
	}

	/**
	 *
	 * 특수 문자 와 영문 한글 체크 해서 길이 를 가지고 온다.
	 * @param src
	 * @param len
	 * @param tail
	 * @return
	 */
	public static String getString(String src,int len, String tail){
		if(src==null){
			return "";
		}
		float rstLen=0;
		String rst="";
		char c[]=src.toCharArray();
		int i=0;
		for (i = 0; i < c.length; i++) {
			if (c[i] == 60) { /* < 시작하는거 체크*/
				rstLen += 1;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] == 62) { /* >끝나는거 체크*/
				rstLen += 1;
				rst+=src.substring(i,i+1);
			} else if (src.charAt(i) >255) { /* 한글로 시작하는 부분 체크 */
				rstLen += 1.21;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] >= 97 && (byte)c[i] <= 122) { /* 영문(소문자)으로 시작하는 부분 체크 */
				rstLen += 0.71;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] >= 65 && (byte)c[i] <= 90) { /* 영문(대문자)으로 시작하는 부분 체크 */
				rstLen += 0.82;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] >= 48 && (byte)c[i] <= 57) { /* 숫자 인지 체크 */
				rstLen += 0.61;
				rst+=src.substring(i,i+1);
			} else { /* 특수 문자 기호값 */
				rstLen += 0.71;
				rst+=src.substring(i,i+1);
			}
			// System.out.println((int) src.charAt(i));
			if (rstLen >= len) {
				rst+=tail;
				break;
			}
		}
		return rst;
	}

    /**
	 * 문자열에 있는 태그를 제거하여 반환한다.
	 * @param str
	 * @return
	 */
	public static String removeTagScript(String str) {
		return str.replaceAll("\\<.*?\\>", "");
	}

	/**
	 * <strong>description</strong> : < > ' " 를 변환한다. <BR/>
	 * @param string
	 * @return <BR/>
	 */
	public static String html(Object string) {
		if(string == null){
			return null;
		}else{
			//'를 치환한다.
			String rst=string.toString();
			rst = StringUtil.replace(rst, "<", "&#60;");
			rst = StringUtil.replace(rst, ">", "&#62;");
			rst = StringUtil.replace(rst, "'", "&#39;");
			rst = StringUtil.replace(rst, "\"", "&#34;");
			return rst;
		}
	}

	/**
	 * <strong>description</strong> : < > ' " 를 변환한다. <BR/>
	 * @param string
	 * @return <BR/>
	 */
	public static String script(Object string) {
		if(string == null){
			return null;
		}else{
			//'를 치환한다.
			String rst=string.toString();
			rst = StringUtil.replace(rst, "'", "\\'");
			rst = StringUtil.replace(rst, "&", "\\&");
			return rst;
		}
	}

	//중복제거
	public  static  String[]  removeDup(String  []  strArray)
    {
		if(strArray  ==  null  ||  strArray.length  ==  0)  return  null;
	     	LinkedHashSet<String>  hSet  =  new  LinkedHashSet<String>();
	        for(String  str  :  strArray){
	         hSet.add(str);
        }
        return  hSet.toArray(new  String  [hSet.size()]);
    }


	//html태그 그대로 노출
	public  static  String  htmlspecialchars(String content) {
		if(isEmpty(content)) return "";
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<content.length(); i++) {
		  char c = content.charAt(i);
		  switch (c) {
		    case '<' :
		      sb.append("&lt;");
		      break;
		    case '>' :
		      sb.append("&gt;");
		      break;
		    case '&' :
		      sb.append("&amp;");
		      break;
		    case '"' :
		      sb.append("&quot;");
		      break;
		    case '\'' :
		      sb.append("&apos;");
		      break;
		    default:
		      sb.append(c);
		    }
		}

		content = sb.toString();
		return content;
	}

	public static int getByteLength(String str){
		int subjectLen = str.length();
		int strLength =0;
		char tempChar[]=new char[subjectLen];
		for(int i=0; i<subjectLen;i++){
			tempChar[i] = str.charAt(i);
			if(tempChar[i]<128) {
				strLength++;
			} else {
				strLength +=2;
			}
		}
		return strLength;
	}

	 /**
	 * <strong>description</strong> : 쿼리스트링을 Map으로 변환. <BR/>
	 * @param queryString
	 * @return <BR/>
	 */
	public static Map<String,String> getMapForQueryString(String queryString){
		String vals[] = StringUtil.splitVal(queryString, "&");
		Map<String,String> tmp = new HashMap<String,String>();
		for(String val : vals){
			String params[] = StringUtil.splitVal(val, "=");
			String key = StringUtil.isNotEmpty(params[0])?params[0]:"";
			String value = "";
			if(params.length>1){
				value = StringUtil.isNotEmpty(params[1])?params[1]:"";
			}
			if(StringUtil.isNotEmpty(key))	tmp.put(key, value);
		}
		return tmp;
	}

	/**
	 * <strong>description</strong> : 쿼리스트링을 Map으로 변환. <BR/>
	 * @param queryString
	 * @return <BR/>
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String,String> getMapForQueryStringDecode(String queryString) {
		String vals[] = StringUtil.splitVal(queryString, "&");
		Map<String,String> tmp = new HashMap<String,String>();
		for(String val : vals){
			String params[] = StringUtil.splitVal(val, "=");
			String key = StringUtil.isNotEmpty(params[0])?params[0]:"";
			String value = StringUtil.isNotEmpty(params[1])?params[1]:"";
			if(StringUtil.isNotEmpty(key))
				try {
					tmp.put(key, URLDecoder.decode(value,"euc-kr"));
				} catch (UnsupportedEncodingException e) { tmp.put(key, value); }
		}
		return tmp;
	}

	 /**
	 * <strong>description</strong> : 문자셋 변경. <BR/>
	 * @param src
	 * @param from
	 * @param to
	 * @return
	 * @throws UnsupportedEncodingException <BR/>
	 */
	public static String convertSet(String src, String from, String to) throws UnsupportedEncodingException {
		return new String(src.getBytes(from),to);
	}

	 /**
	 * <strong>description</strong> : 줄바꿈 처리. <BR/>
	 * @param contents
	 * @return <BR/>
	 */
	public static String setBr(String contents){
		return contents.replace("\n", "<BR/>");
	}


	 /**
	 * <strong>description</strong> : JSON을 MAP으로 .. <BR/>
	 * @param jsonData
	 * @return <BR/>
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> getJsonToMap(String jsonData){
		try {
			HashMap<String,Object> result = new ObjectMapper().readValue(jsonData, HashMap.class);
			return result;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object <-> JSON 변환
	 * @param c
	 * @param name
	 * @return
	 */
	public static String objectToJSON(Object obj, String name){
		String jsonData = "";
		ObjectMapper om = new ObjectMapper();
		LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
		lhm.put(name, obj);
		try {
			jsonData = om.writeValueAsString(lhm);
		} catch (Exception e){
			e.getMessage();
		}
		return nvl(jsonData);
	}

    /**
     * <strong>title</strong> : change  <BR/>
     * <strong>description</strong> : 문자열 변경 <BR/>
     * <strong>date</strong> : 2010. 12. 21.
     * @param source
     * @param before
     * @param after
     * @return
     */
    public static String change(String source, String before, String after) {
        int i = 0;
        int j = 0;
        if(source==null){
        	return "";
        }
        StringBuffer sb = new StringBuffer();

        while ((j = source.indexOf(before, i)) >= 0) {
            sb.append(source.substring(i, j));
            sb.append(after);
            i = j + before.length();
        }

        sb.append(source.substring(i));
        return sb.toString();
    }

    public static void main(String args[]){
    	String test = "devkim_dev";
//    	System.out.println(change(test, "_dev", "kkkk"));
    }

    /**
     * <strong>title</strong> : matchArray  <BR/>
     * <strong>description</strong> : 배열에 해당 문자열이 있는지 확인한다. <BR/>
     * <strong>date</strong> : 2010. 12. 29.
     * @param src
     * @param dest
     * @return
     */
    public static boolean matchArray(String src[],String dest){
        for(int i=0;i<src.length;i++){
            if(src[i]!=null){
                if (src[i].equalsIgnoreCase(dest)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <strong>title</strong> : matchArray  <BR/>
     * <strong>description</strong> : 배열에 해당 문자열이 몇번째에 있는지 <BR/>
     * <strong>date</strong> : 2010. 12. 29.
     * @param src
     * @param dest
     * @return
     */
    public static int matchArrayIdx(String src[],String dest){
    	for(int i=0;i<src.length;i++){
    		if(src[i]!=null){
    			if (src[i].equalsIgnoreCase(dest)) {
    				return i;
    			}
    		}
    	}
    	return -1;
    }

    /**
     * PHP의 explode 기능의 메소드
     * @param : 비교할 문자열
     * @param : delim Token
     * @return : 배열
     */
    public static String[] explode(String srcParam,String delimParam){
    	String src = srcParam;
    	String delim = delimParam;
    	if(src==null || src.length()==0){
    		return new String[0];
    	}
    	if(delim.length()>=2){
    		src=change(src, delim, "\27");
    		delim="\27";
    	}
        StringTokenizer stk=new StringTokenizer(src,delim);
        int size=stk.countTokens();
        String rst[]=new String[size];
        int i=0;
        while(stk.hasMoreTokens()){
            rst[i]=stk.nextToken();
            i++;
        }
        return rst;
    }
    /**
     * PHP의 implode 기능의 메소드
     * @param : 배열
     * @param :  삽입될 토큰
     * @return : 문자열
     */
    public static String implode(String src[],String delim){
    	if(src==null || src.length==0){
    		return "";
    	}
        int size=src.length;
        StringBuffer rst= new StringBuffer("");
        for(int i=0;i<size;i++){
            if(i!=size-1){
            	String tmp = src[i] + delim;
                rst.append(tmp);
            }else{
            	rst.append(src[i]);
            }
        }
        return rst.toString();
    }

}


