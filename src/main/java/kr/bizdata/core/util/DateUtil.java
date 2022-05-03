package kr.bizdata.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	
	/**
	 * 날짜 문자열을 Date 객체로 변환한다.
	 * 
	 * @param str		날짜 문자열
	 * @param pattern	날짜 문자열의 패턴
	 * @return
	 */
	public static Date parse(final String str, final String pattern) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			return DateUtils.parseDate(str, pattern);
		} catch (ParseException e) {
			throw new RuntimeException("Can not parse the date. (" + str + ", " + pattern + ")", e);
		}
	}
	public static Date parseYmd(final String str) {
		
		return parse(str, "yyyyMMdd");
	}
	public static Date parseYms(final String str) {
		
		return parse(str, "yyyyMMddHHmmss");
	}
	
	/**
	 * 날짜 문자열이 Date 형식인지 여부
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static boolean isDate(final String str, final String pattern) {
		Date date = null;
		SimpleDateFormat sdf = null;
		
		if (str != null && pattern != null && str.length() == pattern.length()) {
			sdf = new SimpleDateFormat(pattern, java.util.Locale.KOREA);
			sdf.setLenient(false);
		}
		
		try {
			if (str != null && pattern != null && str.length() == pattern.length()) {
				date = sdf.parse(str);
			}
		} catch (Exception e) {} // ignore
		return date != null;
	}
	
	public static boolean isYmd(String str) {
		return isDate(str, "yyyyMMdd");
	}
	
	public static boolean isYms(String str) {
		return isDate(str, "yyyyMMddHHmmss");
	}
	
	/**
	 * Date 객체를 문자열로 변환한다. (TimeZone 변화없음)
	 * 
	 * @param date		Date 객체
	 * @param pattern	변환될 날짜 문자열의 패턴
	 * @return
	 */
	public static String format(final Date date, final String pattern) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}
	public static String formatYmd(final Date date) {
		
		return format(date, "yyyyMMdd");
	}
	public static String formatYms(final Date date) {
		
		return format(date, "yyyyMMddHHmmss");
	}
	
	/**
	 * 날짜 문자열을 다른 패턴의 문자열로 변환한다. (TimeZone 변화없음)
	 * 
	 * @param str				날짜 문자열
	 * @param sourcePattern		날짜 문자열의 패턴
	 * @param targetPattern		변환될 날짜 문자열의 패턴
	 * @return
	 */
	public static String format(final String str, final String sourcePattern, final String targetPattern) {
		Date date = parse(str, sourcePattern);
        return format(date, targetPattern);
	}
	
	/**
	 * Date 객체에 일수를 더한다.
	 * 
	 * @param date    Date 객체
	 * @param amount  일수
	 * @return
	 */
	public static Date addDays(final Date date, final int amount) {
		if (date == null) {
			return null;
		}
		return DateUtils.addDays(date, amount);
	}
	
	/**
	 * 해당 월의 마지막 날짜를 구한다.
	 * 
	 * @param date
	 * @return
	 */
	public static int getLastDayOfMonth(final Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date is null.");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월의 마지막 날짜
		
		return lastDay;
	}
	
	/**
	 * 해당 월의 특정 요일의 마지막 날짜를 구한다.
	 * 
	 * @param date
	 * @param dayOfWeek 요일 (1: 일요일) {@link Calendar.SUNDAY}
	 * @return
	 */
	public static int getLastDayOfMonth(final Date date, final int dayOfWeek) {
		if (date == null) {
			throw new IllegalArgumentException("date is null.");
		}
		if (dayOfWeek < 1 || 7 < dayOfWeek) {
			throw new IllegalArgumentException("dayOfWeek is invalid. [1-7] (" + dayOfWeek + ")");
		}
		
		String yyyyMM = format(date, "yyyyMM");
		int lastDay = getLastDayOfMonth(date); // 해당 월의 마지막 날짜
		
		for (int i = 0; i < 7; i ++) {
			Calendar cal = Calendar.getInstance();
			int dd = lastDay - i;
			cal.setTime(parseYmd(yyyyMM + dd));
			if (cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek) { // 일요일 (1: SUN)
				return dd; // 해당 월의 특정 요일의 마지막 날짜
			}
		}
		throw new RuntimeException("Not found. (" + (yyyyMM + lastDay) + ", " + dayOfWeek + ")");
	}
	
	/**
	 * 해당 월의 특정 요일의 마지막 날짜인지 여부
	 * 
	 * @param date
	 * @param dayOfWeek 요일 (1: 일요일) {@link Calendar.SUNDAY}
	 * @return
	 */
	public static boolean isLastDayOfMonth(final Date date, final int dayOfWeek) {
		
		int lastDay = getLastDayOfMonth(date, dayOfWeek);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.DATE) == lastDay;
	}
	
}
