package kr.bizdata.semas.pms.bnft.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 수혜개인이력
 */
@Getter
@Setter
public class BnftPsnlHistInfo {
	
	private String diVal;        // DI값, PK
	private long psnlHistSn;     // 개인이력일련번호, PK, GeneratedKey
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
	
	public BnftPsnlHistInfo() {
		// 기본 생성자
	}
	
	public BnftPsnlHistInfo(BnftPsnlInfo old) {
		this.diVal = old.getDiVal(); // PK
		//this.psnlHistSn 생략 -> GeneratedKey
		this.psnlNm = old.getPsnlNm();
		this.eml = old.getEml();
		this.mbno = old.getMbno();
		this.telno = old.getTelno();
		this.zip = old.getZip();
		this.addr = old.getAddr();
		this.daddr = old.getDaddr();
		this.brdt = old.getBrdt();
		this.gndrCd = old.getGndrCd();
		this.ciVal = old.getCiVal();
		this.srcSysCd = old.getSrcSysCd();
		this.srcLgnId = old.getSrcLgnId();
		this.srcRegDt = old.getSrcRegDt();
	}
	
}
