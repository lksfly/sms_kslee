<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!DOCTYPE html>
<html lang="en">
	<body onload="document.logoutForm.submit();">
		<form name="logoutForm" action="<c:url value="/j_spring_security_logout" />" method="POST">
			<!--input type="hidden" name="<c:out value="${_csrf.parameterName}" />" value="<c:out value="${_csrf.token}" />" /-->
		</form>
	</body>
</html>
