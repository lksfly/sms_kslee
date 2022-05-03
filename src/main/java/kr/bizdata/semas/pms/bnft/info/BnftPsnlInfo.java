package kr.bizdata.semas.pms.bnft.info;

import java.util.Date;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 수혜개인기본
 */
@Getter
@Setter
public class BnftPsnlInfo {
	
	private String diVal;        // DI값, PK
	private String psnlNm;       // 개인이름
	private String eml;          // 이메일
	private String mbno;         // 핸드폰
	private String telno;        // 전화번호
	private String zip;          // 우편번호
	private String addr;         // 기본주소
	private String daddr;        // 상세주소
	private String brdt;         // 생년월일
	private String gndrCd;       // 성별코드
	private String ciVal;        // CI값
	private String srcSysCd;     // 출처시스템코드
	private String srcLgnId;     // 출처시스템로그인ID
	private Date srcRegDt;       // 출처시스템등록일시
	private Date regDt;          // 등록일시
	private Date updDt;          // 수정일시
	
	public BnftPsnlInfo() {
		// 기본 생성자
	}
	
	public BnftPsnlInfo(FromInfo from) {
		this.diVal = from.getDiVal(); // PK
		this.psnlNm = from.getAplcntNm();
		this.eml = from.getAplcntEml();
		this.mbno = from.getAplcntMbno();
		this.telno = from.getAplcntTelno();
		this.zip = from.getAplcntZip();
		this.addr = from.getAplcntAddr();
		this.daddr = from.getAplcntDaddr();
		this.brdt = from.getAplcntBrdt();
		this.gndrCd = from.getAplcntGndrCd();
		this.ciVal = from.getCiVal();
		this.srcSysCd = from.getSrcSysCd();
		this.srcLgnId = from.getSrcLgnId();
		this.srcRegDt = from.getSrcRegDt();
	}
	
	public BnftPsnlInfo(FromInfo from, BnftPsnlInfo old) {
		this(from);
		this.regDt = old.getRegDt();
		this.updDt = old.getUpdDt();
	}
	
}
