package kr.bizdata.semas.pms.btch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.core.model.Paging;
import kr.bizdata.semas.pms.btch.info.BtchJobLogInfo;

/**
 * 배치작업로그
 */
@Repository
public interface BtchJobLogMapper {
	
	void insert(BtchJobLogInfo info); // GeneratedKey: jobSn
	
	BtchJobLogInfo select(long jobSn);
	
	List<BtchJobLogInfo> selectListAll(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	List<BtchJobLogInfo> selectListPaging(@Param("param") Map<String, Object> param, @Param("paging") Paging paging);
	int countList(@Param("param") Map<String, Object> param);
	
}
