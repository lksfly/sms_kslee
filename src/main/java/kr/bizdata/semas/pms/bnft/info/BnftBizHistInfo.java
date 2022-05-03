package kr.bizdata.semas.pms.bnft.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 수혜기업이력
 */
@Getter
@Setter
public class BnftBizHistInfo {
	
	private String brno;         // 사업자등록번호, PK
	private long entHistSn;      // 기업이력일련번호, PK, GeneratedKey
	private String entNm;        // 기업명
	private String crno;         // 법인등록번호
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
	
	public BnftBizHistInfo() {
		// 기본 생성자
	}
	
	public BnftBizHistInfo(BnftBizInfo old) {
		this.brno = old.getBrno(); // PK
		//this.entHistSn 생략 -> GeneratedKey
		this.entNm = old.getEntNm();
		this.crno = old.getCrno();
		//this.bzmnTypeCd = old.getBzmnTypeCd();
		this.zip = old.getZip();
		this.addr = old.getAddr();
		this.daddr = old.getDaddr();
		this.fndnYmd = old.getFndnYmd();
		this.slsAmt = old.getSlsAmt();
		this.ftWrkrCnt = old.getFtWrkrCnt();
		this.clsbizYmd = old.getClsbizYmd();
		this.ksicLclsCd = old.getKsicLclsCd();
		this.ksicMclsCd = old.getKsicMclsCd();
		this.ksicSclsCd = old.getKsicSclsCd();
		this.ksicCd = old.getKsicCd();
		this.telno = old.getTelno();
		this.ceoNm = old.getCeoNm();
		this.srcSysCd = old.getSrcSysCd();
		this.srcRegDt = old.getSrcRegDt();
	}
	
}
