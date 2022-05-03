package kr.bizdata.core.util;

import org.apache.commons.lang3.StringUtils;

public class EscapeUtil {
	
	/**
	 * SQL문 LIKE절을 위한 문자열 escape
	 * 
	 * @param str	like절에 사용할 문자열
	 * @return
	 */
	public static String escapeSqlLike(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return str.replace("\\", "\\\\").replace("_", "\\_").replace("%", "\\%");
	}
	
}
