package kr.bizdata.semas.pms.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.Constant.LoginType;
import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.semas.from.smbi.info.VSedaLoginInfo;
import kr.bizdata.semas.from.smbi.service.VSedaLoginService;
import kr.bizdata.semas.pms.system.info.AdmnLgnInfo;
import kr.bizdata.semas.pms.system.info.LoginInfo;

/**
 * 통합로그인
 */
@Service
public class LoginInfoService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdmnLgnService admnLgnService; // 관리자로그인기본
	@Autowired
	private VSedaLoginService vSedaLoginService; // 소진공로그인테이블
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public LoginInfo selectWithPassword(String loginId) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectWithPassword(" + loginId + ")");
		}
		Assert.hasText(loginId, "loginId is blank.");
		
		// 1. 관리자로그인
		AdmnLgnInfo admnLgnInfo = admnLgnService.selectWithPassword(loginId);
		if (admnLgnInfo != null && "Y".equals(admnLgnInfo.getUseYn())) { // 사용 상태인 경우
			LoginInfo info = new LoginInfo();
			info.setName(admnLgnInfo.getLgnNm());
			info.setLoginId(admnLgnInfo.getLgnId());
			info.setPassword(admnLgnInfo.getLgnPw());
			info.setLoginType(admnLgnInfo.getLgnTypeCd()); // 로그인유형코드
			return info;
		}
		
		// 2. 소진공로그인
		VSedaLoginInfo vSedaLoginInfo = vSedaLoginService.selectWithPassword(loginId);
		if (vSedaLoginInfo != null) {
			LoginInfo info = new LoginInfo();
			info.setName(vSedaLoginInfo.getName());
			info.setLoginId(vSedaLoginInfo.getLoginid());
			info.setPassword(vSedaLoginInfo.getPassword());
			info.setLoginType(LoginType.MEM); // 일반사용자
			return info;
		}
		
		throw new BzdNotFoundException("로그인 정보를 찾을 수 없습니다. (loginId: " + loginId + ")");
	}
	
	/**
	 * 로그인 비밀번호 검사
	 * 
	 * @param loginId	로그인 아이디
	 * @param password	비밀번호
	 * @return
	 */
	public boolean checkPassword(String loginId, String password) {
		Assert.hasLength(password, "password is empty.");
		
		LoginInfo info = selectWithPassword(loginId);
		
		return passwordEncoder.matches(password, info.getPassword());
	}
	
}
