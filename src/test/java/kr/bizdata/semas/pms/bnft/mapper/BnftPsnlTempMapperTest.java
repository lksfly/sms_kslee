package kr.bizdata.semas.pms.bnft.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlTempInfo;
import kr.bizdata.semas.pms.sprt.info.SprtBizAplyInfo;
import kr.bizdata.semas.pms.sprt.mapper.SprtBizAplyMapper;

/**
 * 수혜개인임시
 */
public class BnftPsnlTempMapperTest extends AbstractTest {
	
	@Autowired
	private BnftPsnlTempMapper mapper;
	@Autowired
	private BnftBizMapper bnftBizMapper;
	@Autowired
	private SprtBizAplyMapper sprtBizAplyMapper;
	
	private void assertEquals(BnftPsnlTempInfo expected, BnftPsnlTempInfo actual) {
		Assert.assertEquals(expected.getPsnlSn(), actual.getPsnlSn());
		Assert.assertEquals(expected.getAplySn(), actual.getAplySn());
		Assert.assertEquals(expected.getBrno(), actual.getBrno());
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
		
		Long aplySn = null; // FK
		String brno = "1234567890"; // FK
		{
			SprtBizAplyInfo info = new SprtBizAplyInfo();
			sprtBizAplyMapper.insert(info);
			Assert.assertTrue(info.getAplySn() > 0); // GeneratedKey
			aplySn = info.getAplySn();
		} {
			BnftBizInfo info = new BnftBizInfo();
			info.setBrno(brno);
			bnftBizMapper.insert(info);
		}
		
		BnftPsnlTempInfo info = new BnftPsnlTempInfo();
		//info.setPsnlSn(psnlSn); // 개인일련번호, GeneratedKey
		info.setAplySn(aplySn);         // 신청일련번호
		info.setBrno(brno);             // 사업자등록번호
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
		Assert.assertTrue(info.getPsnlSn() > 0); // GeneratedKey
		BnftPsnlTempInfo result = mapper.select(info.getPsnlSn());
		assertEquals(info, result);
	}
	
	@Test
	public void select() {
		
		long psnlSn = 0; // PK
		
		BnftPsnlTempInfo result = mapper.select(psnlSn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
