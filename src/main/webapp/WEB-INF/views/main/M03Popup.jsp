<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!-- start:: modal-s small모달-->
	<div id="M03Modal-content" class="modal-content modal-s" style="max-height: 67% !important;">
		<div class="card body-scroll" style="height: 100%;">
			<div class="card-header title-row">
				<h1 class="content-title2">지급일자 기준 지원건수 현황</h1>
				<div class="spacer"></div>
				<button type="button" class="btn btn-close btn-text-main nomargin" onclick="ModalClose()"><i class="fas fa-times"></i></button>
			</div>
			<div class="card-body">
				<table id="content-list-table" class="table-S1 cell-border" style="width:100%">
					<thead>
						<tr role="row">
							<th class="">NO</th>
							<th class="">지급일자</th>
							<th class="">지원건수</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="modal-bg" onclick="Modal_h('#modal-s')"></div>
<!-- end:: modal-s small모달-->

		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			var content_$dataTable;
			$(document).ready(function() {
				main_setCommonEvent('#M03Modal-content'); // 공통 이벤트 설정
				searchData()
			});

			function searchData() {
				$.get(CONTEXT_PATH + '/main/dashboard/popup/api/M03', {
				}, function(result) {
					content_dataTable(result.data);
				}, 'json');
			}

			function content_dataTable(dataList) {

				if( content_$dataTable != null ) {
					content_$dataTable.clear();
				}

				var columns = [
					{ data: 'NO',                className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'SLCTN_YMD',         className: 'dt-body-center', orderable: true }, // [1]
					{ data: 'SPRT_CNT',          className: 'dt-body-right',  orderable: true }, // [2]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([2].indexOf(colIndex) > -1) {
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
					data: dataList, // 센터별 지원건수 목록
					columns: columns,
					columnDefs: columnDefs,
					order: [],

					ordering: true,   // 정렬기능 여부
					searching: false, // 검색기능 여부
					info: false,      // 정보표시 여부
					paging: true,     // 페이징 여부
					pageLength: 10,      // 한 페이지에 표시할 개수
					lengthChange: 10, // 한 페이지에 표시할 개수 설정 (10/25/50/100)

					scrollX: true,

					dom: '<"caption-top"if> rt p',
				});
			}
		</script>
