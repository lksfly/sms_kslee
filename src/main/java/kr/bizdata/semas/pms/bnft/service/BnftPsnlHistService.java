package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlHistInfo;
import kr.bizdata.semas.pms.bnft.mapper.BnftPsnlHistMapper;

/**
 * 수혜개인이력
 */
@Service
public class BnftPsnlHistService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftPsnlHistMapper mapper;
	
	public void insert(BnftPsnlHistInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getDiVal(), "diVal is blank.");
		Assert.isTrue(info.getPsnlHistSn() == 0, "psnlHistSn is invalid.");
		
		mapper.insert(info); // 추가, GeneratedKey: psnlHistSn
	}
	
	public BnftPsnlHistInfo select(String diVal, long psnlHistSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + diVal + ", " + psnlHistSn + ")");
		}
		Assert.hasText(diVal, "diVal is blank.");
		Assert.isTrue(psnlHistSn > 0, "psnlHistSn is invalid.");
		
		BnftPsnlHistInfo info = mapper.select(diVal, psnlHistSn);
		if (info == null) {
			throw new BzdNotFoundException("수혜개인이력 정보를 찾을 수 없습니다. (diVal: " + diVal + ", psnlHistSn: " + psnlHistSn + ")");
		}
		return info;
	}
	
}
