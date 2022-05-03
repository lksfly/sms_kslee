package kr.bizdata.semas.pms.bnft.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.core.model.Paging;

/**
 * 개인마스터 화면
 */
@Repository
public interface BnftPsnlMstrMapper {
	
	Map<String, Object> selectByDiVal(@Param("diVal") String diVal); // 기본
	Map<String, Object> selectByPsnlSn(@Param("psnlSn") long psnlSn); // 임시
	
	List<Map<String, Object>> selectListAll(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	List<Map<String, Object>> selectListPaging(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	int countList(@Param("param") Map<String, Object> param);
	
}
