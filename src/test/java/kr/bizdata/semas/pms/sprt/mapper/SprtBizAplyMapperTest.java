package kr.bizdata.semas.pms.sprt.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.sprt.info.SprtBizAplyInfo;
import kr.bizdata.semas.pms.sprt.info.SprtBizInfo;

/**
 * 지원사업신청내역
 */
public class SprtBizAplyMapperTest extends AbstractTest {
	
	@Autowired
	private SprtBizAplyMapper mapper;
	@Autowired
	private SprtBizMapper sprtBizMapper;
	
	private void assertEquals(SprtBizAplyInfo expected, SprtBizAplyInfo actual) {
		Assert.assertEquals(expected.getAplySn(), actual.getAplySn()); 
		Assert.assertEquals(expected.getSprtBizSn(), actual.getSprtBizSn());
		Assert.assertEquals(expected.getSprtCntrCd(), actual.getSprtCntrCd());
		Assert.assertEquals(expected.getAplyStsCd(), actual.getAplyStsCd());
		Assert.assertEquals(expected.getBrno(), actual.getBrno());
		Assert.assertEquals(expected.getEntNm(), actual.getEntNm());
		Assert.assertEquals(expected.getCrno(), actual.getCrno());
		Assert.assertEquals(expected.getBzmnTypeCd(), actual.getBzmnTypeCd());
		Assert.assertEquals(expected.getEntZip(), actual.getEntZip());
		Assert.assertEquals(expected.getEntAddr(), actual.getEntAddr());
		Assert.assertEquals(expected.getEntDaddr(), actual.getEntDaddr());
		Assert.assertEquals(expected.getFndnYmd(), actual.getFndnYmd());
		Assert.assertEquals(expected.getSlsAmt(), actual.getSlsAmt());
		Assert.assertEquals(expected.getFtWrkrCnt(), actual.getFtWrkrCnt());
		Assert.assertEquals(expected.getClsbizYmd(), actual.getClsbizYmd());
		Assert.assertEquals(expected.getKsicLclsCd(), actual.getKsicLclsCd());
		Assert.assertEquals(expected.getKsicMclsCd(), actual.getKsicMclsCd());
		Assert.assertEquals(expected.getKsicSclsCd(), actual.getKsicSclsCd());
		Assert.assertEquals(expected.getKsicCd(), actual.getKsicCd());
		Assert.assertEquals(expected.getEntTelno(), actual.getEntTelno());
		Assert.assertEquals(expected.getDiVal(), actual.getDiVal());
		Assert.assertEquals(expected.getAplcntNm(), actual.getAplcntNm());
		Assert.assertEquals(expected.getGiveAmt(), actual.getGiveAmt());
		Assert.assertEquals(expected.getAplcntEml(), actual.getAplcntEml());
		Assert.assertEquals(expected.getAplcntTelno(), actual.getAplcntTelno());
		Assert.assertEquals(expected.getAplcntMbno(), actual.getAplcntMbno());
		Assert.assertEquals(expected.getAplcntZip(), actual.getAplcntZip());
		Assert.assertEquals(expected.getAplcntAddr(), actual.getAplcntAddr());
		Assert.assertEquals(expected.getAplcntDaddr(), actual.getAplcntDaddr());
		Assert.assertEquals(expected.getAplcntBrdt(), actual.getAplcntBrdt());
		Assert.assertEquals(expected.getAplcntGndrCd(), actual.getAplcntGndrCd());
		Assert.assertEquals(expected.getCiVal(), actual.getCiVal());
		Assert.assertEquals(expected.getSrcLgnId(), actual.getSrcLgnId());
		Assert.assertEquals(expected.getAplyYmd(), actual.getAplyYmd());
		Assert.assertEquals(expected.getGiveYmd(), actual.getGiveYmd());
		Assert.assertEquals(expected.getCeoNm(), actual.getCeoNm());
		Assert.assertEquals(expected.getFnshYmd(), actual.getFnshYmd());
		Assert.assertEquals(DateUtil.formatYms(expected.getSrcRegDt()), DateUtil.formatYms(actual.getSrcRegDt()));
		Assert.assertNotNull(actual.getRegDt());
		Assert.assertEquals(expected.getSlctnYmd(), actual.getSlctnYmd());
	}
	
	@Test
	public void insert_update() {
		
		Long sprtBizSn = null; // FK
		Long sprtBizSn2 = null; // FK
		{
			SprtBizInfo info = new SprtBizInfo();
			sprtBizMapper.insert(info);
			sprtBizSn = info.getSprtBizSn(); // GeneratedKey
		} {
			SprtBizInfo info = new SprtBizInfo();
			sprtBizMapper.insert(info);
			sprtBizSn2 = info.getSprtBizSn(); // GeneratedKey
		}
		
		SprtBizAplyInfo info = new SprtBizAplyInfo();
		//info.setAplySn(aplySn); // 신청일련번호, GeneratedKey
		info.setSprtBizSn(sprtBizSn);       // 지원사업일련번호
		info.setSprtCntrCd("123456");       // 지원센터코드
		info.setAplyStsCd("1");             // 신청상태코드
		info.setBrno("1234567890");         // 사업자등록번호
		info.setEntNm("entNm");             // 기업명
		info.setCrno("1234567890123");      // 법인등록번호
		info.setBzmnTypeCd("bzmnTypeCd");   // 사업자유형코드
		info.setEntZip("123456");           // 기업우편번호
		info.setEntAddr("entAddr");         // 기업기본주소
		info.setEntDaddr("entDaddr");       // 기업상세주소
		info.setFndnYmd("20210101");        // 설립일자
		info.setSlsAmt(11L);                // 매출액
		info.setFtWrkrCnt(12L);             // 상시근로자수
		info.setClsbizYmd("20210102");      // 폐업일자
		info.setKsicLclsCd("A");            // 표준산업분류대분류코드
		info.setKsicMclsCd("12");           // 표준산업분류중분류코드
		info.setKsicSclsCd("123");          // 표준산업분류소분류코드
		info.setKsicCd("12345");            // 표준산업분류코드
		info.setEntTelno("entTelno");       // 기업전화번호
		info.setDiVal("diVal");             // DI값
		info.setAplcntNm("aplcntNm");       // 신청자명
		info.setGiveAmt(13L);               // 지급금액
		info.setAplcntEml("aplcntEml");     // 신청자이메일
		info.setAplcntTelno("aplcntTelno"); // 신청자전화번호
		info.setAplcntMbno("aplcntMbno");   // 신청자핸드폰
		info.setAplcntZip("12345");         // 신청자우편번호
		info.setAplcntAddr("aplcntAddr");   // 신청자기본주소
		info.setAplcntDaddr("aplcntDaddr"); // 신청자상세주소
		info.setAplcntBrdt("20210103");     // 신청자생년월일
		info.setAplcntGndrCd("3");          // 신청자성별코드
		info.setCiVal("ciVal");             // CI값
		info.setSrcLgnId("srcLgnId");       // 출처시스템로그인ID
		info.setAplyYmd("20210104");        // 신청일자
		info.setGiveYmd("20210105");        // 지급일자
		info.setCeoNm("ceoNm");             // 대표자명
		info.setFnshYmd("20210106");        // 수료일자
		info.setSrcRegDt(DateUtil.parseYms("20210107010101")); // 출처시스템등록일시
		info.setSlctnYmd("20210108");       // 선정일자
		
		mapper.insert(info);
		Assert.assertTrue(info.getAplySn() > 0); // GeneratedKey
		SprtBizAplyInfo result = mapper.select(info.getAplySn());
		assertEquals(info, result);
		Assert.assertNull(result.getUpdDt());
		
		SprtBizAplyInfo info2 = new SprtBizAplyInfo();
		info2.setAplySn(info.getAplySn());
		info2.setSprtBizSn(sprtBizSn2);
		info2.setSprtCntrCd("223456");
		info2.setAplyStsCd("2");
		info2.setBrno("2234567890");
		info2.setEntNm("2ntNm");
		info2.setCrno("2234567890123");
		info2.setBzmnTypeCd("2zmnTypeCd");
		info2.setEntZip("223456");
		info2.setEntAddr("2ntAddr");
		info2.setEntDaddr("2ntDaddr");
		info2.setFndnYmd("20210201");
		info2.setSlsAmt(21L);
		info2.setFtWrkrCnt(22L);
		info2.setClsbizYmd("20210202");
		info2.setKsicLclsCd("B");
		info2.setKsicMclsCd("22");
		info2.setKsicSclsCd("223");
		info2.setKsicCd("22345");
		info2.setEntTelno("2ntTelno");
		info2.setDiVal("2iVal");
		info2.setAplcntNm("2plcntNm");
		info2.setGiveAmt(23L);
		info2.setAplcntEml("2plcntEml");
		info2.setAplcntTelno("2plcntTelno");
		info2.setAplcntMbno("2plcntMbno");
		info2.setAplcntZip("22345");
		info2.setAplcntAddr("2plcntAddr");
		info2.setAplcntDaddr("2plcntDaddr");
		info2.setAplcntBrdt("20210203");
		info2.setAplcntGndrCd("4");
		info2.setCiVal("2iVal");
		info2.setSrcLgnId("2rcLgnId");
		info2.setAplyYmd("20210204");
		info2.setGiveYmd("20210205");
		info2.setCeoNm("2eoNm");
		info2.setFnshYmd("20210206");
		info2.setSrcRegDt(DateUtil.parseYms("20210207010101"));
		info2.setSlctnYmd("20210208");
		
		mapper.update(info2);
		SprtBizAplyInfo result2 = mapper.select(info.getAplySn());
		assertEquals(info2, result2);
		Assert.assertNotNull(result2.getUpdDt());
	}
	
	@Test
	public void select() {
		
		long aplySn = 0; // PK
		
		SprtBizAplyInfo result = mapper.select(aplySn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
	@Test
	public void insert_from() {
		
		long aplySn = 0; // FK
		{
			SprtBizAplyInfo info = new SprtBizAplyInfo();
			mapper.insert(info);
			aplySn = info.getAplySn(); // GeneratedKey
		}
		
		mapper.insert_edu(1L, "2", aplySn);
		mapper.insert_cnslt(3L, "4", "5", aplySn);
		mapper.insert_hope1("6", aplySn);
		mapper.insert_hope2("7", aplySn);
		mapper.insert_hope3("8", aplySn);
		mapper.insert_dln("9", aplySn);
		mapper.insert_pln("10", "11", aplySn);
	}
	
	@Test
	public void selectAplySn_from() {
		
		Assert.assertEquals(null, mapper.selectAplySn_edu(null, null));
		Assert.assertEquals(null, mapper.selectAplySn_cnslt(null, null, null));
		Assert.assertEquals(null, mapper.selectAplySn_hope1(null));
		Assert.assertEquals(null, mapper.selectAplySn_hope2(null));
		Assert.assertEquals(null, mapper.selectAplySn_hope3(null));
		Assert.assertEquals(null, mapper.selectAplySn_dln(null));
		Assert.assertEquals(null, mapper.selectAplySn_pln(null, null));
	}
	
}
