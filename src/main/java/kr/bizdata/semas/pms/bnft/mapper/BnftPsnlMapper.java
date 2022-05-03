package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.bnft.info.BnftPsnlInfo;

/**
 * 수혜개인기본
 */
@Repository
public interface BnftPsnlMapper {
	
	void insert(BnftPsnlInfo info);
	
	void update(BnftPsnlInfo info);
	
	boolean exists(@Param("diVal") String diVal);
	
	BnftPsnlInfo select(@Param("diVal") String  diVal);
	
}
