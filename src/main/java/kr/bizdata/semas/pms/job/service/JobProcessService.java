package kr.bizdata.semas.pms.job.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.bizdata.core.exception.BzdException;
import kr.bizdata.core.util.JsonUtil;
import kr.bizdata.semas.from.FromInfo;
import kr.bizdata.semas.from.cnslt.info.FromCnsltInfo;
import kr.bizdata.semas.from.dln.info.FromDlnInfo;
import kr.bizdata.semas.from.edu.info.FromEduInfo;
import kr.bizdata.semas.from.hope.info.FromHope1Info;
import kr.bizdata.semas.from.hope.info.FromHope2Info;
import kr.bizdata.semas.from.hope.info.FromHope3Info;
import kr.bizdata.semas.from.pln.info.FromPlnInfo;
import kr.bizdata.semas.from.smbi.info.FromDeptInfo;
import kr.bizdata.semas.pms.bnft.info.BnftBizHistInfo;
import kr.bizdata.semas.pms.bnft.info.BnftBizInfo;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlHistInfo;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlInfo;
import kr.bizdata.semas.pms.bnft.info.BnftPsnlTempInfo;
import kr.bizdata.semas.pms.bnft.info.BnftDeptInfo;
import kr.bizdata.semas.pms.bnft.service.BnftBizHistService;
import kr.bizdata.semas.pms.bnft.service.BnftBizPsnlService;
import kr.bizdata.semas.pms.bnft.service.BnftBizService;
import kr.bizdata.semas.pms.bnft.service.BnftPsnlHistService;
import kr.bizdata.semas.pms.bnft.service.BnftPsnlService;
import kr.bizdata.semas.pms.bnft.service.BnftPsnlTempService;
import kr.bizdata.semas.pms.bnft.service.BnftDeptService;
import kr.bizdata.semas.pms.cmn.info.BizclsKsicLinkInfo;
import kr.bizdata.semas.pms.cmn.service.BizclsKsicLinkService;
import kr.bizdata.semas.pms.sprt.info.SprtBizAplyInfo;
import kr.bizdata.semas.pms.sprt.info.SprtBizInfo;
import kr.bizdata.semas.pms.sprt.info.SprtBizNmMngInfo;
import kr.bizdata.semas.pms.sprt.service.SprtBizAplyService;
import kr.bizdata.semas.pms.sprt.service.SprtBizNmMngService;
import kr.bizdata.semas.pms.sprt.service.SprtBizService;

@Service
public class JobProcessService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BizclsKsicLinkService bizclsKsicLinkService;
	
	@Autowired
	private SprtBizService sprtBizService;
	@Autowired
	private SprtBizAplyService sprtBizAplyService;
	@Autowired
	private SprtBizNmMngService sprtBizNmMngService;
	
	@Autowired
	private BnftBizService bnftBizService;
	@Autowired
	private BnftBizHistService bnftBizHistService;
	
	@Autowired
	private BnftPsnlService bnftPsnlService;
	@Autowired
	private BnftPsnlHistService bnftPsnlHistService;
	@Autowired
	private BnftPsnlTempService bnftPsnlTempService;
	
	@Autowired
	private BnftBizPsnlService bnftBizPsnlService;
	
	@Autowired
	private BnftDeptService bnftDeptService;
	
	/**
	 * 데이터 처리
	 */
	public void process(FromInfo from) {
		if (logger.isDebugEnabled()) {
			logger.debug("process(" + JsonUtil.toString(from) + ")");
		}
		Assert.notNull(from, "from is null.");
		
		Long sprtBizSn = null; // 지원사업일련번호
		Long aplySn = null; // 신청일련번호
		
		// 1. 통합데이터 승인
		if(from != null && from.getAplyStsCd() != null && from.getAplyStsCd().equals("1")) {
			// 2. 시스템별 연계 키 조회
			if (from instanceof FromEduInfo) { // 교육
				FromEduInfo obj = (FromEduInfo) from;
				sprtBizSn = sprtBizService.selectSprtBizSn_edu(obj.getEduSprtBizno(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_edu(obj.getPrgrmSn(), obj.getMberSn());
			} else if (from instanceof FromCnsltInfo) { // 컨설팅
				FromCnsltInfo obj = (FromCnsltInfo) from;
				SprtBizNmMngInfo nmMng = sprtBizNmMngService.select(obj.getSprtBizDivCd());
				obj.setSprtBizNmSn(nmMng.getSprtBizNmSn()); // (1) 지원사업명일련번호 설정
				obj.setSprtBizNm(nmMng.getSprtBizNm());     // (2) 지원사업명 설정
				sprtBizSn = sprtBizService.selectSprtBizSn_cnslt(obj.getSprtBizDivCd(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_cnslt(obj.getCnsltAplySn(), obj.getActvtYr(), obj.getPlcyCd());
			} else if (from instanceof FromHope1Info) { // 희망리턴 (컨설팅)
				FromHope1Info obj = (FromHope1Info) from;
				sprtBizSn = sprtBizService.selectSprtBizSn_hope1(obj.getHopeSprtBizno(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_hope1(obj.getHopeCnsltAplyNo());
			} else if (from instanceof FromHope2Info) { // 희망리턴 (재기교육)
				FromHope2Info obj = (FromHope2Info) from;
				sprtBizSn = sprtBizService.selectSprtBizSn_hope2(obj.getHopeSprtBizno(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_hope2(obj.getReduCrsAplyNo());
			} else if (from instanceof FromHope3Info) { // 희망리턴 (장려금)
				FromHope3Info obj = (FromHope3Info) from;
				SprtBizNmMngInfo nmMng = sprtBizNmMngService.select(obj.getSprtBizDivCd());
				obj.setSprtBizNmSn(nmMng.getSprtBizNmSn()); // (1) 지원사업명일련번호 설정
				obj.setSprtBizNm(nmMng.getSprtBizNm());     // (2) 지원사업명 설정
				sprtBizSn = sprtBizService.selectSprtBizSn_hope3(obj.getSprtBizDivCd(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_hope3(obj.getSbsdyAplyNo());
			} else if (from instanceof FromDlnInfo) { // 직접대출
				FromDlnInfo obj = (FromDlnInfo) from;
				sprtBizSn = sprtBizService.selectSprtBizSn_dln(obj.getPlcyFndCd(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_dln(obj.getLnAplyNo());
			} else if (from instanceof FromPlnInfo) { // 대리대출
				FromPlnInfo obj = (FromPlnInfo) from;
				sprtBizSn = sprtBizService.selectSprtBizSn_pln(obj.getFndMngDivCd(), obj.getSprtBizYr());
				aplySn = sprtBizAplyService.selectAplySn_pln(obj.getBrno(), obj.getCnfrmIssuNo());
			} else {
				throw new BzdException("지원하지 않는 인스턴스 유형입니다. (from: " + from.getClass() + ")");
			}
			
			// 3. 데이터 처리
			SprtBizAplyInfo result = process(from, sprtBizSn, aplySn); // 자동생성된 sprtBizSn, aplySn 값 반환
			
			// 4. 시스템별 연계 키 추가
			if (from instanceof FromEduInfo) { // 교육
				FromEduInfo obj = (FromEduInfo) from;
				if (sprtBizSn == null) sprtBizService.insert_edu(obj.getEduSprtBizno(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_edu(obj.getPrgrmSn(), obj.getMberSn(), result.getAplySn());
			} else if (from instanceof FromCnsltInfo) { // 컨설팅
				FromCnsltInfo obj = (FromCnsltInfo) from;
				if (sprtBizSn == null) sprtBizService.insert_cnslt(obj.getSprtBizNmSn(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_cnslt(obj.getCnsltAplySn(), obj.getActvtYr(), obj.getPlcyCd(), result.getAplySn());
			} else if (from instanceof FromHope1Info) { // 희망리턴 (컨설팅)
				FromHope1Info obj = (FromHope1Info) from;
				if (sprtBizSn == null) sprtBizService.insert_hope1(obj.getHopeSprtBizno(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_hope1(obj.getHopeCnsltAplyNo(), result.getAplySn());
			} else if (from instanceof FromHope2Info) { // 희망리턴 (재기교육)
				FromHope2Info obj = (FromHope2Info) from;
				if (sprtBizSn == null) sprtBizService.insert_hope2(obj.getHopeSprtBizno(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_hope2(obj.getReduCrsAplyNo(), result.getAplySn());
			} else if (from instanceof FromHope3Info) { // 희망리턴 (장려금)
				FromHope3Info obj = (FromHope3Info) from;
				if (sprtBizSn == null) sprtBizService.insert_hope3(obj.getSprtBizNmSn(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_hope3(obj.getSbsdyAplyNo(), result.getAplySn());
			} else if (from instanceof FromDlnInfo) { // 직접대출
				FromDlnInfo obj = (FromDlnInfo) from;
				if (sprtBizSn == null) sprtBizService.insert_dln(obj.getPlcyFndCd(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_dln(obj.getLnAplyNo(), result.getAplySn());
			} else if (from instanceof FromPlnInfo) { // 대리대출
				FromPlnInfo obj = (FromPlnInfo) from;
				if (sprtBizSn == null) sprtBizService.insert_pln(obj.getFndMngDivCd(), result.getSprtBizSn());
				if (aplySn == null) sprtBizAplyService.insert_pln(obj.getBrno(), obj.getCnfrmIssuNo(), result.getAplySn());
			}
		// 5. 통합데이터 취소 
		} else if(from != null && from.getAplyStsCd() != null && from.getAplyStsCd().equals("2")) {
			// 6. 시스템별 연계 키 조회
			if (from instanceof FromCnsltInfo) { // 컨설팅
				FromCnsltInfo obj = (FromCnsltInfo) from;
				aplySn = sprtBizAplyService.selectAplySn_cnslt(obj.getCnsltAplySn(), obj.getActvtYr(), obj.getPlcyCd());
			} else if (from instanceof FromHope1Info) { // 희망리턴 (컨설팅)
				FromHope1Info obj = (FromHope1Info) from;
				aplySn = sprtBizAplyService.selectAplySn_hope1(obj.getHopeCnsltAplyNo());
			} else if (from instanceof FromHope2Info) { // 희망리턴 (재기교육)
				FromHope2Info obj = (FromHope2Info) from;
				aplySn = sprtBizAplyService.selectAplySn_hope2(obj.getReduCrsAplyNo());
			} else if (from instanceof FromHope3Info) { // 희망리턴 (장려금)
				FromHope3Info obj = (FromHope3Info) from;
				aplySn = sprtBizAplyService.selectAplySn_hope3(obj.getSbsdyAplyNo());
			}
			
			// 취소 데이터일 경우에 기존에 지원사업신청내역에 데이터가 없을 경우도 있기 때문에 수정.
			if(aplySn != null) {
				SprtBizAplyInfo old = sprtBizAplyService.select(aplySn);
				SprtBizAplyInfo info = new SprtBizAplyInfo(from, -1, old);
				
				if(old.getAplyStsCd() != null && old.getAplyStsCd().equals("1")) {
					sprtBizAplyService.update_2(info); // 지원사업신청내역
				}
			}
		}
	}
	
	private SprtBizAplyInfo process(FromInfo from, Long sprtBizSn, Long aplySn) {
		if (logger.isDebugEnabled()) {
			logger.debug("process(" + JsonUtil.toString(from) + ", " + sprtBizSn + ", " + aplySn + ")");
		}
		
		SprtBizAplyInfo result = null; // 자동생성된 sprtBizSn, aplySn 값 반환용 객체
		boolean isAplyFlag = false;
		// 표준산업분류 검색 및 설정
		BizclsKsicLinkInfo bizcls = bizclsKsicLinkService.select(from.getKsicSrchCd());
		
		if (bizcls != null) {
			from.setKsicLclsCd(bizcls.getKsicLclsCd()); // 표준산업분류대분류코드
			from.setKsicMclsCd(bizcls.getKsicMclsCd()); // 표준산업분류중분류코드
			from.setKsicSclsCd(bizcls.getKsicSclsCd()); // 표준산업분류소분류코드
			from.setKsicCd(bizcls.getKsicCd());         // 표준산업분류코드
		}
		
		// 1. 지원사업
		if (sprtBizSn == null) {
			SprtBizInfo info = new SprtBizInfo(from);
			sprtBizService.insert(info); // 지원사업기본
			sprtBizSn = info.getSprtBizSn(); // GeneratedKey
		} else {
			SprtBizInfo old = sprtBizService.select(sprtBizSn);
			SprtBizInfo info = new SprtBizInfo(from, old);
			if (!JsonUtil.toString(info).equals(JsonUtil.toString(old))) { // 변경여부 검사
				sprtBizService.update(info); // 지원사업기본
			}
		}
		
		// 2. 신청내역
		if (aplySn == null) {
			SprtBizAplyInfo info = new SprtBizAplyInfo(from, sprtBizSn);
			sprtBizAplyService.insert(info); // 지원사업신청내역
			aplySn = info.getAplySn(); // GeneratedKey
			result = info;
			isAplyFlag = true;
		} else {
			SprtBizAplyInfo old = sprtBizAplyService.select(aplySn);
			SprtBizAplyInfo info = new SprtBizAplyInfo(from, sprtBizSn, old);
			old.setSrcRegDt(info.getSrcRegDt()); // 출처시스템 등록일시 비교제외(동일데이터 설정로직).
			if (!JsonUtil.toString(info).equals(JsonUtil.toString(old))) { // 변경여부 검사
				sprtBizAplyService.update(info); // 지원사업신청내역
			}
			result = info;
		}
		
		// 3. 수혜기업
		if (StringUtils.isNotEmpty(from.getBrno())) {
			if (!bnftBizService.exists(from.getBrno())) {
				BnftBizInfo info = new BnftBizInfo(from);
				bnftBizService.insert(info); // 수혜기업기본
			} else {
				BnftBizInfo old = bnftBizService.select(from.getBrno());
				BnftBizInfo info = new BnftBizInfo(from, old);
				old.setSrcRegDt(info.getSrcRegDt()); // 출처시스템 등록일시 비교제외(동일데이터 설정로직).
				if (isUpdateEnabledBySrcSysCd(info.getSrcSysCd(), old.getSrcSysCd()) // 우선순위 비교
						&& !JsonUtil.toString(info).equals(JsonUtil.toString(old))) { // 변경여부 검사
					bnftBizService.update(info); // 수혜기업기본
					bnftBizHistService.insert(new BnftBizHistInfo(old)); // 수혜기업이력
				}
			}
		}
		
		// 4. 수혜개인
		if (StringUtils.isNotEmpty(from.getDiVal())) {
			if (!bnftPsnlService.exists(from.getDiVal())) {
				BnftPsnlInfo info = new BnftPsnlInfo(from);
				bnftPsnlService.insert(info); // 수혜개인기본
			} else {
				BnftPsnlInfo old = bnftPsnlService.select(from.getDiVal());
				BnftPsnlInfo info = new BnftPsnlInfo(from, old);
				old.setSrcRegDt(info.getSrcRegDt()); // 출처시스템 등록일시 비교제외(동일데이터 설정로직).
				if (isUpdateEnabledBySrcSysCd(info.getSrcSysCd(), old.getSrcSysCd()) // 우선순위 비교
						&& !JsonUtil.toString(info).equals(JsonUtil.toString(old))) { // 변경여부 검사
					bnftPsnlService.update(info); // 수혜개인기본
					bnftPsnlHistService.insert(new BnftPsnlHistInfo(old)); // 수혜개인이력
				}
			}
		} else {
			if(isAplyFlag) {
				BnftPsnlTempInfo info = new BnftPsnlTempInfo(from, aplySn);
				bnftPsnlTempService.insert(info); // 수혜개인임시
			}
		}
		
		// 5. 수혜기업개인관계
		if (StringUtils.isNotEmpty(from.getDiVal()) && StringUtils.isNotEmpty(from.getBrno())) {
			if (!bnftBizPsnlService.exists(from.getDiVal(), from.getBrno())) {
				bnftBizPsnlService.insert(from.getDiVal(), from.getBrno()); // 수혜기업개인관계
			}
		}
		
		return result;
	}
	
	/**
	 * 데이터 처리
	 */
	public void process(FromDeptInfo from) {
		if (StringUtils.isNotEmpty(from.getDeptCd())) {
			if (!bnftDeptService.exists(from.getDeptCd())) {
				BnftDeptInfo info = new BnftDeptInfo(from);
				bnftDeptService.insert(info); // 수혜기업기본
			} else {
				BnftDeptInfo info = new BnftDeptInfo(from);
				bnftDeptService.update(info);
			}
		}
	}
	
	// 출처시스템코드 우선순위 비교
	private boolean isUpdateEnabledBySrcSysCd(String newSrcSysCd, String oldSrcSysCd) {
		
		// 출처시스템코드 (01: 교육, 02: 컨설팅, 03: 희망리턴, 04: 직접대출, 05: 대리대출)
		switch (String.valueOf(newSrcSysCd)) {
			case "01": return ArrayUtils.contains(new String[] { "01" }, oldSrcSysCd);
			case "02": return ArrayUtils.contains(new String[] { "01", "02" }, oldSrcSysCd);
			case "03": return ArrayUtils.contains(new String[] { "01", "02", "03" }, oldSrcSysCd);
			case "04": return ArrayUtils.contains(new String[] { "01", "02", "03", "04", "05" }, oldSrcSysCd);
			case "05": return ArrayUtils.contains(new String[] { "01", "02", "03", "05" }, oldSrcSysCd);
		}
		throw new BzdException("지원하지 않는 출처시스템코드입니다. (newSrcSysCd: " + newSrcSysCd + ")");
	}
	
}
