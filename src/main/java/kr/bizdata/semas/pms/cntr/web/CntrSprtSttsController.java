package kr.bizdata.semas.pms.cntr.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.bizdata.core.model.RestResult;
import kr.bizdata.core.servlet.AbstractController;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.core.util.RequestUtil;
import kr.bizdata.semas.pms.cntr.service.CntrSprtSttsService;

/**
 * 센터지원현황 화면
 */
@Controller
public class CntrSprtSttsController extends AbstractController {
	
	@Autowired
	private CntrSprtSttsService service;
	
	/**
	 * 센터지원현황 화면
	 */
	@RequestMapping(value = "/cntr/cntrSprtStts")
	public String cntrSprtStts(HttpServletRequest request, Authentication auth) {
		
		Date current = new Date(); // 현재
		String srchStartYmd = DateUtil.format(current, "yyyy") + "-01-01";
		String srchEndYmd = DateUtil.format(DateUtil.addDays(current, -1), "yyyy-MM-dd");
		
		request.setAttribute("srchStartYmd", srchStartYmd);
		request.setAttribute("srchEndYmd", srchEndYmd);
		
		return "/cntr/cntrSprtStts";
	}
	
	/**
	 * 센터지원현황 > 지원건수 목록 API
	 */
	@RequestMapping(value = "/cntr/cntrSprtStts/api")
	@ResponseBody
	public RestResult cntrSprtStts_api(HttpServletRequest request, Authentication auth) {
		
		String rgnCd = RequestUtil.getParam(request, "rgnCd", null);
		String srchStartYmd = RequestUtil.getParam(request, "srchStartYmd", null);
		String srchEndYmd = RequestUtil.getParam(request, "srchEndYmd", null);
		
		List<Map<String, Object>> list = null;
		if (rgnCd == null) {
			list = service.selectList(srchStartYmd, srchEndYmd); // 지역본부별
		} else {
			list = service.selectList(rgnCd, srchStartYmd, srchEndYmd); // 센터별
		}
		return new RestResult(list);
	}
	
}
