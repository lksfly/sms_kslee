package kr.bizdata.core.servlet;

import kr.bizdata.semas.pms.system.info.SiteInfo;

public class ClientHolder {
	
	private static final ThreadLocal<SiteInfo> siteInfo = new ThreadLocal<SiteInfo>(); // 사이트 정보
	
	public static void clear() {
		siteInfo.remove();
	}
	
	public static SiteInfo getSiteInfo() {
		return siteInfo.get() != null ? siteInfo.get() : new SiteInfo();
	}
	public static void setSiteInfo(SiteInfo siteInfo) {
		ClientHolder.siteInfo.set(siteInfo);
	}
	
}
