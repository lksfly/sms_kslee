package kr.bizdata.semas.pms.btch.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 배치작업등록
 */
@Getter
@Setter
public class BtchJobRegInfo {
	
	private long jobId; // GeneratedKey
	private Date regDt;
	private String regUserId;
	private Date mdfcnDt;
	private String mdfcnUserId;
	private String delYn;
	private String useYn;
	private String jobNm;
	private String pckgNm;
	private String clsNm;
	private String mthdNm;
	private String cronExp;
	
}
