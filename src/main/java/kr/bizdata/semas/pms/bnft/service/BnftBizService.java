package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;
import kr.bizdata.semas.pms.bnft.mapper.BnftBizMapper;

/**
 * 수혜기업기본
 */
@Service
public class BnftBizService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftBizMapper mapper;
	
	public void insert(BnftBizInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getBrno(), "brno is blank.");
		
		mapper.insert(info); // 추가
	}
	
	public void update(BnftBizInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("update(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getBrno(), "brno is blank.");
		
		mapper.update(info); // 수정
	}
	
	public boolean exists(String brno) {
		if (logger.isDebugEnabled()) {
			logger.debug("exists(" + brno + ")");
		}
		Assert.hasText(brno, "brno is blank.");
		
		return mapper.exists(brno);
	}
	
	public BnftBizInfo select(String brno) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + brno + ")");
		}
		Assert.hasText(brno, "brno is blank.");
		
		BnftBizInfo info = mapper.select(brno);
		if (info == null) {
			throw new BzdNotFoundException("수혜기업기본 정보를 찾을 수 없습니다. (brno: " + brno + ")");
		}
		return info;
	}
	
}
