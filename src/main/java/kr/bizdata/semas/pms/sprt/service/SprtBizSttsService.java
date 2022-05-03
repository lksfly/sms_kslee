package kr.bizdata.semas.pms.sprt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.model.Paging;
import kr.bizdata.core.util.EscapeUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.sprt.mapper.SprtBizSttsMapper;

/**
 * 지원사업현황 화면
 */
@Service
public class SprtBizSttsService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SprtBizSttsMapper mapper;
	
	/**
	 * 전체 사업연도 목록 조회
	 * 
	 * @return
	 */
	public List<String> selectYearList() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectYearList()");
		}
		
		return mapper.selectYearList(null);
	}
	
	/**
	 * 전체 사업연도 목록 조회 (사업자등록번호 기준)
	 * 
	 * @param brno 사업자등록번호
	 * @return
	 */
	public List<String> selectYearList(String brno) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectYearList(" + brno + ")");
		}
		
		return mapper.selectYearList(brno);
	}
	
	/**
	 * 지원사업현황 상세 조회
	 * 
	 * @param aplySn 신청일련번호
	 * @return
	 */
	public Map<String, Object> select(long aplySn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + aplySn + ")");
		}
		Assert.isTrue(aplySn > 0, "aplySn is invalid.");
		
		Map<String, Object> info = mapper.select(aplySn);
		if (info == null) {
			throw new BzdNotFoundException("지원사업현황 정보를 찾을 수 없습니다. (aplySn: " + aplySn + ")");
		}
		return info;
	}
	
	/**
	 * 지원사업현황 목록 조회 (전체/페이징)
	 * 
	 * @param param
	 * @param paging
	 * @return
	 */
	public List<Map<String, Object>> selectList(Map<String, Object> param, Paging paging) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + JsonUtil.toString(param) + ", " + JsonUtil.toString(paging) + ")");
		}
		Assert.notNull(param, "param is null.");
		Assert.notNull(paging, "paging is null.");
		
		param.put("ENT_NM", EscapeUtil.escapeSqlLike((String) param.get("ENT_NM")));
		param.put("APLCNT_NM", EscapeUtil.escapeSqlLike((String) param.get("APLCNT_NM")));
		
		List<Map<String, Object>> list = null;
		if (paging.getRowsCnt() == 0) {
			list = mapper.selectListAll(param, paging); // 전체
		} else {
			list = mapper.selectListPaging(param, paging); // 페이징
		}
		int startIndex = paging.getStartIndex();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("NO", startIndex + i + 1); // 행 번호 추가
		}
		return list;
	}
	
	public int countList(Map<String, Object> param) {
		if (logger.isDebugEnabled()) {
			logger.debug("countList(" + JsonUtil.toString(param) + ")");
		}
		Assert.notNull(param, "param is null.");
		
		param.put("ENT_NM", EscapeUtil.escapeSqlLike((String) param.get("ENT_NM")));
		param.put("APLCNT_NM", EscapeUtil.escapeSqlLike((String) param.get("APLCNT_NM")));
		
		return mapper.countList(param);
	}
	
	
	/**
	 * 지원사업현황 목록 조회 (페이징, 개인용)
	 * 
	 * @param diVal  DI값 (수혜개인기본)
	 * @param psnlSn 개인일련번호 (수혜개인임시)
	 * @param paging
	 * @return
	 */
	public List<Map<String, Object>> selectListForPsnl(String diVal, long psnlSn, Paging paging) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + diVal + ", " + psnlSn + ", " + JsonUtil.toString(paging) + ")");
		}
		Assert.isTrue(StringUtils.isNotBlank(diVal) || psnlSn > 0, "diVal is blank. or psnlSn is invalid.");
		
		Map<String, Object> param = new HashMap<>();
		param.put("DI_VAL", diVal);
		param.put("PSNL_SN", psnlSn);
		
		List<Map<String, Object>> list = mapper.selectListForPsnlPaging(param, paging);
		int startIndex = paging.getStartIndex();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("NO", startIndex + i + 1); // 행 번호 추가
		}
		return list;
	}
	
	public int countListForPsnl(String diVal, long psnlSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("countList(" + diVal + ", " + psnlSn + ")");
		}
		Assert.isTrue(StringUtils.isNotBlank(diVal) || psnlSn > 0, "diVal is blank. or psnlSn is invalid.");
		
		Map<String, Object> param = new HashMap<>();
		param.put("DI_VAL", diVal);
		param.put("PSNL_SN", psnlSn);
		
		return mapper.countListForPsnl(param);
	}
	
	/**
	 * 세부사업 목록 조회 (페이징)
	 * 
	 * @param param
	 * @param paging
	 * @return
	 */
	public List<Map<String, Object>> selectSprtBizList(Map<String, Object> param, Paging paging) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectSprtBizList(" + JsonUtil.toString(param) + ", " + JsonUtil.toString(paging) + ")");
		}
		Assert.notNull(param, "param is null.");
		Assert.notNull(paging, "paging is null.");
		
		param.put("SPRT_BIZ_NM", EscapeUtil.escapeSqlLike((String) param.get("SPRT_BIZ_NM")));
		
		List<Map<String, Object>> list = mapper.selectSprtBizListPaging(param, paging);
		int startIndex = paging.getStartIndex();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("NO", startIndex + i + 1); // 행 번호 추가
		}
		return list;
	}
	
	public int countSprtBizList(Map<String, Object> param) {
		if (logger.isDebugEnabled()) {
			logger.debug("countSprtBizList(" + JsonUtil.toString(param) + ")");
		}
		Assert.notNull(param, "param is null.");
		
		param.put("SPRT_BIZ_NM", EscapeUtil.escapeSqlLike((String) param.get("SPRT_BIZ_NM")));
		
		return mapper.countSprtBizList(param);
	}
	
	public List<Map<String, Object>> selectSprtCntrList(Map<String, Object> param) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectSprtCntrList(" + JsonUtil.toString(param) + ")");
		}
		Assert.notNull(param, "param is null.");
		
		param.put("SPRT_CNTR_NM", EscapeUtil.escapeSqlLike((String) param.get("SPRT_CNTR_NM")));
		
		return mapper.selectSprtCntrList(param);
	}
	
}
