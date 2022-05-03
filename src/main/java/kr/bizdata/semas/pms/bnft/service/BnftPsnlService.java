package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlInfo;
import kr.bizdata.semas.pms.bnft.mapper.BnftPsnlMapper;

/**
 * 수혜개인기본
 */
@Service
public class BnftPsnlService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftPsnlMapper mapper;
	
	public void insert(BnftPsnlInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getDiVal(), "diVal is blank.");
		
		mapper.insert(info); // 추가
	}
	
	public void update(BnftPsnlInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("update(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getDiVal(), "diVal is blank.");
		
		mapper.update(info); // 수정
	}
	
	public boolean exists(String diVal) {
		if (logger.isDebugEnabled()) {
			logger.debug("exists(" + diVal + ")");
		}
		Assert.hasText(diVal, "diVal is blank.");
		
		return mapper.exists(diVal);
	}
	
	public BnftPsnlInfo select(String diVal) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + diVal + ")");
		}
		Assert.hasText(diVal, "diVal is blank.");
		
		BnftPsnlInfo info = mapper.select(diVal);
		if (info == null) {
			throw new BzdNotFoundException("수혜개인기본 정보를 찾을 수 없습니다. (diVal: " + diVal + ")");
		}
		return info;
	}
	
}
