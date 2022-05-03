<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<article class="content-wrapper">-->
		<section class="container-fluid">
			<h1 class="page-title">신청자 정보</h1>
			<div class="card filterCard">
				<div class="filterCard-body">
					<div class="input-wrap">
						<div class="input-title">이름</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-PSNL_NM" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">이메일</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-EML" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">생년월일</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-BRDT" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">성별</div>
						<div class="selectinput w-100 select-S1">
							<input type="hidden" id="content-srch-GNDR_CD">
							<button class="label" id="content-srch-GNDR_NM">전체</button>
							<div class="optionList" style="display: none;">
								<ul style="max-height: 300px;">
									<li class="optionItem" data-value="">전체</li>
								<c:forEach var="item" items="${gndrList}">
									<li class="optionItem" data-value="${item.cmnCd}">${item.cmnCdNm}</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">출처시스템</div>
						<div class="selectinput w-100 select-S1">
							<input type="hidden" id="content-srch-SRC_SYS_CD">
							<button class="label" id="content-srch-SRC_SYS_NM">전체</button>
							<div class="optionList" style="display: none;">
								<ul style="max-height: 300px;">
									<li class="optionItem" data-value="">전체</li>
								<c:forEach var="item" items="${srcSysList}">
									<li class="optionItem" data-value="${item.cmnCd}">${item.cmnCdNm}</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">DI 보유여부</div>
						<div class="selectinput w-100 select-S1">
							<input type="hidden" id="content-srch-DI_YN">
							<button class="label" id="content-srch-DI_YN_NM">전체</button>
							<div class="optionList" style="display: none;">
								<ul style="max-height: 300px;">
									<li class="optionItem" data-value="">전체</li>
									<li class="optionItem" data-value="Y">보유</li>
									<li class="optionItem" data-value="N">미보유</li>
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
				<div class="spacer"></div>
				<button type="button" class="btn btn-s btn-round btn-bright-main" onclick="content_excelDown();"><i class="fas fa-file-alt icon-left"></i>Excel Download</button>
			</div>
			<table id="content-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
				<thead>
					<tr role="row">
						<th>No</th>
						<th>DI 보유여부</th>
						<th>이름</th>
						<th>이메일</th>
						<th>전화번호</th>
						<th>우편번호</th>
						<th>기본주소</th>
						<th>상세주소</th>
						<th>생년월일</th>
						<th>성별</th>
						<th>출처시스템 로그인아이디</th>
						<th>출처시스템</th>
					</tr>
				</thead>
				<tbody>
				<!--
					<tr>
						<td class="">1</td>
						<td class="">left</td>
						<td class="">center</td>
						<td class="">right</td>
					</tr>
				-->
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
				
				$('#content-srch-PSNL_NM, #content-srch-EML, #content-srch-BRDT').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						content_list();
					}
				});
				$('#content-srch-BRDT').on('keyup', function() {
					$(this).val($(this).val().replace(/[^0-9\-]/g, '')); // 숫자와 -만 허용
				});
				
				content_onload();
			});
			
			var content_srch_param = null; // 검색 데이터
			var content_$dataTable = null; // 테이블 객체
			
			function content_onload() {
				content_srch_init();
				content_list(true); // 최초 조회
			}
			
			// 검색 데이터 초기화
			function content_srch_init() {
				$('#content-srch-PSNL_NM').val('');
				$('#content-srch-EML').val('');
				$('#content-srch-BRDT').val('');
				$('#content-srch-GNDR_CD').val(''); $('#content-srch-GNDR_NM').html('전체');
				$('#content-srch-SRC_SYS_CD').val(''); $('#content-srch-SRC_SYS_NM').html('전체');
				$('#content-srch-DI_YN').val('Y'); $('#content-srch-DI_YN_NM').html('보유');
			}
			
			// 목록 조회
			function content_list(isOnload) {
				// 최초 조회가 아니고, 검색 조건이 없는 경우
				if (!isOnload
						&& !$('#content-srch-PSNL_NM').val() && !$('#content-srch-EML').val() && !$('#content-srch-BRDT').val()
						&& !$('#content-srch-GNDR_CD').val() && !$('#content-srch-SRC_SYS_CD').val() && !$('#content-srch-DI_YN').val()) {
					alert('검색 조건을 하나 이상 입력해 주세요.');
					return;
				}
				content_srch_param = { // 검색 데이터
					PSNL_NM:      $('#content-srch-PSNL_NM').val(),
					EML:          $('#content-srch-EML').val(),
					BRDT:         $('#content-srch-BRDT').val(),
					GNDR_CD:      $('#content-srch-GNDR_CD').val(),
					SRC_SYS_CD:   $('#content-srch-SRC_SYS_CD').val(),
					DI_YN:        $('#content-srch-DI_YN').val()
				};
				if (isOnload) { // 최초 조회인 경우, 검색 기간 설정
					content_srch_param.srchStartYmd = '${srchStartYmd}'.replace(/\D/g, ''); // 시작일 (yyyyMMdd)
					content_srch_param.srchEndYmd =   '${srchEndYmd}'.replace(/\D/g, '');   // 종료일 (yyyyMMdd)
				}
				content_dataTable(); // table
			}
			
			function content_dataTable() {
				var columns = [
					{ data: 'NO',           className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'DI_YN_NM',     className: 'dt-body-center' }, // [1]
					{ data: 'PSNL_NM',      className: 'dt-body-left'   }, // [2]
					{ data: 'EML',          className: 'dt-body-center' }, // [3]
					{ data: 'TELNO',        className: 'dt-body-center' }, // [4]
					{ data: 'ZIP',          className: 'dt-body-center' }, // [5]
					{ data: 'ADDR',         className: 'dt-body-left'   }, // [6]
					{ data: 'DADDR',        className: 'dt-body-left'   }, // [7]
					{ data: 'BRDT',         className: 'dt-body-left'   }, // [8]
					{ data: 'GNDR_NM',      className: 'dt-body-center' }, // [9]
					{ data: 'SRC_LGN_ID',   className: 'dt-body-left'   }, // [10]
					{ data: 'SRC_SYS_NM',   className: 'dt-body-left'   }, // [11]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([2].indexOf(colIndex) > -1) {
							var onclick = 'onclick="bnftPsnlInfoModal_open(\'' + (rowData['DI_VAL'] || '') + '\', ' + rowData['PSNL_SN'] + ');"';
							cell.innerHTML = '<a href="#" ' + onclick + '>' + cellData + '</a>';
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				content_$dataTable = $('#content-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: true, // processing: true,
					ajax: {
						url: CONTEXT_PATH + '/bnft/bnftPsnlMstr/api', // 개인마스터 > 목록 API
						type: 'GET',
						data: content_srch_param,
						dataSrc: function(result) {
							$('#content-list-totalCnt').html(formatNumber_addCom(result.recordsTotal));
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
					
					pageLength: 10,      // 한 페이지에 표시할 개수
					lengthChange: false, // 한 페이지에 표시할 개수 설정 (10/25/50/100)
					
					scrollX: true,
					
					dom: '<"caption-top"if> rt p',
				});
			}
			
			// 엑셀 다운로드
			function content_excelDown() {
				var totalCnt = parseInt($('#content-list-totalCnt').html().replace(/\D/g, ''));
				if (!totalCnt) {
					alert('다운로드 할 데이터가 없습니다.');
					return;
				} else if (totalCnt > EXCEL_DOWNLOAD_MAXCOUNT) { // 엑셀 다운로드 건수 제한
					alert('검색결과가 ' + formatNumber_addCom(EXCEL_DOWNLOAD_MAXCOUNT) + '건을 초과하여 다운로드 할 수 없습니다.');
					return;
				}
				
				var url = CONTEXT_PATH + '/bnft/bnftPsnlMstr/excel'; // 개인마스터 > 엑셀 다운로드
				var param = $.extend({}, content_srch_param, {
					sortName: dataTable_getSortName(content_$dataTable),
					sortType: dataTable_getSortType(content_$dataTable)
				});
				FileUtil.download(url, param);
			}
			
			// 개인 상세정보 팝업
			function bnftPsnlInfoModal_open(DI_VAL, PSNL_SN) {
				ajaxLoad('#modal1', CONTEXT_PATH + '/bnft/bnftPsnlInfoModal', { // 개인마스터 > 개인 상세정보 팝업
					DI_VAL:  DI_VAL, // DI값 (수혜개인기본)
					PSNL_SN: PSNL_SN // 개인일련번호 (수혜개인임시)
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function bnftPsnlInfoModal_close() {
				Modal_h('#modal1'); // hide
			}
		</script>
