package kr.bizdata.semas;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring/spring-context.xml", "classpath:config/spring/spring-servlet.xml" })
@Transactional(transactionManager = "txManager-1")
public abstract class AbstractTest {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
}
