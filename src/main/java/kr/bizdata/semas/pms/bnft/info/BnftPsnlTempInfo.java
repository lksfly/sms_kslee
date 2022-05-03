package kr.bizdata.semas.pms.bnft.info;

import java.util.Date;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 수혜개인임시
 */
@Getter
@Setter
public class BnftPsnlTempInfo {
	
	private long psnlSn;         // 개인일련번호, PK, GeneratedKey
	private Long aplySn;         // 신청일련번호
	private String brno;         // 사업자등록번호
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
	
	public BnftPsnlTempInfo() {
		// 기본 생성자
	}
	
	public BnftPsnlTempInfo(FromInfo from, Long aplySn) {
		//this.psnlSn 생략 -> GeneratedKey
		this.aplySn = aplySn;
		this.brno = from.getBrno();
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
	
}
