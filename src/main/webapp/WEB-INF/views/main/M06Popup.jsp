<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!-- start:: modal-l large모달-->
	<div id="M06Modal-content" class="modal-content modal-l">
		<div class="card body-scroll" style="height: 100%;">
			<div class="card-header title-row">
				<h1 class="content-title2">업종별 지원건수 현황(${empty(clsCd) ? "대분류" : "중분류"})</h1>
				<div class="spacer"></div>
				<button type="button" class="btn btn-close btn-text-main nomargin" onclick="ModalClose()"><i class="fas fa-times"></i></button>
			</div>
			<div class="card-body">
				<table id="content-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
					<thead>
					<tr role="row">
						<th class="">NO</th>
						<th class="">업종코드</th>
						<th class="">업종명</th>
						<th class="">지원건수</th>
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
				main_setCommonEvent('#M06Modal-content'); // 공통 이벤트 설정
				searchData()
			});

			function searchData() {
				$.get(CONTEXT_PATH + '/main/dashboard/popup/api/M06', {
					clsCd: '${clsCd}'
				}, function(result) {
					content_dataTable(result.data);
				}, 'json');
			}

			function content_dataTable(dataList) {

				if( content_$dataTable != null ) {
					content_$dataTable.clear();
				}

				var columns = [
					{ data: 'NO',        className: 'dt-body-center', orderable: false }, // [0] No
					{ data: 'CLS_CD',    className: 'dt-body-center', orderable: true }, // [1] 업종코드
					{ data: 'CLS_NM',    className: 'dt-body-left',   orderable: true }, // [2] 업종명
					{ data: 'SPRT_CNT',  className: 'dt-body-right',  orderable: true }, // [3] 지원건수
				];

				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						// console.log([3].indexOf(colIndex) + ", cellData === " + cellData);
						if ( [3].indexOf(colIndex) > -1 ) {
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
					paging: true,     // 페이징 여부
					pageLength: 15,      // 한 페이지에 표시할 개수
					lengthChange: 15, // 한 페이지에 표시할 개수 설정 (10/25/50/100)

					scrollX: true,

					dom: '<"caption-top"if> rt p',
				});
			}
		</script>
