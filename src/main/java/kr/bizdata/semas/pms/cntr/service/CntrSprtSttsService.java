package kr.bizdata.semas.pms.cntr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.semas.pms.cntr.mapper.CntrSprtSttsMapper;

/**
 * 센터지원현황 화면
 */
@Service
public class CntrSprtSttsService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CntrSprtSttsMapper mapper;
	
	/**
	 * 지역본부별 지원건수 목록
	 * 
	 * @param srchStartYmd
	 * @param srchEndYmd
	 * @return
	 */
	public List<Map<String, Object>> selectList(String srchStartYmd, String srchEndYmd) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectYearList(" + srchStartYmd + ", " + srchEndYmd + ")");
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("srchStartYmd", srchStartYmd);
		param.put("srchEndYmd", srchEndYmd);
		
		List<Map<String, Object>> list = mapper.selectList(param);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("NO", i + 1); // 행 번호 추가
		}
		return list;
	}
	
	/**
	 * 센터별 지원건수 목록
	 * 
	 * @param rgnCd 지역코드
	 * @param srchStartYmd
	 * @param srchEndYmd
	 * @return
	 */
	public List<Map<String, Object>> selectList(String rgnCd, String srchStartYmd, String srchEndYmd) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectYearList(" + rgnCd + ", " + srchStartYmd + ", " + srchEndYmd + ")");
		}
		Assert.hasText(rgnCd, "rgnCd is blank.");
		
		Map<String, Object> param = new HashMap<>();
		param.put("rgnCd", rgnCd);
		param.put("srchStartYmd", srchStartYmd);
		param.put("srchEndYmd", srchEndYmd);
		
		List<Map<String, Object>> list = mapper.selectListByRgnCd(param);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("NO", i + 1); // 행 번호 추가
		}
		return list;
	}
	
}
