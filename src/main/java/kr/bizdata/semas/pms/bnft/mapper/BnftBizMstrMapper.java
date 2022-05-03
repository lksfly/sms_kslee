package kr.bizdata.semas.pms.bnft.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.core.model.Paging;

/**
 * 기업마스터 화면
 */
@Repository
public interface BnftBizMstrMapper {
	
	Map<String, Object> select(@Param("brno") String brno);
	
	List<Map<String, Object>> selectListAll(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	List<Map<String, Object>> selectListPaging(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	int countList(@Param("param") Map<String, Object> param);
	
}
