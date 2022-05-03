package kr.bizdata.semas.pms.bnft.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;

/**
 * 수혜기업기본
 */
public class BnftBizMapperTest extends AbstractTest {
	
	@Autowired
	private BnftBizMapper mapper;
	
	private void assertEquals(BnftBizInfo expected, BnftBizInfo actual) {
		Assert.assertEquals(expected.getBrno(), actual.getBrno());
		Assert.assertEquals(expected.getEntNm(), actual.getEntNm());
		Assert.assertEquals(expected.getCrno(), actual.getCrno());
		Assert.assertEquals(expected.getBzmnTypeCd(), actual.getBzmnTypeCd());
		Assert.assertEquals(expected.getZip(), actual.getZip());
		Assert.assertEquals(expected.getAddr(), actual.getAddr());
		Assert.assertEquals(expected.getDaddr(), actual.getDaddr());
		Assert.assertEquals(expected.getFndnYmd(), actual.getFndnYmd());
		Assert.assertEquals(expected.getSlsAmt(), actual.getSlsAmt());
		Assert.assertEquals(expected.getFtWrkrCnt(), actual.getFtWrkrCnt());
		Assert.assertEquals(expected.getClsbizYmd(), actual.getClsbizYmd());
		Assert.assertEquals(expected.getKsicLclsCd(), actual.getKsicLclsCd());
		Assert.assertEquals(expected.getKsicMclsCd(), actual.getKsicMclsCd());
		Assert.assertEquals(expected.getKsicSclsCd(), actual.getKsicSclsCd());
		Assert.assertEquals(expected.getKsicCd(), actual.getKsicCd());
		Assert.assertEquals(expected.getTelno(), actual.getTelno());
		Assert.assertEquals(expected.getCeoNm(), actual.getCeoNm());
		Assert.assertEquals(expected.getSrcSysCd(), actual.getSrcSysCd());
		Assert.assertEquals(DateUtil.formatYms(expected.getSrcRegDt()), DateUtil.formatYms(actual.getSrcRegDt()));
		Assert.assertNotNull(actual.getRegDt());
	}
	
	@Test
	public void insert_update() {
		
		BnftBizInfo info = new BnftBizInfo();
		info.setBrno("1234567890");         // 사업자등록번호
		info.setEntNm("entNm");             // 기업명
		info.setCrno("1234567890123");      // 법인등록번호
		info.setBzmnTypeCd("bzmnType");     // 사업자유형코드
		info.setZip("123456");              // 우편번호
		info.setAddr("addr");               // 기본주소
		info.setDaddr("daddr");             // 상세주소
		info.setFndnYmd("20210101");        // 설립일자
		info.setSlsAmt(11L);                // 매출액
		info.setFtWrkrCnt(12L);             // 상시근로자수
		info.setClsbizYmd("20210102");      // 폐업일자
		info.setKsicLclsCd("A");            // 표준산업분류대분류코드
		info.setKsicMclsCd("12");           // 표준산업분류중분류코드
		info.setKsicSclsCd("123");          // 표준산업분류소분류코드
		info.setKsicCd("12345");            // 표준산업분류코드
		info.setTelno("telno");             // 전화번호
		info.setCeoNm("ceoNm");             // 대표자명
		info.setSrcSysCd("12");             // 출처시스템코드
		info.setSrcRegDt(DateUtil.parseYms("20210107010101")); // 출처시스템등록일시
		
		mapper.insert(info);
		BnftBizInfo result = mapper.select(info.getBrno());
		assertEquals(info, result);
		Assert.assertNull(result.getUpdDt());
		
		BnftBizInfo info2 = new BnftBizInfo();
		info2.setBrno(info.getBrno());
		info2.setEntNm("2ntNm");
		info2.setCrno("2234567890123");
		info2.setBzmnTypeCd("2zmnType");
		info2.setZip("223456");
		info2.setAddr("2ddr");
		info2.setDaddr("2addr");
		info2.setFndnYmd("20210201");
		info2.setSlsAmt(22L);
		info2.setFtWrkrCnt(23L);
		info2.setClsbizYmd("20210202");
		info.setKsicLclsCd("B");
		info.setKsicMclsCd("22");
		info.setKsicSclsCd("223");
		info2.setKsicCd("22345");
		info2.setTelno("2elno");
		info2.setCeoNm("2eoNm");
		info2.setSrcSysCd("22");
		info.setSrcRegDt(DateUtil.parseYms("20210207010101"));
		
		mapper.update(info2);
		BnftBizInfo result2 = mapper.select(info.getBrno());
		assertEquals(info2, result2);
		Assert.assertNotNull(result2.getUpdDt());
	}
	
	@Test
	public void exists() {
		
		String diVal = null; // PK
		
		boolean result = mapper.exists(diVal);
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void select() {
		
		String brno = null; // PK
		
		BnftBizInfo result = mapper.select(brno);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
