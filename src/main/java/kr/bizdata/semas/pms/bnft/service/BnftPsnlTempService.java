package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlTempInfo;
import kr.bizdata.semas.pms.bnft.mapper.BnftPsnlTempMapper;

/**
 * 수혜개인임시
 */
@Service
public class BnftPsnlTempService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftPsnlTempMapper mapper;
	
	public void insert(BnftPsnlTempInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getPsnlSn() == 0, "psnlSn is invalid.");
		
		mapper.insert(info); // 추가, GeneratedKey: psnlSn
	}
	
	public BnftPsnlTempInfo select(long psnlSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + psnlSn + ")");
		}
		Assert.isTrue(psnlSn > 0, "psnlSn is invalid.");
		
		BnftPsnlTempInfo info = mapper.select(psnlSn);
		if (info == null) {
			throw new BzdNotFoundException("수혜개인임시 정보를 찾을 수 없습니다. (psnlSn: " + psnlSn + ")");
		}
		return info;
	}
	
}
