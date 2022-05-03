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
import kr.bizdata.semas.from.hope.info.FromHope1Info;
import kr.bizdata.semas.from.hope.info.FromHope2Info;
import kr.bizdata.semas.from.hope.info.FromHope3Info;
import kr.bizdata.semas.from.hope.service.FromHope1Service;
import kr.bizdata.semas.from.hope.service.FromHope2Service;
import kr.bizdata.semas.from.hope.service.FromHope3Service;
import kr.bizdata.semas.from.smbi.service.VSedaLoginService;
import kr.bizdata.semas.pms.job.info.JobResult;
import kr.bizdata.semas.pms.job.service.JobProcessService;

/**
 * 희망리턴패키지시스템 일배치
 */
@Component
public class JobHopeDaily {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromHope1Service fromService1;
	@Autowired
	private FromHope2Service fromService2;
	@Autowired
	private FromHope3Service fromService3;
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
		
		// 1. 희망리턴패키지시스템 (컨설팅)
		{
			List<FromHope1Info> list = fromService1.selectList(startDt, endDt, "1"); // 사업정리 승인
			for (FromHope1Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope1Info> list = fromService1.selectList(startDt, endDt, "2"); // 사업정리 취소
			for (FromHope1Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope1Info> list = fromService1.selectList(startDt, endDt, "3"); // 점포철거 승인
			for (FromHope1Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope1Info> list = fromService1.selectList(startDt, endDt, "4"); // 점포철거 취소
			for (FromHope1Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope1Info> list = fromService1.selectList(startDt, endDt, "5"); // 법률자문 승인
			for (FromHope1Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope1Info> list = fromService1.selectList(startDt, endDt, "6"); // 법률자문 취소	
			for (FromHope1Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		}
		// 2. 희망리턴패키지시스템 (재기교육)
		{
			List<FromHope2Info> list = fromService2.selectList(startDt, endDt, "1"); // 재기교육 승인
			for (FromHope2Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		}
		// 3. 희망리턴패키지시스템 (장려금)
		{
			List<FromHope3Info> list = fromService3.selectList(startDt, endDt, "1"); // 재도전장려금 승인
			for (FromHope3Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope3Info> list = fromService3.selectList(startDt, endDt, "2"); // 재도전장려금 취소
			for (FromHope3Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope3Info> list = fromService3.selectList(startDt, endDt, "3"); // 전직장려금 승인
			for (FromHope3Info from : list) {
				try {
					jobProcessService.process(from); ++sCnt; // 데이터 처리
				} catch (Exception e) {
					logger.error(e.getMessage(), e); ++eCnt; // ignore
				}
			}
			tCnt += list.size();
		} {
			List<FromHope3Info> list = fromService3.selectList(startDt, endDt, "4"); // 전직장려금 취소
			for (FromHope3Info from : list) {
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
