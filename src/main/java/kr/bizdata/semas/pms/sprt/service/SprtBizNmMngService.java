package kr.bizdata.semas.pms.sprt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.sprt.info.SprtBizNmMngInfo;
import kr.bizdata.semas.pms.sprt.mapper.SprtBizNmMngMapper;

/**
 * 지원사업명관리기본
 */
@Service
public class SprtBizNmMngService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SprtBizNmMngMapper mapper;
	
	public void insert(SprtBizNmMngInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getSprtBizNmSn() == 0, "sprtBizNmSn is invalid.");
		
		mapper.insert(info); // 추가
	}
	
	/**
	 * @param sprtBizNmSn 지원사업명일련번호
	 * @return
	 */
	public SprtBizNmMngInfo select(long sprtBizNmSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + sprtBizNmSn + ")");
		}
		Assert.isTrue(sprtBizNmSn > 0, "sprtBizNmSn is invalid.");
		
		SprtBizNmMngInfo info = mapper.select(sprtBizNmSn);
		if (info == null) {
			throw new BzdNotFoundException("지원사업명관리기본 정보를 찾을 수 없습니다. (sprtBizNmSn: " + sprtBizNmSn + ")");
		}
		return info;
	}
	
	/**
	 * @param sprtBizDivCd 지원사업구분코드
	 * @return
	 */
	public SprtBizNmMngInfo select(String sprtBizDivCd) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + sprtBizDivCd + ")");
		}
		Assert.hasText(sprtBizDivCd, "sprtBizDivCd is blank.");
		
		SprtBizNmMngInfo info = mapper.selectBySprtBizDivCd(sprtBizDivCd);
		if (info == null) {
			throw new BzdNotFoundException("지원사업명관리기본 정보를 찾을 수 없습니다. (sprtBizDivCd: " + sprtBizDivCd + ")");
		}
		return info;
	}
	
}
