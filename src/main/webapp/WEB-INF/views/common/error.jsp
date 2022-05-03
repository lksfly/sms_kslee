<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="org.springframework.web.util.NestedServletException" %>

<%@ page import="kr.bizdata.core.exception.BzdException" %>
<%@ page import="kr.bizdata.core.model.RestResult" %>
<%@ page import="kr.bizdata.core.util.JsonUtil" %>

<%!Logger logger = LoggerFactory.getLogger(getClass());%>
<%
	/**
	 * HTTP Request 오류 처리방식
	 * 
	 * (1) HTTP 상태 코드를 사용한다.
	 * (2) 서버에서 발생하는 모든 오류는 error.jsp에서 처리한다.
	 *     - 설정 위치:
	 *         web.xml 파일의 <error-page>
	 *     - json 요청:
	 *         json 데이터를 반환한다.
	 *     - html 요청:
	 *         인증오류(401)인 경우, 로그인 화면으로 이동한다.
	 *         그 외, 화면에 오류 메시지를 표시한다.
	 * (3) 오류를 발생시키는 방법
	 *     - 특정 코드:
	 *         ex) response.sendError(code, msg);
	 *     - 500 코드:
	 *         ex) throw new BzdException(msg);
	 */
	int httpStatusCode = response.getStatus(); // HTTP 상태 코드
	
	String errorMsg = ""; // 화면에 표시할 오류 메시지
	
	switch (httpStatusCode) {
	case 400: // 400 (Bad Request)
	case 404: // 404 (Not Found)
		errorMsg = "요청하신 페이지를 찾을 수 없습니다.";
		break;
	case 401: // 401 (Unauthorized)
		errorMsg = (String) request.getAttribute("javax.servlet.error.message"); // from response.sendError(401, msg);
		if (StringUtils.isEmpty(errorMsg)) {
			errorMsg = "세션이 유효하지 않습니다.";
		}
		break;
	case 403: // 403 (Forbidden)
		errorMsg = "권한이 없습니다."; // from intercept-url of spring-security.xml
		break;
	default:  // 500 (Internal Server Error)
		errorMsg = "시스템 오류가 발생했습니다.\n운영팀에 문의해 주시기 바랍니다.";
		Throwable cause = (Throwable) request.getAttribute("javax.servlet.error.exception"); // from error-code 500 of web.xml
		if (cause != null) {
			logger.error(cause.getMessage(), cause);
			if (cause instanceof NestedServletException) {
				cause = cause.getCause();
			}
			if (cause instanceof BzdException && StringUtils.isNotEmpty(cause.getMessage())) {
				errorMsg = cause.getMessage(); // BzdException 메시지만 화면에 표시한다.
			}
		}
		break;
	}
	logger.error(errorMsg.replace('\n', ' ') + " (" + request.getAttribute("javax.servlet.error.message") + ") " + httpStatusCode);
	
	String accept = request.getHeader("Accept");
	
	if (accept != null && accept.contains("application/json")) { // Rest 요청인 경우, JSON 데이터를 반환한다.
		response.setContentType("application/json; charset=utf-8");
		out.print(JsonUtil.toString(new RestResult(false, errorMsg, null)));
		return;
	}
	
	if (httpStatusCode == 401) { // 401 (Unauthorized) - 인증 오류인 경우, 로그인 페이지로 이동한다.
		response.setContentType("text/html; charset=UTF-8");
		out.println("<html>");
		out.println("	<head>");
		out.println("		<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
		out.println("		<script language=\"JavaScript\">");
		out.println("			alert('" + errorMsg + "'); "); // alert
		out.println("			window.location.href = '" + request.getContextPath() + "/" + "'; "); // goRoot
		out.println("		</script>");
		out.println("	</head>");
		out.println("</html>");
		return;
	}
	
	errorMsg = StringEscapeUtils.escapeXml11(errorMsg);
	errorMsg = StringUtils.replace(errorMsg, "\n", "<br />");
%>

<!DOCTYPE html>
<html lang="en">
	<head>
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
		<!-- [BZD] -->
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/main.css" />?_=<%=_curTime%>" />
		
		<%--
			error.jsp 내에서는 javascript를 load하면 안 된다.
			
				ex) modal div에 error.jsp가 load될 경우, jquery.js가 같이 load되면, 부모의 jquery.js와 충돌하여 오류가 발생한다.
		--%>
	</head>
	<body>
		<div class="error-wrapper">
			<div class="card-error">
				<i class="fas fa-exclamation-circle"></i>
				<h2><%=errorMsg%></h2>
				<h3>서비스 이용에 불편을 드려 죄송합니다.</h3>
				<button type="button" class="btn error-btn" onclick="window.location.href = '<c:url value="/main" />';">홈 화면으로 이동</button>
			</div>
		</div>
	</body>
</html>
