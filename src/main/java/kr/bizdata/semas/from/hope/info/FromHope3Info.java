package kr.bizdata.semas.from.hope.info;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 희망리턴패키지시스템 (장려금) - 재도전장려금, 전직장려금
 */
@Getter
@Setter
public class FromHope3Info extends FromInfo {
	
	/*
	 * 장려금사업기본
	 */
	private Long sprtBizNmSn; // 지원사업명일련번호
	
	/*
	 * 장려금지원사업신청내역
	 */
	private String sbsdyAplyNo; // 장려금신청번호
	
}
