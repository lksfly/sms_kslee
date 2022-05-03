package kr.bizdata.semas.from;

import java.util.Date;

import kr.bizdata.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FromInfo {
	
	/*
	 * 지원사업기본
	 */
	private String sprtBizNm;    // 지원사업명
	private String sprtBizYr;    // 지원사업연도
	private String srcSysCd;     // 출처시스템코드 (01: 교육, 02: 컨설팅, 03: 희망리턴, 04: 직접대출, 05: 대리대출)
	private String sprtBizDivCd; // 지원사업구분코드 (01: 교육, 02: 컨설팅, 03: 사업정리, 04: 점포정리, 05: 법률자문, 06: 재기교육, 07: 재도전장려금, 08: 전직장려금, 09: 노란우산, 10: 직접대출, 11: 대리대출)
	
	/*
	 * 지원사업신청내역
	 */
	private String sprtCntrCd;   // 지원센터코드
	private String aplyStsCd;    // 신청상태코드
	private String brno;         // 사업자등록번호
	private String entNm;        // 기업명
	private String crno;         // 법인등록번호
	private String bzmnTypeCd;   // 사업자유형코드
	private String entZip;       // 기업우편번호
	private String entAddr;      // 기업기본주소
	private String entDaddr;     // 기업상세주소
	private String fndnYmd;      // 설립일자
	private Long slsAmt;         // 매출액
	private Long ftWrkrCnt;      // 상시근로자수
	private String clsbizYmd;    // 폐업일자
	private String ksicLclsCd;   // 표준산업분류대분류코드
	private String ksicMclsCd;   // 표준산업분류중분류코드
	private String ksicSclsCd;   // 표준산업분류소분류코드
	private String ksicCd;       // 표준산업분류코드
	private String ksicSrchCd;   // 표준산업분류 검색용 코드 (업종코드 또는 표준산업분류코드)
	private String entTelno;     // 기업전화번호
	private String diVal;        // DI값
	private String aplcntNm;     // 신청자명
	private Long giveAmt;        // 지급금액
	private String aplcntEml;    // 신청자이메일
	private String aplcntTelno;  // 신청자전화번호
	private String aplcntMbno;   // 신청자핸드폰
	private String aplcntZip;    // 신청자우편번호
	private String aplcntAddr;   // 신청자기본주소
	private String aplcntDaddr;  // 신청자상세주소
	private String aplcntBrdt;   // 신청자생년월일
	private String aplcntGndrCd; // 신청자성별코드
	private String ciVal;        // CI값
	private String srcLgnId;     // 출처시스템로그인ID
	private String aplyYmd;      // 신청일자
	private String giveYmd;      // 지급일자
	private String ceoNm;        // 대표자명
	private String fnshYmd;      // 수료일자
	private Date srcRegDt;       // 출처시스템등록일시
	private String slctnYmd;     // 선정일자
	
	public void setFndnYmd(String fndnYmd) {
		if (DateUtil.isYmd(fndnYmd)) {
			this.fndnYmd = fndnYmd;
		}
	}
	public void setClsbizYmd(String clsbizYmd) {
		if (DateUtil.isYmd(clsbizYmd)) {
			this.clsbizYmd = clsbizYmd;
		}
	}
	public void setAplyYmd(String aplyYmd) {
		if (DateUtil.isYmd(aplyYmd)) {
			this.aplyYmd = aplyYmd;
		}
	}
	public void setGiveYmd(String giveYmd) {
		if (DateUtil.isYmd(giveYmd)) {
			this.giveYmd = giveYmd;
		}
	}
	public void setFnshYmd(String fnshYmd) {
		if (DateUtil.isYmd(fnshYmd)) {
			this.fnshYmd = fnshYmd;
		}
	}
	
	public void setSlctnYmd(String slctnYmd) {
		if (DateUtil.isYmd(slctnYmd)) {
			this.slctnYmd = slctnYmd;
		}
	}
	
	public void setAplcntBrdt(String aplcntBrdt) {
		if (DateUtil.isYmd(aplcntBrdt)) {
			this.aplcntBrdt = aplcntBrdt;
		}
	}
}
