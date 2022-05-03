<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jsp" %>

<!--<div id="" class="modal">-->
<c:if test="${info.rsltCd == 'S'}">
		<div class="modal-content modal-s" style="height: 343px;">
</c:if><c:if test="${info.rsltCd != 'S'}">
		<div class="modal-content modal-l">
</c:if>
			<div class="card body-scroll" style="height: 100%;">
				<div class="card-header row">
					<h1 class="content-title2">처리내용 상세</h1>
					<div class="spacer"></div>
					<button type="button" class="btn btn-close btn-text-main nomargin" onclick="btchLogInfoModal_close();"><i class="fas fa-times"></i></button>
				</div>
				<div id="btchLogInfoModal-rsltCn" class="card-body">
					<c:out value="${info.rsltCn}" escapeXml="false" />
				</div>
				<div class="card-footer center">
					<button type="button" class="btn btn-ml w-big btn-reversal-off" onclick="btchLogInfoModal_close();">닫기</button>
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
				
			});
		</script>
