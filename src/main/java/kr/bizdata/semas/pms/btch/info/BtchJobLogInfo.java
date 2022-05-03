package kr.bizdata.semas.pms.btch.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 배치작업로그
 */
@Getter
@Setter
public class BtchJobLogInfo {
	
	private long jobSn; // GeneratedKey
	private long jobId;
	private Date bgngDt;
	private Date endDt;
	private String rsltCd;
	private String rsltCn;
	private String srvrIpAddr;
	private String autoYn;
	
	// 목록 조회시
	private int no; // 행 번호
	private String rsltNm; // 실행결과명
	
	// 배치작업등록 정보
	private String jobNm;
	private String pckgNm;
	private String clsNm;
	private String mthdNm;
	
}
