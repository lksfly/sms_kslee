package kr.bizdata.semas.from.hope;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.hope.info.FromHope3Info;
import kr.bizdata.semas.from.hope.service.FromHope3Service;

/**
 * 희망리턴패키지시스템 (장려금)
 */
public class FromHope3ServiceTest extends AbstractTest {
	
	@Autowired
	private FromHope3Service service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromHope3Info> result = service.selectList(startDt, endDt, "1"); // 재도전장려금 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_2() {
		
		List<FromHope3Info> result = service.selectList(startDt, endDt, "2"); // 재도전장려금 취소
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_3() {
		
		List<FromHope3Info> result = service.selectList(startDt, endDt, "3"); // 전직장려금 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_4() {
		
		List<FromHope3Info> result = service.selectList(startDt, endDt, "4"); // 전직장려금 취소
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
