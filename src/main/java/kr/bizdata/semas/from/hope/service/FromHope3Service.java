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
import kr.bizdata.semas.from.hope.info.FromHope3Info;
import kr.bizdata.semas.from.hope.mapper.FromHope3Mapper;

/**
 * 희망리턴패키지시스템 (장려금)
 */
@Service
public class FromHope3Service {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromHope3Mapper mapper;
	
	/**
	 * @param startDt
	 * @param endDt
	 * @param typeCd 타입코드 (1: 재도전장려금 승인, 2: 재도전장려금 취소, 3: 전직장려금 승인, 4: 전직장려금 취소)
	 * @return
	 */
	public List<FromHope3Info> selectList(Date startDt, Date endDt, String typeCd) {
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
			return mapper.selectList_1(param); // 재도전장려금 승인
		case "2":
			return mapper.selectList_2(param); // 재도전장려금 취소
		case "3":
			return mapper.selectList_3(param); // 전직장려금 승인
		case "4":
			return mapper.selectList_4(param); // 전직장려금 취소
		default:
			throw new BzdException("지원하지 않는 타입코드입니다. (typeCd: " + typeCd + ")");
		}
	}
	
}
