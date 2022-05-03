package kr.bizdata.semas.from.hope.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.hope.info.FromHope2Info;

/**
 * 희망리턴패키지시스템 (재기교육)
 */
@Repository
public interface FromHope2Mapper {
	
	List<FromHope2Info> selectList_1(@Param("param") Map<String, Object> param); // 재기교육 승인
	
}
