package kr.bizdata.semas.from.smbi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdException;
import kr.bizdata.core.util.DateUtil;
import kr.bizdata.semas.from.smbi.info.FromDeptInfo;
import kr.bizdata.semas.from.smbi.mapper.FromDeptMapper;

/**
 * 부서정보시스템
 */
@Service
public class FromDeptService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FromDeptMapper mapper;
	
	/**
	 * @param startDt
	 * @param endDt
	 * @param typeCd 타입코드 (1: 지급온라인, 2: 온라인, 3: 오프라인)
	 * @return
	 */
	public List<FromDeptInfo> selectList() {
		return mapper.selectList(); // 지급온라인
	}
}
