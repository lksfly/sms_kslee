package kr.bizdata.semas.pms.btch;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.exception.BzdException;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;

/**
 * [주의] Thread 내의 트랜잭션은 롤백 안 됨.
 */
public class ScheduleManagerTest extends AbstractTest {
	
	@Autowired
	private ScheduleManager scheduleManager;
	
	@Test
	public void getScheduleCount() {
		
		int result = scheduleManager.getScheduleCount();
		logger.info("result = " + result);
	}
	
	@Test
	public void addSchedule() {
		
		System.setProperty("schedule.use", "true"); // 테스트 환경설정
		
		BtchJobRegInfo info = new BtchJobRegInfo();
		info.setJobId(1);
		info.setUseYn("Y");
		info.setJobNm("테스트 작업 1");
		info.setPckgNm("kr.bizdata.semas.pms.job");
		info.setClsNm("TestJob1");
		info.setMthdNm("run");
		info.setCronExp("1/4 * * ? * *");
		
		scheduleManager.addSchedule(info);
		
		int result = scheduleManager.getScheduleCount();
		logger.info("result = " + result);
		Assert.assertTrue(result > 0);
		
		BtchJobRegInfo info2 = new BtchJobRegInfo();
		info2.setJobId(2);
		info2.setUseYn("Y");
		info2.setJobNm("테스트 작업 2");
		info2.setPckgNm("kr.bizdata.semas.pms.job");
		info2.setClsNm("TestJob2");
		info2.setMthdNm("run");
		info2.setCronExp("2/4 * * ? * *");
		
		scheduleManager.addSchedule(info2);
		
		BtchJobRegInfo info3 = new BtchJobRegInfo();
		info3.setJobId(3);
		info3.setUseYn("Y");
		info3.setJobNm("테스트 작업 3");
		info3.setPckgNm("kr.bizdata.semas.pms.job");
		info3.setClsNm("TestJob3");
		info3.setMthdNm("run");
		info3.setCronExp("3/4 * * ? * *");
		
		scheduleManager.addSchedule(info3);
		
		BtchJobRegInfo info4 = new BtchJobRegInfo();
		info4.setJobId(4);
		info4.setUseYn("Y");
		info4.setJobNm("테스트 작업 4");
		info4.setPckgNm("kr.bizdata.semas.pms.job");
		info4.setClsNm("TestJob4");
		info4.setMthdNm("run");
		info4.setCronExp("4/4 * * ? * *");
		
		scheduleManager.addSchedule(info4);
		
		try {
			Thread.sleep(6000);
//			scheduleManager.removeSchedule(1);
//			scheduleManager.removeSchedule(2);
//			scheduleManager.removeSchedule(3);
//			scheduleManager.removeSchedule(4);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new BzdException();
		}
	}
	
	@Test
	public void addSchedule2() {
		
		System.setProperty("schedule.use", "true"); // 테스트 환경설정
		
		BtchJobRegInfo info = new BtchJobRegInfo();
		info.setJobId(1);
		info.setUseYn("Y");
		info.setJobNm("테스트 작업 1");
		info.setPckgNm("kr.bizdata.semas.pms.job");
		info.setClsNm("TestJob1");
		info.setMthdNm("run");
		info.setCronExp("1/4 * * ? * THUL"); // SUN = 7, MON = 1
		
		scheduleManager.addSchedule(info);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new BzdException();
		}
	}
	
	@Test
	public void addSchedule3() {
		
		System.setProperty("schedule.use", "true"); // 테스트 환경설정
		
		BtchJobRegInfo info = new BtchJobRegInfo();
		info.setJobId(1);
		info.setUseYn("Y");
		info.setJobNm("테스트 작업 1");
		info.setPckgNm("kr.bizdata.semas.pms.job");
		info.setClsNm("JobEduDaily");
		info.setMthdNm("run");
		info.setCronExp("1/4 * * ? * *");
		
		scheduleManager.addSchedule(info);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new BzdException();
		}
	}
	
}
