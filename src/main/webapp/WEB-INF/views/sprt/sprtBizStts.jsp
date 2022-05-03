<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<article class="content-wrapper">-->
		<section class="container-fluid">
			<h1 class="page-title">지원사업 현황</h1>
			<div class="card filterCard">
				<div class="filterCard-body">
					<div class="input-wrap">
						<div class="input-title">사업명</div>
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
					<div class="input-wrap searchInput">
						<div class="input-title">세부사업명</div>
						<div class="textinput textinput-S1">
							<input type="hidden" id="content-srch-SPRT_BIZ_SN">
							<input type="text" id="content-srch-SPRT_BIZ_NM" placeholder="선택해 주세요." readonly onclick="sprtBizSearchModal_open();">
						</div>
						<button class="searchButton" onclick="sprtBizSearchModal_open();">검색</button>
					</div>
					<div class="input-wrap">
						<div class="input-title">사업연도</div>
						<div class="selectinput w-100 select-S1">
							<input type="hidden" id="content-srch-SPRT_BIZ_YR">
							<button class="label" id="content-srch-SPRT_BIZ_YR_NM">전체</button>
							<div class="optionList" style="display: none;">
								<ul style="max-height: 300px;">
									<li class="optionItem" data-value="">전체</li>
								<c:forEach var="item" items="${yearList}">
									<li class="optionItem" data-value="${item}">${item}</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">사업자번호</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-BRNO" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">업체명</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-ENT_NM" placeholder="입력해 주세요.">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">신청자명</div>
						<div class="textinput textinput-S1">
							<input type="text" id="content-srch-APLCNT_NM" placeholder="입력해 주세요.">
						</div>
					</div>
<c:if test="${loginInfo.loginType == 'AD1' || loginInfo.loginType == 'AD2'}">
					<div class="input-wrap searchInput">
						<div class="input-title">센터명</div>
						<div class="textinput textinput-S1">
							<input type="hidden" id="content-srch-SPRT_CNTR_CD">
							<input type="text" id="content-srch-SPRT_CNTR_NM" placeholder="선택해 주세요." readonly onclick="sprtCntrSearchModal_open();">
						</div>
						<button class="searchButton" onclick="sprtCntrSearchModal_open();">검색</button>
					</div>
</c:if>
					<div class="input-wrap">
						<div class="input-title">검색일자<br>(지급/신청/수료)</div>
						<div class="inputGroup dateGroup">
							<div class="textinput textinput-S1">
								<div class="textinput-datepicker">
									<input type="text" id="content-srch-srchStartYmd" class="datepicker" readonly>
								</div>
								<button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button>
							</div>
							<div class="textinput-gap">~</div>
							<div class="textinput textinput-S1">
								<div class="textinput-datepicker">
									<input type="text" id="content-srch-srchEndYmd" class="datepicker" readonly>
								</div>
								<button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button>
							</div>
						</div>
					</div>
				<!--
					<div class="input-wrap">
						<div class="input-title">지급일자</div>
						<div>
							<div class="inputGroup dateGroup">
								<div class="textinput textinput-S1">
									<div class="textinput-datepicker">
										<input type="text" id="content-srch-srchStartYmd" class="datepicker" readonly>
									</div>
									<button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button>
								</div>
								<div class="textinput-gap">~</div>
								<div class="textinput textinput-S1">
									<div class="textinput-datepicker">
										<input type="text" id="content-srch-srchEndYmd" class="datepicker" readonly>
									</div>
									<button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button>
								</div>
							</div>
							<div class="clickinput clickinput-S2 dateGroup-radio">
								<input type="checkbox" id="content-srch-">
								<label for="dateVal">
									<span class="clickmarker icon1"></span>
									<span class="clickinput-text inh-color">지급 일자 누락 데이터 포함</span>
								</label>
							</div>
						</div>
					</div>
				-->
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
						<th>사업명</th>
						<th>세부사업명</th>
						<th>사업연도</th>
						<th>업체유형</th>
						<th>업체명</th>
						<th>사업자등록번호</th>
						<th>신청자명</th>
						<th>창업일</th>
						<th>수혜금액(천원)</th>
						<th>담당센터</th>
						<th>검색일자</th>
						<th>신청정보</th>
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
				
				$('#content-srch-BRNO, #content-srch-ENT_NM, #content-srch-APLCNT_NM').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						content_list();
					}
				});
				$('#content-srch-BRNO').on('keyup', function() {
					$(this).val($(this).val().replace(/\D/g, '')); // 숫자만 허용
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
				$('#content-srch-SRC_SYS_CD').val(''); $('#content-srch-SRC_SYS_NM').html('전체');
				$('#content-srch-SPRT_BIZ_SN').val(''); $('#content-srch-SPRT_BIZ_NM').val('');
				$('#content-srch-SPRT_BIZ_YR').val(''); $('#content-srch-SPRT_BIZ_YR_NM').html('전체');
				$('#content-srch-BRNO').val('');
				$('#content-srch-ENT_NM').val('');
				$('#content-srch-APLCNT_NM').val('');
				$('#content-srch-SPRT_CNTR_CD').val(''); $('#content-srch-SPRT_CNTR_NM').val('');
				$('#content-srch-srchStartYmd').val('${srchStartYmd}');
				$('#content-srch-srchEndYmd').val('${srchEndYmd}');
			}
			
			// 목록 조회
			function content_list(isOnload) {
				// 최초 조회가 아니고, 검색 조건이 없는 경우
				if (!isOnload
						&& !$('#content-srch-SRC_SYS_CD').val() && !$('#content-srch-SPRT_BIZ_SN').val() && !$('#content-srch-SPRT_BIZ_YR').val()
						&& !$('#content-srch-BRNO').val() && !$('#content-srch-ENT_NM').val() && !$('#content-srch-APLCNT_NM').val()
						&& !$('#content-srch-SPRT_CNTR_CD').val() && !$('#content-srch-srchStartYmd').val() && !$('#content-srch-srchEndYmd').val()) {
					alert('검색 조건을 하나 이상 입력해 주세요.');
					return;
				}
				content_srch_param = { // 검색 데이터
					SRC_SYS_CD:   $('#content-srch-SRC_SYS_CD').val(),
					SPRT_BIZ_SN:  $('#content-srch-SPRT_BIZ_SN').val(),
					SPRT_BIZ_YR:  $('#content-srch-SPRT_BIZ_YR').val(),
					BRNO:         $('#content-srch-BRNO').val().replace(/\D/g, ''),
					ENT_NM:       $('#content-srch-ENT_NM').val(),
					APLCNT_NM:    $('#content-srch-APLCNT_NM').val(),
					SPRT_CNTR_CD: $('#content-srch-SPRT_CNTR_CD').val(),
					srchStartYmd: $('#content-srch-srchStartYmd').val().replace(/\D/g, ''), // 시작일 (yyyyMMdd)
					srchEndYmd:   $('#content-srch-srchEndYmd').val().replace(/\D/g, '')    // 종료일 (yyyyMMdd)
				};
				content_dataTable(); // table
			}
			
			function content_dataTable() {
				var columns = [
					{ data: 'NO',           className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'SRC_SYS_NM',   className: 'dt-body-left'   }, // [1]
					{ data: 'SPRT_BIZ_NM',  className: 'dt-body-left'   }, // [2]
					{ data: 'SPRT_BIZ_YR',  className: 'dt-body-center' }, // [3]
					{ data: 'BZMN_TYPE_NM', className: 'dt-body-center' }, // [4]
					{ data: 'ENT_NM',       className: 'dt-body-left'   }, // [5]
					{ data: 'BRNO',         className: 'dt-body-center' }, // [6]
					{ data: 'APLCNT_NM',    className: 'dt-body-left'   }, // [7]
					{ data: 'FNDN_YMD',     className: 'dt-body-center' }, // [8]
					{ data: 'GIVE_AMT',     className: 'dt-body-right'  }, // [9]
					{ data: 'SPRT_CNTR_NM', className: 'dt-body-left'   }, // [10]
					{ data: 'SLCTN_YMD',    className: 'dt-body-center' }, // [11]
					{ data: 'APLY_INFO',    className: 'dt-body-center dt-button', orderable: false, defaultContent: '' }  // [12]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([5].indexOf(colIndex) > -1 && rowData['ENT_NM'] && rowData['BRNO']) {
							var onclick = 'onclick="bnftBizInfoModal_open(\'' + rowData['BRNO'] + '\');"';
							cell.innerHTML = '<a href="#" ' + onclick + '>' + cellData + '</a>';
						}
						if ([9].indexOf(colIndex) > -1) {
							cell.innerHTML = formatNumber_addCom(cellData);
						}
						if ([12].indexOf(colIndex) > -1) {
							var onclick = 'onclick="sprtBizAplyInfoModal_open(' + rowData['APLY_SN'] + ');"';
							cell.innerHTML = '<button type="button" ' + onclick + ' class="btn btn-xs btn-icon btn-round btn-text-main2 nomargin"><i class="fas fa-search"></i></button>';
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				content_$dataTable = $('#content-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: true, // processing: true,
					ajax: {
						url: CONTEXT_PATH + '/sprt/sprtBizStts/api', // 지원사업현황 > 목록 API
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
				
				var url = CONTEXT_PATH + '/sprt/sprtBizStts/excel'; // 지원사업현황 > 엑셀 다운로드
				var param = $.extend({}, content_srch_param, {
					sortName: dataTable_getSortName(content_$dataTable),
					sortType: dataTable_getSortType(content_$dataTable)
				});
				FileUtil.download(url, param);
			}
			
			// 세부사업 검색 팝업
			function sprtBizSearchModal_open() {
				ajaxLoad('#modal1', CONTEXT_PATH + '/sprt/sprtBizSearchModal', { // 지원사업현황 > 세부사업 검색 팝업
					SRC_SYS_CD: $('#content-srch-SRC_SYS_CD').val() // 출처시스템코드
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function sprtBizSearchModal_close(p1, p2) {
				if (p1 && p2) {
					$('#content-srch-SPRT_BIZ_SN').val(p1); // 지원사업일련번호
					$('#content-srch-SPRT_BIZ_NM').val(p2); // 지원사업명 (세부사업명)
				}
				Modal_h('#modal1'); // hide
			}
			
			// 센터 검색 팝업
			function sprtCntrSearchModal_open() {
				ajaxLoad('#modal1', CONTEXT_PATH + '/sprt/sprtCntrSearchModal', { // 지원사업현황 > 센터 검색 팝업
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function sprtCntrSearchModal_close(p1, p2) {
				if (p1 && p2) {
					$('#content-srch-SPRT_CNTR_CD').val(p1); // 지원센터코드
					$('#content-srch-SPRT_CNTR_NM').val(p2); // 지원센터명 (담당센터)
				}
				Modal_h('#modal1'); // hide
			}
			
			// 기업 상세정보 팝업
			function bnftBizInfoModal_open(BRNO) {
				ajaxLoad('#modal1', CONTEXT_PATH + '/bnft/bnftBizInfoModal', { // 기업마스터 > 기업 상세정보 팝업
					BRNO: BRNO.replace(/\D/g, '') // 사업자등록번호
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function bnftBizInfoModal_close() {
				Modal_h('#modal1'); // hide
			}
			
			// 신청내역 상세정보 팝업
			function sprtBizAplyInfoModal_open(APLY_SN) {
				ajaxLoad('#modal1', CONTEXT_PATH + '/sprt/sprtBizAplyInfoModal', { // 지원사업현황 > 신청내역 상세정보 팝업
					APLY_SN: APLY_SN // 신청일련번호
				}, function() {
					Modal_s('#modal1'); // show
				});
			}
			function sprtBizAplyInfoModal_close() {
				Modal_h('#modal1'); // hide
			}
		</script>
