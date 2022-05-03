package kr.bizdata.semas.pms.sprt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.model.Paging;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;

/**
 * 지원사업현황 화면
 */
public class SprtBizSttsMapperTest extends AbstractTest {
	
	@Autowired
	private SprtBizSttsMapper mapper;
	
	@Test
	public void select() {
		
		long aplySn = 77;
		
		Map<String, Object> result = mapper.select(aplySn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
	@Test
	public void selectListPaging() {
		
		Map<String, Object> param = new HashMap<>();
		Paging paging = new Paging(null, null, 0, 10);
		
		param.put("SRC_SYS_CD", "01");
		param.put("SPRT_BIZ_SN", "80");
		param.put("SPRT_BIZ_YR", "2021");
		param.put("ENT_NM", "00");
		param.put("BRNO", "00");
		param.put("APLCNT_NM", "길동");
		param.put("SPRT_CNTR_CD", "123456");
		param.put("srchStartYmd", "20200101");
		param.put("srchEndYmd", "20211231");
		
		List<Map<String, Object>> result = mapper.selectListPaging(param, paging);
		for (Map<String, Object> map : result) {
			System.out.println(JsonUtil.toString(map));
		}
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void countList() {
		
		Map<String, Object> param = new HashMap<>();
		
		int result = mapper.countList(param);
		logger.info("result = " + result);
	}
	
}
