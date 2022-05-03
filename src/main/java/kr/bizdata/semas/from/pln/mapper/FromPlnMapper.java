package kr.bizdata.semas.from.pln.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.pln.info.FromPlnInfo;

/**
 * 대리대출시스템
 */
@Repository
public interface FromPlnMapper {
	
	List<FromPlnInfo> selectList_PROD(@Param("param") Map<String, Object> param); // 대리대출 승인(운영)
	
	List<FromPlnInfo> selectList_DEV(@Param("param") Map<String, Object> param); // 대리대출 승인(개발)
	
}
