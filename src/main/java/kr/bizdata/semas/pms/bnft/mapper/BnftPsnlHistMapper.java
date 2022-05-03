package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.bnft.info.BnftPsnlHistInfo;

/**
 * 수혜개인이력
 */
@Repository
public interface BnftPsnlHistMapper {
	
	void insert(BnftPsnlHistInfo info); // GeneratedKey: psnlHistSn
	
	BnftPsnlHistInfo select(@Param("diVal") String diVal, @Param("psnlHistSn") long psnlHistSn);
	
}
