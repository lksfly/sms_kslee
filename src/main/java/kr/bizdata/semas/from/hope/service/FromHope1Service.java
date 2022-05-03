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
import kr.bizdata.semas.from.hope.info.FromHope1Info;
import kr.bizdata.semas.from.hope.mapper.FromHope1Mapper;

/**
 * 희망리턴패키지시스템 (컨설팅)
 */
@Service
public class FromHope1Service {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromHope1Mapper mapper;
	
	/**
	 * @param startDt
	 * @param endDt
	 * @param typeCd 타입코드 (1: 사업정리 승인, 2: 사업정리 취소, 3: 점포철거 승인, 4: 점포철거 취소, 5: 법률자문 승인, 6: 법률자문 취소)
	 * @return
	 */
	public List<FromHope1Info> selectList(Date startDt, Date endDt, String typeCd) {
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
			return mapper.selectList_1(param); // 사업정리 승인
		case "2":
			return mapper.selectList_2(param); // 사업정리 취소
		case "3":
			return mapper.selectList_3(param); // 점포철거 승인
		case "4":
			return mapper.selectList_4(param); // 점포철거 취소
		case "5":
			return mapper.selectList_5(param); // 법률자문 승인
		case "6":
			return mapper.selectList_6(param); // 법률자문 취소
		default:
			throw new BzdException("지원하지 않는 타입코드입니다. (typeCd: " + typeCd + ")");
		}
	}
	
}
