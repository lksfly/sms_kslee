package kr.bizdata.semas.pms.bnft.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.bizdata.semas.AbstractTest;
import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlInfo;

/**
 * 수혜기업개인관계
 */
public class BnftBizPsnlMapperTest extends AbstractTest {
	
	@Autowired
	private BnftBizPsnlMapper mapper;
	@Autowired
	private BnftBizMapper bnftBizMapper;
	@Autowired
	private BnftPsnlMapper bnftPsnlMapper;
	
	@Test
	public void insert() {
		
		String diVal = "diVal"; // FK
		String brno = "1234567890"; // FK
		{
			BnftPsnlInfo info = new BnftPsnlInfo();
			info.setDiVal(diVal);
			bnftPsnlMapper.insert(info);
		} {
			BnftBizInfo info = new BnftBizInfo();
			info.setBrno(brno);
			bnftBizMapper.insert(info);
		}
		
		mapper.insert(diVal, brno);
		boolean result = mapper.exists(diVal, brno);
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void exists() {
		
		String diVal = null; // PK
		String brno = null; // PK
		
		boolean result = mapper.exists(diVal, brno);
		Assert.assertEquals(false, result);
	}
	
}
