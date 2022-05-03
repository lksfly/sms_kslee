package kr.bizdata.semas.pms.sprt.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.sprt.info.SprtBizNmMngInfo;

/**
 * 지원사업명관리기본
 */
@Repository
public interface SprtBizNmMngMapper {
	
	void insert(SprtBizNmMngInfo info);
	
	SprtBizNmMngInfo select(@Param("sprtBizNmSn") long sprtBizNmSn);
	
	SprtBizNmMngInfo selectBySprtBizDivCd(@Param("sprtBizDivCd") String sprtBizDivCd);
	
}
