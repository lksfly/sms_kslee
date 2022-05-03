<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<div id="" class="modal">-->
		<div id="bnftBizInfoModal-content" class="modal-content modal-l">
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">기업 상세정보</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="bnftBizInfoModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div class="card-body">
					<div class="card infoList-card">
						<div class="sub-title2">기본정보</div>
						<div class="infoList">
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">사업자등록번호</div>
									<div class="list-item_content"><c:out value="${info.BRNO}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">법인등록번호</div>
									<div class="list-item_content"><c:out value="${info.CRNO}" /></div>
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
									<div class="list-item_title">폐업일자</div>
									<div class="list-item_content"><c:out value="${info.CLSBIZ_YMD}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">산업분류코드</div>
									<div class="list-item_content"><c:out value="${info.KSIC_CD}" /></div>
								</div>
							</div>
							<div class="m-list">
								<div class="list-item">
									<div class="list-item_title">업체명</div>
									<div class="list-item_content"><c:out value="${info.ENT_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">대표자</div>
									<div class="list-item_content"><c:out value="${info.CEO_NM}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">기본주소</div>
									<div class="list-item_content"><c:out value="${info.ADDR}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">창업일</div>
									<div class="list-item_content"><c:out value="${info.FNDN_YMD}" /></div>
								</div>
								<div class="list-item">
									<div class="list-item_title">전화번호</div>
									<div class="list-item_content"><c:out value="${info.TELNO}" /></div>
								</div>
							</div>
						</div>
					</div>
					<div class="title-row tableCaption">
						<div class="sub-title2">지원사업 이력</div>
						<p class="rightCaption-total">총 <span id="bnftBizInfoModal-list-totalCnt">0</span>건</p>
					<!--
						<div class="selectinput select-S1">
							<input type="hidden" id="bnftBizInfoModal-srch-SPRT_BIZ_YR">
							<button class="label" id="bnftBizInfoModal-srch-SPRT_BIZ_YR_NM">전체</button>
							<div class="optionList" style="display: none;">
								<ul style="max-height: 150px;">
									<li class="optionItem" data-value="">전체</li>
								<c:forEach var="item" items="${yearList}">
									<li class="optionItem" data-value="${item}">${item}</li>
								</c:forEach>
								</ul>
							</div>
						</div>
						<div class="spacer"></div>
						<button type="button" class="btn btn-s btn-round btn-bright-main" onclick="bnftBizInfoModal_excelDown();"><i class="fas fa-file-alt icon-left"></i>Excel Download</button>
					-->
					</div>
					<table id="bnftBizInfoModal-list-table" class=" table-S1 cell-border" style="width:100%">
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
				main_setCommonEvent('#bnftBizInfoModal-content'); // 공통 이벤트 설정
				
				$('#bnftBizInfoModal-srch-SPRT_BIZ_YR_NM').next('.optionList').find('.optionItem').on('click', function() {
					bnftBizInfoModal_list();
				});
				
				bnftBizInfoModal_onload();
			});
			
			var bnftBizInfoModal_srch_param = null; // 검색 데이터
			var bnftBizInfoModal_$dataTable = null; // 테이블 객체
			
			function bnftBizInfoModal_onload() {
				bnftBizInfoModal_list();
			}
			
			// 목록 조회
			function bnftBizInfoModal_list() {
				bnftBizInfoModal_srch_param = { // 검색 데이터
					SPRT_BIZ_YR: $('#bnftBizInfoModal-srch-SPRT_BIZ_YR').val(),
					BRNO:        '${info.BRNO}'.replace(/\D/g, '') // 사업자등록번호
				};
				bnftBizInfoModal_dataTable(); // table
			}
			
			function bnftBizInfoModal_dataTable() {
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
				
				bnftBizInfoModal_$dataTable = $('#bnftBizInfoModal-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					serverSide: true, // processing: true,
					ajax: {
						url: CONTEXT_PATH + '/bnft/bnftBizInfoModal/api', // 기업마스터 > 기업 상세정보 팝업 > 지원사업 이력 목록 API
						type: 'GET',
						data: bnftBizInfoModal_srch_param,
						dataSrc: function(result) {
							$('#bnftBizInfoModal-list-totalCnt').html(formatNumber_addCom(result.recordsTotal));
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
			
			// 엑셀 다운로드
			function bnftBizInfoModal_excelDown() {
				var url = CONTEXT_PATH + '/bnft/bnftBizInfoModal/excel'; // 기업마스터 > 기업 상세정보 팝업 > 지원사업 이력 엑셀 다운로드
				var param = $.extend({}, bnftBizInfoModal_srch_param, {
					sortName: dataTable_getSortName(bnftBizInfoModal_$dataTable),
					sortType: dataTable_getSortType(bnftBizInfoModal_$dataTable)
				});
				FileUtil.download(url, param);
			}
		</script>
