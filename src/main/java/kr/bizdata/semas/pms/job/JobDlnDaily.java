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
import kr.bizdata.semas.from.dln.info.FromDlnInfo;
import kr.bizdata.semas.from.dln.service.FromDlnService;
import kr.bizdata.semas.pms.job.info.JobResult;
import kr.bizdata.semas.pms.job.service.JobProcessService;

/**
 * 직접대출시스템 일배치
 */
@Component
public class JobDlnDaily {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromDlnService fromService;
	@Autowired
	private JobProcessService jobProcessService;
	
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
		// 직접대출은 개발 DB 서버가 오라클 이기 때문에 환경값을 가져와서 암호화 함수(개발=_TX필드/운영=암호화함수)를 이용하기 위하여 메소드를 분기 처리를 해야 함. 
		boolean isProdUse = Config.getBoolean("semas.prod.use", false);
		
		Date current = new Date(); // 현재
		Date startDt = getStartDt(current); // 기간 시작일
		Date endDt = getEndDt(current); // 기간 종료일
		
		int tCnt = 0; // 대상수
		int sCnt = 0; // 성공수
		int eCnt = 0; // 오류수
		{
			List<FromDlnInfo> list = fromService.selectList(startDt, endDt, "1", isProdUse); // 직접대출 승인
			for (FromDlnInfo from : list) {
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
