package kr.bizdata.semas.pms.job.info;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 배치작업결과
 */
@Getter
@Setter
public class JobResult {
	
	private String jobNm;
	private Date startDt;
	private Date endDt;
	private int totalCnt;
	private int successCnt;
	private int errorCnt;
	
	public JobResult(String jobNm) {
		this.jobNm = jobNm;
	}
	
}
