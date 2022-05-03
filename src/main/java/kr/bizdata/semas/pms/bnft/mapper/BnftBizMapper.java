package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;

/**
 * 수혜기업기본
 */
@Repository
public interface BnftBizMapper {
	
	void insert(BnftBizInfo info);
	
	void update(BnftBizInfo info);
	
	boolean exists(@Param("brno") String brno);
	
	BnftBizInfo select(@Param("brno") String brno);
	
}
