package kr.bizdata.semas.from.hope.info;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 희망리턴패키지시스템 (컨설팅)
 */
@Getter
@Setter
public class FromHope1Info extends FromInfo {
	
	/*
	 * 지원사업기본
	 */
	private String hopeSprtBizno; // 희망리턴지원사업번호
	
	/*
	 * 컨설팅지원사업신청내역
	 */
	private String hopeCnsltAplyNo; // 희망리턴컨설팅신청번호
	
}
