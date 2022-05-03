package kr.bizdata.semas.pms.btch.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;

/**
 * 배치작업등록
 */
public class BtchJobRegMapperTest extends AbstractTest {
	
	@Autowired
	private BtchJobRegMapper mapper;
	
	private void assertEquals(BtchJobRegInfo expected, BtchJobRegInfo actual) {
		Assert.assertEquals(expected.getJobId(), actual.getJobId());
		Assert.assertNotNull(actual.getRegDt());
		//Assert.assertEquals(expected.getRegUserId(), actual.getRegUserId());
		Assert.assertNotNull(actual.getMdfcnDt());
		Assert.assertEquals(expected.getMdfcnUserId(), actual.getMdfcnUserId());
		//Assert.assertEquals(expected.getDelYn(), actual.getDelYn());
		Assert.assertEquals(expected.getUseYn(), actual.getUseYn());
		Assert.assertEquals(expected.getJobNm(), actual.getJobNm());
		Assert.assertEquals(expected.getPckgNm(), actual.getPckgNm());
		Assert.assertEquals(expected.getClsNm(), actual.getClsNm());
		Assert.assertEquals(expected.getMthdNm(), actual.getMthdNm());
		Assert.assertEquals(expected.getCronExp(), actual.getCronExp());
	}
	
	@Test
	public void insert_update_delete() {
		
		BtchJobRegInfo info = new BtchJobRegInfo();
		//info.setJobId(jobId); // GeneratedKey
		info.setRegUserId("regUserId");
		info.setMdfcnUserId("mdfcnUserId");
		info.setUseYn("Y");
		info.setJobNm("jobNm");
		info.setPckgNm("pckgNm");
		info.setClsNm("clsNm");
		info.setMthdNm("mthdNm");
		info.setCronExp("cronExp");
		
		mapper.insert(info);
		Assert.assertTrue(info.getJobId() > 0); // GeneratedKey
		BtchJobRegInfo result = mapper.select(info.getJobId());
		Assert.assertEquals(info.getRegUserId(), result.getRegUserId());
		Assert.assertEquals("N", result.getDelYn());
		assertEquals(info, result);
		
		BtchJobRegInfo info2 = new BtchJobRegInfo();
		info2.setJobId(info.getJobId());
		info2.setMdfcnUserId("2dfcnUserId");
		info2.setUseYn("N");
		info2.setJobNm("2obNm");
		info2.setPckgNm("2ckgNm");
		info2.setClsNm("2lsNm");
		info2.setMthdNm("2thdNm");
		info2.setCronExp("2ronExp");
		
		mapper.update(info2);
		BtchJobRegInfo result2 = mapper.select(info.getJobId());
		assertEquals(info2, result2);
		
		mapper.delete(info.getJobId(), "deleteUserId");
		BtchJobRegInfo result3 = mapper.select(info.getJobId());
		Assert.assertNull(result3);
	}
	
	@Test
	public void select() {
		
		int jobId = 0; // PK
		
		BtchJobRegInfo result = mapper.select(jobId);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
	@Test
	public void selectList() {
		
		Map<String, Object> param = new HashMap<>();
		param.put("useYn", "Y"); // 사용여부
		
		List<BtchJobRegInfo> result = mapper.selectList(param);
		logger.info("result.size() = " + result.size());
	}
	
}
