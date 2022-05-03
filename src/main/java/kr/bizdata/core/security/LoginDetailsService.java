package kr.bizdata.core.security;

import static kr.bizdata.Constant.SESSION_SITE_INFO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import kr.bizdata.core.exception.BzdException;
import kr.bizdata.core.model.RestResult;
import kr.bizdata.core.servlet.ClientHolder;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.system.info.LoginInfo;
import kr.bizdata.semas.pms.system.service.LoginInfoService;

public class LoginDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginDetailsService.class);
	
	@Autowired
	private LoginInfoService loginInfoService;
	
	@Override
	public UserDetails loadUserByUsername(String bizNo) {
		
		LoginInfo loginInfo = loginInfoService.selectWithPassword(bizNo);
		
		return new LoginDetail(loginInfo);
	}
	
	public static class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
		
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication auth) throws IOException, ServletException {
			if (logger.isDebugEnabled()) {
				logger.debug("authentication success. (username: " + auth.getName() + ")\n" + ClientHolder.getSiteInfo());
			}
			LoginInfo loginInfo = ((LoginDetail) auth.getPrincipal()).getLoginInfo(); // 통합로그인 정보
			
			/*
				loginHistoryService.insert(loginInfo.getLoginSeq(), ClientHolder.getOrgInfo().getOrgSeq(),
						HttpUtil.getRemoteAddr(request), request.getHeader("User-Agent"));
			*/
			
			HttpSession session = request.getSession();
			
			// spring-security.xml에 newSession으로 설정하면, session이 초기화되기 때문에 세션 정보를 재설정한다.
			session.setAttribute(SESSION_SITE_INFO, ClientHolder.getSiteInfo());
			
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(JsonUtil.toString(new RestResult(true, null, loginInfo.getLoginType())));
		}
		
	}
	
	public static class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
		
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException e) throws IOException, ServletException {
			
			String message = null;
			
			if (e.getCause() instanceof BzdException) {
				message = "아이디 또는 비밀번호를 확인해 주시기 바랍니다.";
			} else if (e instanceof BadCredentialsException) {
				message = "아이디 또는 비밀번호를 확인해 주시기 바랍니다.";
			} else if (e instanceof SessionAuthenticationException) {
				message = "이미 사용중인 아이디입니다."; // Maximum sessions of 1 for this principal exceeded
			} else {
				message = "시스템 오류가 발생했습니다. 운영팀에 문의해 주시기 바랍니다.";
			}
			
			logger.info("authentication failure. (message: " + message + ")");
			
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(JsonUtil.toString(new RestResult(false, message, null)));
		}
		
	}
	
	/**
	 * 인증 오류 발생시 실행되는 핸들러
	 *   - 유효하지 않은 세션 ID로 요청한 경우 ex) session timeout
	 *   - 인증되지 않은 세션으로 요청한 경우
	 */
	public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
		
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException e) throws IOException, ServletException {
			
			response.sendError(401, "다시 로그인해 주시기 바랍니다."); // 401 (Unauthorized) to error.jsp
		}
		
	}
	
}
