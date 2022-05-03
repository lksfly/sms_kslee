package kr.bizdata.semas.pms.sprt.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.sprt.info.SprtBizNmMngInfo;

/**
 * 지원사업명관리기본
 */
public class SprtBizNmMngMapperTest extends AbstractTest {
	
	@Autowired
	private SprtBizNmMngMapper mapper;
	
	private void assertEquals(SprtBizNmMngInfo expected, SprtBizNmMngInfo actual) {
		Assert.assertEquals(expected.getSprtBizNmSn(), actual.getSprtBizNmSn());
		Assert.assertEquals(expected.getSprtBizNm(), actual.getSprtBizNm());
		Assert.assertEquals(expected.getSprtBizDivCd(), actual.getSprtBizDivCd());
		Assert.assertNotNull(actual.getRegDt());
	}
	
	@Test
	public void insert_select() {
		
		SprtBizNmMngInfo info = new SprtBizNmMngInfo();
		info.setSprtBizNmSn(99);            // 지원사업일련번호
		info.setSprtBizNm("sprtBizNm");     // 지원사업명
		info.setSprtBizDivCd("00");         // 지원사업구분코드
		
		mapper.insert(info);
		SprtBizNmMngInfo result1 = mapper.select(info.getSprtBizNmSn());
		assertEquals(info, result1);
		SprtBizNmMngInfo result2 = mapper.selectBySprtBizDivCd(info.getSprtBizDivCd());
		assertEquals(info, result2);
	}
	
}
