package kr.bizdata.semas.pms.bnft.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlInfo;

/**
 * 수혜개인기본
 */
public class BnftPsnlMapperTest extends AbstractTest {
	
	@Autowired
	private BnftPsnlMapper mapper;
	
	private void assertEquals(BnftPsnlInfo expected, BnftPsnlInfo actual) {
		Assert.assertEquals(expected.getDiVal(), actual.getDiVal());
		Assert.assertEquals(expected.getPsnlNm(), actual.getPsnlNm());
		Assert.assertEquals(expected.getEml(), actual.getEml());
		Assert.assertEquals(expected.getMbno(), actual.getMbno());
		Assert.assertEquals(expected.getTelno(), actual.getTelno());
		Assert.assertEquals(expected.getZip(), actual.getZip());
		Assert.assertEquals(expected.getAddr(), actual.getAddr());
		Assert.assertEquals(expected.getDaddr(), actual.getDaddr());
		Assert.assertEquals(expected.getBrdt(), actual.getBrdt());
		Assert.assertEquals(expected.getGndrCd(), actual.getGndrCd());
		Assert.assertEquals(expected.getCiVal(), actual.getCiVal());
		Assert.assertEquals(expected.getSrcSysCd(), actual.getSrcSysCd());
		Assert.assertEquals(expected.getSrcLgnId(), actual.getSrcLgnId());
		Assert.assertEquals(DateUtil.formatYms(expected.getSrcRegDt()), DateUtil.formatYms(actual.getSrcRegDt()));
		Assert.assertNotNull(actual.getRegDt());
	}
	
	@Test
	public void insert_update() {
		
		BnftPsnlInfo info = new BnftPsnlInfo();
		info.setDiVal("diVal");         // DI값
		info.setPsnlNm("psnlNm");       // 개인이름
		info.setEml("eml");             // 이메일
		info.setMbno("mbno");           // 핸드폰
		info.setTelno("telno");         // 전화번호
		info.setZip("123456");          // 우편번호
		info.setAddr("addr");           // 기본주소
		info.setDaddr("daddr");         // 상세주소
		info.setBrdt("20210101");       // 생년월일
		info.setGndrCd("1");            // 성별코드
		info.setCiVal("ciVal");         // CI값
		info.setSrcSysCd("12");         // 출처시스템코드
		info.setSrcLgnId("srcLgnId");   // 출처시스템로그인ID
		info.setSrcRegDt(DateUtil.parseYms("20210103010101")); // 출처시스템등록일시
		
		mapper.insert(info);
		BnftPsnlInfo result = mapper.select(info.getDiVal());
		assertEquals(info, result);
		Assert.assertNull(result.getUpdDt());
		
		BnftPsnlInfo info2 = new BnftPsnlInfo();
		info2.setDiVal(info.getDiVal()); // DI값
		info2.setPsnlNm("2snlNm");       // 개인이름
		info2.setEml("2ml");             // 이메일
		info2.setMbno("2bno");           // 핸드폰
		info2.setTelno("2elno");         // 전화번호
		info2.setZip("223456");          // 우편번호
		info2.setAddr("2ddr");           // 기본주소
		info2.setDaddr("2addr");         // 상세주소
		info2.setBrdt("20210201");       // 생년월일
		info2.setGndrCd("2");            // 성별코드
		info2.setCiVal("2iVal");         // CI값
		info2.setSrcSysCd("22");         // 출처시스템코드
		info2.setSrcLgnId("2rcLgnId");   // 출처시스템로그인ID
		info2.setSrcRegDt(DateUtil.parseYms("20210203010101")); // 출처시스템등록일시
		
		mapper.update(info2);
		BnftPsnlInfo result2 = mapper.select(info.getDiVal());
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
		
		String diVal = null; // PK
		
		BnftPsnlInfo result = mapper.select(diVal);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
