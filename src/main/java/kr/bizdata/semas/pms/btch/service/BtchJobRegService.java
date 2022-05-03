package kr.bizdata.semas.pms.btch.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.EscapeUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.btch.ScheduleManager;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;
import kr.bizdata.semas.pms.btch.mapper.BtchJobRegMapper;

/**
 * 배치작업등록
 */
@Service
public class BtchJobRegService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BtchJobRegMapper mapper;
	
	@Autowired
	private ScheduleManager scheduleManager;
    
	/**
	 * 배치작업등록 목록 저장
	 * 
	 * @param list
	 */
	public void save(BtchJobRegInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("save(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		
		if (info.getJobId() == 0) {
			mapper.insert(info); // 추가, GeneratedKey: jobId
		} else {
			mapper.update(info); // 수정
		}
		
		if ("Y".equals(info.getUseYn())) {
			scheduleManager.addSchedule(info); // schedule 등록
		} else {
			scheduleManager.removeSchedule(info.getJobId()); // schedule 삭제
		}
	}
	
	/**
	 * 배치작업등록 목록 삭제
	 * 
	 * @param jobIds
	 * @param mdfcnUserId
	 */
	public void deleteList(Long[] jobIds, String mdfcnUserId) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteList(" + JsonUtil.toString(jobIds) + ", " + mdfcnUserId + ")");
		}
		Assert.notNull(jobIds, "jobIds is null.");
		
		for (long jobId : jobIds) {
			mapper.delete(jobId, mdfcnUserId);
			
			scheduleManager.removeSchedule(jobId); // schedule 삭제
		}
	}
	
	/**
	 * 배치작업등록 정보 조회
	 * 
	 * @param jobId
	 * @return
	 */
	public BtchJobRegInfo select(long jobId) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + jobId + ")");
		}
		Assert.isTrue(jobId > 0, "jobId is invalid.");
		
		BtchJobRegInfo info = mapper.select(jobId);
		if (info == null) {
			throw new BzdNotFoundException("배치작업등록 정보를 찾을 수 없습니다. (jobId: " + jobId + ")");
		}
		return info;
	}

	/**
	 * 배치작업등록 목록 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<BtchJobRegInfo> selectList(Map<String, Object> param) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + JsonUtil.toString(param) + ")");
		}
		Assert.notNull(param, "param is null.");
		
		param.put("jobNm", EscapeUtil.escapeSqlLike((String) param.get("jobNm")));
		param.put("clsNm", EscapeUtil.escapeSqlLike((String) param.get("clsNm")));
		param.put("mthdNm", EscapeUtil.escapeSqlLike((String) param.get("mthdNm")));
		
		return mapper.selectList(param);
	}
	
	/**
	 * 배치작업등록 목록 실행
	 * 
	 * @param jobIds
	 */
	public void runBtchJobList(Long[] jobIds) {
		if (logger.isDebugEnabled()) {
			logger.debug("runBtchJobList(" + JsonUtil.toString(jobIds) + ")");
		}
		Assert.notNull(jobIds, "jobIds is null.");
		
		for (long jobId : jobIds) {
			BtchJobRegInfo info = select(jobId);
			
			scheduleManager.runJob(jobId, info.getJobNm(), info.getPckgNm(), info.getClsNm(), info.getMthdNm(), false); // Job 실행
		}
	}
	
}
