<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<c:set var="actionName" value="${info == null ? '저장' : '수정'}" />

<!--<div id="" class="modal">-->
		<div id="btchMngInfoModal-content" class="modal-content modal-s" style="height: 690px;">
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">${info == null ? 'Batch 등록' : 'Batch 수정'}</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="btchMngInfoModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div class="card-body">
					<div class="m-list2 list-center" style="width: 340px;">
						<div class="list-item">
							<div class="list-item_title required">&nbsp;스케줄명</div>
							<div class="list-item_content">
								<div class="textinput textinput-S1 nomargin">
									<input type="text" id="btchMngInfoModal-jobNm" value="${info.jobNm}" placeholder="입력해 주세요.">
								</div>
							</div>
						</div>
						<div class="list-item">
							<div class="list-item_title required">&nbsp;패키지명</div>
							<div class="list-item_content">
								<div class="textinput textinput-S1 nomargin">
									<input type="text" id="btchMngInfoModal-pckgNm" value="${info.pckgNm}" placeholder="입력해 주세요.">
								</div>
							</div>
						</div>
						<div class="list-item">
							<div class="list-item_title required">&nbsp;클래스명</div>
							<div class="list-item_content">
								<div class="textinput textinput-S1 nomargin">
									<input type="text" id="btchMngInfoModal-clsNm" value="${info.clsNm}" placeholder="입력해 주세요.">
								</div>
							</div>
						</div>
						<div class="list-item">
							<div class="list-item_title required">&nbsp;메소드명</div>
							<div class="list-item_content">
								<div class="textinput textinput-S1 nomargin">
									<input type="text" id="btchMngInfoModal-mthdNm" value="${info.mthdNm}" placeholder="입력해 주세요.">
								</div>
							</div>
						</div>
						<div class="list-item">
							<div class="list-item_title required">&nbsp;크론표현식</div>
							<div class="list-item_content">
								<div class="textinput textinput-S1 nomargin">
									<input type="text" id="btchMngInfoModal-cronExp" value="${info.cronExp}" placeholder="입력해 주세요.">
								</div>
							</div>
						</div>
						<div class="list-item">
							<div class="list-item_title required">&nbsp;사용여부</div>
							<div class="list-item_content">
								<div class="selectinput w-100 select-S1">
									<input type="hidden" id="btchMngInfoModal-useYn" value="${info == null || info.useYn == 'Y' ? 'Y' : 'N'}">
									<button class="label">${info == null || info.useYn == 'Y' ? '사용' : '미사용'}</button>
									<div class="optionList" style="display: none;">
										<ul style="max-height: 300px;">
											<li class="optionItem" data-value="Y">사용</li>
											<li class="optionItem" data-value="N">미사용</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="card-footer center">
					<button type="button" class="btn btn-ml btn-reversal-off" onclick="btchMngInfoModal_close();">닫기</button>
					<button type="button" class="btn btn-ml btn-main" onclick="btchMngInfoModal_save();">${actionName}</button>
					<button type="button" class="btn btn-ml btn-warning" onclick="btchMngInfoModal_delete();">삭제</button>
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
				main_setCommonEvent('#btchMngInfoModal-content'); // 공통 이벤트 설정
				
			});
			
			function btchMngInfoModal_validate() {
				var $jobNm   = $('#btchMngInfoModal-jobNm');
				var $pckgNm  = $('#btchMngInfoModal-pckgNm');
				var $clsNm   = $('#btchMngInfoModal-clsNm');
				var $mthdNm  = $('#btchMngInfoModal-mthdNm');
				var $cronExp = $('#btchMngInfoModal-cronExp');
				
				if (!$jobNm.val().trim()) {
					alert('스케줄명을 입력해 주세요.');
					$jobNm.focus();
					return false;
				}
				if (!$pckgNm.val().trim()) {
					alert('패키지명을 입력해 주세요.');
					$pckgNm.focus();
					return false;
				}
				if (!$clsNm.val().trim()) {
					alert('클래스명을 입력해 주세요.');
					$clsNm.focus();
					return false;
				}
				if (!$mthdNm.val().trim()) {
					alert('메소드명을 입력해 주세요.');
					$mthdNm.focus();
					return false;
				}
				if (!$cronExp.val().trim()) {
					alert('크론표현식을 입력해 주세요.');
					$cronExp.focus();
					return false;
				}
				
				return true;
			}
			
			function btchMngInfoModal_save() {
				if (!btchMngInfoModal_validate() || !confirm('${actionName}하시겠습니까?')) {
					return;
				}
				
				$.post(CONTEXT_PATH + '/btch/btchRegInfoModal/save', { // Batch 관리 > Batch 등록/수정 팝업 > 저장 API
					jobId: '${info.jobId}',
					jobNm:   $('#btchMngInfoModal-jobNm').val().trim(),
					pckgNm:  $('#btchMngInfoModal-pckgNm').val().trim(),
					clsNm:   $('#btchMngInfoModal-clsNm').val().trim(),
					mthdNm:  $('#btchMngInfoModal-mthdNm').val().trim(),
					cronExp: $('#btchMngInfoModal-cronExp').val().trim(),
					useYn:   $('#btchMngInfoModal-useYn').val()
				}, function(result) {
					alert('${actionName}되었습니다.');
					btchMngInfoModal_close(true); // list reload
				}, 'json');
			}
			
			function btchMngInfoModal_delete() {
				if (!confirm('삭제하시겠습니까?')) {
					return;
				}
				
				$.post(CONTEXT_PATH + '/btch/btchRegInfoModal/delete', { // Batch 관리 > Batch 등록/수정 팝업 > 삭제 API
					jobId: '${info.jobId}'
				}, function(result) {
					alert('삭제되었습니다.');
					btchMngInfoModal_close(true); // list reload
				}, 'json');
			}
		</script>
