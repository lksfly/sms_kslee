<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<div id="" class="modal">-->
		<div id="sprtBizAplyInfoModal-content" class="modal-content modal-l">
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">신청내역 상세정보</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="sprtBizAplyInfoModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div class="card-body">
					<div class="card infoList-card">
						<div class="sub-title2">사업정보</div>
						<div class="infoList">
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">신청일련번호</div>
									<div class="list-item_content"><c:out value="${info.APLY_SN}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">사업명</div>
									<div class="list-item_content"><c:out value="${info.SRC_SYS_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">수혜금액(천원)</div>
									<div class="list-item_content"><fmt:formatNumber value="${info.GIVE_AMT}" pattern="#,###" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">담당센터코드</div>
									<div class="list-item_content"><c:out value="${info.SPRT_CNTR_CD}" /></div>
								</div>
							</div>
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">세부사업일련번호</div>
									<div class="list-item_content"><c:out value="${info.SPRT_BIZ_SN}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">세부사업명</div>
									<div class="list-item_content"><c:out value="${info.SPRT_BIZ_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">검색일자</div>
									<div class="list-item_content"><c:out value="${info.SLCTN_YMD}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">담당센터</div>
									<div class="list-item_content"><c:out value="${info.SPRT_CNTR_NM}" /></div>
								</div>
							</div>
						</div>
					</div>
					<div class="card infoList-card">
						<div class="sub-title2">신청자정보</div>
						<div class="infoList">
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">성명</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">핸드폰번호</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_MBNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">우편번호</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_ZIP}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">상세주소</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_DADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">성별</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_GNDR_NM}" /></div>
								</div>
							</div>
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">이메일</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_EML}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">전화번호</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_TELNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">기본주소</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_ADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">생년월일</div>
									<div class="list-item_content"><c:out value="${info.APLCNT_BRDT}" /></div>
								</div>
							</div>
						</div>
					</div>
					<div class="card infoList-card">
						<div class="sub-title2">기업정보</div>
						<div class="infoList">
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">사업자등록번호</div>
									<div class="list-item_content"><c:out value="${info.BRNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">법인등록번호</div>
									<div class="list-item_content"><c:out value="${info.CRNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">우편번호</div>
									<div class="list-item_content"><c:out value="${info.ENT_ZIP}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">기본주소</div>
									<div class="list-item_content"><c:out value="${info.ENT_ADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">상세주소</div>
									<div class="list-item_content"><c:out value="${info.ENT_DADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">전화번호</div>
									<div class="list-item_content"><c:out value="${info.ENT_TELNO}" /></div>
								</div>
							</div>
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">업체명</div>
									<div class="list-item_content"><c:out value="${info.ENT_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">사업자유형</div>
									<div class="list-item_content"><c:out value="${info.BZMN_TYPE_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">대표자</div>
									<div class="list-item_content"><c:out value="${info.CEO_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">표준산업분류코드</div>
									<div class="list-item_content"><c:out value="${info.KSIC_CD}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">창업일</div>
									<div class="list-item_content"><c:out value="${info.FNDN_YMD}" /></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-bg"></div>
<!--</div>-->
		
		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			$(document).ready(function() {
				main_setCommonEvent('#sprtBizAplyInfoModal-content'); // 공통 이벤트 설정
			});
		</script>
