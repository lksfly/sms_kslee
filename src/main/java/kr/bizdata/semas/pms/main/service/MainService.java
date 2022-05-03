package kr.bizdata.semas.pms.main.service;

import kr.bizdata.core.util.DateUtil;
import kr.bizdata.semas.pms.main.mapper.MainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 메인페이지
 */
@Service
public class MainService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MainMapper mapper;

	/**
	 * 1. 당월 지원사업 진행현황
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M01(Map<String, Object> param) {
		List<Map<String, Object>> resultList = mapper.M01(param);
		// 순번 처리
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).put("NO", i + 1); // 행 번호 추가
		}
		return resultList;
	}

	/**
	 * 2. 당월 사업별 지원 현황
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M02(Map<String, Object> param) {
		return mapper.M02(param);
	}

	/**
	 * 3. 당월 지급일자 별 지원사업 현황
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M03(Map<String, Object> param) {

		param.put("SLCTN_YM", DateUtil.format(new Date(), "yyyyMM"));

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		// 당월의 날짜를 구한다.
		List<Integer> monthDays = new ArrayList<>();
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		for( int day = 1; day <= lastDay; day++ ) {
			monthDays.add(day);
		}

		param.put("monthDays", monthDays);

		List<Map<String, Object>> resultList = mapper.M03(param);
		// 순번 처리
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).put("NO", i + 1); // 행 번호 추가
		}
		return resultList;
	}

	/**
	 * 4. 월별 지원 사업 현황
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M04(Map<String, Object> param) {
		return mapper.M04(param);
	}

	/**
	 * 사업코드 정보 조회
	 * @return
	 */
	public List<Map<String, Object>> M04BizList() {
		return mapper.M04BizList();
	}

	/**
	 * 5. 월별 지원금액 현황
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M05(Map<String, Object> param) {
		return mapper.M05(param);
	}

	/**
	 * 6. 업종별 지원 사업 현황 전체
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M06(Map<String, Object> param) {
		List<Map<String, Object>> resultList;
		if( param.get("KSIC_LCLS_CD") == null ) {
			resultList = mapper.M06L(param);
		} else {
			resultList = mapper.M06M(param);
		}

		// 상세화면인 경우 순번 처리
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).put("NO", i + 1); // 행 번호 추가
		}

		return resultList;
	}

	/**
	 * 7. 지역별 지원사업 현황
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> M07(Map<String, Object> param) {
		List<Map<String, Object>> resultList = mapper.M07(param);

		// 상세화면인 경우 순번 처리
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).put("NO", i + 1); // 행 번호 추가
		}

		return resultList;
	}
}
