package kr.bizdata.semas.pms.btch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;

/**
 * 배치작업등록
 */
@Repository
public interface BtchJobRegMapper {
	
	void insert(BtchJobRegInfo info); // GeneratedKey: jobId
	
	void update(BtchJobRegInfo info);
	
	void delete(@Param("jobId") long jobId, @Param("mdfcnUserId") String mdfcnUserId);
	
	BtchJobRegInfo select(long jobId);
	
	List<BtchJobRegInfo> selectList(@Param("param") Map<String, Object> param);
	
}
