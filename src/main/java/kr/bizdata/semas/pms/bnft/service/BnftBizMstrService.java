package kr.bizdata.semas.pms.bnft.service;

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
import kr.bizdata.semas.pms.bnft.mapper.BnftBizMstrMapper;

/**
 * 기업마스터 화면
 */
@Service
public class BnftBizMstrService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftBizMstrMapper mapper;
	
	/**
	 * 기업마스터 상세 조회
	 * 
	 * @param brno 사업자등록번호
	 * @return
	 */
	public Map<String, Object> select(String brno) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + brno + ")");
		}
		Assert.hasText(brno, "brno is blank.");
		
		Map<String, Object> info = mapper.select(brno);
		if (info == null) {
			throw new BzdNotFoundException("수혜기업기본 정보를 찾을 수 없습니다. (brno: " + brno + ")");
		}
		return info;
	}
	
	/**
	 * 기업마스터 목록 조회 (전체/페이징)
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
		param.put("CEO_NM", EscapeUtil.escapeSqlLike((String) param.get("CEO_NM")));
		
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
		param.put("CEO_NM", EscapeUtil.escapeSqlLike((String) param.get("CEO_NM")));
		
		return mapper.countList(param);
	}
	
}
