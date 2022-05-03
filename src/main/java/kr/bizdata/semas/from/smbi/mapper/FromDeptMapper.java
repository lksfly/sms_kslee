package kr.bizdata.semas.from.smbi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.smbi.info.FromDeptInfo;

/**
 * 부서시스템
 */
@Repository
public interface FromDeptMapper {
	List<FromDeptInfo> selectList(); // 부서정보 조회.
}
