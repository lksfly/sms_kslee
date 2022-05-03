package kr.bizdata.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanUtil implements ApplicationContextAware {
	
	private static ListableBeanFactory lbf;
	
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		BeanUtil.lbf = ac;
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		return BeanFactoryUtils.beanOfType(lbf, requiredType); // lbf.getBean(requiredType);
	}
	
}
