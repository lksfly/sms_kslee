var searchData = new function() {};

/**
 * parameter를 JSON형식으로도 받을 수 있는 Ajax 함수
 * @param url
 * @param param
 * @param callback
 */
searchData.ajaxJson = function ( url, param, callback ) {
	$.post(url, param, callback, "json");
};
searchData.ajaxFile = function ( url, param, callback ) {
	if ($.isFunction(param)) {
		callback = param;
		param = undefined;
	}
	
	$.ajax({
		url: url,
		type: "post",
		data: param,
		enctype: 'multipart/form-data',
		processData: false,  // tell jQuery not to process the data
		contentType: false,  // tell jQuery not to set contentType
		success: callback,
		dataType: "json"
	});
};

/**
 * error.jsp를 출력하기 위해 $(selector).load(url) 대신 $.ajax()를 사용한다.
 * 
 * @param selector
 * @param url
 * @param param
 * @param callback
 * @returns
 */
var ajaxLoad = function(selector, url, param, callback) {
	if ($.isFunction(param)) {
		callback = param;
		param = undefined;
	}
	
	openLoading(); // open loading
	
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		data: param,
		dataType: 'html',
		complete: function(jqXHR, status) { // 성공/실패와 상관없이 항상 마지막에 실행
			$(selector).html(jqXHR.responseText);
			if ('success' == status) {
				if ($.isFunction(callback)) callback();
			}
			closeLoading(); // close loading
		}
	});
}

/**
 * jQuery.ajax() 전역 설정
 */
$.ajaxSetup({
	async: true, // (default: true)
	cache: false, // (default: true)
	headers: { "cache-control": "no-cache" },
	xhrFields: {
		withCredentials: true // 크로스 도메인 요청시, 세션 고정 (for CORS Reqeusts)
	}
});
$(document).ajaxStart(function() {
	openLoading();
});
$(document).ajaxStop(function() {
	closeLoading();
});
$(document).ajaxError(function(event, jqXHR, settings, thrownError) {
	/*
	 * 시스템 오류를 공통으로 처리하기 위해, HTTP 상태 코드를 이용한다.
	 * ex) 400, 401, 403, 404, 500
	 */ 
	var httpStatusCode = jqXHR.status; // HTTP 상태 코드
	
	window.console && window.console.error("ajaxError() " + httpStatusCode + " (" + thrownError + ") " + settings.dataType);
	
	if (settings.dataType == "json") {
		var msg = "처리중 에러가 발생하였습니다.";
		if ((jqXHR.getResponseHeader("content-type") || "").indexOf("json") > -1) {
			var result = $.parseJSON(jqXHR.responseText); // from error.jsp
			if (result && result.msg) { msg = result.msg; }
		}
		alert(msg); // alert
		
		if (/^http/.test(settings.url)) { // 외부요청 (http, https)
			return; // ex) 외부 지도 API 인증 오류
		}
		if (httpStatusCode == 401) { // 401 (Unauthorized) - 인증 오류인 경우, 로그인 페이지로 이동한다.
			window.location.href = CONTEXT_PATH + "/"; // goRoot
			return;
		}
	}
});
