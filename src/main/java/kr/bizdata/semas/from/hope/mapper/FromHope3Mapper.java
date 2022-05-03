package kr.bizdata.semas.from.hope.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.hope.info.FromHope3Info;

/**
 * 희망리턴패키지시스템 (장려금)
 */
@Repository
public interface FromHope3Mapper {
	
	List<FromHope3Info> selectList_1(@Param("param") Map<String, Object> param); // 재도전장려금 승인
	
	List<FromHope3Info> selectList_2(@Param("param") Map<String, Object> param); // 재도전장려금 취소
	
	List<FromHope3Info> selectList_3(@Param("param") Map<String, Object> param); // 전직장려금 승인
	
	List<FromHope3Info> selectList_4(@Param("param") Map<String, Object> param); // 전직장려금 취소
	
}
