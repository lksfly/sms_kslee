package kr.bizdata.semas.pms.btch.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.model.Paging;
import kr.bizdata.core.util.EscapeUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.btch.info.BtchJobLogInfo;
import kr.bizdata.semas.pms.btch.mapper.BtchJobLogMapper;

/**
 * 배치작업로그
 */
@Service
public class BtchJobLogService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BtchJobLogMapper mapper;
	
	/**
	 * 배치작업로그 추가
	 * 
	 * @param info
	 */
	public void insert(BtchJobLogInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getJobSn() == 0, "jobSn is invalid.");
		
		mapper.insert(info); // 추가, GeneratedKey: jobSn
	}
	
	/**
	 * 배치작업로그 정보 조회
	 * 
	 * @param jobSn
	 * @return
	 */
	public BtchJobLogInfo select(long jobSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + jobSn + ")");
		}
		Assert.isTrue(jobSn > 0, "jobSn is invalid.");
		
		BtchJobLogInfo info = mapper.select(jobSn);
		if (info == null) {
			throw new BzdNotFoundException("배치작업로그 정보를 찾을 수 없습니다. (jobSn: " + jobSn + ")");
		}
		return info;
	}
	
	/**
	 * 배치작업로그 목록 조회 (전체/페이징)
	 * 
	 * @param param
	 * @param paging
	 * @return
	 */
	public List<BtchJobLogInfo> selectList(Map<String, Object> param, Paging paging) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + JsonUtil.toString(param) + ", " + JsonUtil.toString(paging) + ")");
		}
		Assert.notNull(param, "param is null.");
		Assert.notNull(paging, "paging is null.");
		
		param.put("jobNm", EscapeUtil.escapeSqlLike((String) param.get("jobNm")));
		
		List<BtchJobLogInfo> list = null;
		if (paging.getRowsCnt() == 0) {
			list = mapper.selectListAll(param, paging); // 전체
		} else {
			list = mapper.selectListPaging(param, paging); // 페이징
		}
		int startIndex = paging.getStartIndex();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNo(startIndex + i + 1); // 행 번호 추가
		}
		return list;
	}
	
	public int countList(Map<String, Object> param) {
		if (logger.isDebugEnabled()) {
			logger.debug("countList(" + JsonUtil.toString(param) + ")");
		}
		Assert.notNull(param, "param is null.");
		
		param.put("jobNm", EscapeUtil.escapeSqlLike((String) param.get("jobNm")));
		
		return mapper.countList(param);
	}
	
}
