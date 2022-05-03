package kr.bizdata.semas.from.smbi;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.smbi.info.VSedaLoginInfo;
import kr.bizdata.semas.from.smbi.service.VSedaLoginService;

/**
 * 소진공로그인테이블
 */
public class VSedaLoginServiceTest extends AbstractTest {
	
	@Autowired
	private VSedaLoginService service;
	
	@Test
	public void selectWithPassword() {
		
		String loginId = "isy33";
		String password = "leesy123!";
		
		VSedaLoginInfo result = service.selectWithPassword(loginId);
		logger.info("result = " + JsonUtil.toString(result));
		Assert.assertNotNull(result);
		
		Assert.assertTrue(service.checkPassword(loginId, password));
	}
	
}
