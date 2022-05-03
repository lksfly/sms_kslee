package kr.bizdata.core.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import kr.bizdata.Constant.LoginType;
import kr.bizdata.semas.pms.system.info.LoginInfo;

public class LoginDetail implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private LoginInfo loginInfo; // 통합로그인 정보
	
	private Collection<GrantedAuthority> authorities;
	
	public LoginDetail(LoginInfo loginInfo) {
		Assert.notNull(loginInfo, "Cannot pass null values to constructor");
		//Assert.isTrue(loginInfo.getLoginSeq() > 0, "Cannot pass 0 values to constructor");
		Assert.hasLength(loginInfo.getLoginId(), "Cannot pass null or empty values to constructor");
		//Assert.hasLength(loginInfo.getPassword(), "Cannot pass null or empty values to constructor");
		
		this.loginInfo = loginInfo;
		authorities = new ArrayList<GrantedAuthority>();
		
		addAuthority(loginInfo.getLoginType());
	}
	
	public void addAuthority(LoginType loginType) {
		if (loginType != null) {
			authorities.add(new SimpleGrantedAuthority(loginType.toString()));
		}
	}
	
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return loginInfo.getPassword();
	}
	
	@Override
	public String getUsername() {
		return loginInfo.getLoginId();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/* 
	 * Spring Security 동시세션 제어 기능을 위해, hashCode()와 equals()를 오버라이딩해야 한다.
	 *   - security:http > session-management > concurrency-control 사용시 필요
	 */
	public int hashCode() {
		return getUsername().hashCode();
	}
	public boolean equals(Object obj) {
		return getUsername().equals(((UserDetails) obj).getUsername());
	}
	
}
