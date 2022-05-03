package kr.bizdata.semas.pms.sprt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.core.model.Paging;

/**
 * 지원사업현황 화면
 */
@Repository
public interface SprtBizSttsMapper {
	
	List<String> selectYearList(@Param("brno") String brno);
	
	Map<String, Object> select(@Param("aplySn") long aplySn);
	
	List<Map<String, Object>> selectListAll(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	List<Map<String, Object>> selectListPaging(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	int countList(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> selectListForPsnlPaging(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	int countListForPsnl(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> selectSprtBizListPaging(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	int countSprtBizList(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> selectSprtCntrList(@Param("param") Map<String, Object> param);
	
}
