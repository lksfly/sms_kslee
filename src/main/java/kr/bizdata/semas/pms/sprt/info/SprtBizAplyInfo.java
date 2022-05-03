package kr.bizdata.semas.pms.sprt.info;

import java.util.Date;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 지원사업신청내역
 */
@Getter
@Setter
public class SprtBizAplyInfo {
	
	private long aplySn;         // 신청일련번호, PK, GeneratedKey
	private Long sprtBizSn;      // 지원사업일련번호
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
	private Date regDt;          // 등록일시
	private Date updDt;          // 수정일시
	private String slctnYmd;     // 선정일자
	
	public SprtBizAplyInfo() {
		// 기본 생성자
	}
	
	public SprtBizAplyInfo(FromInfo from, long sprtBizSn) {
		//this.aplySn -> 생략 // GeneratedKey
		this.sprtBizSn = sprtBizSn;
		this.sprtCntrCd = from.getSprtCntrCd();
		this.aplyStsCd = from.getAplyStsCd();
		this.brno = from.getBrno();
		this.entNm = from.getEntNm();
		this.crno = from.getCrno();
		this.bzmnTypeCd = from.getBzmnTypeCd();
		this.entZip = from.getEntZip();
		this.entAddr = from.getEntAddr();
		this.entDaddr = from.getEntDaddr();
		this.fndnYmd = from.getFndnYmd();
		this.slsAmt = from.getSlsAmt();
		this.ftWrkrCnt = from.getFtWrkrCnt();
		this.clsbizYmd = from.getClsbizYmd();
		this.ksicLclsCd = from.getKsicLclsCd();
		this.ksicMclsCd = from.getKsicMclsCd();
		this.ksicSclsCd = from.getKsicSclsCd();
		this.ksicCd = from.getKsicCd();
		this.entTelno = from.getEntTelno();
		this.diVal = from.getDiVal();
		this.aplcntNm = from.getAplcntNm();
		this.giveAmt = from.getGiveAmt();
		this.aplcntEml = from.getAplcntEml();
		this.aplcntTelno = from.getAplcntTelno();
		this.aplcntMbno = from.getAplcntMbno();
		this.aplcntZip = from.getAplcntZip();
		this.aplcntAddr = from.getAplcntAddr();
		this.aplcntDaddr = from.getAplcntDaddr();
		this.aplcntBrdt = from.getAplcntBrdt();
		this.aplcntGndrCd = from.getAplcntGndrCd();
		this.ciVal = from.getCiVal();
		this.srcLgnId = from.getSrcLgnId();
		this.aplyYmd = from.getAplyYmd();
		this.giveYmd = from.getGiveYmd();
		this.ceoNm = from.getCeoNm();
		this.fnshYmd = from.getFnshYmd();
		this.srcRegDt = from.getSrcRegDt();
		this.slctnYmd = from.getSlctnYmd();
	}
	
	public SprtBizAplyInfo(FromInfo from, long sprtBizSn, SprtBizAplyInfo old) {
		this(from, sprtBizSn);
		this.aplySn = old.getAplySn(); // PK
		this.regDt = old.getRegDt();
		this.updDt = old.getUpdDt();
	}
	
}
