package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.bnft.info.BnftBizHistInfo;

/**
 * 수혜기업이력
 */
@Repository
public interface BnftBizHistMapper {
	
	void insert(BnftBizHistInfo info); // GeneratedKey: entHistSn
	
	BnftBizHistInfo select(@Param("brno") String brno, @Param("entHistSn") long entHistSn);
	
}
