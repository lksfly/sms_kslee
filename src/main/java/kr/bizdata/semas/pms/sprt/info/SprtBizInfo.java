package kr.bizdata.semas.pms.sprt.info;

import java.util.Date;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 지원사업기본
 */
@Getter
@Setter
public class SprtBizInfo {
	
	private long sprtBizSn;      // 지원사업일련번호, PK, GeneratedKey
	private String sprtBizNm;    // 지원사업명
	private String sprtBizYr;    // 지원사업연도
	private String srcSysCd;     // 출처시스템코드
	private String sprtBizDivCd; // 지원사업구분코드
	private Date regDt;          // 등록일시
	
	public SprtBizInfo() {
		// 기본 생성자
	}
	
	public SprtBizInfo(FromInfo from) {
		//this.sprtBizSn -> 생략 // GeneratedKey
		this.sprtBizNm = from.getSprtBizNm();
		this.sprtBizYr = from.getSprtBizYr();
		this.srcSysCd = from.getSrcSysCd();
		this.sprtBizDivCd = from.getSprtBizDivCd();
	}
	
	public SprtBizInfo(FromInfo from, SprtBizInfo old) {
		this(from);
		this.sprtBizSn = old.getSprtBizSn(); // PK
		this.regDt = old.getRegDt();
	}
	
}
