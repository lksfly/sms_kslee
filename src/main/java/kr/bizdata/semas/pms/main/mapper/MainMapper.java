package kr.bizdata.semas.pms.main.mapper;

import kr.bizdata.core.model.Paging;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 메인페이지
 */
@Repository
public interface MainMapper {
	
	List<Map<String, Object>> M01(Map<String, Object> param); // 1. 당월 지원사업 진행현황
	List<Map<String, Object>> M02(Map<String, Object> param); // 2. 당월 사업별 지원 현황
	List<Map<String, Object>> M03(Map<String, Object> param); // 3. 당월 지급일자 별 지원사업 현황
	List<Map<String, Object>> M04(Map<String, Object> param); // 4. 월별 지원 사업 현황
	List<Map<String, Object>> M04BizList();                   //    사업코드 정보 조회
	List<Map<String, Object>> M05(Map<String, Object> param); // 5. 월별 지원금액 현황
	List<Map<String, Object>> M06L(Map<String, Object> param); // 6-1. 업종별 지원 사업 현황 전체
	List<Map<String, Object>> M06M(Map<String, Object> param); // 6-2. 업종별 지원 사업 현황 중분류
	List<Map<String, Object>> M07(Map<String, Object> param); // 7. 지역별 지원사업 현황

}
