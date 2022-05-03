package kr.bizdata.semas.pms.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.job.info.JobResult;

/**
 * 컨설팅시스템 일배치
 */
public class JobCnsltDailyTest extends AbstractTest {
	
	@Autowired
	private JobCnsltDaily job;
	
	@Test @Rollback(false)
	public void run() {
		
		JobResult result = job.run("컨설팅시스템 일배치 테스트");
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
