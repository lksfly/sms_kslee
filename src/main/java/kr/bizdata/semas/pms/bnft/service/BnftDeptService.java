package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.bnft.info.BnftDeptInfo;
import kr.bizdata.semas.pms.bnft.mapper.BnftDeptMapper;

/**
 * 공단부서
 */
@Service
public class BnftDeptService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftDeptMapper mapper;
	
	public void insert(BnftDeptInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getDeptCd(), "brno is blank.");
		
		mapper.insert(info); // 추가
	}
	
	public void update(BnftDeptInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("update(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.hasText(info.getDeptCd(), "brno is blank.");
		
		mapper.update(info); // 수정
	}
	
	public boolean exists(String deptCd) {
		if (logger.isDebugEnabled()) {
			logger.debug("exists(" + deptCd + ")");
		}
		Assert.hasText(deptCd, "brno is blank.");
		
		return mapper.exists(deptCd);
	}
	
}
