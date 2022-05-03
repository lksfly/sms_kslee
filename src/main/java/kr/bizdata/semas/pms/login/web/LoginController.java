package kr.bizdata.semas.pms.login.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.bizdata.core.servlet.AbstractController;

/**
 * 로그인, 로그아웃
 */
@Controller
public class LoginController extends AbstractController {
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Authentication auth) {
		
		String applcationUrl = getApplcationUrl(request);
		
		request.setAttribute("AppURL", applcationUrl);
		
		return "/login/login";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, Authentication auth) {
		
		return "/login/logout";
	}
	
}
