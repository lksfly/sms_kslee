<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/views/common/head.jsp" %>
	</head>
	<body>
		<div class="login-wrapper">
			<h2>Welcome to<img src="<c:url value="/assets/images/logo/semas.svg" />" alt="" class="login-logo" /></h2>
			<div class="login-discription"><c:out value="${SESSION_SITE_INFO.siteName}" />에 오신 것을 환영합니다.</div>
			<h4 class="">로그인을 진행해 주세요.</h4>
			<div class="textinput textinput-S2">
				<div class="textinput-input">
					<input type="text" id="login-id" maxlength="30" placeholder=" " required />
					<div class="title">아이디</div>
					<div class="placeholder">아이디를 입력해 주세요.</div>
				</div>
			</div>
			<div class="textinput textinput-S2">
				<div class="textinput-input">
					<input type="password" id="login-pw" maxlength="30" placeholder=" " required />
					<div class="title">비밀번호</div>
					<div class="placeholder">비밀번호를 입력해 주세요.</div>
				</div>
			</div>
			<button type="button" class="btn login-btn" id="login-btn"><span>로그인</span></button><!-- disabled -->
		</div>
		
		<!--
			head와 body의 내용을 전부다 불러온 후에, 스크립트를 실행한다.
		-->
		<script type="text/javascript">
			$(document).ready(function() {
				main_setCommonEvent('body'); // 공통 이벤트 설정
				
				var $loginBtn = $('#login-btn');
				// 로그인
				$('#login-id, #login-pw').on('keypress', function() {
					if (event.keyCode === 13) { // 13(Enter)
						login();
					}
				}).on('keyup', function() {
					if ($bizNo.val().length) {
						$loginBtn.addClass('on');
					} else {
						$loginBtn.removeClass('on');
					}
				});
				$loginBtn.on('click', function() {
					login();
				});
			});
			
			// 아이디/패스워드 로그인
			function login() {
				var $loginId = $('#login-id');
				var $loginPw = $('#login-pw');
				
				if (!$loginId.val()) {
					$loginId.val('').focus();
					return;
				}
				if (!$loginPw.val()) {
					$loginPw.focus();
					return;
				}
				$loginPw.focus();
				
				$.post(CONTEXT_PATH + '/j_spring_security_login', {
					loginId: $loginId.val(),
					loginPw: $loginPw.val()
				}, function(result) {
					if (result.result === true) {
						window.location.href = CONTEXT_PATH + '/main'; // goHome
					} else {
						alert(result.msg); // 로그인 실패
						$loginPw.focus();
					}
				}, 'json');
			}
		</script>
	</body>
</html>
