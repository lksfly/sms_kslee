<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<%--
	### 당월 지원사업 진행 현황 ###
--%>

<c:set var="SPRT_CNT"			value="0" 	/><%-- 지원사업수 --%>
<c:set var="GIVE_AMT"			value="0"	/><%-- 지원금액(천원) --%>
<c:set var="SPRT_CNTR_CNT"		value="0"	/><%-- 지원센터 --%>
<c:set var="SPRT_BIZ_PSNL_CNT"	value="0"	/><%-- 지원사업 업체 수 + 지원사업 개인 수 --%>

<c:if test="${!empty(M01)}"><c:set var="SPRT_CNT"			value="${M01[0].SPRT_CNT}" 			/></c:if>
<c:if test="${!empty(M01)}"><c:set var="GIVE_AMT"			value="${M01[0].GIVE_AMT}" 			/></c:if>
<c:if test="${!empty(M01)}"><c:set var="SPRT_CNTR_CNT"		value="${M01[0].SPRT_CNTR_CNT}" 	/></c:if>
<c:if test="${!empty(M01)}"><c:set var="SPRT_BIZ_PSNL_CNT"	value="${M01[0].SPRT_BIZ_PSNL_CNT}" /></c:if>

<%--
	### 당월 사업별 지원 현황 ###
--%>

<c:set var="EDU_CNT"	value="0" 	/><%-- 교육 --%>
<c:set var="CNSLT_CNT"	value="0"	/><%-- 컨설팅 --%>
<c:set var="HOPE_CNT"	value="0"	/><%-- 희망리턴 --%>
<c:set var="DL_CNT"		value="0"	/><%-- 직접대출 --%>
<c:set var="PL_CNT"		value="0"	/><%-- 대리대출 --%>

<c:if test="${!empty(M02)}"><c:set var="EDU_CNT"	value="${M02[0].EDU_CNT}" 	/></c:if>
<c:if test="${!empty(M02)}"><c:set var="CNSLT_CNT"	value="${M02[0].CNSLT_CNT}"	/></c:if>
<c:if test="${!empty(M02)}"><c:set var="HOPE_CNT"	value="${M02[0].HOPE_CNT}"	/></c:if>
<c:if test="${!empty(M02)}"><c:set var="DL_CNT"		value="${M02[0].DL_CNT}"	/></c:if>
<c:if test="${!empty(M02)}"><c:set var="PL_CNT"		value="${M02[0].PL_CNT}"	/></c:if>

<!--<article class="content-wrapper">-->
		<section class="container-fluid">
			<div class="row">
				<div class="col s12 m5">
					<div class="title-row">
						<div class="content-title">${thisMonth}월 지원사업 진행 현황</div>
						<div class="tooltip">
							<i class="fas fa-info-circle"></i>
							<div class="tooltip-content left"><span class="title">Tip1. 지원건수 일자 측정 기준은 아래와 같은 기준으로 측정합니다.</span>
								1. 교육:수료일자
                                ※ 전문기술교육, 튼튼창업은 지급일자
								2. 희망리턴
								- 사업정리:지급일자
								- 점포철거: 신청일자
								- 법률자문: 신청일자
								- 재기교육: 수료일자
								- 재도장려금: 지급일자
								- 전직장려금: 지급일자
								3. 직접대출:지급일자
								4. 대리대출:지급일자
								5. 컨설팅:신청일자

								<span class="title">Tip2. 지원금액이 표기되는 사업은 아래와 같습니다.</span>
								1. 교육(전문기술교육, 튼튼창업)
								2. 희망리턴
								- 사업정리
								- 점포철거
								- 전직장려금
								3. 직접대출
								4. 대리대출
								5. 컨설팅

								<span class="title">Tip3. 지원대상수는 해당월 총 지원건수 중 기업과 개인으로 분류하여 중복을 제거한 값을 나타냅니다.</span>
								1. 기업: 당월 지원건수 중 사업자번호를 기준으로 중복제거한 후 측정한 수
								2. 개인: 당월 지원건수 중 사업자번호가 없는 개인신청자 중 DI값 보유 기준으로 중복제거한 후 측정한 수
							</div>
						</div>
						<div class="spacer"></div>
						<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="M01DetailPopup();"><i class="fas fa-file-alt icon-left"></i>지난 지원 사업 현황</button>
					</div>
					<div class="dashboard-statistics1">
						<div class="each">
							<h5>지원건수</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${SPRT_CNT}" /><span>건</span></p>
						</div>
						<div class="each">
							<h5>지원금액</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${GIVE_AMT}" /><span>천원</span></p>
						</div>
						<div class="each">
							<h5>지원센터</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${SPRT_CNTR_CNT}" /><span>개</span></p>
						</div>
						<div class="each">
							<h5>지원대상수</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${SPRT_BIZ_PSNL_CNT}" /><span>개</span></p>
						</div>
					</div>
				</div>
				<div class="col s12 m7">
					<div class="title-row content-title">${thisMonth}월 사업별 지원 현황</div>
					<div class="dashboard-statistics2">
						<div class="each">
							<h5>희망리턴</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${HOPE_CNT}" /><span>건</span></p>
						</div>
						<div class="each">
							<h5>직접대출</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${DL_CNT}" /><span>건</span></p>
						</div>
						<div class="each">
							<h5>대리대출</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${PL_CNT}" /><span>건</span></p>
						</div>
						<div class="each">
							<h5>교육</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${EDU_CNT}" /><span>건</span></p>
						</div>
						<div class="each">
							<h5>컨설팅</h5>
							<p><fmt:formatNumber type="number" maxFractionDigits="3" value="${CNSLT_CNT}" /><span>건</span></p>
						</div>
					</div>
				</div>
				<div class="col s12 m6">
					<div class="card">
						<div class="card-header row">
							<div class="content-title">월별 지원 사업 현황</div>
							<div class="selectinput select-S1">
								<input type="hidden" id="SPRT_BIZ_YEAR" value="${yearList[0]}">
								<button class="label">${yearList[0]}</button>
								<div class="optionList" style="display: none;">
									<ul style="max-height: 150px;">
									<c:forEach var="item" items="${yearList}">
										<li class="optionItem" data-value="${item}" onclick="M04ApiCall('${item}')">${item}</li>
									</c:forEach>
									</ul>
								</div>
							</div>
							<div class="spacer"></div>
							<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="M04DetailPopup()"><i class="fas fa-file-alt icon-left"></i>로우데이터</button>
						</div>
						<div class="card-body">
							<div id="M04Chart" class="" style="height: 236px;"></div>
						</div>
					</div>
				</div>
				<div class="col s12 m6">
					<div class="card">
						<div class="card-header row">
							<div class="content-title">월별 지원 금액 현황</div>
							<div class="selectinput select-S1">
								<input type="hidden" id="SPRT_BIZ_AMT_YEAR" value="${yearList[0]}">
								<button class="label">${yearList[0]}</button>
								<div class="optionList" style="display: none;">
									<ul style="max-height: 150px;">
									<c:forEach var="item" items="${yearList}">
										<li class="optionItem" data-value="${item}" onclick="M05ApiCall('${item}')">${item}</li>
									</c:forEach>
									</ul>
								</div>
							</div>
							<div class="spacer"></div>
							<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="M05DetailPopup()"><i class="fas fa-file-alt icon-left"></i>로우데이터</button>
						</div>
						<div class="card-body">
							<div id="M05Chart" class="" style="height: 236px;"></div>
						</div>
					</div>
				</div>
				<div class="col s12 m12">
					<div class="card">
						<div class="card-header row">
							<div class="content-title">지급 일자 별 지원 사업 현황</div>
							<div class="spacer"></div>
							<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="M03DetailPopup()"><i class="fas fa-file-alt icon-left"></i>로우데이터</button>
						</div>
						<div class="card-body">
							<div id="M03Chart" class="" style="height: 186px;"></div>
						</div>
					</div>
				</div>
				<div class="col s12 m9">
					<div class="card">
						<div class="card-header row">
							<div class="content-title">업종별 지원 사업 현황</div>
							<div class="spacer"></div>
							<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="M06DetailPopup()"><i class="fas fa-file-alt icon-left"></i>로우데이터</button>
						</div>
						<div class="card-body">
							<div class="chart-breadcrumb1 chart-back">
							</div>
							<div id="M06Chart" class="" style="height: 455px;"></div>
						</div>
					</div>
				</div>
				<div class="col s12 m3">
					<div class="card">
						<div class="card-header row">
							<div class="content-title">지역별 지원 사업 현황</div>
							<div class="spacer"></div>
							<button type="button" class="btn btn-s btn-round btn-bright-main nomargin" onclick="M07DetailPopup()"><i class="fas fa-file-alt icon-left"></i>로우데이터</button>
						</div>
						<div class="card-body">
							<div class="pubilc-mapChart">
								<div class="legend">
									<div class="box"></div>
									<span class="text">지원 사업 수</span>
								</div>
								<div class="map">
									<!--
									01	서울중부센터
									02	부산중부센터
									03	대구서부센터
									04	광주서부센터
									05	인천북부센터
									06	대전북부센터
									07	울산북부센터
									11	화성센터(경기도)
									12	춘천센터
									13	충주센터(충북)
									14	천안아산센터(충남)
									15	정읍센터(전북)
									16	여수센터(전남)
									17	포항센터(경북)
									18	통영센터(경남)
									19	제주센터
									99	세종
									-->
									<img src="./assets/images/map.png" alt="지역별 지원 사업 현황 지도">
									<div id="marker-01" class="marker" style="top: 71px;left: 90px;">서울<div class="count">0</div></div>
									<div id="marker-02" class="marker" style="top: 340px;left: 231px;">부산<div class="count">0</div></div>
									<div id="marker-03" class="marker" style="top: 259px;left: 214px;">대구<div class="count">0</div></div>
									<div id="marker-04" class="marker" style="top: 326px;left: 75px;">광주<div class="count">0</div></div>
									<div id="marker-05" class="marker" style="top: 105px;left: 40px;">인천<div class="count">0</div></div>
									<div id="marker-06" class="marker" style="top: 233px; left: 154px;">대전<div class="count">0</div></div>
									<div id="marker-07" class="marker" style="top: 293px;left: 268px;">울산<div class="count">0</div></div>
									<div id="marker-11" class="marker" style="top: 123px;left: 118px;">경기도<div class="count">0</div></div>
									<div id="marker-12" class="marker" style="top: 75px; left: 199px;">강원<div class="count">0</div></div>
									<div id="marker-13" class="marker" style="top: 151px;left: 173px;">충북<div class="count">0</div></div>
									<div id="marker-99" class="marker" style="top: 186px;left: 118px;">세종<div class="count">0</div></div>
									<div id="marker-14" class="marker" style="top: 169px;left: 63px;">충남<div class="count">0</div></div>
									<div id="marker-15" class="marker" style="top: 271px;left: 102px;">전북<div class="count">0</div></div>
									<div id="marker-16" class="marker" style="top: 373px;left: 92px;">전남<div class="count">0</div></div>
									<div id="marker-17" class="marker" style="top: 193px;left: 241px;">경북<div class="count">0</div></div>
									<div id="marker-18" class="marker" style="top: 306px;left: 173px;">경남<div class="count">0</div></div>
									<div id="marker-19" class="marker" style="top: 440px;left: 61px;">제주<div class="count">0</div></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
<!--</article>-->
		
		<%--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		--%>
		<script type="text/javascript">

			var //M02Chart, // 2. 당월 사업별 지원 현황
				M03Chart, // 3. 당월 지급일자 별 지원사업 현황
				M04Chart, // 4. 월별 지원 사업 현황
			    M05Chart, // 5. 월별 지원금액 현황
			    M06Chart  // 6. 업종별 지원 사업 현황 전체
			;
			$(document).ready(function() {
				main_setCommonEvent('.container-fluid'); // 공통 이벤트 설정
				/*
				M02Chart = echarts.init(document.getElementById('M02Chart'));
				$(window).unbind('resize.M02Chart').bind('resize.M02Chart', function () {
					if (M02Chart) M02Chart.resize();
				});
				 */

				M03Chart = echarts.init(document.getElementById('M03Chart'));
				$(window).unbind('resize.M03Chart').bind('resize.M03Chart', function () {
					if (M03Chart) M03Chart.resize();
				});

				M04Chart = echarts.init(document.getElementById('M04Chart'));
				$(window).unbind('resize.M04Chart').bind('resize.M04Chart', function () {
					if (M04Chart) M04Chart.resize();
				});

				M05Chart = echarts.init(document.getElementById('M05Chart'));
				$(window).unbind('resize.M05Chart').bind('resize.M05Chart', function () {
					if (M05Chart) M05Chart.resize();
				});

				M06Chart = echarts.init(document.getElementById('M06Chart'));
				$(window).unbind('resize.M06Chart').bind('resize.M06Chart', function () {
					if (M06Chart) M06Chart.resize();
				});

				// M02ApiCall(); // 2. 당월 사업별 지원 현황
				M03ApiCall(); // 3. 당월 지급일자 별 지원사업 현황
				M04ApiCall(); // 4. 월별 지원 사업 현황
				M05ApiCall(); // 5. 월별 지원금액 현황
				M06ApiCall(); // 6. 업종별 지원 사업 현황
				M07ApiCall(); // 7. 지역별 지원사업 현황

				M06breadcrumb();
			});

			// 2. 당월 사업별 지원 현황
			/*
			function M02ApiCall() {
				var option = {
					tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
					legend: {},
					xAxis: {},
					yAxis: {
					show: false,
						data: ['컨설팅', '교육', '대리대출', '직접대출','희망리턴']
					},
					series: [{
						type: 'bar',
						label: {
							show: true,
							formatter:function (params) {
								return params.name + " " + formatNumber_addCom(params.value);
							},
							position: 'right'
						},
						data: [
							{ // 컨설팅
								value: '${CNSLT_CNT}'.replace(/,/g, ''),
								itemStyle: { color: '#8c67d1' }
							},
							{ // 교육
								value: '${EDU_CNT}'.replace(/,/g, ''),
								itemStyle: { color: '#646fe5' }
							},
							{ // 대리대출
								value: '${PL_CNT}'.replace(/,/g, ''),
								itemStyle: { color: '#388fd6' }
							},
							{ // 직접대출
								value: '${DL_CNT}'.replace(/,/g, ''),
								itemStyle: { color: '#b0d352' }
							},
							{ // 희망리턴
								value: '${HOPE_CNT}'.replace(/,/g, ''),
								itemStyle: { color: '#f5c208' }
							}
						]
					}]
				};

				M02Chart.clear();
				M02Chart.setOption(option);
				M02Chart.resize();
			}
			*/

			// 3. 당월 지급일자 별 지원사업 현황
			function M03ApiCall() {
				$.get(CONTEXT_PATH + "/main/dashboard/api/M03", {}, function(result){
					var xAxisData = [];
					var seriesData = [];

					for( var data_idx = 0 ; data_idx < result.data.length; data_idx++ ) {
						xAxisData.push(result.data[data_idx].SLCTN_YMD); // 지급일자
						seriesData.push(result.data[data_idx].SPRT_CNT); // 지급건수
					}

					var option = {
						tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
						legend: {},
						xAxis: {
							type: 'category',
							data: xAxisData,
							axisLine: { show: false }, // 라인숨김
							axisTick: { length: 0 },    // 라인숨김
							offset: 4
						},
						yAxis: {},
						grid: [ { top: '15%', bottom: '13%', left: '4.5%', right: '0%' } ], // 여백조정
						series: [{
							name: '지원사업수(건)',
							type: 'line',
							symbolSize: 13, // circle 크기
							label: { show: false, distance: 5, position: 'top' }, // label 위치
							itemStyle: { color: '#60A5DE' }, // 라인색상
							data: seriesData
					    }]
					};

					// console.log(option);

					M03Chart.clear();
					M03Chart.setOption(option);
					M03Chart.resize();
				},'json');
			}

			// 4. 월별 지원 사업 현황
			function M04ApiCall(biz_year) {
				if( biz_year == undefined ) {
					biz_year = $("#SPRT_BIZ_YEAR").val();
				}

				var param = {
					BIZ_YEAR : biz_year
				}
				$.get(CONTEXT_PATH + "/main/dashboard/api/M04", param, function(result){
					var data = result.data.list;
					var bizList = result.data.bizList;

					var xAxisData = [];
					var seriesData = [];

					var hope_cnt_arr  = []; // 희망리턴
					var dl_cnt_arr    = []; // 직접대출
					var pl_cnt_arr    = []; // 대리대출
					var edu_cnt_arr   = []; // 교육
					var cnslt_cnt_arr = []; // 컨설팅
					for( var data_idx = 0 ; data_idx < data.length; data_idx++ ) {

						hope_cnt_arr.push(data[data_idx].HOPE_CNT);
						dl_cnt_arr.push(data[data_idx].DL_CNT);
						pl_cnt_arr.push(data[data_idx].PL_CNT);
						edu_cnt_arr.push(data[data_idx].EDU_CNT);
						cnslt_cnt_arr.push(data[data_idx].CNSLT_CNT);

						xAxisData.push(data[data_idx].SPRT_YM+"월");
					}

					var legendArray = [];
					for( var idx = 0; idx < bizList.length; idx++ ) { // legend 생성
						legendArray.push({
							bizCd: bizList[idx].CMN_CD,
							bizNm: bizList[idx].CMN_CD_NM
						})
					}

					for( var legend_idx = 0; legend_idx < legendArray.length; legend_idx++ ) {

						var series = [];
						var legendNm = legendArray[legend_idx].bizNm;
						var legendCd = legendArray[legend_idx].bizCd;
						var itemColor = ""; // BarChart 색상
						if( legendCd == "03" ) { series = hope_cnt_arr;  itemColor = "#f5c208";} // 희망리턴
						if( legendCd == "04" ) { series = dl_cnt_arr;    itemColor = "#b0d352";} // 직접대출
						if( legendCd == "05" ) { series = pl_cnt_arr;    itemColor = "#388fd6";} // 대리대출
						if( legendCd == "01" ) { series = edu_cnt_arr;   itemColor = "#646fe5";} // 교육
						if( legendCd == "02" ) { series = cnslt_cnt_arr; itemColor = "#8c67d1";} // 컨설팅

						var seriesOption = {
							name: legendNm,
							data: series,
							stack: 'total',
							type: 'bar', label: { show: false, distance: 5, formatter: '{c}  {name|{a}}' },
							itemStyle: { color: itemColor }
						};

						seriesData.push(seriesOption);
					}

					var option = {
						tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
						legend: {/*top: 'bottom'*/},
						xAxis: {
							type: 'category',
				    		data: xAxisData,
							/*axisLabel: { rotate: 50 },*/
							nameTextStyle: { fontSize: 22 },
							axisLine: { show: false }, // 라인숨김
							axisTick: { length: 0 }    // 라인숨김
				  		},
					    yAxis: {type: 'value'},
						grid: [ { top: '15%', bottom: '10%', left: '8%', right: '0%' } ],
					    series: seriesData
					};

					// console.log(option);

					M04Chart.clear();
					M04Chart.setOption(option);
					M04Chart.resize();

				},'json');
			}

			// 5. 월별 지원금액 현황
			function M05ApiCall(biz_year) {
				if( biz_year == undefined ) {
					biz_year = $("#SPRT_BIZ_AMT_YEAR").val();
				}

				var param = {
					BIZ_YEAR : biz_year
				}
				$.get(CONTEXT_PATH + "/main/dashboard/api/M05", param, function(result){
					var data = result.data;

					var xAxisData = [];
					var seriesData = [];

					for( var data_idx = 0 ; data_idx < data.length; data_idx++ ) {
						xAxisData.push(data[data_idx].SPRT_YM+"월");
						seriesData.push(data[data_idx].GIVE_AMT);
					}

					var option = {
						tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
						legend: {},
						xAxis: {
							type: 'category',
							data: xAxisData,
							axisLine: { show: false }, // 라인숨김
							axisTick: { length: 0 },    // 라인숨김
							offset: 4
						},
						yAxis: {},
						grid: [ { top: '15%', bottom: '13%', left: '13%', right: '3%' } ], // 여백조정
						series: [{
							name: '지원금액(천원)',
							type: 'line',
							symbolSize: 13, // circle 크기
							label: { show: false, distance: 5, position: 'top' }, // label 위치
							itemStyle: { color: '#60A5DE' }, // 라인색상
							data: seriesData
					    }]
					};

					// console.log(option);

					M05Chart.clear();
					M05Chart.setOption(option);
					M05Chart.resize();

				},'json');
			}

			var gIsLCls = true;  // 현재 카테고리가 대분류인지 여부
			var gRowData = [];   // 파라미터로 사용하기 위한 RowData
			var gClsCd = "";     // 전역 분류코드

			// 6. 업종별 지원 사업 현황
			function M06ApiCall(clsCd) {
				var param = {
						KSIC_LCLS_CD: clsCd,
						DETAIL_YN: 'N'
					};

				if( clsCd == undefined ) {
					param = {};
					gIsLCls = true;
					gClsCd = "";
					M06breadcrumb();
				} else {
					gClsCd = clsCd;
					gIsLCls = false;
				}

				// console.log("clsCd ---- " + clsCd + ", param ---- " + param);
				$.get(CONTEXT_PATH + "/main/dashboard/api/M06", param, function(result){
					var data = result.data;

					gRowData = []; // 파라미터로 사용하기 위한 rowData
					var yAxisData = [];
					var seriesData = [];

					// 데이터 정렬을 위해 역순으로 처리
					for( var idx = data.length - 1 ; idx >= 0 ; idx-- ) {
						yAxisData.push(data[idx].CLS_CD + ". " + data[idx].CLS_NM); // 표준산업분류 분류코드 + 분류명
						seriesData.push(data[idx].SPRT_CNT);    // 업종별 지원건수
						gRowData.push(data[idx]);
					}

					var option = {
						tooltip: {
							trigger: 'axis', axisPointer: { type: 'shadow' }, order:'seriesDesc',
							//,  textStyle: { color: '#60A5DE' },
							formatter:function (params) {
								var tooltipLabel = params[0].axisValueLabel +"<br/><b>"+ formatNumber_addCom(params[0].data) + "건</b>";
								return tooltipLabel;
							}
						},
						xAxis: {},
						yAxis: { show: true, type: 'category', data: yAxisData,
							axisLine: { show: false, /* 라인숨김 */ lineStyle: {color: '#60A5DE' } /* labelText color */},
							axisTick: { length: 0 }, // 라인숨김
							offset: 10,
						},
						grid: [ { top: '0%', bottom: '5%', left: '32%', right: '4.5%' }],
						series: [{
				      		type: 'bar',
							itemStyle: { color: '#60A5DE' },
							label: {
								show: true,
								position: 'right',
						  		formatter:function (params) {
									return formatNumber_addCom(params.value) + "건";
								}
				      		},
					  		data: seriesData
				    	}]
					};

					M06Chart.clear();
					M06Chart.setOption(option);
					M06Chart.resize();

					// 차트 이벤트
					M06Chart.on('click', function (params) {
						// console.log(gRowData[params.dataIndex]);
						if( gIsLCls ) { // 대분류일때만 이벤트 발생
							M06breadcrumb(gRowData[params.dataIndex]);
							M06ApiCall(gRowData[params.dataIndex].CLS_CD);
						}
					});

				},'json');
			}

			// 업종별 지원 사업 현황 카테고리 경로
			function M06breadcrumb(barData) {
				var breadcrumbHtml = '<span class="text">표준산업분류 대분류 기준</span>';
				if( barData != undefined ) {
					breadcrumbHtml =  '<button class="btn btn-back" href="#" onclick="M06ApiCall()"><i class="fas fa-arrow-left"></i></button>';
					breadcrumbHtml += '<button class="text" onclick="M06ApiCall()">표준산업분류 대분류 기준</button>>';
					breadcrumbHtml += '<span class="text">'+ barData.CLS_CD + '.'+ barData.CLS_NM +'</span>';
				}

				$(".chart-breadcrumb1.chart-back").html(
					breadcrumbHtml
				);
			}

			// 7. 지역별 지원사업 현황
			function M07ApiCall() {
				var param = {};
				$.get(CONTEXT_PATH + "/main/dashboard/api/M07", param, function(result){
					for( idx = 0; idx < result.data.length; idx++ ) {
						// var rgn_nm = result.data[idx].RGN_NM;
						var rgn_cd = result.data[idx].RGN_CD;
						var give_cnt = formatNumber_addCom(result.data[idx].GIVE_CNT);
						$("#marker-" + rgn_cd +" .count").text(give_cnt);
					}
				},'json');
			}

			// 1. 지원사업 진행 현황 상세보기
			function M01DetailPopup() {
				// ajaxLoad('#modal1', CONTEXT_PATH + '/main/dashboard/popup/M01', {}, function() { Modal_s('#modal1'); });
				detailPopup('M01', null);
			}

			// 지급일자 별 지원사업 현황 상세보기
			function M03DetailPopup() {
				detailPopup('M03', null);
			}

			// 4. 월별 지원 사업 현황
			function M04DetailPopup() {
				var param = {
					BIZ_YEAR: $('#SPRT_BIZ_YEAR').val() // 월별 지원 사업 현황 사업년도
				};
				detailPopup('M04', param);
			}

			// 5. 월별 지원금액 현황
			function M05DetailPopup() {
				var param = {
					BIZ_YEAR: $('#SPRT_BIZ_AMT_YEAR').val() // 월별 지원 금액 현황 사업년도
				};
				detailPopup('M05', param);
			}

			// 6. 업종별 지원 사업 현황
			function M06DetailPopup() {
				var param = {
					clsCd: gClsCd // 분류코드
				};
				detailPopup('M06', param);
			}

			// 7. 지역별 지원사업 현황
			function M07DetailPopup() {
				detailPopup('M07', null);
			}

			function detailPopup(id, param) {
				ajaxLoad('#modal1', CONTEXT_PATH + '/main/dashboard/popup/' + id, param, function() { Modal_s('#modal1'); });
			}

			function ModalClose() {
				Modal_h('#modal1'); // hide
			}

		</script>
