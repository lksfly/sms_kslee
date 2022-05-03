<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- BEGIN HEAD -->
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		
		<title><c:out value="${SESSION_SITE_INFO.siteName}" /></title>
		
		<!-- favicon -->
		<link rel="apple-touch-icon" sizes="57x57" href="<c:url value="/assets/images/favicon/apple-icon-57x57.png" />" />
		<link rel="apple-touch-icon" sizes="60x60" href="<c:url value="/assets/images/favicon/apple-icon-60x60.png" />" />
		<link rel="icon" type="image/png" sizes="96x96" href="<c:url value="/assets/images/favicon/android-icon-96x96.png" />" />
		<link rel="icon" type="image/png" sizes="32x32" href="<c:url value="/assets/images/favicon/favicon-32x32.png" />" />
		<link rel="shortcut icon" href="<c:url value="/assets/images/favicon/favicon.ico" />" />
		<link rel="icon" href="<c:url value="/assets/images/favicon/favicon.ico" />" />
		
		<!-- CSS -->
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/plugin/normalize/normalize.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/plugin/jquery-ui/jquery-ui.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/plugin/fontawesome/css/all.min.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/plugin/dataTables/datatables.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/plugin/dataTables/custom_datatables.css" />" />
		<!-- [BZD] -->
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/main.css" />?_=<%=_curTime%>" />
		
		<!-- Required Jqurey -->
		<script type="text/javascript" src="<c:url value="/assets/plugin/jquery/jquery-3.5.1.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/assets/plugin/jquery-ui/jquery-ui.min.js" />"></script>
		<!-- ECharts -->
		<script type="text/javascript" src="<c:url value="/assets/plugin/echarts/echarts.min.js" />"></script>
		<!-- DataTables -->
		<script type="text/javascript" src="<c:url value="/assets/plugin/dataTables/datatables.min.js" />"></script>
		
		<!-- [BZD] -->
		<script type="text/javascript">
			/* global variables */
			var CONTEXT_PATH = '<%=CONTEXT_PATH%>';
			
			var EXCEL_DOWNLOAD_MAXCOUNT = <%=EXCEL_DOWNLOAD_MAXCOUNT%>; // 엑셀 다운로드 건수 제한
			
			/* jquery ui - datepicker :: 날짜 출력폼 설정 */
			var DATEPICKER_SETTINGS = {
				dateFormat: "yy-mm-dd",    // 날짜 출력폼 설정
				
				numberOfMonths: 1,
				buttonText: "Choose",
				showMonthAfterYear: true,
				changeYear: true,
				yearSuffix: '<span>년</span>',
				
				buttonImageOnly: true,
				changeMonth: true,
				monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				dayNamesMin: ['일','월', '화', '수', '목', '금', '토' ],
				
				prevText: '이전',
				nextText: '다음'
			};
			
			/* DataTables */
			$.extend(true, $.fn.dataTable.defaults, {
				language: {
					emptyTable: '조회된 데이터가 없습니다.',
					lengthMenu: '페이지당 _MENU_ 개씩 보기',
					info: '검색결과 총 _MAX_건',
					infoEmpty: '검색결과 총 _MAX_건',
					infoFiltered: '',
					search: '',
					searchPlaceholder: '검색어를 입력해 주세요.',
					zeroRecords: '일치하는 데이터가 없습니다.',
					loadingRecords: '로딩중...',
					processing: '잠시만 기다려 주세요...',
					paginate: {
						previous: '<i class="fas fa-angle-left"></i>',
						next: '<i class="fas fa-angle-right"></i>'
					}
				}
			});
			function dataTable_getSortName($dataTable) { // DataTable 소팅기준
				var order = $dataTable.order();
				if (order && order[0]) {
					order = (order[0] instanceof Array) ? order[0] : order;
					return $dataTable.settings().init().columns[order[0]].data; // sortName
				} else {
					return null;
				}
			}
			function dataTable_getSortType($dataTable) { // DataTable 소팅유형 (asc, desc)
				var order = $dataTable.order();
				if (order && order[0]) {
					order = (order[0] instanceof Array) ? order[0] : order;
					return order[1]; // sortType
				} else {
					return null;
				}
			}
		</script>
		<script type="text/javascript" src="<c:url value="/assets/js/common/common.js" />?_=<%=_curTime%>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/common/common_ModalUtil.js" />?_=<%=_curTime%>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/common/data_ajax.js" />?_=<%=_curTime%>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/common/filedownload.js" />?_=<%=_curTime%>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/main.js" />?_=<%=_curTime%>"></script>
<!-- END HEAD -->
