package kr.bizdata.semas.pms.system.info;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 사이트 정보 - 세션에 저장된다.
 */
@Getter
@Setter
public class SiteInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String siteName;
	
	public String toString() {
		return "[사이트 정보]"
				+ "\n  - 사이트명: " + getSiteName();
	}
	
}
