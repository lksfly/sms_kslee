package kr.bizdata.semas.from.edu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.edu.info.FromEduInfo;

/**
 * 교육시스템
 */
@Repository
public interface FromEduMapper {
	
	List<FromEduInfo> selectList_1(@Param("param") Map<String, Object> param); // 지급온라인
	
	List<FromEduInfo> selectList_2(@Param("param") Map<String, Object> param); // 온라인
	
	List<FromEduInfo> selectList_3(@Param("param") Map<String, Object> param); // 오프라인
	
}
