<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!-- start:: modal-l large모달-->
	<div id="M04Modal-content" class="modal-content modal-l" style="max-height: 70% !important;">
		<div class="card body-scroll" style="height: 100%;">
			<div class="card-header title-row">
				<h1 class="content-title2">월별 지원건수 현황</h1>
				<div class="spacer"></div>
				<button type="button" class="btn btn-close btn-text-main nomargin" onclick="ModalClose()"><i class="fas fa-times"></i></button>
			</div>
			<div class="card-body">
				<table id="content-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
					<thead>
						<tr role="row">
							<th>NO</th>
							<th>연도</th>
							<th>월</th>
							<th>전체지원건수</th>
							<th>희망리턴 지원건수</th>
							<th>직접대출 지원건수</th>
							<th>대리대출 지원건수</th>
							<th>교육 지원건수</th>
							<th>컨설팅 지원건수</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="modal-bg"></div>
<!-- end:: modal-l large모달-->

		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			var content_$dataTable;
			$(document).ready(function() {
				main_setCommonEvent('#M04Modal-content'); // 공통 이벤트 설정
				searchData();
			});

			function searchData() {
				$.get(CONTEXT_PATH + '/main/dashboard/api/M04', {
					BIZ_YEAR: $("#SPRT_BIZ_YEAR").val(),
					DETAIL_YN: "Y"
				}, function(result) {
					content_dataTable(result.data);
				}, 'json');
			}


			function content_dataTable(dataList) {

				if( content_$dataTable != null ) {
					content_$dataTable.clear();
				}

				var columns = [
					{ data: 'SORT_ORDER',  className: 'dt-body-center', orderable: false }, // [0] // No
					{ data: 'SPRT_Y',      className: 'dt-body-center', orderable: false }, // [1] // 연도
					{ data: 'SPRT_M',      className: 'dt-body-center', orderable: true }, // [2] // 월
					{ data: 'SPRT_SUM',    className: 'dt-body-right',  orderable: true }, // [3] // 전체지원건수
					{ data: 'HOPE_CNT',    className: 'dt-body-right',  orderable: true }, // [4] // 희망리턴
					{ data: 'DL_CNT',      className: 'dt-body-right',  orderable: true }, // [5] // 직접대출
					{ data: 'PL_CNT',      className: 'dt-body-right',  orderable: true }, // [6] // 대리대출
					{ data: 'EDU_CNT',     className: 'dt-body-right',  orderable: true }, // [7] // 교육
					{ data: 'CNSLT_CNT',   className: 'dt-body-right',  orderable: true }  // [8] // 컨설팅
				];

				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ( [3].indexOf(colIndex) > -1 || [4].indexOf(colIndex) > -1 ||
							 [5].indexOf(colIndex) > -1 || [6].indexOf(colIndex) > -1 ||
							 [7].indexOf(colIndex) > -1 || [8].indexOf(colIndex) > -1
						) {
							if( cellData == null ) {
								cell.innerHTML = 0;
							} else {
								cell.innerHTML = formatNumber_addCom(cellData)+"건";
							}
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];

				content_$dataTable = $('#content-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					data: dataList,
					columns: columns,
					columnDefs: columnDefs,
					order: [],

					ordering: true,   // 정렬기능 여부
					searching: false, // 검색기능 여부
					info: false,      // 정보표시 여부
					paging: false,     // 페이징 여부

					scrollX: true,

					dom: '<"caption-top"if> rt p',
				});
			}
		</script>
