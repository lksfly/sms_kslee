package kr.bizdata.semas.pms.bnft.info;

import java.util.Date;

import kr.bizdata.semas.from.smbi.info.FromDeptInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 공단부서
 */
@Getter
@Setter
public class BnftDeptInfo {
	
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
	
	public BnftDeptInfo() {
		// 기본 생성자
	}
	
	public BnftDeptInfo(FromDeptInfo from) {
		this.deptCd     = from.getDeptCd    ();
		this.upDeptCd   = from.getUpDeptCd  ();
		this.deptNm     = from.getDeptNm    ();
		this.cntrTypeCd = from.getCntrTypeCd();
		this.useYn      = from.getUseYn     ();
		this.odr        = from.getOdr       ();
		this.zip        = from.getZip       ();
		this.addr1      = from.getAddr1     ();
		this.addr2      = from.getAddr2     ();
		this.telno      = from.getTelno     ();
		this.fxno       = from.getFxno      ();
		this.jrsdcArea  = from.getJrsdcArea ();
		this.rgnCd      = from.getRgnCd     ();
	}
}
