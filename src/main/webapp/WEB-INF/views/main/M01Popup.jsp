<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!-- start:: modal-l large모달-->
	<div id="M01Modal-content" class="modal-content modal-l" style="max-height: 76% !important;">
		<div class="card body-scroll" style="height: 100%;">
			<div class="card-header title-row">
				<h1 class="content-title2">월별 지원사업 진행 현황</h1>
				<div class="spacer"></div>
				<button type="button" class="btn btn-close btn-text-main nomargin" onclick="ModalClose()"><i class="fas fa-times"></i></button>
			</div>
			<div class="card-body">
				<div class="title-row">
					<div class="selectinput select-S1">
						<input type="hidden" id="BIZ_YEAR" value="${yearList[0]}">
						<button class="label">${yearList[0]}</button>
						<div class="optionList" style="display: none;">
							<ul style="max-height: 150px;">
							<c:forEach var="item" items="${yearList}">
								<li class="optionItem" data-value="${item}" onclick="searchData('${item}')">${item}</li>
							</c:forEach>
							</ul>
						</div>
					</div>
				</div>

				<table id="content-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
					<thead>
						<tr role="row">
							<th>NO</th>
							<th>연도</th>
							<th>월</th>
							<th>지원건수</th>
							<th>지원금액</th>
							<th>지원센터</th>
							<th>지원대상수</th>
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
				main_setCommonEvent('#M01Modal-content'); // 공통 이벤트 설정
				searchData($("#BIZ_YEAR").val());
			});

			function searchData(biz_year) {
				if( biz_year == undefined ) {
					biz_year = $("#BIZ_YEAR").val();
				}
				$.get(CONTEXT_PATH + '/main/dashboard/popup/api/M01', {
					BIZ_YEAR: biz_year
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
					{ data: 'SPRT_Y',            className: 'dt-body-center', orderable: false }, // [1] 연도
					{ data: 'SPRT_M',            className: 'dt-body-center', orderable: true }, // [2] 월
					{ data: 'SPRT_CNT',          className: 'dt-body-right',  orderable: true }, // [3] 지원사업수
					{ data: 'GIVE_AMT',          className: 'dt-body-right',  orderable: true }, // [4] 지원금액
					{ data: 'SPRT_CNTR_CNT',     className: 'dt-body-right',  orderable: true }, // [5] 지원센터
					{ data: 'SPRT_BIZ_PSNL_CNT', className: 'dt-body-right',  orderable: true }  // [6] 지원업체 수
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {

						if( cellData == null ) {
							cell.innerHTML = 0;
						} else {
							if ( [3].indexOf(colIndex) > -1 ) {
								cell.innerHTML = formatNumber_addCom(cellData) + "건";
							}
							if ( [4].indexOf(colIndex) > -1 ) {
								cell.innerHTML = formatNumber_addCom(cellData) + "천원";
							}
							if ( [5].indexOf(colIndex) > -1 || [6].indexOf(colIndex) > -1 ) {
								cell.innerHTML = formatNumber_addCom(cellData) + "개";
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
