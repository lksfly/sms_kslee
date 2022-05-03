package kr.bizdata.semas.pms.btch.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.bizdata.core.model.Paging;
import kr.bizdata.core.model.RestResult;
import kr.bizdata.core.model.RestResultDatatables;
import kr.bizdata.core.servlet.AbstractController;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.RequestUtil;
import kr.bizdata.semas.pms.btch.info.BtchJobLogInfo;
import kr.bizdata.semas.pms.btch.info.BtchJobRegInfo;
import kr.bizdata.semas.pms.btch.service.BtchJobLogService;
import kr.bizdata.semas.pms.btch.service.BtchJobRegService;
import kr.bizdata.semas.pms.cmn.info.CmnCdInfo;
import kr.bizdata.semas.pms.cmn.service.CmnCdService;

/**
 * Data Batch 화면
 */
@Controller
public class BtchController extends AbstractController {
	
	@Autowired
	private BtchJobRegService regService; // Batch 관리
	@Autowired
	private BtchJobLogService logService; // Batch 로그
	
	@Autowired
	private CmnCdService cmnCdService;
	
	/**
	 * Batch 관리 화면
	 */
	@RequestMapping(value = "/btch/btchMng")
	public String btchMng(HttpServletRequest request, Authentication auth) {
		
		return "/btch/btchMng";
	}
	
	/**
	 * Batch 관리 > 목록 API
	 */
	@RequestMapping(value = "/btch/btchMng/api")
	@ResponseBody
	public RestResult btchMng_api(HttpServletRequest request, Authentication auth) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("jobNm",   RequestUtil.getParam(request, "jobNm",   null));
		param.put("clsNm",   RequestUtil.getParam(request, "clsNm",   null));
		param.put("mthdNm",  RequestUtil.getParam(request, "mthdNm",  null));
		
		List<BtchJobRegInfo> list = regService.selectList(param);
		
		return new RestResult(list);
	}
	
	/**
	 * Batch 관리 > 실행 API
	 */
	@RequestMapping(value = "/btch/btchMng/run")
	@ResponseBody
	public RestResult btchMng_run(HttpServletRequest request, Authentication auth) {
		
		Long[] jobIds = RequestUtil.splitParamLong(request, "jobIds", ",");
		
		regService.runBtchJobList(jobIds);
		
		return new RestResult(true);
	}
	
	/**
	 * Batch 관리 > Batch 등록/수정 팝업
	 */
	@RequestMapping(value = "/btch/btchRegInfoModal")
	public String btchRegInfoModal(HttpServletRequest request, Authentication auth) {
		
		long jobId = RequestUtil.getParamLong(request, "jobId", 0);
		
		BtchJobRegInfo info = null;
		if (jobId > 0) {
			info = regService.select(jobId);
		}
		
		request.setAttribute("info", info);
		
		return "/btch/btchRegInfoModal";
	}
	
	/**
	 * Batch 관리 > Batch 등록/수정 팝업 > 저장 API
	 */
	@RequestMapping(value = "/btch/btchRegInfoModal/save", method = RequestMethod.POST)
	@ResponseBody
	public RestResult btchRegInfoModal_save(HttpServletRequest request, Authentication auth) {
		
		String loginId = getLoginInfo(auth).getLoginId();
		
		long jobId = RequestUtil.getParamLong(request, "jobId", 0);
		String jobNm = RequestUtil.getParam(request, "jobNm", null);
		String pckgNm = RequestUtil.getParam(request, "pckgNm", null);
		String clsNm = RequestUtil.getParam(request, "clsNm", null);
		String mthdNm = RequestUtil.getParam(request, "mthdNm", null);
		String cronExp = RequestUtil.getParam(request, "cronExp", null);
		String useYn = RequestUtil.getParam(request, "useYn", null);
		
		BtchJobRegInfo info = new BtchJobRegInfo();
		info.setJobId(jobId);
		info.setRegUserId(loginId);
		info.setMdfcnUserId(loginId);
		info.setUseYn("Y".equalsIgnoreCase(useYn) ? "Y" : "N");
		info.setJobNm(jobNm);
		info.setPckgNm(pckgNm);
		info.setClsNm(clsNm);
		info.setMthdNm(mthdNm);
		info.setCronExp(cronExp);
		
		regService.save(info); // 저장 (추가/수정)
		
		return new RestResult(true);
	}
	
	/**
	 * Batch 관리 > Batch 등록/수정 팝업 > 삭제 API
	 */
	@RequestMapping(value = "/btch/btchRegInfoModal/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult btchRegInfoModal_delete(HttpServletRequest request, Authentication auth) {
		
		String loginId = getLoginInfo(auth).getLoginId();
		
		long jobId = RequestUtil.getParamLong(request, "jobId", 0);
		
		regService.deleteList(new Long[] { jobId }, loginId); // 삭제
		
		return new RestResult(true);
	}
	
	/**
	 * Batch 로그 화면
	 */
	@RequestMapping(value = "/btch/btchLog")
	public String btchLog(HttpServletRequest request, Authentication auth) {
		
		List<CmnCdInfo> rsltList = cmnCdService.selectList("RSLT_CD", "code", "desc"); // 실행결과 목록
		
		Date current = new Date(); // 현재
		String bgngYmd = DateUtil.format(current, "yyyy-MM-dd");
		
		request.setAttribute("rsltList", rsltList);
		request.setAttribute("bgngYmd", bgngYmd);
		
		return "/btch/btchLog";
	}
	
	/**
	 * Batch 로그 > 목록 API
	 */
	@RequestMapping(value = "/btch/btchLog/api")
	@ResponseBody
	public RestResult btchLog_api(HttpServletRequest request, Authentication auth) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("bgngYmd", RequestUtil.getParam(request, "bgngYmd", null));
		param.put("jobNm",   RequestUtil.getParam(request, "jobNm",   null));
		param.put("rsltCd",  RequestUtil.getParam(request, "rsltCd",  null));
		
		int draw = RequestUtil.getParamInt(request, "draw", 0);
		String column = RequestUtil.getParam(request, "order[0][column]", null); // 소팅컬럼 인덱스
		String sortName = RequestUtil.getParam(request, "columns["+ column +"][data]", null); // 소팅기준 컬럼명
		String sortType = RequestUtil.getParam(request, "order[0][dir]", null); // 소팅유형 (asc, desc)
		int startIndex = RequestUtil.getParamInt(request, "start", 0);
		int rowsCnt = RequestUtil.getParamInt(request, "length", 10);
		
		Paging paging = new Paging(sortName, sortType, startIndex, rowsCnt);
		
		List<BtchJobLogInfo> list = logService.selectList(param, paging);
		
		int totalCnt = logService.countList(param);
		
		return new RestResultDatatables(list, totalCnt, draw, startIndex);
	}
	
	/**
	 * Batch 로그 > 처리내용 상세 팝업
	 */
	@RequestMapping(value = "/btch/btchLogInfoModal")
	public String btchLogInfoModal(HttpServletRequest request, Authentication auth) {
		
		long jobSn = RequestUtil.getParamLong(request, "jobSn", 0);
		
		BtchJobLogInfo info = logService.select(jobSn);
		
		String rsltCn = StringUtils.trim(info.getRsltCn());
		rsltCn = StringEscapeUtils.escapeXml11(rsltCn);
		rsltCn = StringUtils.replace(rsltCn, "\n", "<br />");
		info.setRsltCn(rsltCn);
		
		request.setAttribute("info", info);
		
		return "/btch/btchLogInfoModal";
	}
	
}
