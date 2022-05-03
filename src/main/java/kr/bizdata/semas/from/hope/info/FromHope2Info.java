package kr.bizdata.semas.from.hope.info;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 희망리턴패키지시스템 (재기교육)
 */
@Getter
@Setter
public class FromHope2Info extends FromInfo {
	
	/*
	 * 지원사업기본
	 */
	private String hopeSprtBizno; // 희망리턴지원사업번호
	
	/*
	 * 재기교육지원사업신청내역
	 */
	private String reduCrsAplyNo; // 재기교육수강신청번호
	
}
