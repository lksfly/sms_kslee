package kr.bizdata.semas.from.cnslt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.cnslt.info.FromCnsltInfo;

/**
 * 컨설팅시스템
 */
@Repository
public interface FromCnsltMapper {
	
	List<FromCnsltInfo> selectList_1(@Param("param") Map<String, Object> param); // 승인
	
	List<FromCnsltInfo> selectList_2(@Param("param") Map<String, Object> param); // 취소
	
}
