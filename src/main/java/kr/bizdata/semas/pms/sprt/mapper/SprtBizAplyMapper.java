package kr.bizdata.semas.pms.sprt.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.sprt.info.SprtBizAplyInfo;

/**
 * 지원사업신청내역
 */
@Repository
public interface SprtBizAplyMapper {
	
	void insert(SprtBizAplyInfo info); // GeneratedKey: aplySn
	
	void update(SprtBizAplyInfo info);
	
	void update_2(SprtBizAplyInfo info);
	
	SprtBizAplyInfo select(@Param("aplySn") long aplySn);
	
	void insert_edu(@Param("prgrmSn") Long prgrmSn, @Param("mberSn") String mberSn, @Param("aplySn") long aplySn); // 교육
	void insert_cnslt(@Param("cnsltAplySn") Long cnsltAplySn, @Param("actvtYr") String actvtYr, @Param("plcyCd") String plcyCd, @Param("aplySn") long aplySn); // 컨설팅
	void insert_hope1(@Param("hopeCnsltAplyNo") String hopeCnsltAplyNo, @Param("aplySn") long aplySn); // 희망리턴 (컨설팅)
	void insert_hope2(@Param("reduCrsAplyNo") String reduCrsAplyNo, @Param("aplySn") long aplySn); // 희망리턴 (재기교육)
	void insert_hope3(@Param("sbsdyAplyNo") String sbsdyAplyNo, @Param("aplySn") long aplySn); // 희망리턴 (장려금)
	void insert_dln(@Param("lnAplyNo") String lnAplyNo, @Param("aplySn") long aplySn); // 직접대출
	void insert_pln(@Param("brno") String brno, @Param("cnfrmIssuNo") String cnfrmIssuNo, @Param("aplySn") long aplySn); // 대리대출
	
	Long selectAplySn_edu(@Param("prgrmSn") Long prgrmSn, @Param("mberSn") String mberSn); // 교육
	Long selectAplySn_cnslt(@Param("cnsltAplySn") Long cnsltAplySn, @Param("actvtYr") String actvtYr, @Param("plcyCd") String plcyCd); // 컨설팅
	Long selectAplySn_hope1(@Param("hopeCnsltAplyNo") String hopeCnsltAplyNo); // 희망리턴 (컨설팅)
	Long selectAplySn_hope2(@Param("reduCrsAplyNo") String reduCrsAplyNo); // 희망리턴 (재기교육)
	Long selectAplySn_hope3(@Param("sbsdyAplyNo") String sbsdyAplyNo); // 희망리턴 (장려금)
	Long selectAplySn_dln(@Param("lnAplyNo") String lnAplyNo); // 직접대출
	Long selectAplySn_pln(@Param("brno") String brno, @Param("cnfrmIssuNo") String cnfrmIssuNo); // 대리대출
	
}
