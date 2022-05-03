package kr.bizdata.semas.pms.cmn.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.bizdata.semas.pms.cmn.info.BizclsKsicLinkInfo;
import kr.bizdata.semas.pms.cmn.mapper.BizclsKsicLinkMapper;

/**
 * 업종코드10차표준산업분류연계
 */
@Service
public class BizclsKsicLinkService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BizclsKsicLinkMapper mapper;
	
	/**
	 * @param ksicSrchCd 표준산업분류 검색용 코드 (업종코드 또는 표준산업분류코드)
	 * @return
	 */
	public BizclsKsicLinkInfo select(String ksicSrchCd) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + ksicSrchCd + ")");
		}
		
		if (StringUtils.isBlank(ksicSrchCd)) {
			return null;
		}
		return mapper.select(ksicSrchCd);
	}
	
}
