package kr.bizdata.semas.pms.sprt.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.bizdata.semas.pms.sprt.info.SprtBizInfo;

/**
 * 지원사업기본
 */
@Repository
public interface SprtBizMapper {
	
	void insert(SprtBizInfo info); // GeneratedKey: sprtBizSn
	
	void update(SprtBizInfo info);
	
	SprtBizInfo select(@Param("sprtBizSn") long sprtBizSn);
	
	void insert_edu(@Param("eduSprtBizno") Long eduSprtBizno, @Param("sprtBizSn") long sprtBizSn); // 교육
	void insert_cnslt(@Param("sprtBizNmSn") Long sprtBizNmSn, @Param("sprtBizSn") long sprtBizSn); // 컨설팅
	void insert_hope1(@Param("hopeSprtBizno") String hopeSprtBizno, @Param("sprtBizSn") long sprtBizSn); // 희망리턴 (컨설팅)
	void insert_hope2(@Param("hopeSprtBizno") String hopeSprtBizno, @Param("sprtBizSn") long sprtBizSn); // 희망리턴 (재기교육)
	void insert_hope3(@Param("sprtBizNmSn") Long sprtBizNmSn, @Param("sprtBizSn") long sprtBizSn); // 희망리턴 (장려금)
	void insert_dln(@Param("plcyFndCd") String plcyFndCd, @Param("sprtBizSn") long sprtBizSn); // 직접대출
	void insert_pln(@Param("fndMngDivCd") String fndMngDivCd, @Param("sprtBizSn") long sprtBizSn); // 대리대출
	
	Long selectSprtBizSn_edu(@Param("eduSprtBizno") Long eduSprtBizno, @Param("sprtBizYr") String sprtBizYr); // 교육
	Long selectSprtBizSn_cnslt(@Param("sprtBizDivCd") String sprtBizDivCd, @Param("sprtBizYr") String sprtBizYr); // 컨설팅
	Long selectSprtBizSn_hope1(@Param("hopeSprtBizno") String hopeSprtBizno, @Param("sprtBizYr") String sprtBizYr); // 희망리턴 (컨설팅)
	Long selectSprtBizSn_hope2(@Param("hopeSprtBizno") String hopeSprtBizno, @Param("sprtBizYr") String sprtBizYr); // 희망리턴 (재기교육)
	Long selectSprtBizSn_hope3(@Param("sprtBizDivCd") String sprtBizDivCd, @Param("sprtBizYr") String sprtBizYr); // 희망리턴 (장려금)
	Long selectSprtBizSn_dln(@Param("plcyFndCd") String plcyFndCd, @Param("sprtBizYr") String sprtBizYr); // 직접대출
	Long selectSprtBizSn_pln(@Param("fndMngDivCd") String fndMngDivCd, @Param("sprtBizYr") String sprtBizYr); // 대리대출
	
}
