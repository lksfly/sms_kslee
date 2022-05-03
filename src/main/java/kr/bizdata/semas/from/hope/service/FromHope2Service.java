package kr.bizdata.semas.from.hope.service;

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
import kr.bizdata.semas.from.hope.info.FromHope2Info;
import kr.bizdata.semas.from.hope.mapper.FromHope2Mapper;

/**
 * 희망리턴패키지시스템 (재기교육)
 */
@Service
public class FromHope2Service {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromHope2Mapper mapper;
	
	/**
	 * @param startDt
	 * @param endDt
	 * @param typeCd 타입코드 (1: 재기교육 승인)
	 * @return
	 */
	public List<FromHope2Info> selectList(Date startDt, Date endDt, String typeCd) {
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
			return mapper.selectList_1(param); // 재기교육 승인
		default:
			throw new BzdException("지원하지 않는 타입코드입니다. (typeCd: " + typeCd + ")");
		}
	}
	
}
