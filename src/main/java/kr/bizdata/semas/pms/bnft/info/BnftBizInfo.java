package kr.bizdata.semas.pms.bnft.info;

import java.util.Date;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 수혜기업기본
 */
@Getter
@Setter
public class BnftBizInfo {
	
	private String brno;         // 사업자등록번호, PK
	private String entNm;        // 기업명
	private String crno;         // 법인등록번호
	private String bzmnTypeCd;   // 사업자유형코드
	private String zip;          // 우편번호
	private String addr;         // 기본주소
	private String daddr;        // 상세주소
	private String fndnYmd;      // 설립일자
	private Long slsAmt;         // 매출액
	private Long ftWrkrCnt;      // 상시근로자수
	private String clsbizYmd;    // 폐업일자
	private String ksicLclsCd;   // 표준산업분류대분류코드
	private String ksicMclsCd;   // 표준산업분류중분류코드
	private String ksicSclsCd;   // 표준산업분류소분류코드
	private String ksicCd;       // 표준산업분류코드
	private String telno;        // 전화번호
	private String ceoNm;        // 대표자명
	private String srcSysCd;     // 출처시스템코드
	private Date srcRegDt;       // 출처시스템등록일시
	private Date regDt;          // 등록일시
	private Date updDt;          // 수정일시
	
	public BnftBizInfo() {
		// 기본 생성자
	}
	
	public BnftBizInfo(FromInfo from) {
		this.brno = from.getBrno(); // PK
		this.entNm = from.getEntNm();
		this.crno = from.getCrno();
		this.bzmnTypeCd = from.getBzmnTypeCd();
		this.zip = from.getEntZip();
		this.addr = from.getEntAddr();
		this.daddr = from.getEntDaddr();
		this.fndnYmd = from.getFndnYmd();
		this.slsAmt = from.getSlsAmt();
		this.ftWrkrCnt = from.getFtWrkrCnt();
		this.clsbizYmd = from.getClsbizYmd();
		this.ksicLclsCd = from.getKsicLclsCd();
		this.ksicMclsCd = from.getKsicMclsCd();
		this.ksicSclsCd = from.getKsicSclsCd();
		this.ksicCd = from.getKsicCd();
		this.telno = from.getEntTelno();
		this.ceoNm = from.getCeoNm();
		this.srcSysCd = from.getSrcSysCd();
		this.srcRegDt = from.getSrcRegDt();
	}
	
	public BnftBizInfo(FromInfo from, BnftBizInfo old) {
		this(from);
		this.regDt = old.getRegDt();
		this.updDt = old.getUpdDt();
	}
	
}
