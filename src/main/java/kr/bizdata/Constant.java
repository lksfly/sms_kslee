package kr.bizdata;

public interface Constant {
	
	/**
	 * Session Key
	 */
	public final String SESSION_SITE_INFO = "SESSION_SITE_INFO"; // 사이트 정보
	
	/**
	 * 로그인 유형
	 */
	public enum LoginType {
		AD1, // 배치관리자
		AD2, // 센터관리자
		MEM; // 일반사용자
	}
	
}
