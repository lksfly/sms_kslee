package kr.bizdata.semas.pms.sprt.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.sprt.info.SprtBizInfo;
import kr.bizdata.semas.pms.sprt.info.SprtBizNmMngInfo;

/**
 * 지원사업기본
 */
public class SprtBizMapperTest extends AbstractTest {
	
	@Autowired
	private SprtBizMapper mapper;
	
	@Autowired
	private SprtBizNmMngMapper sprtBizNmMngMapper;
	
	private void assertEquals(SprtBizInfo expected, SprtBizInfo actual) {
		Assert.assertEquals(expected.getSprtBizSn(), actual.getSprtBizSn());
		Assert.assertEquals(expected.getSprtBizNm(), actual.getSprtBizNm());
		Assert.assertEquals(expected.getSprtBizYr(), actual.getSprtBizYr());
		Assert.assertEquals(expected.getSrcSysCd(), actual.getSrcSysCd());
		Assert.assertEquals(expected.getSprtBizDivCd(), actual.getSprtBizDivCd());
		Assert.assertNotNull(actual.getRegDt());
	}
	
	@Test
	public void insert_update() {
		
		SprtBizInfo info = new SprtBizInfo();
		//info.setSprtBizSn(sprtBizSn); // 지원사업일련번호, GeneratedKey
		info.setSprtBizNm("sprtBizNm");     // 지원사업명
		info.setSprtBizYr("2021");          // 지원사업연도
		info.setSrcSysCd("11");             // 출처시스템코드
		info.setSprtBizDivCd("12");         // 지원사업구분코드
		
		mapper.insert(info);
		Assert.assertTrue(info.getSprtBizSn() > 0); // GeneratedKey
		SprtBizInfo result = mapper.select(info.getSprtBizSn());
		assertEquals(info, result);
		
		SprtBizInfo info2 = new SprtBizInfo();
		info2.setSprtBizSn(info.getSprtBizSn());
		info2.setSprtBizNm("2prtBizNm");
		info2.setSprtBizYr("2022");
		info2.setSrcSysCd("21");
		info2.setSprtBizDivCd("22");
		
		mapper.update(info2);
		SprtBizInfo result2 = mapper.select(info.getSprtBizSn());
		assertEquals(info2, result2);
	}
	
	@Test
	public void select() {
		
		long sprtBizSn = 0; // PK
		
		SprtBizInfo result = mapper.select(sprtBizSn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
	@Test
	public void insert_from() {
		
		long sprtBizSn = 0; // FK
		long sprtBizNmSn = 0; // FK
		{
			SprtBizInfo info = new SprtBizInfo();
			mapper.insert(info);
			sprtBizSn = info.getSprtBizSn(); // GeneratedKey
		} {
			SprtBizNmMngInfo info = new SprtBizNmMngInfo();
			sprtBizNmMngMapper.insert(info);
			sprtBizNmSn = info.getSprtBizNmSn(); // GeneratedKey
		}
		
		mapper.insert_edu(1L, sprtBizSn);
		mapper.insert_cnslt(sprtBizNmSn, sprtBizSn);
		mapper.insert_hope1("2", sprtBizSn);
		mapper.insert_hope2("3", sprtBizSn);
		mapper.insert_hope3(sprtBizNmSn, sprtBizSn);
		mapper.insert_dln("4", sprtBizSn);
		mapper.insert_pln("5", sprtBizSn);
	}
	
	@Test
	public void selectSprtBizSn_from() {
		
		Assert.assertEquals(null, mapper.selectSprtBizSn_edu(null, null));
		Assert.assertEquals(null, mapper.selectSprtBizSn_cnslt(null, null));
		Assert.assertEquals(null, mapper.selectSprtBizSn_hope1(null, null));
		Assert.assertEquals(null, mapper.selectSprtBizSn_hope2(null, null));
		Assert.assertEquals(null, mapper.selectSprtBizSn_hope3(null, null));
		Assert.assertEquals(null, mapper.selectSprtBizSn_dln(null, null));
		Assert.assertEquals(null, mapper.selectSprtBizSn_pln(null, null));
	}
	
}
