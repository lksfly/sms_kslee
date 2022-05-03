package kr.bizdata.semas.pms.system.info;

import java.io.Serializable;

import kr.bizdata.Constant.LoginType;
import lombok.Getter;
import lombok.Setter;

/**
 * 통합로그인 정보 - 로그인시 세션에 저장된다.
 */
@Getter
@Setter
public class LoginInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/* 통합로그인 정보 */
	private String name;
	private String loginId;
	private String password;
	private LoginType loginType; // 로그인 유형
	
}
