package kr.bizdata.core.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	/**
	 * JSON 문자열을 Value Object 로 변환한다.
	 * 
	 * @param str
	 * @param objType
	 * @return
	 */
	public static <T> T toObject(String str, Class<T> objType) {
		if (str == null || str.length() == 0) {
			return null;
		}
		
		try {
			return new ObjectMapper().readValue(str, objType);
		} catch (IOException e) {
			throw new RuntimeException("JsonUtil.toObject() failed.", e);
		}
	}
	
	/**
	 * Value Object 를 JSON 문자열로 변환한다.
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return null;
		}
		
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JsonUtil.toString() failed.", e);
		}
	}
	
}
