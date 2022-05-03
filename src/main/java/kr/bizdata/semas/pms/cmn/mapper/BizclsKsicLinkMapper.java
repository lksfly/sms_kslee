package kr.bizdata.semas.pms.cmn.mapper;

import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.cmn.info.BizclsKsicLinkInfo;

/**
 * 업종코드10차표준산업분류연계
 */
@Repository
public interface BizclsKsicLinkMapper {
	
	BizclsKsicLinkInfo select(String ksicSrchCd);
	
}
