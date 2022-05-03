package kr.bizdata.semas.from.smbi.mapper;

import org.springframework.stereotype.Repository;

import kr.bizdata.semas.from.smbi.info.VSedaLoginInfo;

/**
 * 소진공로그인테이블
 */
@Repository
public interface VSedaLoginMapper {
	
	VSedaLoginInfo selectWithPassword(String loginId);
	
	String selectENC();
	
}
