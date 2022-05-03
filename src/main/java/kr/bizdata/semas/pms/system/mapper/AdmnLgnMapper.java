package kr.bizdata.semas.pms.system.mapper;

import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.system.info.AdmnLgnInfo;

/**
 * 관리자로그인기본
 */
@Repository
public interface AdmnLgnMapper {
	
	AdmnLgnInfo selectWithPassword(String loginId);
	
}
