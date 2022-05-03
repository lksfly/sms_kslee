package kr.bizdata.semas.from.pln.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.bizdata.semas.from.FromInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 대리대출시스템
 */
@Getter
@Setter
public class FromPlnInfo extends FromInfo {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/*
	 * 지원사업기본
	 */
	private String fndMngDivCd; // 자금운용구분코드
	
	/*
	 * 지원사업신청내역
	 */
	private String cnfrmIssuNo; // 확인서발급번호
	private String lnCustRnno;    // 신청자주민번호
	
	public void setLnCustRnno(String lnCustRnno) {
		this.lnCustRnno = lnCustRnno;
		String gendor_cd = super.getAplcntGndrCd();
		// DB에서 암호화 처리의 부하로 인하여 아래와 같이 수정.
		// 신청자성별 정보 처리.
		if(lnCustRnno != null && lnCustRnno.trim().length() > 6) {
			super.setAplcntGndrCd(getGendorCd(lnCustRnno.substring(6, 7)));
		}
		
		if(getAplcntGndrCd() != null && getAplcntGndrCd().equals("9")) {
			super.setAplcntGndrCd(gendor_cd);
		}
		
		// 신청자생년월일 정보 처리.
		super.setAplcntBrdt(getBirthDay(lnCustRnno));
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
}
