package kr.bizdata.semas.pms.cmn.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.cmn.info.CmnCdInfo;

/**
 * 공통코드
 */
public class CmnCdMapperTest extends AbstractTest {
	
	@Autowired
	private CmnCdMapper mapper;
	
	@Test
	public void select() {
		
		String cmnDivCd = "SPRT_BIZ_DIV_CD";
		String cmnCd = "01";
		
		CmnCdInfo result = mapper.select(cmnDivCd, cmnCd);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
	@Test
	public void selectList() {
		
		String cmnDivCd = "SPRT_BIZ_DIV_CD";
		
		List<CmnCdInfo> result = mapper.selectList(cmnDivCd, null, null);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
