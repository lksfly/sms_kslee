package kr.bizdata.semas.pms.cmn.info;

import lombok.Getter;
import lombok.Setter;

/**
 * 업종코드10차표준산업분류연계
 */
@Getter
@Setter
public class BizclsKsicLinkInfo {
	
	private String bizclsCd;   // 업종코드 (6자리, 세세분류)
	
	private String ksicLclsCd; // 표준산업분류대분류코드 (1자리)
	private String ksicMclsCd; // 표준산업분류중분류코드 (2자리)
	private String ksicSclsCd; // 표준산업분류소분류코드 (3자리)
	private String ksicCd;     // 표준산업분류코드 (4자리 또는 5자리, 세분류 또는 세세분류)
	
}
