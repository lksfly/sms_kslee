package kr.bizdata.semas.pms.system.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.system.info.AdmnLgnInfo;

/**
 * 관리자로그인기본
 */
public class AdmnLgnServiceTest extends AbstractTest {
	
	@Autowired
	private AdmnLgnService service;
	
	@Test
	public void selectWithPassword() {
		
		String loginId = "admin1";
		String password = "@1234";
		
		AdmnLgnInfo result = service.selectWithPassword(loginId);
		logger.info("result = " + JsonUtil.toString(result));
		Assert.assertNotNull(result);
		
		Assert.assertTrue(service.checkPassword(loginId, password));
	}
	
}
