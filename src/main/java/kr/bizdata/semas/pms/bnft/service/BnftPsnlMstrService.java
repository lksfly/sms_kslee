package kr.bizdata.semas.pms.bnft.service;

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
import kr.bizdata.semas.pms.bnft.mapper.BnftPsnlMstrMapper;

/**
 * 개인마스터 화면
 */
@Service
public class BnftPsnlMstrService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftPsnlMstrMapper mapper;
	
	/**
	 * 개인마스터 상세 조회
	 * 
	 * @param diVal  DI값 (수혜개인기본)
	 * @param psnlSn 개인일련번호 (수혜개인임시)
	 * @return
	 */
	public Map<String, Object> select(String diVal, long psnlSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + diVal + ", " + psnlSn + ")");
		}
		Assert.isTrue(StringUtils.isNotBlank(diVal) || psnlSn > 0, "diVal is blank. or psnlSn is invalid.");
		
		Map<String, Object> info = null;
		if (StringUtils.isNotBlank(diVal)) {
			info = mapper.selectByDiVal(diVal);
			if (info == null) {
				throw new BzdNotFoundException("수혜개인기본 정보를 찾을 수 없습니다. (diVal: " + diVal + ")");
			}
		} else if (psnlSn > 0) {
			info = mapper.selectByPsnlSn(psnlSn);
			if (info == null) {
				throw new BzdNotFoundException("수혜개인임시 정보를 찾을 수 없습니다. (psnlSn: " + psnlSn + ")");
			}
		}
		return info;
	}
	
	/**
	 * 개인마스터 목록 조회 (전체/페이징)
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
