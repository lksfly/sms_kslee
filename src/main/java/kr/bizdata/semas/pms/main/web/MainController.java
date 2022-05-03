package kr.bizdata.semas.pms.main.web;

import kr.bizdata.core.model.RestResult;
import kr.bizdata.core.model.RestResultDatatables;
import kr.bizdata.core.servlet.AbstractController;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.RequestUtil;
import kr.bizdata.semas.pms.main.service.MainService;
import kr.bizdata.semas.pms.sprt.service.SprtBizSttsService;
import kr.bizdata.semas.pms.system.info.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class MainController extends AbstractController {
	
	@Autowired
	private SprtBizSttsService sprtBizSttsService;

	@Autowired
	private MainService mainService;

	/**
	 * 메인 화면
	 */
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, Authentication auth) {
		
		LoginInfo loginInfo = getLoginInfo(auth);
		request.setAttribute("loginInfo", loginInfo);
		
		return "/main/main";
	}
	
	/**
	 * 대시보드 화면
	 */
	@RequestMapping(value = "/main/dashboard")
	public String dashboard(HttpServletRequest request, Authentication auth) {
		
		List<String> yearList = sprtBizSttsService.selectYearList(); // 사업연도 목록

		Map<String, Object> param = new HashMap<>();
		param.put("SLCTN_YM", DateUtil.format(new Date(), "yyyyMM"));
		param.put("DETAIL_YN", "N");

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		request.setAttribute("thisMonth", c.get(Calendar.MONTH)+1); // 헌재 월
		request.setAttribute("yearList", yearList);
		request.setAttribute("M01", mainService.M01(param)); // 1. 당월 지원사업 진행현황
		request.setAttribute("M02", mainService.M02(param)); // 2. 당월 사업별 지원 현황

		return "/main/dashboard";
	}

	/**
	 * 1. 지원사업 진핸현황 > 월별 지원사업 진행 현황
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/M01")
	public String dashboardPopupM01(HttpServletRequest request, Authentication auth) {
		request.setAttribute("yearList", sprtBizSttsService.selectYearList());  // 사업연도 목록
		return "/main/M01Popup";
	}

	/**
	 * 1. 지원사업 진핸현황 > 월별 지원사업 진행 현황 조회
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/api/M01")
	@ResponseBody
	public RestResult dashboardPopupApiM01(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();

		String SLCTN_YM = RequestUtil.getParam(request, "BIZ_YEAR", null);
		String DETAIL_YN = RequestUtil.getParam(request, "DETAIL_YN", "Y");

		param.put("SLCTN_YM", SLCTN_YM);
		param.put("DETAIL_YN", DETAIL_YN);
		List<Map<String, Object>> list = mainService.M01(param);

		return new RestResultDatatables(list, 0, 1, 0);
	}

	/**
	 * 2. 당월 사업별 지원 현황
	 *    성능 테스트를 위한 메소드
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/api/M02")
	@ResponseBody
	public RestResult dashboardPopupApiM02(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		param.put("SLCTN_YM", DateUtil.format(new Date(), "yyyyMM"));
		List<Map<String, Object>> list = mainService.M02(param);

		return new RestResult(list);
	}

	/**
	 * 3. 당월 지급일자 별 지원사업 현황 Chart
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/api/M03")
	@ResponseBody
	public RestResult dashboardApiM03(HttpServletRequest request, Authentication auth) {

		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> list = mainService.M03(param);

		return new RestResult(list);
	}

	/**
	 * 3-1. 지급일자 별 지원사업 현황 > 지급일자 기준 지원건수 현황 팝업
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/M03")
	public String dashboardPopupM03(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		return "/main/M03Popup";
	}

	/**
	 * 3-1. 지급일자 별 지원사업 현황 > 지급일자 기준 지원건수 현황 조회
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/api/M03")
	@ResponseBody
	public RestResult dashboardPopupApiM03(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> dataList = mainService.M03(param);
		return new RestResult(dataList);
	}

	/**
	 * 4. 월별 지원 사업 현황 Chart
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/api/M04")
	@ResponseBody
	public RestResult dashboardM04Api(HttpServletRequest request, Authentication auth) {

		Map<String, Object> param = new HashMap<>();

		String bizYear = RequestUtil.getParam(request, "BIZ_YEAR", null);   // 사업연도
		String detailYn = RequestUtil.getParam(request, "DETAIL_YN", "N"); // 상세화면 여부

		param.put("BIZ_YEAR", bizYear);
		param.put("DETAIL_YN", detailYn);
		List<Map<String, Object>> list = mainService.M04(param);

		RestResult result;
		if( "Y".equals(detailYn) ) {
			result = new RestResultDatatables(list, list.size(), 1, 0);;
		} else {
			Map<String, Object> resultMap = new HashMap<>();
			List<Map<String, Object>> bizlist = mainService.M04BizList();
			resultMap.put("list", list);
			resultMap.put("bizList", bizlist);
			result = new RestResult(resultMap);
		}

		return result;
	}

	/**
	 * 4. 월별 지원 사업 현황 > 월별 지원 사업 현황 상세보기
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/M04")
	public String dashboardPopupM04(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		return "/main/M04Popup";
	}

	/**
	 * 5. 월별 지원금액 현황 Chart
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/api/M05")
	@ResponseBody
	public RestResult dashboardM05Api(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();

		String bizYear = RequestUtil.getParam(request, "BIZ_YEAR", null);   // 사업연도
		String detailYn = RequestUtil.getParam(request, "DETAIL_YN", null); // 상세화면 여부

		param.put("BIZ_YEAR", bizYear);
		param.put("DETAIL_YN", detailYn);
		List<Map<String, Object>> list = mainService.M05(param);

		RestResult result;
		if( "Y".equals(detailYn) ) {
			result = new RestResultDatatables(list, list.size(), 1, 0);;
		} else {
			result = new RestResult(list);
		}

		return result;
	}

	/**
	 * 5.월별 지원금액 현황 > 월별 지원 금액 현황 상세보기
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/M05")
	public String dashboardPopupM05(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		return "/main/M05Popup";
	}

	/**
	 * 6. 업종별 지원 사업 현황
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/api/M06")
	@ResponseBody
	public RestResult dashboardM06Api(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();

		param.put("PAGING_YN", "N");
		param.put("SLCTN_YM", DateUtil.format(new Date(), "yyyyMM"));
		param.put("KSIC_LCLS_CD", RequestUtil.getParam(request, "KSIC_LCLS_CD", null)); // 표준산업분류대분류코드
		List<Map<String, Object>> list = mainService.M06(param);

		return new RestResult(list);
	}

	/**
	 * 6-1. 업종별 지원 사업 현황 상세조회 화면
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/M06")
	public String dashboardPopupM06(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		request.setAttribute("clsCd", RequestUtil.getParam(request, "clsCd", null)); // 분류코드
		return "/main/M06Popup";
	}

	/**
	 * 6-1. 업종별 지원 사업 현황 상세조회
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/api/M06")
	@ResponseBody
	public RestResult dashboardPopupApiM06(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();

		param.put("SLCTN_YM", DateUtil.format(new Date(), "yyyyMM")); // 지원년월
		param.put("KSIC_LCLS_CD", RequestUtil.getParam(request, "clsCd", null));  // 분류코드
		List<Map<String, Object>> dataList = mainService.M06(param);

		return new RestResult(dataList);
	}

	/**
	 * 7. 지역별 지원사업 현황
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/popup/M07")
	public String dashboardPopupM07(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();
		return "/main/M07Popup";
	}

	/**
	 * 7. 지역별 지원사업 현황
	 * @param request
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/main/dashboard/api/M07")
	@ResponseBody
	public RestResult dashboardM07Api(HttpServletRequest request, Authentication auth) {
		Map<String, Object> param = new HashMap<>();

		String detailYn = RequestUtil.getParam(request, "DETAIL_YN", null); // 상세화면 여부
		param.put("SLCTN_YM", DateUtil.format(new Date(), "yyyyMM"));
		List<Map<String, Object>> list = mainService.M07(param);

		RestResult result;
		if( detailYn != null ) {
			result = new RestResultDatatables(list, 0, 1, 0);;
		} else {
			result = new RestResult(list);
		}

		return result;
	}

}
