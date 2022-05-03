package kr.bizdata.semas.pms.cmn.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 공통코드
 */
@Getter
@Setter
public class CmnCdInfo {
	
	private String cmnDivCd; // 공통구분코드
	private String cmnCd;    // 공통코드
	private String cmnCdNm;  // 공통코드명
	private Date regDt;      // 등록일시
	
}
