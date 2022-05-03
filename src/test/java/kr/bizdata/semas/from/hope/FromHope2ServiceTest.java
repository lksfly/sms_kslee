package kr.bizdata.semas.from.hope;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.hope.info.FromHope2Info;
import kr.bizdata.semas.from.hope.service.FromHope2Service;

/**
 * 희망리턴패키지시스템 (재기교육)
 */
public class FromHope2ServiceTest extends AbstractTest {
	
	@Autowired
	private FromHope2Service service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromHope2Info> result = service.selectList(startDt, endDt, "1"); // 재기교육 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
