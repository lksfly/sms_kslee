package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.bnft.info.BnftBizHistInfo;
import kr.bizdata.semas.pms.bnft.mapper.BnftBizHistMapper;

/**
 * 수혜기업이력
 */
@Service
public class BnftBizHistService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftBizHistMapper mapper;
	
	public void insert(BnftBizHistInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getBrno(), "brno is blank.");
		Assert.isTrue(info.getEntHistSn() == 0, "entHistSn is invalid.");
		
		mapper.insert(info); // 추가, GeneratedKey: entHistSn
	}
	
	public BnftBizHistInfo select(String brno, long entHistSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + brno + ", " + entHistSn + ")");
		}
		Assert.hasText(brno, "brno is blank.");
		Assert.isTrue(entHistSn > 0, "entHistSn is invalid.");
		
		BnftBizHistInfo info = mapper.select(brno, entHistSn);
		if (info == null) {
			throw new BzdNotFoundException("수혜기업이력 정보를 찾을 수 없습니다. (brno: " + brno + ", entHistSn: " + entHistSn + ")");
		}
		return info;
	}
	
}
