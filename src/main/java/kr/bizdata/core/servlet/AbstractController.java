package kr.bizdata.core.servlet;

import static kr.bizdata.Constant.SESSION_SITE_INFO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.bizdata.Constant.LoginType;
import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.security.LoginDetail;
import kr.bizdata.semas.pms.system.info.LoginInfo;
import kr.bizdata.semas.pms.system.info.SiteInfo;

public abstract class AbstractController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 인증이 되었는지 검사한다.
	 * 
	 * @param auth
	 * @return
	 */
	protected boolean isAuthenticated(Authentication auth) {
		return auth != null && auth.isAuthenticated();
	}
	
	/**
	 * 권한을 가지고 있는지 검사한다.
	 * 
	 * @param auth
	 * @param loginType		로그인 유형
	 * @return
	 */
	protected boolean hasAuthority(Authentication auth, LoginType loginType) {
		if (!isAuthenticated(auth)) {
			return false;
		}
		
		Iterator<? extends GrantedAuthority> iterator = auth.getAuthorities().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getAuthority().equals(loginType.toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 사이트 정보를 구한다.
	 * 
	 * @param request
	 * @return
	 */
	protected SiteInfo getSiteInfo(HttpServletRequest request) {
		SiteInfo siteInfo = (SiteInfo) request.getSession().getAttribute(SESSION_SITE_INFO);
		if (siteInfo == null) {
			throw new BzdNotFoundException("사이트 정보가 없습니다.");
		}
		return siteInfo;
	}
	
	/**
	 * 통합로그인 정보를 구한다.
	 * 
	 * @param auth
	 * @return 로그인되지 않은 경우, null 반환
	 */
	protected LoginInfo getLoginInfo(Authentication auth) {
		LoginInfo loginInfo = null; // 통합로그인 정보
		if (isAuthenticated(auth) && auth.getPrincipal() instanceof LoginDetail) {
			loginInfo = ((LoginDetail) auth.getPrincipal()).getLoginInfo();
		}
		return loginInfo;
	}
	
	/**
	 * 업로드한 파일 목록을 구한다.
	 * 
	 * @param request
	 * @return
	 */
	protected List<MultipartFile> getMultipartFileList(MultipartHttpServletRequest request) {
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		Iterator<String> fileNames =  request.getFileNames();
		while(fileNames.hasNext()) {
			List<MultipartFile> files =  request.getFiles(fileNames.next());
			for (MultipartFile upload : files) {
				if (upload.getSize() == 0 || StringUtils.isBlank(upload.getOriginalFilename())) {
					continue;
				}
				list.add(upload);
			}
		}
		return list;
	}
	
	/**
	 * ex) http://localhost:8080
	 * 
	 * @param request
	 * @return
	 */
	protected String getApplcationUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		
		String rootPath = StringUtils.isEmpty(uri) ? url : url.substring(0, url.indexOf(uri));
		String contextPath = "/".equals(request.getContextPath()) ? "" : request.getContextPath();
		
		return rootPath + contextPath;
	}
	
}
