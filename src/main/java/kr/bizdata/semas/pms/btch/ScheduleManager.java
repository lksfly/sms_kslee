package kr.bizdata.semas.pms.btch;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kr.bizdata.core.config.Config;
import kr.bizdata.core.exception.BzdException;
import kr.bizdata.core.util.BeanUtil;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.btch.info.BtchJobLogInfo;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;
import kr.bizdata.semas.pms.btch.service.BtchJobLogService;
import kr.bizdata.semas.pms.btch.service.BtchJobRegService;
import kr.bizdata.semas.pms.job.info.JobResult;

/**
 * 스케줄 관리
 */
@Component
public class ScheduleManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<Long, ScheduledFuture<?>> scheduledMap = new ConcurrentHashMap<>();
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private BtchJobLogService btchJobLogService;
	@Autowired
	private BtchJobRegService btchJobRegService;
    
    /**
     * 웹 서버 시작 시 스케줄을 등록한다.
     */
    @EventListener
    public void initSchedule(ContextRefreshedEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("initSchedule()");
		}
		
		if (!Config.getBoolean("schedule.use", false)) { // 스케줄 사용 상태가 아닌 경우
			return;
		} else if (getScheduleCount() > 0) { // 중복실행 방지
			return;
		}
		logger.info("initSchedule() START");
		
		Map<String, Object> param = new HashMap<>();
		param.put("useYn", "Y"); // 사용여부
		
		List<BtchJobRegInfo> list = btchJobRegService.selectList(param);
		
		for (BtchJobRegInfo info : list) {
			try {
				addSchedule(info); // schedule 등록
			} catch (Exception e) {
				logger.error(e.getMessage(), e); // ignore
			}
		}
		
		logger.info("initSchedule() END : count = " + getScheduleCount());
    }
	
	/**
	 * 등록된 스케줄 수 
	 */
	public int getScheduleCount() {
		
		return scheduledMap.size();
	}

    /**
     * 스케줄 등록
     */
	public void addSchedule(BtchJobRegInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("addSchedule(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getJobId() > 0, "jobId is invalid.");
		
		if (!Config.getBoolean("schedule.use", false)) { // 스케줄 사용 상태가 아닌 경우
			return;
		} else if (!"Y".equals(info.getUseYn())) { // 배치작업등록 사용 상태가 아닌 경우
			return;
		}
		logger.info("addSchedule(" + info.getJobId() + ", " + info.getJobNm() + ") START");
		
		final long jobId = info.getJobId();
		final String jobNm = info.getJobNm();
		final String packageName = info.getPckgNm();
		final String className = info.getClsNm();
		final String methodName = info.getMthdNm();
		final String cronExpr = info.getCronExp();
		
		validateTarget(packageName, className, methodName); // 타겟 유효성 검사
		validateCronExpr(cronExpr); // 크론표현식 유효성 검사
		
		Runnable task = () -> {
			runJob(jobId, jobNm, packageName, className, methodName, true);
		};
		Trigger trigger = new CronTrigger(cronExpr);
		
		ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(task, trigger);
		
		if (scheduledMap.get(jobId) != null) {
			removeSchedule(jobId); // 삭제
		}
		scheduledMap.put(jobId, schedule); // 등록
		
		logger.info("addSchedule(" + info.getJobId() + ", " + info.getJobNm() + ") END : count = " + getScheduleCount());
	}
	
	/**
	 * 스케줄 삭제
	 */
	public void removeSchedule(long jobId) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeSchedule(" + jobId + ")");
		}
		Assert.isTrue(jobId > 0, "jobId is invalid.");
		
		if (!Config.getBoolean("schedule.use", false)) { // 스케줄 사용 상태가 아닌 경우
			return;
		} else if (scheduledMap.get(jobId) == null) { // 등록된 스케줄이 아닌 경우
			return;
		}
		logger.info("removeSchedule(" + jobId + ") START");
		
		
		try {
			scheduledMap.get(jobId).cancel(true); // mayInterruptIfRunning -> 실행중인 경우, 해당 쓰레드에서 InvocationTargetException 발생
			scheduledMap.remove(jobId);
		} catch (Exception e) {
			throw new BzdException("스케줄 삭제 오류");
		}
		
		logger.info("removeSchedule(" + jobId + ") END : count = " + getScheduleCount());
	}
	
	/**
	 * 작업 실행
	 */
	public void runJob(long jobId, String jobNm, String packageName, String className, String methodName, boolean isAuto) {
		logger.info("runJob(" + jobId + ", " + jobNm + ", " + packageName + ", " + className + ", " + methodName + ", " + isAuto + ") START");
		
		Calendar start = Calendar.getInstance(); // 작업 시작시간
		
		String rsltCd = null; // 실행결과코드 (S: 성공, E: 오류)
		String rsltCn = null; // 실행결과내용
		try {
			String classPath = packageName + "." + className;
			Object bean = findBean(classPath);
			Method method = findMethod(bean, methodName);
			JobResult result = (JobResult) method.invoke(bean, jobNm); // Method 호출
			
			rsltCd = result.getTotalCnt() == result.getSuccessCnt() ? "S" : "E";
			rsltCn = "작업명: " + result.getJobNm();
			if (result.getStartDt() != null && result.getEndDt() != null) {
				rsltCn += "\n기간: " + DateUtil.format(result.getStartDt(), "yyyy.MM.dd") + " ~ " + DateUtil.format(result.getEndDt(), "yyyy.MM.dd");
			}
			rsltCn += "\n대상수: " + result.getTotalCnt()
					+ "\n성공수: " + result.getSuccessCnt()
					+ "\n오류수: " + result.getErrorCnt();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			rsltCd = "E"; // E: 오류
			rsltCn = sw.toString();
		}
		
		Calendar end = Calendar.getInstance(); // 작업 종료시간
		
		rsltCn += "\n작업시간: " + formatPeriod(end.getTimeInMillis() - start.getTimeInMillis());
		
		BtchJobLogInfo info = new BtchJobLogInfo();
		info.setJobId(jobId);
		info.setBgngDt(start.getTime());
		info.setEndDt(end.getTime());
		info.setRsltCd(rsltCd);
		info.setRsltCn(rsltCn);
		try {
			info.setSrvrIpAddr(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) { logger.error(e.getMessage(), e); } // ignore
		info.setAutoYn(isAuto ? "Y" : "N"); // Y: 자동실행, N: 수동실행
		
		btchJobLogService.insert(info);
		
		logger.info("runJob(" + jobId + ", " + jobNm + ", " + packageName + ", " + className + ", " + methodName + ", " + isAuto + ") END");
	}
	
	/*
	 * 타겟 유효성 검사
	 */
	private void validateTarget(String packageName, String className, String methodName) {
		try {
			String classPath = packageName + "." + className;
			Object bean = findBean(classPath); // Class 체크
			findMethod(bean, methodName);      // Method 체크
		} catch (ClassNotFoundException ce) {
			throw new BzdException("패키지와 클래스를 확인해 주세요.");
		} catch (NoSuchMethodException me) {
			throw new BzdException("메소드를 확인해 주세요.");
		}
	}
	
	/*
	 * 크론표현식 유효성 검사
	 */
	private void validateCronExpr(String cronExpr) {
		if (StringUtils.isBlank(cronExpr)) {
			throw new BzdException("크론표현식을 확인해 주세요.");
		}
		try {
			new CronSequenceGenerator(cronExpr); // Spring 5.2
			//CronExpression.parse(cronExpr); // Spring 5.3
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BzdException("크론표현식을 확인해 주세요.");
		}
	}
	
	/*
	 * Class를 찾는다.
	 */
	private Object findBean(String classPath) throws ClassNotFoundException {
		
		return BeanUtil.getBean(Class.forName(classPath));
	}
	
	/*
	 * Class의 Method를 찾는다.
	 */
	private Method findMethod(Object bean, String methodName) throws NoSuchMethodException {
		
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new NoSuchMethodException();
	}
	
	/**
	 * 작업시간을 문자열로 변환한다.
	 * 
	 * @param millis 밀리초
	 * @return
	 */
	private String formatPeriod(long millis) {
		long sec = millis / 1000; // 작업시간 (단위: 초)
		String str = "";
		if (sec >= 3600) {
			str += (sec / 3600) + "시간";
			sec = sec % 3600;
		}
		if (sec >= 60) {
			str += str.length() > 0 ? " " : "";
			str += (sec / 60) + "분";
			sec = sec % 60;
		}
		if (sec > 0) {
			str += str.length() > 0 ? " " : "";
			str += sec + "초";
		}
		if (str.length() == 0) {
			str = millis + "밀리초";
		}
		return str;
	}
	
}
