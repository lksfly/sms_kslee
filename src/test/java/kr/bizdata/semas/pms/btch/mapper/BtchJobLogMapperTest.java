package kr.bizdata.semas.pms.btch.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.core.model.Paging;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.btch.info.BtchJobLogInfo;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;

/**
 * 배치작업로그
 */
public class BtchJobLogMapperTest extends AbstractTest {
	
	@Autowired
	private BtchJobLogMapper mapper;
	
	@Autowired
	private BtchJobRegMapper btchJobRegMapper;
	
	private void assertEquals(BtchJobLogInfo expected, BtchJobLogInfo actual) {
		Assert.assertEquals(expected.getJobSn(), actual.getJobSn());
		Assert.assertEquals(expected.getJobId(), actual.getJobId());
		Assert.assertEquals(DateUtil.formatYms(expected.getBgngDt()), DateUtil.formatYms(actual.getBgngDt()));
		Assert.assertEquals(DateUtil.formatYms(expected.getEndDt()), DateUtil.formatYms(actual.getEndDt()));
		Assert.assertEquals(expected.getRsltCd(), actual.getRsltCd());
		Assert.assertEquals(expected.getRsltCn(), actual.getRsltCn());
		Assert.assertEquals(expected.getSrvrIpAddr(), actual.getSrvrIpAddr());
		Assert.assertEquals(expected.getAutoYn(), actual.getAutoYn());
	}
	
	@Test
	public void insert() {
		
		long jobId = 0; // FK
		{
			BtchJobRegInfo info = new BtchJobRegInfo();
			btchJobRegMapper.insert(info);
			Assert.assertTrue(info.getJobId() > 0); // GeneratedKey
			jobId = info.getJobId();
		}
		
		BtchJobLogInfo info = new BtchJobLogInfo();
		//info.setJobSn(jobSn); // GeneratedKey
		info.setJobId(jobId);
		info.setBgngDt(DateUtil.parseYms("20210101010101"));
		info.setEndDt(DateUtil.parseYms("20210203010101"));
		info.setRsltCd("S");
		info.setRsltCn("rsltCn");
		info.setSrvrIpAddr("srvrIpAddr");
		info.setAutoYn("Y");
		
		mapper.insert(info);
		Assert.assertTrue(info.getJobSn() > 0); // GeneratedKey
		BtchJobLogInfo result = mapper.select(info.getJobSn());
		assertEquals(info, result);
	}
	
	@Test
	public void select() {
		
		long jobSn = 0; // PK
		
		BtchJobLogInfo result = mapper.select(jobSn);
		logger.info("result = " + JsonUtil.toString(result));
	}
	
	@Test
	public void selectListAll() {
		
		Map<String, Object> param = new HashMap<>();
		Paging paging = new Paging(null, null, 0, 10);
		
		List<BtchJobLogInfo> result = mapper.selectListAll(param, paging);
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void selectListPaging() {
		
		Map<String, Object> param = new HashMap<>();
		Paging paging = new Paging(null, null, 0, 10);
		
		List<BtchJobLogInfo> result = mapper.selectListPaging(param, paging);
		logger.info("result.size() = " + result.size());
	}
	
	@Test
	public void countList() {
		
		Map<String, Object> param = new HashMap<>();
		
		int result = mapper.countList(param);
		logger.info("result = " + result);
	}
	
}
