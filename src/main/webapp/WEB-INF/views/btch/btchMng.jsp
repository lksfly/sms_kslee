<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<article class="content-wrapper">-->
		<section class="container-fluid">
			<h1 class="page-title">Batch 관리</h1>
			<div class="card filterCard">
				<div class="filterCard-body">
					<div class="input-wrap">
						<div class="input-title">스케줄명</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-jobNm" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">클래스명</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-clsNm" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">메소드명</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-mthdNm" placeholder="입력해 주세요.">
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
				<div class="spacer"></div>
				<button type="button" class="btn btn-m btn-reversal-main" onclick="content_runBtch();">실행</button>
				<button type="button" class="btn btn-m btn-reversal-main" onclick="btchMngInfoModal_open();">Batch 등록</button>
			</div>
			<table id="content-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
				<colgroup>
					<col style="width: 60px;" />
				</colgroup>
				<thead>
					<tr role="row">
						<th>
							<div class="clickinput clickinput-S1">
								<input type="checkbox" name="treeCheckbox" id="content-list-checkAll">
								<label for="content-list-checkAll"><span class="clickmarker icon1" style="margin-right: 0px;"></span></label>
							</div>
						</th>
						<th>No</th>
						<th>스케줄명</th>
						<th>패키지명</th>
						<th>클래스명</th>
						<th>메소드명</th>
						<th>크론표현식</th>
						<th>사용여부</th>
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
				
				$('#content-srch-jobNm, #content-srch-clsNm, #content-srch-mthdNm').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						content_list();
					}
				});
				$('#content-list-checkAll').on('click', function() {
					$('input[id^="content-list-check-"]').prop('checked', this.checked);
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
				$('#content-srch-jobNm').val('');
				$('#content-srch-clsNm').val('');
				$('#content-srch-mthdNm').val('');
			}
			
			// 목록 조회
			function content_list() {
				content_srch_param = { // 검색 데이터
					jobNm:   $('#content-srch-jobNm').val(),
					clsNm:   $('#content-srch-clsNm').val(),
					mthdNm:  $('#content-srch-mthdNm').val()
				};
				$.get(CONTEXT_PATH + '/btch/btchMng/api', content_srch_param, function(result) { // Batch 관리 > 목록 API
					$('#content-list-totalCnt').html(formatNumber_addCom(result.data.length));
					content_dataTable(result.data); // table
				}, 'json');
			}
			
			function content_dataTable(dataList) {
				var columns = [
					{ data: 'CHECKBOX',   className: 'dt-body-center', orderable: false, defaultContent: '' }, // [0]
					{ data: 'NO',         className: 'dt-body-center', orderable: false, defaultContent: '' }, // [1]
					{ data: 'jobNm',      className: 'dt-body-center' }, // [2]
					{ data: 'pckgNm',     className: 'dt-body-center' }, // [3]
					{ data: 'clsNm',      className: 'dt-body-center' }, // [4]
					{ data: 'mthdNm',     className: 'dt-body-center' }, // [5]
					{ data: 'cronExp',    className: 'dt-body-center' }, // [6]
					{ data: 'useYn',      className: 'dt-body-center' }, // [7]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([0].indexOf(colIndex) > -1) { // CHECKBOX
							var id = 'content-list-check-' + rowData['jobId'];
							var str = '<div class="clickinput clickinput-S1">'
									+ '    <input type="checkbox" name="treeCheckbox" id="' + id + '">'
									+ '    <label for="' + id + '"><span class="clickmarker icon1" style="margin-right: 0px;"></span></label>'
									+ '</div>';
							cell.innerHTML = str;
						}
						if ([1].indexOf(colIndex) > -1) { // NO
							cell.innerHTML = rowIndex + 1;
						}
						if ([2].indexOf(colIndex) > -1) {
							var onclick = 'onclick="btchMngInfoModal_open(' + rowData['jobId'] + ');"';
							cell.innerHTML = '<a href="#" ' + onclick + '>' + cellData + '</a>';
						}
						if ([7].indexOf(colIndex) > -1) {
							cell.innerHTML = cellData == 'Y' ? '사용' : '미사용';
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				content_$dataTable = $('#content-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: false,
					data: dataList,
					columns: columns,
					columnDefs: columnDefs,
					order: [],
					
					ordering: true,   // 정렬기능 여부
					searching: false, // 검색기능 여부
					info: false,      // 정보표시 여부
					paging: false,    // 페이징 여부
					
					scrollX: true,
					
					dom: '<"caption-top"if> rt p',
				});
			}
			
			function content_runBtch() {
				var jobIds = []; // 선택된 작업
				$('input[id^="content-list-check-"]:checked').each(function() {
					var jobId = $(this).attr('id').substring('content-list-check-'.length);
					jobIds.push(jobId);
				});
				
				if (!jobIds.length) {
					alert('실행할 스케줄을 선택해 주세요.');
					return;
				} else if (!confirm('선택한 ' + jobIds.length + '개의 스케줄을 실행하시겠습니까?')) {
					return;
				}
				
				$.get(CONTEXT_PATH + '/btch/btchMng/run', { // Batch 관리 > 실행 API
					jobIds: jobIds.join(',')
				}, function(result) {
					alert('실행이 완료되었습니다.');
				}, 'json');
			}
			
			// Batch 등록/수정 팝업
			function btchMngInfoModal_open(jobId) {
				ajaxLoad('#modal1', CONTEXT_PATH + '/btch/btchRegInfoModal', { // Batch 관리 > Batch 등록/수정 팝업
					jobId: jobId // 작업ID
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function btchMngInfoModal_close(reload) {
				if (reload) {
					content_list(); // reload
				}
				Modal_h('#modal1'); // hide
			}
		</script>
