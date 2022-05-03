package kr.bizdata.semas.pms.cmn.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.bizdata.semas.pms.cmn.info.CmnCdInfo;
import kr.bizdata.semas.pms.cmn.mapper.CmnCdMapper;

/**
 * 공통코드
 */
@Service
public class CmnCdService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CmnCdMapper mapper;
	
	/**
	 * @param cmnDivCd
	 * @param cmnCd
	 * @return
	 */
	public CmnCdInfo select(String cmnDivCd, String cmnCd) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + cmnDivCd + ", " + cmnCd + ")");
		}
		
		if (StringUtils.isBlank(cmnDivCd) || StringUtils.isBlank(cmnCd)) {
			return null;
		}
		return mapper.select(cmnDivCd, cmnCd);
	}
	
	/**
	 * @param cmnDivCd
	 * @return
	 */
	public List<CmnCdInfo> selectList(String cmnDivCd) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + cmnDivCd + ")");
		}
		
		return mapper.selectList(cmnDivCd, null, null);
	}
	
	/**
	 * @param cmnDivCd
	 * @param sortName
	 * @param sortType
	 * @return
	 */
	public List<CmnCdInfo> selectList(String cmnDivCd, String sortName, String sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectList(" + cmnDivCd + ", " + sortName + ", " + sortType + ")");
		}
		
		sortType = "DESC".equalsIgnoreCase(sortType) ? "DESC" : "ASC";
		
		return mapper.selectList(cmnDivCd, sortName, sortType);
	}
	
}
