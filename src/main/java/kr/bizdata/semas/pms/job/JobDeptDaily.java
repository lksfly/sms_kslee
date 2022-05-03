package kr.bizdata.semas.pms.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.bizdata.semas.from.smbi.info.FromDeptInfo;
import kr.bizdata.semas.from.smbi.service.FromDeptService;
import kr.bizdata.semas.pms.job.info.JobResult;
import kr.bizdata.semas.pms.job.service.JobProcessService;

/**
 * 공단부서정보 일배치
 */
@Component
public class JobDeptDaily {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromDeptService fromService;
	@Autowired
	private JobProcessService jobProcessService;
	
	/**
	 * 작업 실행
	 */
	public JobResult run(String jobNm) {
		if (logger.isDebugEnabled()) {
			logger.debug("run(" + jobNm + ")");
		}
		
		int tCnt = 0; // 대상수
		int sCnt = 0; // 성공수
		int eCnt = 0; // 오류수
		{
			List<FromDeptInfo> list = fromService.selectList(); // 공단부서정보
			for (FromDeptInfo from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		}
		
		JobResult result = new JobResult(jobNm);
		result.setTotalCnt(tCnt);
		result.setSuccessCnt(sCnt);
		result.setErrorCnt(eCnt);
		
		return result;
	}
	
}
