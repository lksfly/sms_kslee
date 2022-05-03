package kr.bizdata.semas.from.dln.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 직접대출시스템
 */
@Getter
@Setter
public class FromDlnInfo extends FromInfo {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/*
	 * 지원사업기본
	 */
	private String plcyFndCd; // 정책자금코드
	
	/*
	 * 지원사업신청내역
	 */
	private String lnAplyNo; // 대출신청번호
	private String lnCustRnno; // 신청자주민번호
	
	public void setLnCustRnno(String lnCustRnno) {
		this.lnCustRnno = lnCustRnno;
		// DB에서 암호화 처리의 부하로 인하여 아래와 같이 수정.
		// 신청자성별 정보 처리.
		if(lnCustRnno != null && lnCustRnno.trim().length() > 6) {
			super.setAplcntGndrCd(getGendorCd(lnCustRnno.substring(6, 7)));
		}
		// 신청자생년월일 정보 처리.
		super.setAplcntBrdt(getBirthDay(lnCustRnno));
		// 사업자유형코드 정보 처리.
		super.setBzmnTypeCd(getBizTypeCd(getBrno()));
	}
	
	public String getGendorCd(String custRnno) {
		String gendorCd = "9";
		if(custRnno != null && custRnno.trim().length() > 0) {
			if(custRnno.equals("1") || custRnno.equals("3") || custRnno.equals("5") || custRnno.equals("7")) {
				gendorCd = "1";
			} else if(custRnno.equals("2") || custRnno.equals("4") || custRnno.equals("6") || custRnno.equals("8")) {
				gendorCd = "2";
			} 
		}
		return gendorCd;
	}
	
	public String getBirthDay(String custRnno) {
		String birthDay = null;
		if(custRnno != null && custRnno.trim().length() > 6) {
			if(custRnno.substring(6, 7).equals("1") || custRnno.substring(6, 7).equals("2") || custRnno.substring(6, 7).equals("5") || custRnno.substring(6, 7).equals("6")) {
				birthDay = "19" + custRnno.substring(0, 6);
			} else if(custRnno.substring(6, 7).equals("3") || custRnno.substring(6, 7).equals("4") || custRnno.substring(6, 7).equals("7") || custRnno.substring(6, 7).equals("8")) {
				birthDay = "20" + custRnno.substring(0, 6);
			} 
		}
		return birthDay;
	}
	
	public String getBizTypeCd(String custRnno) {
		String bizTypeCd = "";
		if(custRnno == null) {
			bizTypeCd = "9";
		} else {
			custRnno.replaceAll("-", "");
			if(custRnno.length() != 10) {
				bizTypeCd = "9";
			} else if(custRnno.substring(3, 5).equals("81") || custRnno.substring(3, 5).equals("82") || custRnno.substring(3, 5).equals("83") || custRnno.substring(3, 5).equals("84") || custRnno.substring(3, 5).equals("85") || custRnno.substring(3, 5).equals("86") || custRnno.substring(3, 5).equals("87") || custRnno.substring(3, 5).equals("88")) {
				bizTypeCd = "1";
			} else {
				bizTypeCd = "2";
			}
		}
		return bizTypeCd;
	}
}
