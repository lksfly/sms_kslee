package kr.bizdata.semas.pms.cmn.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.cmn.info.BizclsKsicLinkInfo;

/**
 * 업종코드10차표준산업분류연계
 */
public class BizclsKsicLinkMapperTest extends AbstractTest {
	
	@Autowired
	private BizclsKsicLinkMapper mapper;
	
	@Test
	public void select() {
		
		String ksicSrchCd = "01110";
		
		BizclsKsicLinkInfo result = mapper.select(ksicSrchCd);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
}
