package com.bizsp.framework.util.lang;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;


/**
 * <strong>description</strong> :  날짜를 제어 한다. <BR/>
 */
public class DateUtil
{
    public final static int SUNDAY = 1;
    public final static int MONDAY = 2;
    public final static int TUESDAY = 3;
    public final static int WEDNESDAY = 4;
    public final static int THURSDAY = 5;
    public final static int FRIDAY = 6;
    public final static int SATURDAY = 7;

    public static final int TYPE_TO_MONTH = 0; // YYYYMM
	public static final int TYPE_TO_DAY = 1; // YYYYMMDD
	public static final int TYPE_TO_SEC = 2; // YYYYMMDDhhmmss


	/**
	 * 현재의 년도를 int 로 반환 한다 <br>
	 * @return YYYY
	 */
	public static int getCurrentYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 현재의 달을 int 로 반환 한다 <br>
	 * @return MM
	 */
	public static int getCurrentMonth()
	{
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 현재의 날짜를  int 로 반환 한다 <br>
	 * @return DD
	 */
	public static int getCurrentDay()
	{
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 현재의 시간을  int 로 반환 한다 <br>
	 * @return HH
	 */
	public static int getCurrentHour()
	{
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 현재의 분을  int 로 반환 한다 <br>
	 * @return mm
	 */
	public static int getCurrentMinute()
	{
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 현재의 초를  int 로 반환 한다 <br>
	 * @return ss
	 */
	public static int getCurrentSecond()
	{
		return Calendar.getInstance().get(Calendar.SECOND);
	}


	/**
	 * 문자형 날짜 데이터를 형식에 맞게 넣어서 Date 로 변환 한다.<br>
	 * @param str
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getStringToDate(String str, String pattern)
			throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(str);
	}

	/**
	 * Date 를  문자형으로 도출해 낸다.<br>
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateToString(Date date, String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

/** 여기서부터 Append by Youn Byong Suk **/

    /**
     * 날짜문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함 <br>
     * @param str yyyyMMdd 형식의 날짜문자열
     * @param days 더할, 혹은 뺄 날수
     * @return yyyyyMMdd 형식의 8자리 날짜
     * @exception 날짜문자열 형식이 잘못되었을 경우 ParseException return
     */
//    public static String addDays(String str, int days)
//    {
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//        Date date = fmt.parse(str);
//        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
//        return fmt.format(date);
//    }

    /**
     * date에 날짜를 더한(혹은 뺀) 일자를 구함 <br>
     * @param date Date객체
     * @param days 더할, 혹은 뺄 날수
     * @return yyyyyMMdd 형식의 8자리 날짜
     * @exception 날짜문자열 형식이 잘못되었을 경우 ParseException return
     */
    public static String addDays(Date date, int days)
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
        return fmt.format(date);
    }

    /**
     * 날짜문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함 <br>
     * @param str yyyyMMdd 형식의 날짜문자열
     * @param days 더할, 혹은 뺄 날수
     * @return yyyyyMMdd 형식의 8자리 날짜
     * @exception 날짜문자열 형식이 잘못되었을 경우 ParseException return
     */
//    public static Date addDays2Date(String str, int days)
//    {
//    	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//        Date date = fmt.parse(str);
//        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
//        return date;
//    }

    /**
     * 날짜문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함 <br>
     * @param date Date객체
     * @param days 더할, 혹은 뺄 날수
     * @return date
     * @exception 날짜문자열 형식이 잘못되었을 경우 ParseException return
     */
    public static Date addDays2Date(Date date, int days)
    {
        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
        return date;
    }

   /**
    * 주어진 문자열이 주어진 요일과 일치하는지 아닌지 여부리턴 <br>
    * @param str yyyyMMdd 형식의 날짜문자열
    * @param dayOfWeek 요일 (SUNDAY~SATURDAY중 하나)
    * @return 날짜와 요일이 일치할시 true,아니면 false를 리턴한다
    */
    public static boolean isDayOfWeek(String str, int dayOfWeek)
    {
    	int day = getCalendar(str).get(Calendar.DAY_OF_WEEK);
    	if (day == dayOfWeek)
    	{
    		return true;
    	}else
    	{
          	return false;
    	}
    }

   /**
    * 주어진 문자열이 주어진 요일과 일치하는지 아닌지 여부리턴 <br>
    * @param date Date객체
    * @param dayOfWeek 요일 (SUNDAY~SATURDAY중 하나)
    * @return 날짜와 요일이 일치할시 true,아니면 false를 리턴한다
    */
//    public static boolean isDayOfWeek(Date date, int dayOfWeek)
//    {
//    	int day = getCalendar(date).get(Calendar.DAY_OF_WEEK);
//       	if (day == dayOfWeek)
//       	{
//       		return true;
//       	}else
//       	{
//       		return false;
//       	}
//    }

	/**
	 * 주어진 문자열로 날짜셋팅한 calendar class 구하기 <br>
	 * @param str yyyymmdd 형식의 날짜문자열
	 * @return Calendar-인스턴스
	 */
	public static Calendar getCalendar(String str)
	{
        int yy = Integer.parseInt(str.substring(0, 4));
        int mm = Integer.parseInt(str.substring(4, 6)) - 1;
        int dd = Integer.parseInt(str.substring(6, 8));

        Calendar cal = Calendar.getInstance();
        cal.set(yy, mm, dd);
        return cal;
    }

	/**
	 * 주어진 문자열로 날짜셋팅한 calendar class 구하기 <br>
	 * @param date Date객체
	 * @return Calendar-인스턴스
	 */
	public static Calendar getCalendar(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
    }

    /**
     * 날짜형태의 String의 날짜 포맷만을 변경해 주는 메소드
     * @param String    strSource       바꿀 날짜 String
     * @param String    fromDateFormat  기존의 날짜 형태
     * @param String    toDateFormat    원하는 날짜 형태
     * @return 소스 String의 날짜 포맷을 변경한 String
     */
//    public static String convertDate(String strSource, String fromDateFormat, String toDateFormat)
//    {
//        return convertDate(strSource, fromDateFormat, toDateFormat, "");
//    }
//
//    /**
//     * 날짜형태의 String의 날짜 포맷 밑 TimeZone을 변경해 주는 메소드
//     * @param String    strSource       바꿀 날짜 String
//     * @param String    fromDateFormat  기존의 날짜 형태
//     * @param String    toDateFormat    원하는 날짜 형태
//     * @param String    strTimeZone     변경할 TimeZone(""이면 변경 안함)
//     * @return 소스 String의 날짜 포맷을 변경한 String
//     */
//    public static String convertDate(String strSource, String fromDateFormat, String toDateFormat, String strTimeZone)
//    {
//        SimpleDateFormat simpledateformat = null;
//        Date date = null;
//        if(StringUtil.trimToEmpty(strSource).trim().equals(""))
//        {
//            return "";
//        }
//        if(StringUtil.trimToEmpty(fromDateFormat).trim().equals(""))
//        {
//            fromDateFormat = "yyyyMMddHHmmss";
//        }
//        if(StringUtil.trimToEmpty(toDateFormat).trim().equals(""))
//        {
//            toDateFormat = "yyyy-MM-dd HH:mm:ss";
//        }
//        try
//        {
//            simpledateformat = new SimpleDateFormat(fromDateFormat);
//            date = simpledateformat.parse(strSource);
//            if (!StringUtil.trimToEmpty(strTimeZone).trim().equals(""))
//            {
//                simpledateformat.setTimeZone(TimeZone.getTimeZone(strTimeZone));
//            }
//            simpledateformat = new SimpleDateFormat(toDateFormat);
//        }
//        catch(Exception exception)
//        {
//            exception.printStackTrace();
//        }
//        finally
//        {
//        }
//        return simpledateformat.format(date);
//    }

    /**
     * 주어진 날짜가 속한 주의 월요일/일요일 날짜를 구해 스트링배열로 리턴한다 <br>
     * @param str yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월/일요일
     * @exception ParseException 문자열파싱시 발생
     */
    public static String[] getBothDayOfWeek(String str, int week)
    {
        return new String[]{getFirstDayOfWeek(str, week), getLastDayOfWeek(str, week)};
    }

	/**
	 * 주어진 날짜가 속한 주의 월요일/일요일 날짜를 구해 스트링배열로 리턴한다 <br>
	 * @param date Date객체
	 * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
	 * @return 월/일요일
	 * @exception ParseException 문자열파싱시 발생
	 */
//    public static String[] getBothDayOfWeek(Date date, int week)
//	{
//	    return new String[] { getFirstDayOfWeek(date, week), getLastDayOfWeek(date, week)};
//	}

    /**
     * 주어진 날짜가 속한 주의 월요일 날짜를 구한다 <br>
     * @param str yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월요일
     * @exception ParseException 문자열파싱시 발생
     */
    public static String getFirstDayOfWeek(String str, int week)
    {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0)
        {
            conStr = str;
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }else
        {
            conStr = addDays(str, week * 7,"");
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }

        int gap = 0;
        if (dayOfWeek != 1)
        {
             gap = dayOfWeek - 2;
        }else
        {
             gap = 6;
        }

        return addDays(conStr, -gap,"");
    }

    /**
     * 주어진 날짜가 속한 주의 월요일 날짜를 구한다 <br>
     * @param date Date객체
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월요일
     * @exception ParseException 문자열파싱시 발생
     */
//    public static String getFirstDayOfWeek(Date date, int week)
//    {
//        Date conDate = null;
//        int dayOfWeek = 0;
//
//        if (week == 0)
//        {
//            conDate = date;
//            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
//        }else
//        {
//            conDate = addDays2Date(date, week * 7);
//            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
//        }
//
//        int gap = 0;
//        if (dayOfWeek != 1)
//        {
//            gap = dayOfWeek - 2;
//        }else
//        {
//        	gap = 6;
//        }
//        return addDays(conDate, -gap);
//     }

    /**
     * 주어진 날짜가 속한 주의 일요일 날짜를 구한다 <br>
     * @param str yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 일요일
     * @exception ParseException 문자열파싱시 발생
     */
    public static String getLastDayOfWeek(String str, int week)
    {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0)
        {
            conStr = str;
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }else
        {
            conStr = addDays(str, week * 7,"");
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }
        int gap = 0;
        if (dayOfWeek != 1)
        {
        	gap = 8 - dayOfWeek;
        }else
        {
            gap = 0;
        }

        return addDays(conStr, gap,"");
    }

    /**
     * 주어진 날짜가 속한 주의 일요일 날짜를 구한다 <br>
     * @param date Date객체
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 일요일
     * @exception ParseException 문자열파싱시 발생
     */
//    public static String getLastDayOfWeek(Date date, int week)
//    {
//        Date conDate = null;
//        int dayOfWeek = 0;
//
//        if (week == 0)
//        {
//            conDate = date;
//            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
//        }else
//        {
//            conDate = addDays2Date(date, week * 7);
//            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
//        }
//        int gap = 0;
//        if (dayOfWeek != 1)
//        {
//             gap = 8 - dayOfWeek;
//        }else
//        {
//             gap = 0;
//        }
//        return addDays(conDate, gap);
//    }

    /**
     * 입력한 날짜 기준으로 몇일 전,후
     * (주의)입력날짜는 구분자가 없는 string형
     * @param    dayString   string date (19991002)
     * @param    day         가감하려고 하는 일자
     * @return   String
     */
     public static String addDays(String dayString, int day,String format)
     {
    	 format = StringUtil.nvl(format,"yyyyMMdd");
         SimpleDateFormat m_formatter = new SimpleDateFormat(format);
         int y = Integer.parseInt( dayString.substring(0,4) );
         int m = Integer.parseInt( dayString.substring(4,6) );
         int d = Integer.parseInt( dayString.substring(6,8) );

         Calendar aCalendar = Calendar.getInstance();
         aCalendar.set(y, m-1, d+day);

         return m_formatter.format( aCalendar.getTime() );
     }

     /**
      * 현재(한국기준) 시간정보를 얻는다. <br>
      * (예) 입력파리미터인 format string에 "yyyyMMddhh"를 셋팅하면 1998121011과 같이 Return.
      * (예) format string에 "yyyyMMddHHmmss"를 셋팅하면 19990114232121과 같이
      * (예) 밀리세컨드는 yyyyMMddHHmmssSSS
      *      0~23시간 타입으로 Return.
      *      String CurrentDate = BaseUtil.getKST("yyyyMMddHH");

      * @param    format      얻고자하는 현재시간의 Type
      * @return   string      현재 한국 시간.
      */
     public static String getToday(String format)
     {
         //1hour(ms) = 60s * 60m * 1000ms
         int millisPerHour = 60 * 60 * 1000;
         SimpleDateFormat fmt= new SimpleDateFormat(format);

         SimpleTimeZone timeZone = new SimpleTimeZone(9*millisPerHour,"KST");
         fmt.setTimeZone(timeZone);

         String str = fmt.format(new java.util.Date(System.currentTimeMillis()));

         return str;
     }

     /**
      * 주어진 날짜가 속한 주의 요일을 구함 <br>
      * @param str yyyymmdd 형식의 날짜
      * @param dayOfWeek 요일 (SUNDAY ~ SATURDAY 중 하나)
      * @return yyyyyMMdd 형식의 8자리 날짜
      */
//     public static String getSpecialDayOfWeek(String str, int dayOfWeek)
//     {
//         int gap = 0;
//         int day = getCalendar(str).get(Calendar.DAY_OF_WEEK);
//
//         if (day == dayOfWeek)
//         {
//             return str;
//         }else if (dayOfWeek == SUNDAY)
//         {
//             gap = 8 - day;
//         }else if (day < dayOfWeek)
//         {
//             gap = dayOfWeek - day;
//         }else
//         {
//             gap = - (day - dayOfWeek);
//         }
//         if (day == SUNDAY)
//         {
//             gap = gap - 7;
//         }
//         return addDays(str, gap);
//     }


 	/**
 	 * 년차 구하기
 	 * @param String from date string
 	 * @param String to date string
 	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜:
 	 *         java.text.ParseException 발생
 	 */
// 	public static int yearBetween(String from, String to)
// 			throws java.text.ParseException
// 	{
// 		return yearBetween(from, to, "yyyyMMdd");
// 	}

 	/**
 	 * 년차 구하기
 	 * @param String from date string
 	 * @param String to date string
 	 * @param format string representation of the date format. For example,
 	 *            "yyyy-MM-dd".
 	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜:
 	 *         java.text.ParseException 발생
 	 */
// 	public static int yearBetween(String from, String to, String format)
// 			throws java.text.ParseException
// 	{
// 		return (daysBetween(from, to, format) / 365);
// 	}


 	/**
 	 * 기간 구하기 날짜의 형식(Format) 없음.
 	 * @param String from date string
 	 * @param String to date string
 	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜:
 	 *         java.text.ParseException 발생
 	 */
// 	public static int daysBetween(String from, String to)
// 			throws java.text.ParseException
// 	{
// 		return daysBetween(from, to, "yyyyMMdd");
// 	}

 	/**
 	 * 일자 사이의 기간 구하기
 	 * @param String from date string
 	 * @param String to date string
 	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일자 리턴 형식이 잘못 되었거나 존재하지 않는 날짜:
 	 *         java.text.ParseException 발생
 	 */
// 	public static int daysBetween(String from, String to, String format)
// 			throws java.text.ParseException
// 	{
// 		// 날짜 검사
// 		java.util.Date d1 = check(from, format);
// 		java.util.Date d2 = check(to, format);
//
// 		long duration = d2.getTime() - d1.getTime();
//
// 		return (int) (duration / (1000 * 60 * 60 * 24));
// 	}

	/**
	 * 날짜 검사 날짜의 형식(Format) 없음.
	 * @param s date string you want to check with default format "yyyyMMdd".
	 * @return date java.util.Date
	 */
	public static java.util.Date check(String s)
			throws java.text.ParseException
	{
		return check(s, "yyyyMMdd");
	}

	/**
	 * 일자 검사
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return date java.util.Date
	 */
	public static java.util.Date check(String s, String format)
			throws java.text.ParseException
	{
		// 파라메터 검사
		if (s == null)
		{
			throw new java.text.ParseException("date string to check is null",
					0);
		}
		if (format == null)
		{
			throw new java.text.ParseException(
					"format string to check date is null", 0);
		}

		// 날짜 형식 지정
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format, java.util.Locale.KOREA);

		// 검사
		java.util.Date date = null;

		try
		{
			date = formatter.parse(s);
		}catch (java.text.ParseException e)
		{
			throw new java.text.ParseException(" wrong date:\"" + s
					+ "\" with format \"" + format + "\"", 0);
		}

		// 날짜 포멧이 틀린 경우
		if (!formatter.format(date).equals(s))
		{
			throw new java.text.ParseException("Out of bound date:\"" + s
					+ "\" with format \"" + format + "\"", 0);
		}

		return date;
	}

    /**
     * 현재 시간에 month를 더한다.
     * @param str		적용할 날짜
     * @param format	적용할 포맷
     * @param month		적용할 개월수
     * @return	String	month가 더해진 Date String ('yyyyMMdd')
     */
    public static String addMonth(String str,int month, String format)
    {
        Calendar calendar = Calendar.getInstance();
        try {
			calendar.setTime(getStringToDate(str, "yyyyMMdd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

        calendar.add(Calendar.MONTH, month);
        return getDateToString(calendar.getTime(), format);
    }

	/**
	 * 해당 String의 Timestamp를 반환한다.
	 * @param dateStr	Timestamp로 변환할 Date String
	 * @return Timestamp
	 */
	public static java.sql.Timestamp getTimestamp(String dateStr)
	{
		java.sql.Timestamp tempTimestamp = null;

		Date tempDate = null;
		try {
			tempDate = getStringToDate(dateStr, "yyyyMMdd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(tempDate != null)
		{
			tempTimestamp = new java.sql.Timestamp(tempDate.getTime());
		}

		return tempTimestamp;
	}


    /**
     * 현재 시점 Calenadar를 반환한다.
     * @return Calendar
     */
    public static Calendar getCurrentCalendar()
    {
        return GregorianCalendar.getInstance();
    }



//     /**
//     * <strong>description</strong> : YMD 를 넘겨 주어 나이를 계산한다. <BR/>
//     * @param ymd
//     * @return <BR/>
//     */
//    public static int getAge(String ymd){
//    	int rst = 0;
//    	if(StringUtil.isNotEmpty(ymd) && ymd.length()>=4){
//    		int curYear = getCurrentYear();
//    		int year = StringUtil.parseInt(ymd.substring(0,4));
//    		rst = curYear - year;
//    	}
//    	return rst;
//    }

    /**
     * GregorianCalendar 객체를 반환함
     *
     * @param yyyymmdd , yyMMddHHmmss날짜 인수
     * @return GregorianCalendar
     */
    public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
    	yyyymmdd=yyyymmdd.replace("-", "");
        int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int dd = Integer.parseInt(yyyymmdd.substring(6,8));
        int hh = 0;
        int mi = 0;
        int ss = 0;
        if(yyyymmdd.length()>8){
        	hh = Integer.parseInt(yyyymmdd.substring(8, 10));
        	mi = Integer.parseInt(yyyymmdd.substring(10, 12));
        	ss = Integer.parseInt(yyyymmdd.substring(12,14));
        }

        GregorianCalendar calendar =
                new GregorianCalendar(yyyy, mm - 1, dd, hh, mi, ss);

        return calendar;

    }

    /**
     * <strong>description</strong> : 문자를 제거후 yyyyMMddhhmmss 형식으로 반환 <BR/>
     * @param ymd
     * @return <BR/>
     */
    public static Date toDateHour(String ymd) {
        if (ymd == null || ymd.trim().length() < 1) {
            return new Date();
        }
        ymd=ymd.replaceAll("[^0-9]", "");
        int year, mon, day, h, m, s = 0;

        Calendar cal = Calendar.getInstance();
        year = Integer.parseInt(ymd.substring(0, 4));
        if(ymd.length()==4){
        	ymd=ymd+"0101";
        }
        mon = Integer.parseInt(ymd.substring(4, 6));
        if(ymd.length()==6){
        	ymd=ymd+"01";
        }
        day = Integer.parseInt(ymd.substring(6, 8));
        if(ymd.length()<=9){
        	h=00;
        	m=00;
        	s=00;
        }else{
        	try{
        		h=Integer.parseInt(ymd.substring(8, 10));
        	}catch(Exception e){
        		h=0;
        	}
	        try{
	        	m=Integer.parseInt(ymd.substring(10, 12));
	        }catch(Exception e){
	        	m=0;
	        }
	        try{
	        	s=Integer.parseInt(ymd.substring(12, 14));
	        }catch(Exception e){
	        	s=0;
	        }
        }
        cal.set(year, mon - 1, day, h, m, s);

        return cal.getTime();
    }

    /**
     * 나이 구하기
     * @param String from date string
     * @param String to date string
     * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴 형식이 잘못 되었거나 존재하지 않는 날짜:
     *         java.text.ParseException 발생
     */
    public static int getAge(String yymmdd)
    {
   	 long diff = getDifferYears(yymmdd, getToday("yyyyMMdd"));
   	 return (int)diff;
    }

	/**
     * 두 날짜간의 날짜수를 반환(윤년을 감안함)
     *
     * @param startDate 시작 날짜
     * @param endDate 끝 날짜
     * @return 날수
     */
    public static long getDifferDays(String startDate, String endDate) {

        GregorianCalendar StartDate = getGregorianCalendar(startDate);
        GregorianCalendar EndDate = getGregorianCalendar(endDate);

        long difer =
                (EndDate.getTime().getTime() - StartDate.getTime().getTime())
                / 86400000;

        return difer;

    }

	/**
	 * 두 날짜의 초단위의 차이를 구한다.
	 * 기준은 Date 형식이다
	 * @param curr	yyyyMMddhhmiss
	 * @param next	yyyyMMddhhmiss
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long getDiffTime(String curr, String next) {
		if(StringUtil.isEmpty(curr)) return 0;
		if(StringUtil.isEmpty(next)) return 0;

		GregorianCalendar StartDate = getGregorianCalendar(curr);
		GregorianCalendar EndDate = getGregorianCalendar(next);


		long diffDays =
			((EndDate.getTime().getTime() - StartDate.getTime().getTime())
					/ 1000)*-1;
		return diffDays;
	}

     /**
     * <strong>description</strong> : 현재시간을 기준으로 초단위로 계산 <BR/>
     * @param date
     * @return <BR/>
     */
    public static long getDiffTimeFromToday(String date){
    	String curTime = getToday("yyyyMMddHHmmssSSS");
    	long rst;
		rst = getDiffTime(curTime,date);
    	return rst;
    }

    /**
     * 두 날짜간의 연차를 반환(윤년을 감안함)
     *
     * @param startDate 시작일
     * @param endDate 끝일
     * @return a <code>double</code> value
     */
    public static long getDifferYears(String startDate, String endDate) {
    	long rst = (getDifferDays(startDate,endDate)/365);
    	return rst;
    }

    /**
     * 오늘날짜를 전달한 날짜형식(포멧)으로 취득한다.
     * "yyyyMMddHHmmss"
     * @param formet
     * @return
     */
//    public static String getToday(String formet){
//    	SimpleDateFormat df = new SimpleDateFormat(formet);
//    	Calendar today =Calendar.getInstance();
//    	return df.format(today.getTime());
//    }

	/**
	 * 날짜 포멧 Method
	 * @param strDate
	 * @param strFormat yyyy. MM. dd yyyyMMddhhmmss
	 * @return
	 */
	public static String dateFormat(String strDate, String strFormat) {
		if(StringUtil.isEmpty(strDate)){
			return "";
		}
		String date = "";
		try{
			SimpleDateFormat smf = new SimpleDateFormat(strFormat);
			date = smf.format(toDateHour(strDate));
		}catch(Exception e){
			return strDate;
		}
		return date;
	}

	public static void main(String a[]){
		try{
//			System.out.println(getDifferYears("19761108","19771107"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}