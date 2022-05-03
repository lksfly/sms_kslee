package kr.bizdata.semas.pms.job;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.bizdata.core.config.Config;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.semas.from.edu.info.FromEduInfo;
import kr.bizdata.semas.from.edu.service.FromEduService;
import kr.bizdata.semas.from.smbi.service.VSedaLoginService;
import kr.bizdata.semas.pms.job.info.JobResult;
import kr.bizdata.semas.pms.job.service.JobProcessService;

/**
 * 교육시스템 일배치
 */
@Component
public class JobEduDaily {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromEduService fromService;
	@Autowired
	private JobProcessService jobProcessService;
	@Autowired
	private VSedaLoginService encService;
	
	// 기간 시작일
	private Date getStartDt(Date date) {
		if (DateUtil.isLastDayOfMonth(date, Calendar.SUNDAY)) { // 매월 마지막 일요일
			return DateUtil.addDays(date, Config.getInt("bzd.batch.monthly.start.term", -90));
		} else {
			return DateUtil.addDays(date, Config.getInt("bzd.batch.daily.start.term", -8));
		}
	}
	
	// 기간 종료일
	private Date getEndDt(Date date) {
		return DateUtil.addDays(date, Config.getInt("bzd.batch.daily.end.term", -1));
	}
	
	/**
	 * 작업 실행
	 */
	public JobResult run(String jobNm) {
		if (logger.isDebugEnabled()) {
			logger.debug("run(" + jobNm + ")");
		}
		
		boolean isProdUse = Config.getBoolean("semas.prod.use", false);
		// 테스트 서버에서 복호화 함수를 사용하기 위하여 암호화 함수를 한번 사용해야 하기 때문에 아래와 같은 로직 추가.
		if(!isProdUse) encService.selectENC();
		
		Date current = new Date(); // 현재
		Date startDt = getStartDt(current); // 기간 시작일
		Date endDt = getEndDt(current); // 기간 종료일
		
		int tCnt = 0; // 대상수
		int sCnt = 0; // 성공수
		int eCnt = 0; // 오류수
		
		{
			List<FromEduInfo> list = fromService.selectList(startDt, endDt, "1"); // 지급온라인
			for (FromEduInfo from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} 
		{
			List<FromEduInfo> list = fromService.selectList(startDt, endDt, "2"); // 온라인
			for (FromEduInfo from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		}
		{
			List<FromEduInfo> list = fromService.selectList(startDt, endDt, "3"); // 오프라인
			for (FromEduInfo from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		}
		
		JobResult result = new JobResult(jobNm);
		result.setStartDt(startDt);
		result.setEndDt(endDt);
		result.setTotalCnt(tCnt);
		result.setSuccessCnt(sCnt);
		result.setErrorCnt(eCnt);
		
		return result;
	}
	
}