package kr.bizdata.semas.pms.cntr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 센터지원현황 화면
 */
@Repository
public interface CntrSprtSttsMapper {
	
	List<Map<String, Object>> selectList(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> selectListByRgnCd(@Param("param") Map<String, Object> param);
	
}
