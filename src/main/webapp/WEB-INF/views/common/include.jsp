<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*" %>
<%@ page import="java.util.regex.*" %>
<%@ page import="kr.bizdata.core.config.Config" %>

<%
	response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "Thu, 01 Dec 1994 16:00:00 GMT");
	
	String CONTEXT_PATH = "/".equals(request.getContextPath()) ? "" : request.getContextPath();
	
	long _curTime = new java.util.Date().getTime();
	
	int EXCEL_DOWNLOAD_MAXCOUNT = Config.getInt("bzd.excel.download.maxCount", 50000); // 엑셀 다운로드 건수 제한
%>
