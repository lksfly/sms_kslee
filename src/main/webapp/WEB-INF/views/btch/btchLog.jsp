<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<article class="content-wrapper">-->
		<section class="container-fluid">
			<h1 class="page-title">Batch 로그</h1>
			<div class="card filterCard">
				<div class="filterCard-body">
					<div class="input-wrap">
						<div class="input-title">실행일자</div>
						<div class="textinput textinput-S1">
							<div class="textinput-datepicker">
								<input type="text" id="content-srch-bgngYmd" class="datepicker" readonly>
							</div>
							<button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button>
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">스케줄명</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-jobNm" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">실행결과</div>
						<div class="selectinput w-100 select-S1">
							<input type="hidden" id="content-srch-rsltCd">
							<button class="label" id="content-srch-rsltNm">전체</button>
							<div class="optionList" style="display: none;">
								<ul style="max-height: 300px;">
									<li class="optionItem" data-value="">전체</li>
								<c:forEach var="item" items="${rsltList}">
									<li class="optionItem" data-value="${item.cmnCd}">${item.cmnCdNm}</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="filterCard-footer">
					<button type="button" class="btn btn-m btn-reset" onclick="content_srch_init();">초기화</button>
					<div class="spacer"></div>
					<button type="button" class="btn btn-m btn-step nomargin" onclick="content_list();">조회</button>
				</div>
			</div>
			<div class="title-row tableCaption">
				<p class="rightCaption-total">검색결과 총 <span id="content-list-totalCnt">0</span>건</p>
			</div>
			<table id="content-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
				<thead>
					<tr role="row">
						<th>No</th>
						<th>스케줄명</th>
						<th>실행일시</th>
						<th>종료일시</th>
						<th>실행결과</th>
						<th>처리내용</th>
					<!--<th>실행 서버 IP 주소</th>-->
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</section>
<!--</article>-->
		
		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			$(document).ready(function() {
				main_setCommonEvent('.container-fluid'); // 공통 이벤트 설정
				
				$('#content-srch-jobNm').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						content_list();
					}
				});
				
				content_onload();
			});
			
			var content_srch_param = null; // 검색 데이터
			var content_$dataTable = null; // 테이블 객체
			
			function content_onload() {
				content_srch_init();
				content_list();
			}
			
			// 검색 데이터 초기화
			function content_srch_init() {
				$('#content-srch-bgngYmd').val('${bgngYmd}');
				$('#content-srch-jobNm').val('');
				$('#content-srch-rsltCd').val(''); $('#content-srch-rsltNm').html('전체');
			}
			
			// 목록 조회
			function content_list() {
				content_srch_param = { // 검색 데이터
					bgngYmd: $('#content-srch-bgngYmd').val().replace(/\D/g, ''), // 실행일자 (yyyyMMdd)
					jobNm:   $('#content-srch-jobNm').val(),
					rsltCd:  $('#content-srch-rsltCd').val()
				};
				content_dataTable(); // table
			}
			
			function content_dataTable() {
				var columns = [
					{ data: 'no',         className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'jobNm',      className: 'dt-body-center' }, // [1]
					{ data: 'bgngDt',     className: 'dt-body-center' }, // [2]
					{ data: 'endDt',      className: 'dt-body-center' }, // [3]
					{ data: 'rsltNm',     className: 'dt-body-center' }, // [4]
					{ data: 'LOG_INFO',   className: 'dt-body-center dt-button', orderable: false, defaultContent: '' }, // [5]
				//	{ data: 'srvrIpAddr', className: 'dt-body-center', visible: false } // [6]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([2, 3].indexOf(colIndex) > -1) {
							cell.innerHTML = DateUtil.format(cellData, 'yyyy-MM-dd HH:mm:ss');
						}
						if ([4].indexOf(colIndex) > -1) {
							cell.className += ' ' + (rowData['rsltCd'] == 'S' ? 'text-success' : 'text-warning');
						}
						if ([5].indexOf(colIndex) > -1) {
							var onclick = 'onclick="btchLogInfoModal_open(' + rowData['jobSn'] + ');"';
							cell.innerHTML = '<button type="button" ' + onclick + ' class="btn btn-xs btn-warning btn-round">상세 보기</button>';
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				content_$dataTable = $('#content-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: true, // processing: true,
					ajax: {
						url: CONTEXT_PATH + '/btch/btchLog/api', // Batch 로그 > 목록 API
						type: 'GET',
						data: content_srch_param,
						dataSrc: function(result) {
							$('#content-list-totalCnt').html(formatNumber_addCom(result.recordsTotal));
							return result.data;
						}
					},
					columns: columns,
					columnDefs: columnDefs,
					order: [2, 'desc'],
					
					ordering: true,   // 정렬기능 여부
					searching: false, // 검색기능 여부
					info: false,      // 정보표시 여부
					paging: true,     // 페이징 여부
					
					pageLength: 10,      // 한 페이지에 표시할 개수
					lengthChange: false, // 한 페이지에 표시할 개수 설정 (10/25/50/100)
					
					scrollX: true,
					
					dom: '<"caption-top"if> rt p',
				});
			}
			
			// 처리내용 상세 팝업
			function btchLogInfoModal_open(jobSn) {
				ajaxLoad('#modal1', CONTEXT_PATH + '/btch/btchLogInfoModal', { // Batch 로그 > 처리내용 상세 팝업
					jobSn: jobSn // 작업순번
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function btchLogInfoModal_close() {
				Modal_h('#modal1'); // hide
			}
		</script>
