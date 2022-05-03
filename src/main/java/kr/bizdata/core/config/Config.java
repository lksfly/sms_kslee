package kr.bizdata.core.config;

import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class Config extends PropertyPlaceholderConfigurer {
	
	private static Properties properties;
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		
		if (properties == null) {
			properties = props;
		} else {
			properties.putAll(props);
		}
		logger.info("System.getProperty(\"application.properties\") = " + System.getProperty("application.properties")); // ex) -Dapplication.properties="application_R.properties"
		logger.info("System.getProperty(\"file.encoding\") = " + System.getProperty("file.encoding") + ", " + getString("file.encoding"));
		logger.info("Charset.defaultCharset() = " + Charset.defaultCharset() + ", \"한글\".getBytes().length = " + "한글".getBytes().length);
		logger.debug("properties info = " + properties);
		logger.debug("System.getProperty(\"java.io.tmpdir\") = " + System.getProperty("java.io.tmpdir"));
		logger.debug("System.getProperty(\"oracle.net.tns_admin\") = " + System.getProperty("oracle.net.tns_admin"));
	}
	
	/**
	 * 예약어(Reserved word)를 이용한 속성값 치환 기능
	 * ex) property_key=prefix ${other_property_key} suffix
	 * 
	 * @param value
	 * @return
	 */
	protected static String resolve(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		int index = 0;
		while (true) {
			int begin = value.indexOf("${", index);
			int end = value.indexOf("}", index);
			
			if (begin > -1 && begin < end) {
				String key = value.substring(begin + 2, end);
				String replace = getString(key, "${" + key + "}");
				
				result.append(value.substring(index, begin));
				result.append(replace);
				
				index = end + 1;
			} else {
				result.append(value.substring(index));
				break;
			}
		}
		return result.toString();
	}
	
	/**
	 * 시스템 환경변수에서 먼저 찾고, 없으면 설정파일에서 찾는다.
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		String value = System.getProperty(key);
		if (value == null) {
			value = resolve(properties.getProperty(key));
		}
		return value;
	}
	
	/**
	 * 인자를 전달하여 {0}, {1} 등을 인자값으로 치환한다.
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getString(String key, String[] args) {
		String[] words = null;
		if (args != null && args.length > 0) {
			words = new String[args.length];
			for (int i = 0; i < args.length; i++) {
				words[i] = "{" + i + "}"; // ex) {0}, {1}
			}
		}
		String value = getString(key);
		return StringUtils.replaceEach(value, words, args);
	}
	
	public static String getString(String key, String defaultValue) {
		String value = getString(key);
		return (value == null) ? defaultValue : value;
	}
	
	public static int getInt(String key, int defaultValue) {
		String value = getString(key, String.valueOf(defaultValue));
		return Integer.parseInt(value);
	}
	
	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = getString(key, String.valueOf(defaultValue));
		return "true".equalsIgnoreCase(value) || "1".equals(value) || "Y".equals(value);
	}
	
}
