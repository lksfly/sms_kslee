package kr.bizdata.core.servlet;

import static kr.bizdata.Constant.SESSION_SITE_INFO;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import kr.bizdata.core.config.Config;
import kr.bizdata.core.security.LoginDetail;
import kr.bizdata.core.util.HttpUtil;
import kr.bizdata.semas.pms.system.info.LoginInfo;
import kr.bizdata.semas.pms.system.info.SiteInfo;

/**
 * 세션과 ThreadLocal 변수에 정보를 저장한다.
 * 
 * (1) 사이트 정보
 * (2) 기관 정보
 */
public class ClientHolderSettingFilter implements Filter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 크로스 도메인 요청을 위한, 응답 헤더 설정 (for CORS Reqeusts)
	 */
	private void setCorsHeader(HttpServletRequest request, HttpServletResponse response) {
		
		if (StringUtils.isNotEmpty(request.getHeader("Origin"))) {
			response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); // "*"
			response.addHeader("Access-Control-Allow-Credentials", "true"); // 크로스 도메인 요청시, 세션 고정 (for CORS Reqeusts)
			response.addHeader("Access-Control-Max-Age", "86400"); // cache for 1 day
		}
		if ("OPTIONS".equals(request.getMethod())) {
			response.addHeader("Access-Control-Allow-Methods", request.getMethod()); // "GET, POST, OPTIONS"
			response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		}
	}
	
	/**
	 * 사이트 정보를 구한다.
	 *   (1) 세션
	 *   (2) 요청 URL
	 */
	private SiteInfo getSiteInfo(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		SiteInfo siteInfo = (SiteInfo) session.getAttribute(SESSION_SITE_INFO); // from Session
		
		if (siteInfo != null) {
			return siteInfo; // 세션값이 있으면, 세션값 반환
		}
		
		siteInfo = new SiteInfo();
		siteInfo.setSiteName(Config.getString("bzd.siteName"));
		return siteInfo;
	}
	
	/**
	 * 로그 메시지를 구한다. (모니터링용)
	 * 
	 * @param request
	 * @return
	 */
	private String getLogMsg(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		LoginInfo loginInfo = null; // 통합로그인 정보
		
		if (session.getAttribute("SPRING_SECURITY_CONTEXT") instanceof SecurityContext) {
			Authentication auth = ((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication();
			if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof LoginDetail) {
				loginInfo = ((LoginDetail) auth.getPrincipal()).getLoginInfo();
			}
		}
		
		String loginId = "";
		String loginType = "";
		
		if (loginInfo != null) {
			loginId = loginInfo.getLoginId();
			loginType = loginInfo.getLoginType().toString();
		}
		
		return request.getRequestURL() + " (remoteAddr=" + HttpUtil.getRemoteAddr(request) + ", JSESSIONID=" + session.getId()
				+ ", loginId=" + loginId + ", loginType=" + loginType + ")";
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		
		setCorsHeader(request, (HttpServletResponse) servletResponse);
		
		String contextPath = "/".equals(request.getContextPath()) ? "" : request.getContextPath();
		String uri = request.getRequestURI();
		
		if (uri != null && (uri.startsWith(contextPath + "/assets/") || uri.startsWith(contextPath + "/jsp/nx/res/"))) { // 정적 리소스 (assets 경로 아래의 모든 파일)
			// Pass control on to the next filter
			chain.doFilter(servletRequest, servletResponse);
			return; // 정적 리소스는 처리하지 않는다.
		}
		
		String logMsg = getLogMsg(request);
		long start = System.currentTimeMillis();
		logger.info("[START] " + logMsg);
		
		try {
			SiteInfo siteInfo = getSiteInfo(request); // 사이트 정보
			
			// session
			session.setAttribute(SESSION_SITE_INFO, siteInfo);
			
			// ThreadLocal
			ClientHolder.setSiteInfo(siteInfo);
		
			// Pass control on to the next filter
			chain.doFilter(servletRequest, servletResponse);
		} finally {
			ClientHolder.clear(); // ThreadLocal clear
			long end = System.currentTimeMillis();
			logger.info("[END " + (end - start) + "ms] " + logMsg);
		}
	}
	
	@Override
	public void destroy() {
	}
	
}