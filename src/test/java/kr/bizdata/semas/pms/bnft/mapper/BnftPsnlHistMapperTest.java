package kr.bizdata.semas.pms.bnft.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlHistInfo;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlInfo;

/**
 * 수혜개인이력
 */
public class BnftPsnlHistMapperTest extends AbstractTest {
	
	@Autowired
	private BnftPsnlHistMapper mapper;
	@Autowired
	private BnftPsnlMapper bnftPsnlMapper;
	
	private void assertEquals(BnftPsnlHistInfo expected, BnftPsnlHistInfo actual) {
		Assert.assertEquals(expected.getDiVal(), actual.getDiVal());
		Assert.assertEquals(expected.getPsnlHistSn(), actual.getPsnlHistSn());
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
	public void insert() {
		
		String diVal = "diVal"; // FK
		{
			BnftPsnlInfo info = new BnftPsnlInfo();
			info.setDiVal(diVal);
			bnftPsnlMapper.insert(info);
		}
		
		BnftPsnlHistInfo info = new BnftPsnlHistInfo();
		info.setDiVal(diVal);             // DI값
		//info.setPsnlHistSn(psnlHistSn); // 개인이력일련번호, GeneratedKey
		info.setPsnlNm("psnlNm");           // 개인이름
		info.setEml("eml");                 // 이메일
		info.setMbno("mbno");               // 핸드폰
		info.setTelno("telno");             // 전화번호
		info.setZip("123456");              // 우편번호
		info.setAddr("addr");               // 기본주소
		info.setDaddr("daddr");             // 상세주소
		info.setBrdt("20210101");           // 생년월일
		info.setGndrCd("1");                // 성별코드
		info.setCiVal("ciVal");             // CI값
		info.setSrcSysCd("12");             // 출처시스템코드
		info.setSrcLgnId("srcLgnId");       // 출처시스템로그인ID
		info.setSrcRegDt(DateUtil.parseYms("20210107010101")); // 출처시스템등록일시
		
		mapper.insert(info);
		Assert.assertTrue(info.getPsnlHistSn() > 0); // GeneratedKey
		BnftPsnlHistInfo result = mapper.select(info.getDiVal(), info.getPsnlHistSn());
		assertEquals(info, result);
	}
	
	@Test
	public void select() {
		
		String diVal = null; // PK
		long psnlHistSn = 0; // PK
		
		BnftPsnlHistInfo result = mapper.select(diVal, psnlHistSn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
