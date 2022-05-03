package kr.bizdata.core.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.bizdata.core.util.CryptUtil;

public class SemasPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		return CryptUtil.encodeSHA512(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		return encode(rawPassword).equals(encodedPassword);
	}

}
