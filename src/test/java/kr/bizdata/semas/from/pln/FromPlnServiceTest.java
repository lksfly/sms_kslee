package kr.bizdata.semas.from.pln;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.pln.info.FromPlnInfo;
import kr.bizdata.semas.from.pln.service.FromPlnService;

/**
 * 대리대출시스템
 */
public class FromPlnServiceTest extends AbstractTest {
	
	@Autowired
	private FromPlnService service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromPlnInfo> result = service.selectList(startDt, endDt, "1", false); // 대리대출 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
