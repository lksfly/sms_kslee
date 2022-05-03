package kr.bizdata.semas.pms.sprt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.sprt.info.SprtBizInfo;
import kr.bizdata.semas.pms.sprt.mapper.SprtBizMapper;

/**
 * 지원사업기본
 */
@Service
public class SprtBizService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SprtBizMapper mapper;
	
	public void insert(SprtBizInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getSprtBizSn() == 0, "sprtBizSn is invalid.");
		
		mapper.insert(info); // 추가, GeneratedKey: sprtBizSn
	}
	
	public void update(SprtBizInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("update(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getSprtBizSn() > 0, "sprtBizSn is invalid.");
		
		mapper.update(info); // 수정
	}
	
	/**
	 * @param sprtBizSn 지원사업일련번호
	 * @return
	 */
	public SprtBizInfo select(long sprtBizSn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + sprtBizSn + ")");
		}
		Assert.isTrue(sprtBizSn > 0, "sprtBizSn is invalid.");
		
		SprtBizInfo info = mapper.select(sprtBizSn);
		if (info == null) {
			throw new BzdNotFoundException("지원사업기본 정보를 찾을 수 없습니다. (sprtBizSn: " + sprtBizSn + ")");
		}
		return info;
	}
	
	/**
	 * @param eduSprtBizno 교육지원사업번호
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_edu(Long eduSprtBizno, long sprtBizSn) { // 교육
		mapper.insert_edu(eduSprtBizno, sprtBizSn);
	}
	
	/**
	 * @param sprtBizNmSn 지원사업명일련번호
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_cnslt(Long sprtBizNmSn, long sprtBizSn) { // 컨설팅
		mapper.insert_cnslt(sprtBizNmSn, sprtBizSn);
	}
	
	/**
	 * @param hopeSprtBizno 희망리턴지원사업번호
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_hope1(String hopeSprtBizno, long sprtBizSn) { // 희망리턴 (컨설팅)
		mapper.insert_hope1(hopeSprtBizno, sprtBizSn);
	}
	
	/**
	 * @param hopeSprtBizno 희망리턴지원사업번호
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_hope2(String hopeSprtBizno, long sprtBizSn) { // 희망리턴 (재기교육)
		mapper.insert_hope2(hopeSprtBizno, sprtBizSn);
	}
	
	/**
	 * @param sprtBizNmSn 지원사업명일련번호
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_hope3(Long sprtBizNmSn, long sprtBizSn) { // 희망리턴 (장려금)
		mapper.insert_hope3(sprtBizNmSn, sprtBizSn);
	}
	
	/**
	 * @param plcyFndCd 정책자금코드
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_dln(String plcyFndCd, long sprtBizSn) { // 직접대출
		mapper.insert_dln(plcyFndCd, sprtBizSn);
	}
	
	/**
	 * @param fndMngDivCd 자금운용구분코드
	 * @param sprtBizSn 지원사업일련번호
	 */
	public void insert_pln(String fndMngDivCd, long sprtBizSn) { // 대리대출
		mapper.insert_pln(fndMngDivCd, sprtBizSn);
	}
	
	/**
	 * @param eduSprtBizno 교육지원사업번호
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_edu(Long eduSprtBizno, String sprtBizYr) { // 교육
		return mapper.selectSprtBizSn_edu(eduSprtBizno, sprtBizYr);
	}
	
	/**
	 * @param sprtBizDivCd 지원사업구분코드
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_cnslt(String sprtBizDivCd, String sprtBizYr) { // 컨설팅
		return mapper.selectSprtBizSn_cnslt(sprtBizDivCd, sprtBizYr);
	}
	
	/**
	 * @param hopeSprtBizno 희망리턴지원사업번호
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_hope1(String hopeSprtBizno, String sprtBizYr) { // 희망리턴 (컨설팅)
		return mapper.selectSprtBizSn_hope1(hopeSprtBizno, sprtBizYr);
	}
	
	/**
	 * @param hopeSprtBizno 희망리턴지원사업번호
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_hope2(String hopeSprtBizno, String sprtBizYr) { // 희망리턴 (재기교육)
		return mapper.selectSprtBizSn_hope2(hopeSprtBizno, sprtBizYr);
	}
	
	/**
	 * @param sprtBizDivCd 지원사업구분코드
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_hope3(String sprtBizDivCd, String sprtBizYr) { // 희망리턴 (장려금)
		return mapper.selectSprtBizSn_hope3(sprtBizDivCd, sprtBizYr);
	}
	
	/**
	 * @param plcyFndCd 정책자금코드
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_dln(String plcyFndCd, String sprtBizYr) { // 직접대출
		return mapper.selectSprtBizSn_dln(plcyFndCd, sprtBizYr);
	}
	
	/**
	 * @param fndMngDivCd 자금운용구분코드
	 * @param sprtBizYr 지원사업연도
	 * @return 지원사업일련번호
	 */
	public Long selectSprtBizSn_pln(String fndMngDivCd, String sprtBizYr) { // 대리대출
		return mapper.selectSprtBizSn_pln(fndMngDivCd, sprtBizYr);
	}
	
}
