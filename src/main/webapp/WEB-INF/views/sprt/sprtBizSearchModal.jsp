<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<div id="" class="modal">-->
		<div id="sprtBizSearchModal-content" class="modal-content modal-s" style="height: 840px;">
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">세부사업 검색</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="sprtBizSearchModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div class="card-body">
					<div class="card filterCard">
						<div class="filterCard-body">
							<div class="input-wrap">
								<div class="input-title">세부사업명</div>
								<div class="textinput textinput-S1">
									<input type="text" id="sprtBizSearchModal-srch-SPRT_BIZ_NM" placeholder="입력해 주세요.">
								</div>
							</div>
							<div class="input-wrap">
								<div class="input-title">사업연도</div>
								<div class="selectinput select-S1">
									<input type="hidden" id="sprtBizSearchModal-srch-SPRT_BIZ_YR">
									<button class="label" id="sprtBizSearchModal-srch-SPRT_BIZ_YR_NM">전체</button>
									<div class="optionList" style="display: none;">
										<ul style="max-height: 300px;">
											<li class="optionItem" data-value="" style="width: 84px;">전체</li>
										<c:forEach var="item" items="${yearList}">
											<li class="optionItem" data-value="${item}">${item}</li>
										</c:forEach>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="filterCard-footer">
							<button type="button" class="btn btn-m btn-reset" onclick="sprtBizSearchModal_srch_init();">초기화</button>
							<div class="spacer"></div>
							<button type="button" class="btn btn-m btn-step nomargin" onclick="sprtBizSearchModal_list();">조회</button>
						</div>
					</div>
					<div class="title-row tableCaption">
						<p class="rightCaption-total">검색결과 총 <span id="sprtBizSearchModal-list-totalCnt">0</span>건</p>
					</div>
					<table id="sprtBizSearchModal-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
						<thead>
							<tr role="row">
								<th class="">No</th>
								<th class="">사업명</th>
								<th class="">세부사업명</th>
								<th class="">사업연도</th>
							</tr>
						</thead>
						<tbody>
						<!--
							<tr>
								<td class="">1</td>
								<td class="">1</td>
								<td class="">1</td>
								<td class="">1</td>
							</tr>
						-->
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
				main_setCommonEvent('#sprtBizSearchModal-content'); // 공통 이벤트 설정
				
				$('#sprtBizSearchModal-srch-SPRT_BIZ_NM').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						sprtBizSearchModal_list();
					}
				}).focus();
				
				sprtBizSearchModal_onload();
			});
			
			var sprtBizSearchModal_srch_param = null; // 검색 데이터
			var sprtBizSearchModal_$dataTable = null; // 테이블 객체
			
			function sprtBizSearchModal_onload() {
				sprtBizSearchModal_srch_init();
				sprtBizSearchModal_list();
			}
			
			// 검색 데이터 초기화
			function sprtBizSearchModal_srch_init() {
				$('#sprtBizSearchModal-srch-SPRT_BIZ_NM').val('');
				$('#sprtBizSearchModal-srch-SPRT_BIZ_YR').val(''); $('#sprtBizSearchModal-srch-SPRT_BIZ_YR_NM').html('전체');
			}
			
			// 목록 조회
			function sprtBizSearchModal_list() {
				sprtBizSearchModal_srch_param = { // 검색 데이터
					SPRT_BIZ_NM: $('#sprtBizSearchModal-srch-SPRT_BIZ_NM').val(),
					SPRT_BIZ_YR: $('#sprtBizSearchModal-srch-SPRT_BIZ_YR').val(),
					SRC_SYS_CD:  '${param.SRC_SYS_CD}' // 출처시스템코드
				};
				sprtBizSearchModal_dataTable(); // table
			}
			
			function sprtBizSearchModal_dataTable() {
				var columns = [
					{ data: 'NO',           className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'SRC_SYS_NM',   className: 'dt-body-left'   }, // [1]
					{ data: 'SPRT_BIZ_NM',  className: 'dt-body-left'   }, // [2]
					{ data: 'SPRT_BIZ_YR',  className: 'dt-body-center' }, // [3]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([9].indexOf(colIndex) > -1) {
							cell.innerHTML = formatNumber_addCom(cellData);
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				sprtBizSearchModal_$dataTable = $('#sprtBizSearchModal-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: true, // processing: true,
					ajax: {
						url: CONTEXT_PATH + '/sprt/sprtBizSearchModal/api', // 지원사업현황 > 세부사업 검색 팝업 > 목록 API
						type: 'GET',
						data: sprtBizSearchModal_srch_param,
						dataSrc: function(result) {
							$('#sprtBizSearchModal-list-totalCnt').html(formatNumber_addCom(result.recordsTotal));
							return result.data;
						}
					},
					columns: columns,
					columnDefs: columnDefs,
					order: [2, 'asc'],
					
					ordering: true,   // 정렬기능 여부
					searching: false, // 검색기능 여부
					info: false,      // 정보표시 여부
					paging: true,     // 페이징 여부
					
					pageLength: 10,      // 한 페이지에 표시할 개수
					lengthChange: false, // 한 페이지에 표시할 개수 설정 (10/25/50/100)
					
					scrollX: true,
					
					dom: '<"caption-top"if> rt p',
				});
				
				sprtBizSearchModal_$dataTable.off('click').on('click', 'tbody tr', function() { // 행 클릭
					var $tr = $(this).closest('tr');
					var row = sprtBizSearchModal_$dataTable.row($tr);
					
					var p1 = row.data()['SPRT_BIZ_SN']; // 지원사업일련번호
					var p2 = row.data()['SPRT_BIZ_NM']; // 지원사업명 (세부사업명)
					sprtBizSearchModal_close(p1, p2);
				});
			}
		</script>
