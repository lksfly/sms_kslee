<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<div id="" class="modal">-->
		<div id="sprtCntrSearchModal-content" class="modal-content modal-s" style="height: 840px;">
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">센터 검색</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="sprtCntrSearchModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div class="card-body d-flex">
					<div class="card filterCard">
						<div class="filterCard-body">
							<div class="input-wrap">
								<div class="input-title">센터명</div>
								<div class="textinput textinput-S1">
									<input type="text" id="sprtCntrSearchModal-srch-SPRT_CNTR_NM" placeholder="입력해 주세요.">
								</div>
							</div>
							<div class="input-wrap">
								<div class="input-title">상태</div>
								<div class="selectinput select-S1">
									<input type="hidden" id="sprtCntrSearchModal-srch-USE_YN">
									<button class="label" id="sprtCntrSearchModal-srch-USE_NM"></button>
									<div class="optionList" style="display: none;">
										<ul style="max-height: 300px;">
											<li class="optionItem" data-value="">전체</li>
											<li class="optionItem" data-value="Y">운영</li>
											<li class="optionItem" data-value="N">정지</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="filterCard-footer">
							<button type="button" class="btn btn-m btn-reset" onclick="sprtCntrSearchModal_srch_init();">초기화</button>
							<div class="spacer"></div>
							<button type="button" class="btn btn-m btn-step nomargin" onclick="sprtCntrSearchModal_list();">조회</button>
						</div>
					</div>
					<div class="title-row tableCaption">
						<p class="rightCaption-total">검색결과 총 <span id="sprtCntrSearchModal-list-totalCnt">0</span>건</p>
					</div>
					<div id="sprtCntrSearchModal-list-tree" class="tree">
					<!--
						<ol>
							<li>
								<div class="tree-text">검색된 센터가 없습니다.</div>
							</li>
						</ol>
					-->
					</div>
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
				main_setCommonEvent('#sprtCntrSearchModal-content'); // 공통 이벤트 설정
				
				$('#sprtCntrSearchModal-srch-SPRT_CNTR_NM').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						sprtCntrSearchModal_list();
					}
				}).focus();
				
				sprtCntrSearchModal_onload();
			});
			
			function sprtCntrSearchModal_onload() {
				sprtCntrSearchModal_srch_init();
				sprtCntrSearchModal_list();
			}
			
			// 검색 데이터 초기화
			function sprtCntrSearchModal_srch_init() {
				$('#sprtCntrSearchModal-srch-SPRT_CNTR_NM').val('');
				$('#sprtCntrSearchModal-srch-USE_YN').val('Y'); $('#sprtCntrSearchModal-srch-USE_NM').html('운영');
			}
			
			// 목록 조회
			function sprtCntrSearchModal_list() {
				$.get(CONTEXT_PATH + '/sprt/sprtCntrSearchModal/api', { // 지원사업현황 > 센터 검색 팝업 > 목록 API
					SPRT_CNTR_NM: $('#sprtCntrSearchModal-srch-SPRT_CNTR_NM').val(),
					USE_YN:       $('#sprtCntrSearchModal-srch-USE_YN').val()
				}, function(result) {
					var totalCnt = 0; // 검색된 센터 수
					result.data.forEach(function(d) {
						if (d.UP_DEPT_CD) {
							++totalCnt;
						}
					});
					$('#sprtCntrSearchModal-list-totalCnt').html(formatNumber_addCom(totalCnt));
					sprtCntrSearchModal_tree(result.data); // tree
				}, 'json');
			}
			
			function sprtCntrSearchModal_tree(dataList) {
				var $tree = $('#sprtCntrSearchModal-list-tree').empty(); // clear
				
				var str = '';
				if (dataList.length) {
					str = '<ol>';
					dataList.forEach(function(d) { // DEPT_CD, UP_DEPT_CD, DEPT_NM, USE_YN
						if (d.UP_DEPT_CD) {
							return;
						}
						var subList = []; // 자식 목록
						dataList.forEach(function(s) {
							if (d.DEPT_CD == s.UP_DEPT_CD) {
								subList.push(s);
							}
						});
						
						str += '<li class="show">'
							+  '    <div class="tree-text">';
						if (subList.length) { // 자식이 있는 경우
							str += '    <button type="button" class="tree-toggle tree-icon1"></button>';
						}
							str += '    <a href="#">' + d.DEPT_NM + '</a>'
								+  '</div>';
						if (subList.length) { // 자식이 있는 경우
							str += '<ol class="sub-tree" style="display: block;">'; // 자식이 있는 경우
							subList.forEach(function(s) {
								str += '<li>'
									+  '    <div class="tree-text">'
									+  '        <a href="#" onclick="sprtCntrSearchModal_close(\'' + s.DEPT_CD + '\', \'' + s.DEPT_NM + '\');">' + s.DEPT_NM + '</a>'
									+  '    </div>'
									+  '</li>'
							});
							str += '</ol>'
						}
						str += '</li>';
					});
					str += '</ol>';
				} else {
					str = '<ol><li><div class="tree-text">검색된 센터가 없습니다.</div></li></ol>';
				}
				
				$tree.append(str);
				
				$tree.find('.tree-text').off('click').on('click', function() {
					if ($(this).closest('li').hasClass('show')) { // tree가 열려있을 경우
						$(this).closest('li').removeClass('show').children('.sub-tree').slideUp(); // sub-tree hide
					} else { // tree가 닫혀있을 경우
						$(this).closest('li').addClass('show').children('.sub-tree').slideDown(); // sub-tree show
					}
				});
			}
		</script>
