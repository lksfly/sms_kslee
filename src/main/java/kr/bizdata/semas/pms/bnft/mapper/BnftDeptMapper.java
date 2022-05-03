package kr.bizdata.semas.pms.bnft.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.bnft.info.BnftDeptInfo;

/**
 * 공단부서
 */
@Repository
public interface BnftDeptMapper {
	
	void insert(BnftDeptInfo info);
	
	void update(BnftDeptInfo info);
	
	boolean exists(@Param("deptCd") String deptCd);
	
}
