package kr.bizdata.semas.from.smbi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.semas.from.smbi.info.VSedaLoginInfo;
import kr.bizdata.semas.from.smbi.mapper.VSedaLoginMapper;

/**
 * 소진공로그인테이블
 */
@Service
public class VSedaLoginService {
	
	@Autowired
	private VSedaLoginMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public VSedaLoginInfo selectWithPassword(String loginId) {
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
		
		VSedaLoginInfo info = selectWithPassword(loginId);
		
		return passwordEncoder.matches(password, info.getPassword());
	}
	
	public void selectENC() {
		mapper.selectENC(); // 복호화.
	}
	
}
