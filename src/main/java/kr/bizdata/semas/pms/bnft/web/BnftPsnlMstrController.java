package kr.bizdata.semas.pms.bnft.web;

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
import kr.bizdata.semas.pms.bnft.service.BnftPsnlMstrService;
import kr.bizdata.semas.pms.cmn.info.CmnCdInfo;
import kr.bizdata.semas.pms.cmn.service.CmnCdService;
import kr.bizdata.semas.pms.sprt.service.SprtBizSttsService;

/**
 * 개인마스터 화면
 */
@Controller
public class BnftPsnlMstrController extends AbstractController {
	
	@Autowired
	private BnftPsnlMstrService service;
	
	@Autowired
	private CmnCdService cmnCdService;
	@Autowired
	private SprtBizSttsService sprtBizSttsService;
	
	/**
	 * 개인마스터 화면
	 */
	@RequestMapping(value = "/bnft/bnftPsnlMstr")
	public String bnftPsnlMstr(HttpServletRequest request, Authentication auth) {
		
		List<CmnCdInfo> srcSysList = cmnCdService.selectList("SRC_SYS_CD"); // 출처시스템 (사업명) 목록
		List<CmnCdInfo> gndrList = cmnCdService.selectList("GNDR_CD"); // 성별 목록
		
		Date current = new Date(); // 현재
		String srchStartYmd = DateUtil.format(DateUtil.addDays(current, -30), "yyyy-MM-dd");
		String srchEndYmd = DateUtil.format(DateUtil.addDays(current, -0), "yyyy-MM-dd");
		
		request.setAttribute("srcSysList", srcSysList);
		request.setAttribute("gndrList", gndrList);
		request.setAttribute("srchStartYmd", srchStartYmd);
		request.setAttribute("srchEndYmd", srchEndYmd);
		
		return "/bnft/bnftPsnlMstr";
	}
	
	private Map<String, Object> getSelectListParam(HttpServletRequest request) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("PSNL_NM",      RequestUtil.getParam(request, "PSNL_NM",      null));
		param.put("EML",          RequestUtil.getParam(request, "EML",          null));
		param.put("BRDT",         RequestUtil.getParam(request, "BRDT",         null));
		param.put("GNDR_CD",      RequestUtil.getParam(request, "GNDR_CD",      null));
		param.put("SRC_SYS_CD",   RequestUtil.getParam(request, "SRC_SYS_CD",   null));
		param.put("DI_YN",        RequestUtil.getParam(request, "DI_YN",        null));
		param.put("srchStartYmd", RequestUtil.getParam(request, "srchStartYmd", null));
		param.put("srchEndYmd",   RequestUtil.getParam(request, "srchEndYmd",   null));
		
		if (param.get("srchStartYmd") != null && param.get("srchEndYmd") != null) {
			param.put("srchStartDt", DateUtil.parse(param.get("srchStartYmd") + "000000000", "yyyyMMddHHmmssSSS"));
			param.put("srchEndDt", DateUtil.parse(param.get("srchEndYmd") + "235959999", "yyyyMMddHHmmssSSS"));
		}
		
		return param;
	}
	
	/**
	 * 개인마스터 > 목록 API
	 */
	@RequestMapping(value = "/bnft/bnftPsnlMstr/api")
	@ResponseBody
	public RestResult bnftPsnlMstr_api(HttpServletRequest request, Authentication auth) {
		
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
	 * 개인마스터 > 엑셀 다운로드
	 */
	@RequestMapping(value = "/bnft/bnftPsnlMstr/excel")
	public String bnftPsnlMstr_excel(HttpServletRequest request, Authentication auth) {
		
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
		
		String[] headers = { "No", "DI 보유여부", "이름", "이메일", "전화번호", "우편번호", "기본주소", "상세주소", "생년월일", "성별",
				"출처시스템 로그인아이디", "출처시스템" };
		String[] dataKeys = { "NO", "DI_YN_NM", "PSNL_NM", "EML", "TELNO", "ZIP", "ADDR", "DADDR", "BRDT", "GNDR_NM",
				"SRC_LGN_ID", "SRC_SYS_NM" };
		
		Map<String, Object> excelData = new HashMap<String, Object>();
		excelData.put("fileName", "신청자 정보_" + DateUtil.format(new Date(), "yyyyMMdd"));
		excelData.put("headers", headers);
		excelData.put("dataKeys", dataKeys);
		excelData.put("dataList", list);
		
		request.setAttribute("excelData", excelData);
		
		return "excelXlsxView";
	}
	
	/**
	 * 개인마스터 > 개인 상세정보 팝업
	 */
	@RequestMapping(value = "/bnft/bnftPsnlInfoModal")
	public String bnftPsnlInfoModal(HttpServletRequest request, Authentication auth) {
		
		String diVal = RequestUtil.getParam(request, "DI_VAL", null);
		long psnlSn = RequestUtil.getParamLong(request, "PSNL_SN", 0);
		
		Map<String, Object> info = service.select(diVal, psnlSn);
		
		request.setAttribute("info", info);
		
		return "/bnft/bnftPsnlInfoModal";
	}
	
	/**
	 * 개인마스터 > 개인 상세정보 팝업 > 지원사업 이력 목록 API
	 */
	@RequestMapping(value = "/bnft/bnftPsnlInfoModal/api")
	@ResponseBody
	public RestResult bnftPsnlInfoModal_api(HttpServletRequest request, Authentication auth) {
		
		String diVal = RequestUtil.getParam(request, "DI_VAL", null);
		long psnlSn = RequestUtil.getParamLong(request, "PSNL_SN", 0);
		
		int draw = RequestUtil.getParamInt(request, "draw", 0);
		String column = RequestUtil.getParam(request, "order[0][column]", null); // 소팅컬럼 인덱스
		String sortName = RequestUtil.getParam(request, "columns["+ column +"][data]", null); // 소팅기준 컬럼명
		String sortType = RequestUtil.getParam(request, "order[0][dir]", null); // 소팅유형 (asc, desc)
		int startIndex = RequestUtil.getParamInt(request, "start", 0);
		int rowsCnt = RequestUtil.getParamInt(request, "length", 10);
		
		Paging paging = new Paging(sortName, sortType, startIndex, rowsCnt);
		
		List<Map<String, Object>> list = sprtBizSttsService.selectListForPsnl(diVal, psnlSn, paging);
		
		int totalCnt = sprtBizSttsService.countListForPsnl(diVal, psnlSn);
		
		return new RestResultDatatables(list, totalCnt, draw, startIndex);
	}
	
}
