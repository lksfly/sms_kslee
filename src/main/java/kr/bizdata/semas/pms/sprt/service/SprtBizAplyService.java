package kr.bizdata.semas.pms.sprt.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdNotFoundException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.pms.sprt.info.SprtBizAplyInfo;
import kr.bizdata.semas.pms.sprt.mapper.SprtBizAplyMapper;

/**
 * 지원사업신청내역
 */
@Service
public class SprtBizAplyService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SprtBizAplyMapper mapper;
	
	public void insert(SprtBizAplyInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("insert(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getAplySn() == 0, "aplySn is invalid.");
		Assert.isTrue(info.getSprtBizSn() > 0, "sprtBizSn is invalid.");
		
		mapper.insert(info); // 추가, GeneratedKey: aplySn
	}
	
	public void update(SprtBizAplyInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("update(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getAplySn() > 0, "aplySn is invalid.");
		Assert.isTrue(info.getSprtBizSn() > 0, "sprtBizSn is invalid.");
		
		mapper.update(info); // 수정
	}
	
	public void update_2(SprtBizAplyInfo info) {
		if (logger.isDebugEnabled()) {
			logger.debug("update(" + JsonUtil.toString(info) + ")");
		}
		Assert.notNull(info, "info is null.");
		Assert.isTrue(info.getAplySn() > 0, "aplySn is invalid.");
		
		mapper.update_2(info); // 수정
	}
	
	/**
	 * @param aplySn 신청일련번호
	 * @return
	 */
	public SprtBizAplyInfo select(long aplySn) {
		if (logger.isDebugEnabled()) {
			logger.debug("select(" + aplySn + ")");
		}
		Assert.isTrue(aplySn > 0, "aplySn is invalid.");
		
		SprtBizAplyInfo info = mapper.select(aplySn);
		if (info == null) {
			throw new BzdNotFoundException("지원사업신청내역 정보를 찾을 수 없습니다. (aplySn: " + aplySn + ")");
		}
		return info;
	}
	
	/**
	 * @param prgrmSn 프로그램순번
	 * @param mberSn 회원순번
	 * @param aplySn 신청일련번호
	 */
	public void insert_edu(Long prgrmSn, String mberSn, long aplySn) { // 교육
		mapper.insert_edu(prgrmSn, mberSn, aplySn);
	}
	
	/**
	 * @param cnsltAplySn 컨설팅신청일련번호
	 * @param actvtYr 활동연도
	 * @param plcyCd 정책코드
	 * @param aplySn 신청일련번호
	 */
	public void insert_cnslt(Long cnsltAplySn, String actvtYr, String plcyCd, long aplySn) { // 컨설팅
		mapper.insert_cnslt(cnsltAplySn, actvtYr, plcyCd, aplySn);
	}
	
	/**
	 * @param hopeCnsltAplyNo 희망리턴컨설팅신청번호
	 * @param aplySn 신청일련번호
	 */
	public void insert_hope1(String hopeCnsltAplyNo, long aplySn) { // 희망리턴 (컨설팅)
		mapper.insert_hope1(hopeCnsltAplyNo, aplySn);
	}
	
	/**
	 * @param reduCrsAplyNo 재기교육수강신청번호
	 * @param aplySn 신청일련번호
	 */
	public void insert_hope2(String reduCrsAplyNo, long aplySn) { // 희망리턴 (재기교육)
		mapper.insert_hope2(reduCrsAplyNo, aplySn);
	}
	
	/**
	 * @param sbsdyAplyNo 장려금신청번호
	 * @param aplySn 신청일련번호
	 */
	public void insert_hope3(String sbsdyAplyNo, long aplySn) { // 희망리턴 (장려금)
		mapper.insert_hope3(sbsdyAplyNo, aplySn);
	}
	
	/**
	 * @param lnAplyNo 대출신청번호
	 * @param aplySn 신청일련번호
	 */
	public void insert_dln(String lnAplyNo, long aplySn) { // 직접대출
		mapper.insert_dln(lnAplyNo, aplySn);
	}
	
	/**
	 * @param brno 사업자등록번호
	 * @param cnfrmIssuNo 확인서발급번호
	 * @param aplySn 신청일련번호
	 */
	public void insert_pln(String brno, String cnfrmIssuNo, long aplySn) { // 대리대출
		mapper.insert_pln(brno, cnfrmIssuNo, aplySn);
	}
	
	/**
	 * @param prgrmSn 프로그램순번
	 * @param mberSn 회원순번
	 * @return 신청일련번호
	 */
	public Long selectAplySn_edu(Long prgrmSn, String mberSn) { // 교육
		if (prgrmSn == null || StringUtils.isBlank(mberSn)) {
			return null;
		}
		return mapper.selectAplySn_edu(prgrmSn, mberSn);
	}
	
	/**
	 * @param cnsltAplySn 컨설팅신청일련번호
	 * @param actvtYr 활동연도
	 * @param plcyCd 정책코드
	 * @return 신청일련번호
	 */
	public Long selectAplySn_cnslt(Long cnsltAplySn, String actvtYr, String plcyCd) { // 컨설팅
		if (cnsltAplySn == null || StringUtils.isBlank(actvtYr) || StringUtils.isBlank(plcyCd)) {
			return null;
		}
		return mapper.selectAplySn_cnslt(cnsltAplySn, actvtYr, plcyCd);
	}
	
	/**
	 * @param hopeCnsltAplyNo 희망리턴컨설팅신청번호
	 * @return 신청일련번호
	 */
	public Long selectAplySn_hope1(String hopeCnsltAplyNo) { // 희망리턴 (컨설팅)
		if (StringUtils.isBlank(hopeCnsltAplyNo)) {
			return null;
		}
		return mapper.selectAplySn_hope1(hopeCnsltAplyNo);
	}
	
	/**
	 * @param reduCrsAplyNo 재기교육수강신청번호
	 * @return 신청일련번호
	 */
	public Long selectAplySn_hope2(String reduCrsAplyNo) { // 희망리턴 (재기교육)
		if (StringUtils.isBlank(reduCrsAplyNo)) {
			return null;
		}
		return mapper.selectAplySn_hope2(reduCrsAplyNo);
	}
	
	/**
	 * @param sbsdyAplyNo 장려금신청번호
	 * @return 신청일련번호
	 */
	public Long selectAplySn_hope3(String sbsdyAplyNo) { // 희망리턴 (장려금)
		if (StringUtils.isBlank(sbsdyAplyNo)) {
			return null;
		}
		return mapper.selectAplySn_hope3(sbsdyAplyNo);
	}
	
	/**
	 * @param lnAplyNo 대출신청번호
	 * @return 신청일련번호
	 */
	public Long selectAplySn_dln(String lnAplyNo) { // 직접대출
		if (StringUtils.isBlank(lnAplyNo)) {
			return null;
		}
		return mapper.selectAplySn_dln(lnAplyNo);
	}
	
	/**
	 * @param brno 사업자등록번호
	 * @param cnfrmIssuNo 확인서발급번호
	 * @return 신청일련번호
	 */
	public Long selectAplySn_pln(String brno, String cnfrmIssuNo) { // 대리대출
		if (StringUtils.isBlank(brno) || StringUtils.isBlank(cnfrmIssuNo)) {
			return null;
		}
		return mapper.selectAplySn_pln(brno, cnfrmIssuNo);
	}
	
}
