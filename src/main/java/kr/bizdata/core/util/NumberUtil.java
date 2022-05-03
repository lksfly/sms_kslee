package kr.bizdata.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

public class NumberUtil {
	
	private static final BigInteger INT_MIN = BigInteger.valueOf(Integer.MIN_VALUE);
	private static final BigInteger INT_MAX = BigInteger.valueOf(Integer.MAX_VALUE);
	private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
	private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
	
	private static void raiseOverflowException(final Object obj, final Class<?> targetClass) {
		throw new IllegalArgumentException("Can not parse the number. (" + obj + ":" + obj.getClass().getName()
				+ ", " + targetClass.getName() + "): overflow");
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Number> T parse(final Object obj, final Class<T> targetClass) {
		if (obj == null || targetClass == null
				|| obj.toString().trim().length() == 0) {
			return null;
		}
		
		try {
			BigDecimal num = (obj instanceof BigDecimal) ? (BigDecimal) obj : new BigDecimal(obj.toString().trim());
			
			if (Integer.class == targetClass) { // Integer
				BigInteger bigInt = num.toBigInteger();
				if (bigInt != null && (bigInt.compareTo(INT_MIN) < 0 || bigInt.compareTo(INT_MAX) > 0)) {
					raiseOverflowException(obj, targetClass);
				}
				return (T) Integer.valueOf(num.intValue());
			}
			else if (Long.class == targetClass) { // Long
				BigInteger bigInt = num.toBigInteger();
				if (bigInt != null && (bigInt.compareTo(LONG_MIN) < 0 || bigInt.compareTo(LONG_MAX) > 0)) {
					raiseOverflowException(obj, targetClass);
				}
				return (T) Long.valueOf(num.longValue());
			}
			else if (Float.class == targetClass) { // Float
				return (T) Float.valueOf(num.floatValue());
			}
			else if (Double.class == targetClass) { // Double
				return (T) Double.valueOf(num.doubleValue());
			}
			else if (BigDecimal.class == targetClass || Number.class == targetClass) { // BigDecimal, Number
				return (T) num;
			}
			else {
				throw new IllegalArgumentException("Not supported. (" + targetClass.getName() + ")");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Can not parse the number. (" + obj + ":" + obj.getClass().getName()
					+ ", " + targetClass.getName() + ")", e);
		}
	}
	
	public static BigDecimal parse(final Object obj) {
		return parse(obj, BigDecimal.class);
	}
	
	/**
	 * 더하기 (+)
	 */
	public static <T extends Number> T add(final Object v1, final Object v2, final Class<T> targetClass) {
		
		BigDecimal num1 = parse(v1, BigDecimal.class);
		BigDecimal num2 = parse(v2, BigDecimal.class);
		
		BigDecimal result = null;
		
		if (num1 != null && num2 != null) {
			// result = num1 + num2
			result = num1.add(num2);
		}
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal add(final BigDecimal v1, final BigDecimal v2) {
		return add(v1, v2, BigDecimal.class);
	}
	
	/**
	 * 빼기 (-)
	 */
	public static <T extends Number> T subtract(final Object v1, final Object v2, final Class<T> targetClass) {
		
		BigDecimal num1 = parse(v1, BigDecimal.class);
		BigDecimal num2 = parse(v2, BigDecimal.class);
		
		BigDecimal result = null;
		
		if (num1 != null && num2 != null) {
			// result = num1 - num2
			result = num1.subtract(num2);
		}
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal subtract(final BigDecimal v1, final BigDecimal v2) {
		return subtract(v1, v2, BigDecimal.class);
	}
	
	/**
	 * 곱하기 (*)
	 */
	public static <T extends Number> T multiply(final Object v1, final Object v2, final Class<T> targetClass) {
		
		BigDecimal num1 = parse(v1, BigDecimal.class);
		BigDecimal num2 = parse(v2, BigDecimal.class);
		
		BigDecimal result = null;
		
		if (num1 != null && num2 != null) {
			// result = num1 * num2
			result = num1.multiply(num2);
		}
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal multiply(final BigDecimal v1, final BigDecimal v2) {
		return multiply(v1, v2, BigDecimal.class);
	}
	
	/**
	 * 나누기 (/) (반올림)
	 */
	public static <T extends Number> T divide(final Object v1, final Object v2, final Class<T> targetClass, final int scale) {
		
		BigDecimal num1 = parse(v1, BigDecimal.class);
		BigDecimal num2 = parse(v2, BigDecimal.class);
		
		BigDecimal result = null;
		
		if (num1 != null && num2 != null && BigDecimal.ZERO.compareTo(num2) != 0) {
			// result = num1 / num2
			result = num1.divide(num2, scale, BigDecimal.ROUND_HALF_UP); // 소수점 n 자리까지 표시, 반올림
		}
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal divide(final BigDecimal v1, final BigDecimal v2, final int scale) {
		return divide(v1, v2, BigDecimal.class, scale);
	}
	
	/**
	 * 절대값 (abs)
	 */
	public static <T extends Number> T abs(final Object v1, final Class<T> targetClass) {
		
		BigDecimal num1 = parse(v1, BigDecimal.class);
		
		BigDecimal result = null;
		
		if (num1 != null) {
			// result = abs(num1)
			result = num1.abs();
		}
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal abs(final BigDecimal v1) {
		return abs(v1, BigDecimal.class);
	}
	
	/**
	 * 퍼센트 (%)
	 */
	public static <T extends Number> T percent(final Object v1, final Object v2, final Class<T> targetClass, final int scale) {
		
		BigDecimal result = null;
		
		// result = v1 * 100 / v2
		result = multiply(v1, 100, BigDecimal.class);
		result = divide(result, v2, BigDecimal.class, scale);
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal percent(final BigDecimal v1, final BigDecimal v2, final int scale) {
		return percent(v1, v2, BigDecimal.class, scale);
	}
	
	/**
	 * 증감률 (%) (양수: 증가, 음수: 감소, 0: 동일)
	 */
	public static <T extends Number> T rateChange(final Object v1, final Object v2, final Class<T> targetClass, final int scale) {
		
		BigDecimal result = null;
		
		// result = (v1 - v2) * 100 / abs(v2)
		result = subtract(v1, v2, BigDecimal.class);
		result = multiply(result, 100, BigDecimal.class);
		result = divide(result, abs(v2, BigDecimal.class), BigDecimal.class, scale); // [주의] 분모는 절대값 사용
		
		return parse(result, targetClass);
	}
	
	public static BigDecimal rateChange(final BigDecimal v1, final BigDecimal v2, final int scale) {
		return rateChange(v1, v2, BigDecimal.class, scale);
	}
	
	public static String numberFormat(Long val) {
		if (val == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		return nf.format(val);
	}
	
}
