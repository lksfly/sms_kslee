package kr.bizdata.semas.pms.system.info;

import kr.bizdata.Constant.LoginType;
import lombok.Getter;
import lombok.Setter;

/**
 * 관리자로그인기본
 */
@Getter
@Setter
public class AdmnLgnInfo {
	
	private String lgnId;
	private String lgnPw;
	private String lgnNm;
	private LoginType lgnTypeCd; // 로그인 유형
	private String useYn;
	
}
