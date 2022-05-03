<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/views/common/head.jsp" %>
	</head>
	<body>
		<form name="mainForm" method="GET" action="<c:url value="/main" />"><!-- // goHome -->
			<input type="hidden" name="menu" />
			<input type="hidden" name="url" />
			<input type="hidden" name="param" />
		</form>
		<div class="gnb-wrapper">
			<header class="gnb-header fixed">
				<a href="<c:url value="/main" />" class="header-logo"><img src="<c:url value="/assets/images/logo/semas.svg" />" /></a><!-- // goHome -->
				<div class="spacer"></div>
				<div class="header-end">
					<div class="user-info"><c:out value="${loginInfo.name}" /></div>
					<button type="button" class="btn btn-logout" onclick="logout();"><i class="fas fa-sign-out-alt"></i>LogOut</button>
				</div>
			</header>
			<div class="gnb-body">
				<nav class="gnb-nav fixed">
					<ul class="gnb-nav-list">
						<li id="main-menu-dashboard" class="gnb-nab-entity"><!-- class="active" -->
							<a href="javascript:main_clickMenu('dashboard');" class="menu-title">Dashboard</a>
						</li>
						<li id="main-menu-sprtBizStts" class="gnb-nab-entity">
							<a href="javascript:main_clickMenu('sprtBizStts');" class="menu-title">지원사업현황</a>
						</li>
						<li id="main-menu-bnftBizMstr" class="gnb-nab-entity">
							<a href="javascript:main_clickMenu('bnftBizMstr');" class="menu-title">기업정보</a>
						</li>
						<li id="main-menu-bnftPsnlMstr" class="gnb-nab-entity">
							<a href="javascript:main_clickMenu('bnftPsnlMstr');" class="menu-title">신청자 정보</a>
						</li>
<c:if test="${loginInfo.loginType == 'AD1' || loginInfo.loginType == 'AD2'}">
						<li id="main-menu-cntrSprtStts" class="gnb-nab-entity">
							<a href="javascript:main_clickMenu('cntrSprtStts');" class="menu-title">센터지원현황</a>
						</li>
</c:if>
<c:if test="${loginInfo.loginType == 'AD1'}">
						<li class="gnb-nab-group">
							<a href="#" class="menu-title">Data Batch</a>
							<ul class="gnb-nav-sub-list" style="display: none;">
								<li id="main-menu-btchMng"><a href="javascript:main_clickMenu('btchMng');">Batch 관리</a></li><!-- class="active" -->
								<li id="main-menu-btchLog"><a href="javascript:main_clickMenu('btchLog');">Batch 로그</a></li>
							</ul>
						</li>
</c:if>
					</ul>
				</nav>
				<article class="content-wrapper">
				<!--
					<section class="container-fluid">
					</section>
				-->
				</article>
			</div>
		</div>
		
		<!--
			모달 팝업을 위한 div - 2단계 모달(모달에 모달)인 경우, modal2 div를 사용한다.
		-->
		<div id="modal1" class="modal"></div>
		<div id="modal2" class="modal"></div>
		
		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			$(document).ready(function() {
				$('.gnb-nab-group .menu-title').click(function () {
					var $group = $(this).closest('.gnb-nab-group');
					if ($group.hasClass('active')) {
						$group.removeClass('active').children('.gnb-nav-sub-list').slideUp();
					} else {
						$group.addClass('active').children('.gnb-nav-sub-list').slideDown();
					}
				});
				
				main_onload();
			});
			
			function main_onload() {
				var defaultMenu = 'dashboard';
				
				var menu = '<c:out value="${param.menu}" />' || defaultMenu;
				var url = '<c:out value="${param.url}" />';
				var param = {};
				<c:if test="${not empty param.param}">
					param = <c:out value="${param.param}" escapeXml="false" />;
				</c:if>
				
				if (!url) { // url 값이 없으면, 메뉴의 기본 URL 설정
					url = _main_getMenuUrl(menu, param);
				}
				document.mainForm.menu.value = menu; // 메뉴 저장 (내용만 변경시, 메뉴를 유지하기 위해)
				$('#main-menu-' + menu).addClass('active') // 메뉴 활성화
						.closest('.gnb-nab-group').addClass('active') // 하위메뉴인 경우, 상위메뉴 활성화
						.children('.gnb-nav-sub-list').show();        // 하위메뉴인 경우, 메뉴 목록 오픈
				
				ajaxLoad('.content-wrapper', url, param); /* $(selector).load(url) 대신 사용 */
			}
			
			function _main_getMenuUrl(menu, param) {
				var url = null;
				switch (menu) {
					case 'dashboard':    url = '/main/dashboard';    break; // 대시보드
					case 'sprtBizStts':  url = '/sprt/sprtBizStts';  break; // 지원사업현황
					case 'bnftBizMstr':  url = '/bnft/bnftBizMstr';  break; // 기업마스터
					case 'bnftPsnlMstr': url = '/bnft/bnftPsnlMstr'; break; // 개인마스터
					case 'cntrSprtStts': url = '/cntr/cntrSprtStts'; break; // 센터지원현황
					case 'btchMng':      url = '/btch/btchMng';      break; // Data Batch > Batch 관리
					case 'btchLog':      url = '/btch/btchLog';      break; // Data Batch > Batch 로그
				}
				return CONTEXT_PATH + url;
			}
			function _main_formSubmit(menu, url, param) {
				if (menu)  { document.mainForm.menu.value  = menu }
				if (url)   { document.mainForm.url.value   = url }
				if (param) { document.mainForm.param.value = JSON.stringify(param) }
				document.mainForm.submit();
			}
			function main_clickMenu(menu, param) { // 메뉴 이동시 사용
				_main_formSubmit(menu, null, param);
			}
			function main_loadContent(url, param) { // 내용만 변경시 사용
				_main_formSubmit(null, url, param);
			}
		</script>
	</body>
</html>
