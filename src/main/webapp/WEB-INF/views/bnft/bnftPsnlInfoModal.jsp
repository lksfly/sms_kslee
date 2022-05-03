<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<div id="" class="modal">-->
		<div id="bnftPsnlInfoModal-content" class="modal-content modal-l">
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">개인 상세정보</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="bnftPsnlInfoModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div class="card-body">
					<div class="card infoList-card">
						<div class="sub-title2">기본정보</div>
						<div class="infoList">
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">이름</div>
									<div class="list-item_content"><c:out value="${info.PSNL_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">핸드폰번호</div>
									<div class="list-item_content"><c:out value="${info.MBNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">우편번호</div>
									<div class="list-item_content"><c:out value="${info.ZIP}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">상세주소</div>
									<div class="list-item_content"><c:out value="${info.DADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">성별</div>
									<div class="list-item_content"><c:out value="${info.GNDR_NM}" /></div>
								</div>
							</div>
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">이메일</div>
									<div class="list-item_content"><c:out value="${info.EML}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">전화번호</div>
									<div class="list-item_content"><c:out value="${info.TELNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">기본주소</div>
									<div class="list-item_content"><c:out value="${info.ADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">생년월일</div>
									<div class="list-item_content"><c:out value="${info.BRDT}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">DI 보유여부</div>
									<div class="list-item_content"><c:out value="${info.DI_YN_NM}" /></div>
								</div>
							</div>
						</div>
					</div>
					<div class="title-row tableCaption">
						<div class="sub-title2">지원사업 이력</div>
						<p class="rightCaption-total">총 <span id="bnftPsnlInfoModal-list-totalCnt">0</span>건</p>
					</div>
					<table id="bnftPsnlInfoModal-list-table" class=" table-S1 cell-border" style="width:100%">
						<thead>
							<tr role="row">
								<th>No</th>
								<th>사업명</th>
								<th>세부사업명</th>
								<th>사업연도</th>
								<th>검색일자</th>
								<th>신청자명</th>
								<th>수혜금액(천원)</th>
								<th>담당센터</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
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
				main_setCommonEvent('#bnftPsnlInfoModal-content'); // 공통 이벤트 설정
				
				bnftPsnlInfoModal_onload();
			});
			
			var bnftPsnlInfoModal_srch_param = null; // 검색 데이터
			var bnftPsnlInfoModal_$dataTable = null; // 테이블 객체
			
			function bnftPsnlInfoModal_onload() {
				bnftPsnlInfoModal_list();
			}
			
			// 목록 조회
			function bnftPsnlInfoModal_list() {
				bnftPsnlInfoModal_srch_param = { // 검색 데이터
					DI_VAL:  '${info.DI_VAL}', // DI값 (수혜개인기본)
					PSNL_SN: '${info.PSNL_SN}' // 개인일련번호 (수혜개인임시)
				};
				bnftPsnlInfoModal_dataTable(); // table
			}
			
			function bnftPsnlInfoModal_dataTable() {
				var columns = [
					{ data: 'NO',           className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'SRC_SYS_NM',   className: 'dt-body-left'   }, // [1]
					{ data: 'SPRT_BIZ_NM',  className: 'dt-body-left'   }, // [2]
					{ data: 'SPRT_BIZ_YR',  className: 'dt-body-center' }, // [3]
					{ data: 'SLCTN_YMD',    className: 'dt-body-center' }, // [4]
					{ data: 'APLCNT_NM',    className: 'dt-body-left'   }, // [5]
					{ data: 'GIVE_AMT',     className: 'dt-body-right'  }, // [6]
					{ data: 'SPRT_CNTR_NM', className: 'dt-body-left'   }, // [7]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([6].indexOf(colIndex) > -1) {
							cell.innerHTML = formatNumber_addCom(cellData);
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				bnftPsnlInfoModal_$dataTable = $('#bnftPsnlInfoModal-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: true, // processing: true,
					ajax: {
						url: CONTEXT_PATH + '/bnft/bnftPsnlInfoModal/api', // 개인마스터 > 개인 상세정보 팝업 > 지원사업 이력 목록 API
						type: 'GET',
						data: bnftPsnlInfoModal_srch_param,
						dataSrc: function(result) {
							$('#bnftPsnlInfoModal-list-totalCnt').html(formatNumber_addCom(result.recordsTotal));
							return result.data;
						}
					},
					columns: columns,
					columnDefs: columnDefs,
					order: [],
					
					ordering: true,   // 정렬기능 여부
					searching: false, // 검색기능 여부
					info: false,      // 정보표시 여부
					paging: true,     // 페이징 여부
					
					pageLength:  5,      // 한 페이지에 표시할 개수
					lengthChange: false, // 한 페이지에 표시할 개수 설정 (10/25/50/100)
					
					scrollX: true,
					
					dom: '<"caption-top"if> rt p',
				});
			}
		</script>
