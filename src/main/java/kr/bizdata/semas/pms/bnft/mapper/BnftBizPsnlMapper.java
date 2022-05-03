package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 수혜기업개인관계
 */
@Repository
public interface BnftBizPsnlMapper {
	
	void insert(@Param("diVal") String diVal, @Param("brno") String brno);
	
	boolean exists(@Param("diVal") String diVal, @Param("brno") String brno);
	
}
