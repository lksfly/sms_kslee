<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<article class="content-wrapper">-->
		<section class="container-fluid">
			<div class="title-row">
				<div class="content-title">기간설정</div>
				<div class="inputGroup dateGroup">
					<div class="textinput textinput-S1">
						<div class="textinput-datepicker">
							<input type="text" id="content-srch-srchStartYmd" class="datepicker" readonly>
						</div>
						<!-- <button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button> -->
					</div>
					<div class="textinput-gap">~</div>
					<div class="textinput textinput-S1">
						<div class="textinput-datepicker">
							<input type="text" id="content-srch-srchEndYmd" class="datepicker" readonly>
						</div>
						<!-- <button type="button" class="btn btn-dateReset"><i class="fas fa-times-circle"></i></button> -->
					</div>
				</div>
				<div class="spacer"></div>
			</div>
			<div class="row">
				<div class="col s12 m5">
					<div class="card">
						<div class="card-header">
							<div class="content-title">지원건수</div>
						</div>
						<div class="card-body">
							<div id="content-rgn-chart" class="" style="height: 360px;"><!-- chart --></div>
						</div>
					</div>
				</div>
				<div class="col s12 m7">
					<div id="content-rgn-card" class="area-info-card">
					<%--
						<div class="card on" color="#e56491">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">2,500</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">5,000</span><span class="unit">개</span></p>
							</div>
						</div>
						<div class="card" color="#F57D20">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">250</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">25,000</span><span class="unit">개</span></p>
							</div>
						</div>
						<div class="card" color="#f5c208">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">500</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">200</span><span class="unit">개</span></p>
							</div>
						</div>
						<div class="card" color="#b0d352">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">250</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">20</span><span class="unit">개</span></p>
							</div>
						</div>
						<div class="card" color="#388fd6">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">12,500</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">200</span><span class="unit">개</span></p>
							</div>
						</div>
						<div class="card" color="#646fe5">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">5</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">10</span><span class="unit">개</span></p>
							</div>
						</div>
						<div class="card" color="#8c67d1">
							<h5 class="title">서울강원지역본부</h5>
							<div class="info">
								<p>센터 수<span class="count">250</span><span class="unit">개</span></p>
								<p>지원 사업 수<span class="count">50</span><span class="unit">개</span></p>
							</div>
						</div>
					--%>
					</div>
				</div>
				<div class="col s12 m12">
					<div class="card nomargin">
						<div class="card-header row">
							<div class="content-title">[<span id="content-cntr-rgnNm">서울강원지역본부</span>] 센터지원현황</div>
							<div class="spacer"></div>
							<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="cntrSprtSttsModal_open();"><i class="fas fa-file-alt icon-left"></i>로우데이터</button>
						</div>
						<div class="card-body">
							<div id="content-cntr-chart" class="" style="height: 186px;"><!-- chart --></div>
						</div>
					</div>
				</div>
			</div>
		</section>
<!--</article>-->
		
		<!--
			모달 팝업을 위한 div
		-->
		<div id="cntrSprtSttsModal" class="modal">
			<div class="modal-content modal-s">
				<div class="card body-scroll" style="height: 100%;">
					<div class="card-header row">
						<h1 class="content-title2">[<span id="cntrSprtSttsModal-rgnNm"></span>] 센터지원현황</h1>
						<div class="spacer"></div>
						<button type="button" class="btn btn-close btn-text-main nomargin" onclick="cntrSprtSttsModal_close();"><i class="fas fa-times"></i></button>
					</div>
					<div class="card-body">
						<table id="cntrSprtSttsModal-list-table" class="table-S1 cell-border nowrap hover" style="width:100%">
							<thead>
								<tr role="row">
									<th class="">No</th>
									<th class="">센터명</th>
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
		</div>
		
		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			$(document).ready(function() {
				main_setCommonEvent('.container-fluid'); // 공통 이벤트 설정
				
				content_onload();
			});
			
			var content_colors = ['#e56491', '#F57D20', '#f5c208', '#b0d352', '#388fd6', '#646fe5', '#8c67d1'];
			var content_cntr_dataList = []; // 센터별 지원건수 목록
			
			function content_onload() {
				content_srch_init();
				content_rgn(); // 지역본부
			}
			
			// 검색 데이터 초기화
			function content_srch_init() {
				$('#content-srch-srchStartYmd').on('change', function() {
					content_rgn(); // 지역본부
				}).datepicker('option', 'minDate', '2021-01-01').val('${srchStartYmd}');
				$('#content-srch-srchEndYmd').on('change', function() {
					content_rgn(); // 지역본부
				}).datepicker('option', 'minDate', '2021-01-01').val('${srchEndYmd}');
			}
			
			// 목록 조회
			function content_list(rgnCd, callback) {
				$.get(CONTEXT_PATH + '/cntr/cntrSprtStts/api', { // 센터지원현황 > 지원건수 목록 API
					rgnCd:        rgnCd, // 지역코드
					srchStartYmd: $('#content-srch-srchStartYmd').val().replace(/\D/g, ''), // 시작일 (yyyyMMdd)
					srchEndYmd:   $('#content-srch-srchEndYmd').val().replace(/\D/g, '')    // 종료일 (yyyyMMdd)
				}, function(result) {
					callback(result.data);
				}, 'json');
			}
			
			// 지역본부
			function content_rgn() {
				content_list(null, function(dataList) {
					content_rgn_chart(dataList);
					content_rgn_card(dataList);
				});
			}
			// 지역본부 차트
			function content_rgn_chart(dataList) {
				var valArr = dataList.map(function(d) { return { name: d.RGN_NM, value: d.GIVE_CNT } }); // 지역명, 지원건수
				var option = {
					tooltip: { trigger: 'item' },
					legend: {
						top: '0%',
						selected: (function() {
							var selected = {};
							selected[valArr[valArr.length - 1].name] = false; // 마지막 지역본부(기타) 레전드 비활성화
							return selected;
						})()
					},
					series: [{
						type: 'pie',
						center: ['50%', '58%'],
						radius: ['43%', '81%'],
						label: { show: false },
						data: valArr
					}],
					color: content_colors
				};
				var chart = echarts.init(document.getElementById('content-rgn-chart'));
				$(window).unbind('resize.content-rgn-chart').bind('resize.content-rgn-chart', function() { // window.onresize
					if (chart) chart.resize();
				});
				chart.setOption(option);
				chart.on('click', function(params) {
					var d = dataList[params.dataIndex];
					content_rgn_card_click(d.RGN_CD, d.RGN_NM);
				});
			}
			// 지역본부 카드
			function content_rgn_card(dataList) {
				var str = '';
				dataList.forEach(function(d, i) {
					var id = 'content-rgn-card-' + d.RGN_CD;
					var onclick = 'content_rgn_card_click(\'' + d.RGN_CD + '\', \'' + d.RGN_NM + '\');';
					var color = content_colors[i] || 'gray';
					
					str += '<div id="' + id + '" class="card" onclick="' + onclick + '" color="' + color + '">'
						+  '    <h5 class="title">' + d.RGN_NM + '</h5>'
						+  '    <div class="info">';
					if (i < dataList.length - 1) { // 마지막 지역본부(기타) 센터수 표시안함
						str += '    <p>센터수<span class="count">' + formatNumber_addCom(d.CNTR_CNT) + '</span><span class="unit">개</span></p>';
					}
					str += '        <p>지원건수<span class="count">' + formatNumber_addCom(d.GIVE_CNT) + '</span><span class="unit">건</span></p>'
						+  '    </div>'
						+  '    <div class="card-bg"><span style="background-color: ' + color + ';"></span></div>'
						+  '    <span class="check" style="background-color: ' + color + ';"></span>'
						+  '</div>';
				});
				$('#content-rgn-card').empty().append(str);
				
				var d = dataList[0];
				if (d) {
					content_rgn_card_click(d.RGN_CD, d.RGN_NM); // 첫 번째 지역본부 클릭
				}
			}
			function content_rgn_card_click(rgnCd, rgnNm) {
				$('#content-rgn-card .card').removeClass('on');
				var $card = $('#content-rgn-card-' + rgnCd).addClass('on');
				var color = $card.attr('color');
				content_cntr(rgnCd, rgnNm, color); // 센터
			}
			
			// 센터
			function content_cntr(rgnCd, rgnNm, color) {
				content_list(rgnCd, function(dataList) {
					$('#content-cntr-rgnNm').text(rgnNm);
					content_cntr_chart(dataList, color);
					content_cntr_dataList = dataList; // 센터별 지원건수 목록
				});
			}
			// 센터 차트
			function content_cntr_chart(dataList, color) {
				var tmpArr = dataList.map(function(d) { return { CNTR_NM: d.CNTR_NM, GIVE_CNT: d.GIVE_CNT } });
				/*tmpArr.sort(function(a, b) { // 높은순->낮은순으로 정렬 (최대 10개)
					return b.GIVE_CNT - a.GIVE_CNT; // 내림차순 정렬
				});*/
				if (tmpArr.length > 12) {
					tmpArr = tmpArr.slice(0, 12);
				}
				
				var catArr = tmpArr.map(function(d) { return d.CNTR_NM }); // 센터명
				var valArr = tmpArr.map(function(d) { return d.GIVE_CNT }); // 지원건수
				var option = {
					tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
					xAxis: {
						show: true, type: 'category', data: catArr,
						axisLabel: {
							interval: 0, // interval (0: 모든 라벨 표시, 'auto': 기본값)
							formatter: function(cat) {
								if (cat.length > 9) {
									cat = cat.substring(0, 9) + '..';
								}
								return cat;
							}
						},
						axisLine: { show: false }, // 라인숨김
						axisTick: { length: 0 },   // 라인숨김
						offset: 10
					},
					yAxis: {},
					grid: [{ top: '10%', bottom: '17%', left: '5%', right: '5%' }],
					series: [{
			      		type: 'bar',
						itemStyle: { color: color },
						label: {
							show: true,
							position: 'top',
					  		formatter: function(params) {
								return formatNumber_addCom(params.value);
							}
			      		},
				  		data: valArr
			    	}]
				};
				var chart = echarts.init(document.getElementById('content-cntr-chart'));
				$(window).unbind('resize.content-cntr-chart').bind('resize.content-cntr-chart', function() { // window.onresize
					if (chart) chart.resize();
				});
				chart.setOption(option);
			}
			// 센터 팝업
			function cntrSprtSttsModal_open() {
				Modal_s('#cntrSprtSttsModal'); // show
				
				$('#cntrSprtSttsModal-rgnNm').text($('#content-cntr-rgnNm').text());
				
				var columns = [
					{ data: 'NO',       className: 'dt-body-center', orderable: false }, // [0]
					{ data: 'CNTR_NM',  className: 'dt-body-left'   }, // [1]
					{ data: 'GIVE_CNT', className: 'dt-body-right' }, // [2]
				];
				var columnDefs = [{
					createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
						if ([2].indexOf(colIndex) > -1) {
							cell.innerHTML = formatNumber_addCom(cellData);
						}
					}, targets: columns.map(function(d, i) { return i; }) // 모든 컬럼
				}];
				
				$('#cntrSprtSttsModal-list-table').DataTable({
					destroy: true, // 기존 테이블이 있으면 삭제되고 새 테이블로 대체된다.
					data: content_cntr_dataList, // 센터별 지원건수 목록 
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
				//$dataTable.columns.adjust(); // 컬럼 너비 다시 계산
			}
			function cntrSprtSttsModal_close() {
				Modal_h('#cntrSprtSttsModal'); // hide
			}
		</script>
