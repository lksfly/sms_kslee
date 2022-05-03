package kr.bizdata.semas.from.smbi.info;

import lombok.Getter;
import lombok.Setter;

/**
 * 소진공로그인테이블
 */
@Getter
@Setter
public class FromDeptInfo {
	private String deptCd;
	private String upDeptCd;
	private String deptNm;
	private String cntrTypeCd;
	private String useYn;
	private String odr;
	private String zip;
	private String addr1;
	private String addr2;
	private String telno;
	private String fxno;
	private String jrsdcArea;
	private String rgnCd;
}
