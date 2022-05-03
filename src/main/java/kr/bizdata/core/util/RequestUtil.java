package kr.bizdata.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestUtil {
	
	@SuppressWarnings("unchecked")
	public static String getParam(Object object, String name, String defaultValue) {
		String value = null;
		if (object instanceof HttpServletRequest) {
			value = ((ServletRequest) object).getParameter(name);
		} else if (object instanceof Map) {
			Object o = ((Map<String, Object>) object).get(name);
			value = (o != null) ? o.toString() : null;
		} else if (object == null) {
			throw new IllegalArgumentException("object is null.");
		} else {
			throw new IllegalArgumentException("Not supported. (" + object.getClass().getName() + ")");
		}
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}
	
	public static int getParamInt(Object object, String name, int defaultValue) {
		String value = getParam(object, name, null);
		return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
	}
	
	public static long getParamLong(Object object, String name, long defaultValue) {
		String value = getParam(object, name, null);
		return StringUtils.isEmpty(value) ? defaultValue : Long.parseLong(value);
	}
	
	public static double getParamDouble(Object object, String name, double defaultValue) {
		String value = getParam(object, name, null);
		return StringUtils.isEmpty(value) ? defaultValue : Double.parseDouble(value);
	}
	
	/**
	 * 구분자가 포함된 파라미터값을 String 배열로 가져온다.<pre>
	 *   - 결과 값의 앞뒤 공백 제거
	 *   - 결과 배열에서 빈값 제외</pre>
	 */
	public static String[] splitParam(Object object, String name, String separator) {
		String[] array = getParam(object, name, "").split(separator);
		List<String> list = new ArrayList<String>();
		for (String value : array) {
			if (value.trim().length() > 0) { // 빈값 제외
				list.add(value.trim()); // 공백 제거
			}
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * 구분자가 포함된 파라미터값을 Integer 배열로 가져온다.
	 */
	public static Integer[] splitParamInt(Object object, String name, String separator) {
		String[] array = getParam(object, name, "").split(separator);
		List<Integer> list = new ArrayList<Integer>();
		for (String value : array) {
			if (value.trim().length() > 0) { // 빈값 제외
				list.add(Integer.parseInt(value.trim())); // 공백 제거
			}
		}
		return list.toArray(new Integer[0]);
	}
	
	/**
	 * 구분자가 포함된 파라미터값을 Long 배열로 가져온다.
	 */
	public static Long[] splitParamLong(Object object, String name, String separator) {
		String[] array = getParam(object, name, "").split(separator);
		List<Long> list = new ArrayList<Long>();
		for (String value : array) {
			if (value.trim().length() > 0) { // 빈값 제외
				list.add(Long.parseLong(value.trim())); // 공백 제거
			}
		}
		return list.toArray(new Long[0]);
	}
	
	/**
	 * 구분자가 포함된 파라미터값을 Double 배열로 가져온다.
	 */
	public static Double[] splitParamDouble(Object object, String name, String separator) {
		String[] array = getParam(object, name, "").split(separator);
		List<Double> list = new ArrayList<Double>();
		for (String value : array) {
			if (value.trim().length() > 0) { // 빈값 제외
				list.add(Double.parseDouble(value.trim())); // 공백 제거
			}
		}
		return list.toArray(new Double[0]);
	}
	
	/**
	 * 쿠키값을 구한다.
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					value = cookie.getValue();
					break;
				}
			}
		}
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}
	
}
