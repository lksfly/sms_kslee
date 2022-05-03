package kr.bizdata.semas.from.cnslt.info;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 컨설팅시스템
 */
@Getter
@Setter
public class FromCnsltInfo extends FromInfo {
	
	/*
	 * 지원사업기본
	 */
	private Long sprtBizNmSn; // 지원사업명일련번호
	
	/*
	 * 지원사업신청내역
	 */
	private Long cnsltAplySn; // 컨설팅신청일련번호
	private String actvtYr;   // 활동연도
	private String plcyCd;    // 정책코드
	
}
