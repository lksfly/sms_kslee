package kr.bizdata.semas.from.edu;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.edu.info.FromEduInfo;
import kr.bizdata.semas.from.edu.service.FromEduService;

/**
 * 교육시스템
 */
public class FromEduServiceTest extends AbstractTest {
	
	@Autowired
	private FromEduService service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromEduInfo> result = service.selectList(startDt, endDt, "1"); // 지급온라인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_2() {
		
		List<FromEduInfo> result = service.selectList(startDt, endDt, "2"); // 온라인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_3() {
		
		List<FromEduInfo> result = service.selectList(startDt, endDt, "3"); // 오프라인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
