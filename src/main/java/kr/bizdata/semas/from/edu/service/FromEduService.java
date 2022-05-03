package kr.bizdata.semas.from.edu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdException;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.semas.from.edu.info.FromEduInfo;
import kr.bizdata.semas.from.edu.mapper.FromEduMapper;

/**
 * 교육시스템
 */
@Service
public class FromEduService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromEduMapper mapper;
	
	/**
	 * @param startDt
	 * @param endDt
	 * @param typeCd 타입코드 (1: 지급온라인, 2: 온라인, 3: 오프라인)
	 * @return
	 */
	public List<FromEduInfo> selectList(Date startDt, Date endDt, String typeCd) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + DateUtil.formatYmd(startDt) + ", " + DateUtil.formatYmd(endDt) + ", " + typeCd + ")");
		}
		Assert.notNull(startDt, "startDt is null.");
		Assert.notNull(endDt, "endDt is null.");
		
		startDt = DateUtil.parse(DateUtil.formatYmd(startDt) + "000000000", "yyyyMMddHHmmssSSS");
		endDt = DateUtil.parse(DateUtil.formatYmd(endDt) + "235959999", "yyyyMMddHHmmssSSS");
		
		Map<String, Object> param = new HashMap<>();
		param.put("startDt", startDt);
		param.put("endDt", endDt);
		
		switch (String.valueOf(typeCd)) {
		case "1":
			return mapper.selectList_1(param); // 지급온라인
		case "2":
			return mapper.selectList_2(param); // 온라인
		case "3":
			return mapper.selectList_3(param); // 오프라인
		default:
			throw new BzdException("지원하지 않는 타입코드입니다. (typeCd: " + typeCd + ")");
		}
	}
	
}
