package kr.bizdata.semas.from.hope;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.from.hope.info.FromHope1Info;
import kr.bizdata.semas.from.hope.service.FromHope1Service;

/**
 * 희망리턴패키지시스템 (컨설팅)
 */
public class FromHope1ServiceTest extends AbstractTest {
	
	@Autowired
	private FromHope1Service service;
	
	Date startDt = DateUtil.addDays(new Date(), -8);
	Date endDt = DateUtil.addDays(new Date(), -1);
	
	@Test
	public void selectList_1() {
		
		List<FromHope1Info> result = service.selectList(startDt, endDt, "1"); // 사업정리 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_2() {
		
		List<FromHope1Info> result = service.selectList(startDt, endDt, "2"); // 사업정리 취소
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_3() {
		
		List<FromHope1Info> result = service.selectList(startDt, endDt, "3"); // 점포철거 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_4() {
		
		List<FromHope1Info> result = service.selectList(startDt, endDt, "4"); // 점포철거 취소
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_5() {
		
		List<FromHope1Info> result = service.selectList(startDt, endDt, "5"); // 법률자문 승인
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectList_6() {
		
		List<FromHope1Info> result = service.selectList(startDt, endDt, "6"); // 법률자문 취소
		logger.info("result = " + JsonUtil.toString(result));
		logger.info("result.size() = " + result.size());
	}
	
}
