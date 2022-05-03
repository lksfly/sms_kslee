package kr.bizdata.semas.from.pln.service;

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
import kr.bizdata.semas.from.pln.info.FromPlnInfo;
import kr.bizdata.semas.from.pln.mapper.FromPlnMapper;

/**
 * 대리대출시스템
 */
@Service
public class FromPlnService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromPlnMapper mapper;
	
	/**
	 * @param startDt
	 * @param endDt
	 * @param typeCd 타입코드 (1: 대리대출 승인)
	 * @return
	 */
	public List<FromPlnInfo> selectList(Date startDt, Date endDt, String typeCd, boolean isProdUse) {
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
			if(isProdUse) {
				return mapper.selectList_PROD(param); // 대리대출 승인(운영)
			} else {
				return mapper.selectList_DEV(param); // 대리대출 승인(개발)
			}
		default:
			throw new BzdException("지원하지 않는 타입코드입니다. (typeCd: " + typeCd + ")");
		}
	}
	
}
