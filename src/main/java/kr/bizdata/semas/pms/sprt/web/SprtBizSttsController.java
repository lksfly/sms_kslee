package kr.bizdata.semas.pms.sprt.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.bizdata.core.config.Config;
import kr.bizdata.core.exception.BzdException;
import kr.bizdata.core.model.Paging;
import kr.bizdata.core.model.RestResult;
import kr.bizdata.core.model.RestResultDatatables;
import kr.bizdata.core.servlet.AbstractController;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.NumberUtil;
import kr.bizdata.core.util.RequestUtil;
import kr.bizdata.semas.pms.cmn.info.CmnCdInfo;
import kr.bizdata.semas.pms.cmn.service.CmnCdService;
import kr.bizdata.semas.pms.sprt.service.SprtBizSttsService;
import kr.bizdata.semas.pms.system.info.LoginInfo;

/**
 * 지원사업현황 화면
 */
@Controller
public class SprtBizSttsController extends AbstractController {
	
	@Autowired
	private SprtBizSttsService service;
	
	@Autowired
	private CmnCdService cmnCdService;
	
	/**
	 * 지원사업현황 화면
	 */
	@RequestMapping(value = "/sprt/sprtBizStts")
	public String sprtBizStts(HttpServletRequest request, Authentication auth) {
		
		LoginInfo loginInfo = getLoginInfo(auth);
		
		List<CmnCdInfo> srcSysList = cmnCdService.selectList("SRC_SYS_CD"); // 출처시스템 (사업명) 목록
		List<String> yearList = service.selectYearList(); // 사업연도 목록
		
		Date current = new Date(); // 현재
		String srchStartYmd = DateUtil.format(DateUtil.addDays(current, -31), "yyyy-MM-dd");
		String srchEndYmd = DateUtil.format(DateUtil.addDays(current, -1), "yyyy-MM-dd");
		
		request.setAttribute("loginInfo", loginInfo);
		request.setAttribute("srcSysList", srcSysList);
		request.setAttribute("yearList", yearList);
		request.setAttribute("srchStartYmd", srchStartYmd);
		request.setAttribute("srchEndYmd", srchEndYmd);
		
		return "/sprt/sprtBizStts";
	}
	
	private Map<String, Object> getSelectListParam(HttpServletRequest request) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("SRC_SYS_CD",   RequestUtil.getParam(request, "SRC_SYS_CD",   null));
		param.put("SPRT_BIZ_SN",  RequestUtil.getParam(request, "SPRT_BIZ_SN",  null));
		param.put("SPRT_BIZ_YR",  RequestUtil.getParam(request, "SPRT_BIZ_YR",  null));
		param.put("BRNO",         RequestUtil.getParam(request, "BRNO",         null));
		param.put("ENT_NM",       RequestUtil.getParam(request, "ENT_NM",       null));
		param.put("APLCNT_NM",    RequestUtil.getParam(request, "APLCNT_NM",    null));
		param.put("SPRT_CNTR_CD", RequestUtil.getParam(request, "SPRT_CNTR_CD", null));
		param.put("srchStartYmd", RequestUtil.getParam(request, "srchStartYmd", null));
		param.put("srchEndYmd",   RequestUtil.getParam(request, "srchEndYmd",   null));
		
		return param;
	}
	
	/**
	 * 지원사업현황 > 목록 API
	 */
	@RequestMapping(value = "/sprt/sprtBizStts/api")
	@ResponseBody
	public RestResult sprtBizStts_api(HttpServletRequest request, Authentication auth) {
		
		Map<String, Object> param = getSelectListParam(request);
		
		int draw = RequestUtil.getParamInt(request, "draw", 0);
		String column = RequestUtil.getParam(request, "order[0][column]", null); // 소팅컬럼 인덱스
		String sortName = RequestUtil.getParam(request, "columns["+ column +"][data]", null); // 소팅기준 컬럼명
		String sortType = RequestUtil.getParam(request, "order[0][dir]", null); // 소팅유형 (asc, desc)
		int startIndex = RequestUtil.getParamInt(request, "start", 0);
		int rowsCnt = RequestUtil.getParamInt(request, "length", 10);
		
		Paging paging = new Paging(sortName, sortType, startIndex, rowsCnt);
		
		List<Map<String, Object>> list = service.selectList(param, paging);
		
		int totalCnt = service.countList(param);
		
		return new RestResultDatatables(list, totalCnt, draw, startIndex);
	}
	
	/**
	 * 지원사업현황 > 엑셀 다운로드
	 */
	@RequestMapping(value = "/sprt/sprtBizStts/excel")
	public String sprtBizStts_excel(HttpServletRequest request, Authentication auth) {
		
		Map<String, Object> param = getSelectListParam(request);
		
		int EXCEL_DOWNLOAD_MAXCOUNT = Config.getInt("bzd.excel.download.maxCount", 50000);
		int totalCnt = service.countList(param);
		if (totalCnt > EXCEL_DOWNLOAD_MAXCOUNT) { // 엑셀 다운로드 건수 제한
			throw new BzdException("검색결과가 " + NumberUtil.numberFormat(Long.valueOf(EXCEL_DOWNLOAD_MAXCOUNT)) + "건을 초과하여 다운로드 할 수 없습니다.");
		}
		
		String sortName = RequestUtil.getParam(request, "sortName", null); // 소팅기준 컬럼명
		String sortType = RequestUtil.getParam(request, "sortType", null); // 소팅유형 (asc, desc)
		
		Paging paging = new Paging(sortName, sortType, 0, 0); // 전체 (rowsCnt = 0)
		
		List<Map<String, Object>> list = service.selectList(param, paging);
		
		String[] headers = { "No", "사업명", "세부사업명", "사업연도", "업체유형", "업체명", "사업자등록번호",
				"신청자명", "창업일", "수혜금액(천원)", "담당센터", "검색일자(지급/신청/수료)" };
		String[] dataKeys = { "NO", "SRC_SYS_NM", "SPRT_BIZ_NM", "SPRT_BIZ_YR", "BZMN_TYPE_NM", "ENT_NM", "BRNO",
				"APLCNT_NM", "FNDN_YMD", "GIVE_AMT", "SPRT_CNTR_NM", "SLCTN_YMD" };
		
		Map<String, Object> excelData = new HashMap<String, Object>();
		excelData.put("fileName", "지원사업현황_" + DateUtil.format(new Date(), "yyyyMMdd"));
		excelData.put("headers", headers);
		excelData.put("dataKeys", dataKeys);
		excelData.put("dataList", list);
		
		request.setAttribute("excelData", excelData);
		
		return "excelXlsxView";
	}
	
	/**
	 * 지원사업현황 > 세부사업 검색 팝업
	 */
	@RequestMapping(value = "/sprt/sprtBizSearchModal")
	public String sprtBizSearchModal(HttpServletRequest request, Authentication auth) {
		
		List<String> yearList = service.selectYearList(); // 사업연도 목록
		
		request.setAttribute("yearList", yearList);
		
		return "/sprt/sprtBizSearchModal";
	}
	
	/**
	 * 지원사업현황 > 세부사업 검색 팝업 > 목록 API
	 */
	@RequestMapping(value = "/sprt/sprtBizSearchModal/api")
	@ResponseBody
	public RestResult sprtBizSearchModal_api(HttpServletRequest request, Authentication auth) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("SPRT_BIZ_NM", RequestUtil.getParam(request, "SPRT_BIZ_NM", null));
		param.put("SPRT_BIZ_YR", RequestUtil.getParam(request, "SPRT_BIZ_YR", null));
		param.put("SRC_SYS_CD", RequestUtil.getParam(request, "SRC_SYS_CD", null));
		
		int draw = RequestUtil.getParamInt(request, "draw", 0);
		String column = RequestUtil.getParam(request, "order[0][column]", null); // 소팅컬럼 인덱스
		String sortName = RequestUtil.getParam(request, "columns["+ column +"][data]", null); // 소팅기준 컬럼명
		String sortType = RequestUtil.getParam(request, "order[0][dir]", null); // 소팅유형 (asc, desc)
		int startIndex = RequestUtil.getParamInt(request, "start", 0);
		int rowsCnt = RequestUtil.getParamInt(request, "length", 10);
		
		Paging paging = new Paging(sortName, sortType, startIndex, rowsCnt);
		
		List<Map<String, Object>> list = service.selectSprtBizList(param, paging);
		
		int totalCnt = service.countSprtBizList(param);
		
		return new RestResultDatatables(list, totalCnt, draw, startIndex);
	}
	
	/**
	 * 지원사업현황 > 센터 검색 팝업
	 */
	@RequestMapping(value = "/sprt/sprtCntrSearchModal")
	public String sprtCntrSearchModal(HttpServletRequest request, Authentication auth) {
		
		return "/sprt/sprtCntrSearchModal";
	}
	
	/**
	 * 지원사업현황 > 센터 검색 팝업 > 목록 API
	 */
	@RequestMapping(value = "/sprt/sprtCntrSearchModal/api")
	@ResponseBody
	public RestResult sprtCntrSearchModal_api(HttpServletRequest request, Authentication auth) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("SPRT_CNTR_NM", RequestUtil.getParam(request, "SPRT_CNTR_NM", null));
		param.put("USE_YN", RequestUtil.getParam(request, "USE_YN", null));
		
		List<Map<String, Object>> list = service.selectSprtCntrList(param);
		
		return new RestResult(list);
	}
	
	/**
	 * 지원사업현황 > 신청내역 상세정보 팝업
	 */
	@RequestMapping(value = "/sprt/sprtBizAplyInfoModal")
	public String sprtBizAplyInfoModal(HttpServletRequest request, Authentication auth) {
		
		long aplySn = RequestUtil.getParamLong(request, "APLY_SN", 0);
		
		Map<String, Object> info = service.select(aplySn);
		
		request.setAttribute("info", info);
		
		return "/sprt/sprtBizAplyInfoModal";
	}
	
}
