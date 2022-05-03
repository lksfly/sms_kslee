package kr.bizdata.semas.from.dln.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.dln.info.FromDlnInfo;

/**
 * 직접대출시스템
 */
@Repository
public interface FromDlnMapper {
	
	List<FromDlnInfo> selectList_PROD(@Param("param") Map<String, Object> param); // 직접대출 승인(운영)
	
	List<FromDlnInfo> selectList_DEV(@Param("param") Map<String, Object> param); // 직접대출 승인(개발)
	
}
