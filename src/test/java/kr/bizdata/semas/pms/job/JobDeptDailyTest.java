package kr.bizdata.semas.pms.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.job.info.JobResult;

/**
 * 공단부서정보 일배치
 */
public class JobDeptDailyTest extends AbstractTest {
	
	@Autowired
	private JobDeptDaily job;
	
	@Test @Rollback(false)
	public void run() {
		
		JobResult result = job.run("공단부서정보 일배치 테스트");
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
