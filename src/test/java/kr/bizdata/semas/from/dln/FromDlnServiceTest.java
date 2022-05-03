package kr.bizdata.semas.from.dln;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.dln.info.FromDlnInfo;
import kr.bizdata.semas.from.dln.service.FromDlnService;

/**
 * 직접대출시스템
 */
public class FromDlnServiceTest extends AbstractTest {
	
	@Autowired
	private FromDlnService service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromDlnInfo> result = service.selectList(startDt, endDt, "1", false); // 직접대출 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
