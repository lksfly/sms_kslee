package kr.bizdata.semas.from.hope.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.hope.info.FromHope1Info;

/**
 * 희망리턴패키지시스템 (컨설팅)
 */
@Repository
public interface FromHope1Mapper {
	
	List<FromHope1Info> selectList_1(@Param("param") Map<String, Object> param); // 사업정리 승인
	
	List<FromHope1Info> selectList_2(@Param("param") Map<String, Object> param); // 사업정리 취소
	
	List<FromHope1Info> selectList_3(@Param("param") Map<String, Object> param); // 점포철거 승인
	
	List<FromHope1Info> selectList_4(@Param("param") Map<String, Object> param); // 점포철거 취소
	
	List<FromHope1Info> selectList_5(@Param("param") Map<String, Object> param); // 법률자문 승인
	
	List<FromHope1Info> selectList_6(@Param("param") Map<String, Object> param); // 법률자문 취소
	
}
