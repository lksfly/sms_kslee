package kr.bizdata.semas.pms.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.semas.pms.system.info.AdmnLgnInfo;
import kr.bizdata.semas.pms.system.mapper.AdmnLgnMapper;

/**
 * 관리자로그인기본
 */
@Service
public class AdmnLgnService {
	
	@Autowired
	private AdmnLgnMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AdmnLgnInfo selectWithPassword(String loginId) {
		Assert.hasText(loginId, "loginId is blank.");
		
		return mapper.selectWithPassword(loginId);
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
		
		AdmnLgnInfo info = selectWithPassword(loginId);
		
		return passwordEncoder.matches(password, info.getLgnPw());
	}
	
}
