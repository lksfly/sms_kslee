package kr.bizdata.semas.pms.bnft.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.bnft.info.BnftBizHistInfo;
import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;

/**
 * 수혜기업이력
 */
public class BnftBizHistMapperTest extends AbstractTest {
	
	@Autowired
	private BnftBizHistMapper mapper;
	@Autowired
	private BnftBizMapper bnftBizMapper;
	
	private void assertEquals(BnftBizHistInfo expected, BnftBizHistInfo actual) {
		Assert.assertEquals(expected.getBrno(), actual.getBrno());
		Assert.assertEquals(expected.getEntHistSn(), actual.getEntHistSn());
		Assert.assertEquals(expected.getEntNm(), actual.getEntNm());
		Assert.assertEquals(expected.getCrno(), actual.getCrno());
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
	public void insert() {
		
		String brno = "1234567890"; // FK
		{
			BnftBizInfo info = new BnftBizInfo();
			info.setBrno(brno);
			bnftBizMapper.insert(info);
		}
		
		BnftBizHistInfo info = new BnftBizHistInfo();
		info.setBrno(brno);                 // 사업자등록번호
		//info.setEntHistSn(entHistSn); // 기업이력일련번호, GeneratedKey
		info.setEntNm("entNm");             // 기업명
		info.setCrno("1234567890123");      // 법인등록번호
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
		Assert.assertTrue(info.getEntHistSn() > 0); // GeneratedKey
		BnftBizHistInfo result = mapper.select(info.getBrno(), info.getEntHistSn());
		assertEquals(info, result);
	}
	
	@Test
	public void select() {
		
		String brno = null; // PK
		long entHistSn = 0; // PK
		
		BnftBizHistInfo result = mapper.select(brno, entHistSn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
