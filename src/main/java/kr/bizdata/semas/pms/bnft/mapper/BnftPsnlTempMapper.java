package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.bnft.info.BnftPsnlTempInfo;

/**
 * 수혜개인임시
 */
@Repository
public interface BnftPsnlTempMapper {
	
	void insert(BnftPsnlTempInfo info); // GeneratedKey: psnlSn
	
	BnftPsnlTempInfo select(@Param("psnlSn") long psnlSn);
	
}
