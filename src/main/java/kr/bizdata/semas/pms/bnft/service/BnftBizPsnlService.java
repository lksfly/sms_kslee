package kr.bizdata.semas.pms.bnft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.semas.pms.bnft.mapper.BnftBizPsnlMapper;

/**
 * 수혜기업개인관계
 */
@Service
public class BnftBizPsnlService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BnftBizPsnlMapper mapper;
	
	public void insert(String diVal, String brno) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + diVal + ", " + brno + ")");
		}
		Assert.hasText(diVal, "diVal is blank.");
		Assert.hasText(brno, "brno is blank.");
		
		mapper.insert(diVal, brno); // 추가
	}
	
	public boolean exists(String diVal, String brno) {
		if (logger.isDebugEnabled()) {
			logger.debug("exists(" + diVal + ", " + brno + ")");
		}
		Assert.hasText(diVal, "diVal is blank.");
		Assert.hasText(brno, "brno is blank.");
		
		return mapper.exists(diVal, brno);
	}
	
}
