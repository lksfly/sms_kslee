package kr.bizdata.semas.pms.cmn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.cmn.info.CmnCdInfo;

/**
 * 공통코드
 */
@Repository
public interface CmnCdMapper {
	
	CmnCdInfo select(@Param("cmnDivCd") String cmnDivCd, @Param("cmnCd") String cmnCd);
	
	List<CmnCdInfo> selectList(@Param("cmnDivCd") String cmnDivCd, @Param("sortName") String sortName, @Param("sortType") String sortType);
	
}
