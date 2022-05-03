package kr.bizdata.semas.pms.sprt.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 지원사업명관리기본
 */
@Getter
@Setter
public class SprtBizNmMngInfo {
	
	private long sprtBizNmSn;    // 지원사업명일련번호, PK
	private String sprtBizNm;    // 지원사업명
	private String sprtBizDivCd; // 지원사업구분코드
	private Date regDt;          // 등록일시
	
}
