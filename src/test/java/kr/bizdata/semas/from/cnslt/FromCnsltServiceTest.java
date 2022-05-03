package kr.bizdata.semas.from.cnslt;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.cnslt.info.FromCnsltInfo;
import kr.bizdata.semas.from.cnslt.service.FromCnsltService;

/**
 * 컨설팅시스템
 */
public class FromCnsltServiceTest extends AbstractTest {
	
	@Autowired
	private FromCnsltService service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromCnsltInfo> result = service.selectList(startDt, endDt, "1"); // 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_2() {
		
		List<FromCnsltInfo> result = service.selectList(startDt, endDt, "2"); // 취소
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
