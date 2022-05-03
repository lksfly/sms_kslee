package kr.bizdata.semas.from.edu.info;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 교육시스템
 */
@Getter
@Setter
public class FromEduInfo extends FromInfo {
	
	/*
	 * 지원사업기본
	 */
	private Long eduSprtBizno; // 교육지원사업번호
	
	/*
	 * 지원사업신청내역
	 */
	private Long prgrmSn;  // 프로그램순번
	private String mberSn; // 회원순번
	
}
