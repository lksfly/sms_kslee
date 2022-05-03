var isDebugEnabled = true;

/**
 * 콘솔에 디버그 메시지를 출력한다. ex) logDebug(JSON.stringify(o));
 */
function logDebug(logMsg) {
	if (!isDebugEnabled) {
		return;
	}
	if (typeof(console) != 'undefined' && console.log) {
		console.log(logMsg);
	}
}
/**
 * 콘솔에 오류 메시지를 출력한다.
 */
function logError(logMsg) {
	if (typeof(console) != 'undefined' && console.error) {
		console.error(logMsg);
	}
}

/**
 * 숫자 3자리 마다 콤마를 찍는다.
 */
function formatNumber_addCom(v) {
    if (NumberUtil.isNotNumber(v)) {
        return '';
    }
    v = (v + '').split('.');
    return v[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,') + (v.length > 1 ? '.' + v[1] : '');
}
/**
 * 숫자 3자리 마다 콤마를 찍고, 실수인 경우 소수점 n 자리까지 표시한다. (반올림)
 */
function formatNumber_addCom_toFixed(v, n) {
	v = parseFloat(v).toFixed(n);
	v = parseFloat(v); // 소수점 아래 0 제거
	return formatNumber_addCom(v);
	
//	v = parseFloat(v).toFixed(n);
//	v = formatNumber_addCom(v);
//	return (v.indexOf('.') == -1) ? v : v.replace(/\.?0*$/, ''); // 소수점 아래 0 제거
}
/**
 * 금액을 만원 단위의 문자열로 표시한다. (반올림) ex) 125000000 -> 1억 2,500만원
 */
function formatNumber_addCom_won(v) {
	
	var isPlus = true;
	if (v < 0) {
		isPlus = false;
		v = -v;
	}
	
	var a = Math.round(parseFloat(v) / 10000); // 단위: 만 (반올림)
	var b = 0;
	var c = 0;
	if (a >= 10000) {
		b = Math.floor(a / 10000); // 단위: 억 (내림)
		a = a % 10000;
	}
	if (b >= 10000) {
		c = Math.floor(b / 10000); // 단위: 조 (내림)
		b = b % 10000;
	}
	
	var str = (isPlus ? '' : '-');
	if (c > 0) {
		str += formatNumber_addCom(c) + '조';
	}
	if (b > 0) {
		if (str.length > 0) str += ' ';
		str += formatNumber_addCom(b) + '억';
	}
	if (a > 0) {
		if (str.length > 0) str += ' ';
		str += formatNumber_addCom(a) + '만';
	}
	if (str.length == 0) {
		str = '0';
	}
	str += '원';
	
	return str;
}

/**
 * 로그아웃
 */
function logout() {
	window.location.href = CONTEXT_PATH + '/logout'; // 로그아웃
}

/**
 * 버튼 중복클릭 방지용 modal dialog
 */
var loadingDialog = {
	id: 'loading-dialog',
	openCnt: 0,
	open: function(msg) {
		++this.openCnt;
		//logDebug('loadingDialog.open() ' + this.openCnt);
		if (this.openCnt != 1) {
			return;
		}
		//logDebug('loadingDialog.open() open!! ' + this.openCnt);
		
		if (typeof HoldOn != 'undefined') {
			HoldOn.open({
				message: msg || '',
				theme: 'sk-rect', // sk-rect/sk-bounce/sk-folding-cube/sk-circle/sk-dot/sk-bounce/sk-falding-circle/sk-cub-grid/custom
				// content: '<b style="color:#fff;">hello</b>', // theme를 'custom'으로 설정해야 함.
				backgroundColor: 'rgba(0, 0, 0, 0.8)',
				textColor: 'white'
			});
		} else if (typeof $.fn.dialog != 'undefined') {
			var div = $('#' + this.id);
			if (div.length == 0) {
				var str = '<div id="' + this.id + '" style="display: none; padding: 5px;">'
						+ '    <div style="text-align: center; padding: 10px;">'
						+ '        <img src="' + CONTEXT_PATH + '/assets/images/loading-rect.gif" width="64" />';
				if (msg) {
					str +='        <br><span>' + msg + '</span>';
				}
					str +='    </div>'
						+ '</div>';
				
				div = $(str).appendTo('body');
			}
			div.dialog({
				modal: true,
				resizable: false,
				width: 'auto',
				height: 'auto',
				close: function() {
					$(this).dialog('destroy');
				},
				closeOnEscape: false
			});
			div.parents('.ui-dialog:first').css({ background: 'none', border: '0px' }); // 배경 투명하게
			div.parents('.ui-dialog:first').find('.ui-dialog-titlebar').remove();
		}
	},
	set: function(msg) {
		if (typeof HoldOn != 'undefined') {
			$('#holdon-message').html(msg);
		} else if (typeof $.fn.dialog != 'undefined') {
			// TODO
		}
	},
	close: function() {
		--this.openCnt;
		//logDebug('loadingDialog.close() ' + this.openCnt);
		if (this.openCnt >= 1) {
			return;
		}
		//logDebug('loadingDialog.close() close!! ' + this.openCnt);
		
		this.openCnt = 0; // 0으로 초기화
		
		if (typeof HoldOn != 'undefined') {
			HoldOn.close();
		} else if (typeof $.fn.dialog != 'undefined') {
			var div = $('#' + this.id);
			if (div.length == 0 || !div.is(':visible')) {
				return;
			}
			try {
				div.dialog('close');
			} catch (e) {
				logError('loadingDialog.close() ' + e); // ignore
			}
		}
	}
};

/**
 * 버튼 중복클릭 방지용 modal dialog
 */
var loadingIndicator = {
	id: 'loadingIndicator',
	openCnt: 0,
	open: function(msg) {
		++this.openCnt;
		//logDebug('loadingIndicator.open() ' + this.openCnt);
		if (this.openCnt != 1) {
			return;
		}
		//logDebug('loadingIndicator.open() open!! ' + this.openCnt);
		
		var $div = $('#' + this.id);
		if ($div.length == 0) {
			var str = '<!-- 로딩인디게이터 -->'
					+ '<div id="' + this.id + '" class="loadingIndicator">'
					+ '    <span class="ball"></span>'
					+ '    <span class="ball"></span>'
					+ '    <span class="ball"></span>';
			if (msg) {
				str +='    ' + msg;
			}
				str +='</div>';
			
			$div = $(str).appendTo('body');
		}
		
		$div.show(); // 열기
	},
	set: function(msg) {
		// TODO
	},
	close: function() {
		--this.openCnt;
		//logDebug('loadingIndicator.close() ' + this.openCnt);
		if (this.openCnt >= 1) {
			return;
		}
		//logDebug('loadingIndicator.close() close!! ' + this.openCnt);
		
		this.openCnt = 0; // 0으로 초기화
		
		var $div = $('#' + this.id);
		if ($div.length == 0 || !$div.is(':visible')) {
			return;
		}
		
		$div.hide(); // 닫기
	}
};

function openLoading(msg) {
	loadingIndicator.open(msg);
}
function setLoading(msg) {
	loadingIndicator.set(msg);
}
function closeLoading() {
	loadingIndicator.close();
}

var DateUtil = {
	addYears: function(date, amount) {
		var d = new Date(date);
		d.setFullYear(d.getFullYear() + amount);
		return d;
	},
	addMonths: function(date, amount) {
		var d = new Date(date);
		d.setMonth(d.getMonth() + amount);
		return d;
	},
	addDays: function(date, amount) {
		var d = new Date(date);
		d.setDate(d.getDate() + amount);
		return d;
	},
	/**
	 * 문자열을 Date 객체로 변환한다.
	 * 
	 * src 예시
	 *   - '2018-01-17 15:02:44'
	 * pattern 예시
	 *   - yyyy-MM-dd HH:mm:ss.SSS
	 */
	parse: function(src, pattern) {
		if (typeof src != 'string' || typeof pattern != 'string') {
			return null;
		}
		
		var y = '';
		var M = '';
		var d = '';
		var H = '';
		var m = '';
		var s = '';
		var S = '';
		
		for (var i = 0; i < src.length && i < pattern.length; i++) {
			switch (pattern[i]) {
				case 'y': y += src[i]; break;
				case 'M': M += src[i]; break;
				case 'd': d += src[i]; break;
				case 'H': H += src[i]; break;
				case 'm': m += src[i]; break;
				case 's': s += src[i]; break;
				case 'S': S += src[i]; break;
			}
		}
		M = parseInt(M) - 1; // 월 - 1
		d = d || '1'; // 값이 없으면, 1일
		
		var date = new Date(y, M, d, H, m, s, S);
		//alert(this.format(date, pattern));
		return date;
	},
	/**
	 * Date 객체를 문자열로 변환한다.
	 * 
	 * pattern 예시
	 *   - yyyy-MM-dd HH:mm:ss
	 *   - yyyy년 M월 d일 H시 m분 s초
	 */
	format: function(date, pattern, defaultValue) {
		if (typeof date == 'number') {
			date = new Date(date);
		}
		if (!(date instanceof Date) || typeof pattern != 'string') {
			return defaultValue ? defaultValue : null;
		}
		
		var y = date.getFullYear();
		var M = date.getMonth() + 1; // 월 + 1
		var d = date.getDate();
		var H = date.getHours();
		var m = date.getMinutes();
		var s = date.getSeconds();
		var S = date.getMilliseconds();
		
		return pattern
			.replace('yyyy', y).replace('yy', y % 100)
			.replace('MM', this.s2d(M)).replace('M', M)
			.replace('dd', this.s2d(d)).replace('d', d)
			.replace('HH', this.s2d(H)).replace('H', H)
			.replace('mm', this.s2d(m)).replace('m', m)
			.replace('ss', this.s2d(s)).replace('s', s)
			.replace('SSS', S);
	},
	/*getNewDate: function(value) {
		return value instanceof Date ? value : new Date(typeof value == 'string' ? value.replace(/-/g, '/') : value);
	},*/
	s2d: function(value) {
		return (value < 10) ? '0' + value : value;
	},
	getDaysInMonth: function(obj) { // 해당 월의 마지막 날짜 (28 ~ 31)
		var y = (obj instanceof Date) ? obj.getFullYear()  : obj.toString().substring(0, 4); // 연
		var m = (obj instanceof Date) ? obj.getMonth() + 1 : obj.toString().substring(4, 6); // 월
		var days = $.datepicker._getDaysInMonth(y, parseInt(m) - 1); // [주의] 인자 전달시, 월 값에서 1 빼기
		return days;
	},
	getQuarter: function(obj) { // 해당 월의 분기 (1 ~ 4)
		var m = (obj instanceof Date) ? obj.getMonth() + 1 : parseInt(obj); // 월
		return Math.floor((m + 2) / 3); // 내림
	},
	getYears: function(stD, enD) { // 연 목록을 구한다.
		var stY = parseInt(stD.toString().substring(0, 4)); // yyyy
		var enY = parseInt(enD.toString().substring(0, 4));
		var arr = [];
		for (var i = stY; i <= enY; i++) {
			arr.push(i.toString());
		}
		//logDebug('getYears(' + stD + ', ' + enD + ') = ' + JSON.stringify(arr));
		return arr; // 'yyyy'
	},
	getMonths: function(stD, enD, type) { // 월 목록을 구한다.
		var stM = parseInt(stD.toString().substring(0, 6)); // yyyyMM
		var enM = parseInt(enD.toString().substring(0, 6));
		var arr = [];
		for (var i = stM; i <= enM; i++) {
			var m = i % 100;
			if (m < 1 || m > 12) {
				continue; // 월(1 ~ 12) 값만 사용한다.
			}
			if (type == 'quarterStart') { // 분기시작월(1, 4, 7, 10)
				m = DateUtil.getQuarter(m) * 3 - 2;
			} else if (type == 'quarterEnd') { // 분기종료월(1, 4, 7, 10)
				m = DateUtil.getQuarter(m) * 3;
			}
			var month = i.toString().substring(0, 4) + (m < 10 ? '0' + m : m);
			if (arr[arr.length - 1] != month) { // 중복 제거
				arr.push(month);
			}
		}
		//logDebug('getMonths(' + stD + ', ' + enD + ', ' + type + ') = ' + JSON.stringify(arr));
		return arr; // 'yyyyMM'
	}
};

var NumberUtil = {
	isNumber: function(v) {
		return !this.isNotNumber(v);
	},
	isNotNumber: function(v) {
		return v === null || v === '' || isNaN(v);
	},
	/**
	 * 빼기
	 */
	subtract: function(v1, v2) {
		if (this.isNotNumber(v1) || this.isNotNumber(v2)) {
			return null;
		}
		return parseFloat(v1) - parseFloat(v2);
	},
	/**
	 * 곱하기
	 */
	multiply: function(v1, v2) {
		if (this.isNotNumber(v1) || this.isNotNumber(v2)) {
			return null;
		}
		return parseFloat(v1) * parseFloat(v2);
	},
	/**
	 * 나누기, 소수점 n 자리까지 표시 (반올림)
	 */
	divide: function(v1, v2, n) {
		if (this.isNotNumber(v1) || this.isNotNumber(v2) || parseFloat(v2) == 0) {
			return null;
		}
		var r = parseFloat(v1) / parseFloat(v2);
		if (this.isNumber(n)) {
			r = parseFloat(r.toFixed(n));
		}
		return r;
	},
	/**
	 * 절대값
	 */
	abs: function(v) {
		if (this.isNotNumber(v)) {
			return null;
		}
		return Math.abs(v);
	},
	/**
	 * 합계
	 */
	sum: function(arr) {
		if (!arr || !(arr instanceof Array) || !arr.length) {
			return null
		}
		var flag = false;
		var sum = 0;
		var len = arr.length;
		for (var i = 0; i < len; i++) {
			if (this.isNotNumber(arr[i])) {
				continue;
			}
			flag = true;
			sum += parseFloat(arr[i]);
		}
		return flag ? sum : null;
	},
	/**
	 * 평균 (null 제외)
	 */
	avg: function(arr, n) {
		var sum = this.sum(arr);
		if (!sum) {
			return null;
		}
		var cnt = 0;
		var len = arr.length;
		for (var i = 0; i < len; i++) {
			if (this.isNotNumber(arr[i])) {
				continue;
			}
			++cnt;
		}
		return this.divide(sum, cnt, n);
	},
	/**
	 * 퍼센트 (%)
	 */
	percent: function(v1, v2, n) {
		var r = null;
		// r = v1 * 100 / v2
		r = this.multiply(v1, 100);
		r = this.divide(r, v2, n);
		if (this.isNotNumber(r)) {
			return null;
		}
		return r;
	},
	/**
	 * 증감률 (%) (양수: 증가, 음수: 감소, 0: 동일)
	 */
	rateChange: function(v1, v2, n) {
		var r = null;
		// r = (v1 - v2) * 100 / abs(v2)
		r = this.subtract(v1, v2);
		r = this.multiply(r, 100);
		r = this.divide(r, this.abs(v2), n); // [주의] 분모는 절대값 사용
		if (this.isNotNumber(r)) {
			return null;
		}
		return r;
	}
};

var MobileUtil = {
	/**
	 * 모바일인지 여부를 반환한다.
	 */
	isMobile: function() {
		if (!navigator.userAgent) {
			return false;
		}
		
		var userAgent = navigator.userAgent.toLowerCase();
		var mobileKeyWords = [
			'iphone', 'ipad', 'ipod', 'blackberry', 'android', 'windows ce', 'lg', 'mot', 'samsung',
			'sonyericsson', 'mobile', 'symbian', 'opera mobi', 'opera mini', 'iemobile', 'nokia', 'webos'
		];
		
		for (var i = 0; i < mobileKeyWords.length; i++) {
			if (userAgent.indexOf(mobileKeyWords[i]) > -1) {
				return true; // mobile
			}
		}
		return false; // pc
	}
};

var CookieUtil = {
	/**
	 * 쿠키조회
	 */
	get: function(name) {
		var nameOfCookie = name + '=';
		var x = 0;
		while (x <= document.cookie.length) {
			var y = (x + nameOfCookie.length);
			if (document.cookie.substring(x, y) == nameOfCookie) {
				if ((endOfCookie = document.cookie.indexOf(';', y)) == -1) {
					endOfCookie = document.cookie.length;
				}
				var value = document.cookie.substring(y, endOfCookie);
				return unescape(value);
			}
			x = document.cookie.indexOf(' ', x) + 1;
			if (x == 0) { break; }
		}
		return '';
	},
	/**
	 * 쿠키설정 (expiredays: 만료일수)
	 */
	set: function(name, value, expiredays) {
		value = value || '';
		
		var date = new Date();
		date.setDate(date.getDate() + parseInt(expiredays));
		date = new Date(date.getFullYear(), date.getMonth(), date.getDate()); // 시/분/초 제거
		
		document.cookie = name + '=' + escape(value) + '; path=/; expires=' + date.toGMTString() + ';';		
	}
};

var FileUtil = {
	/**
	 * 파일 다운로드
	 */
	download: function(url, param, callback) {
		ajax_fileDownload(url, param, callback);
	}
};

var RegexUtil = {
	/**
	 * 숫자
	 */
	numeric: /^[0-9]+$/,
	/**
	 * 전화번호
	 */
	phoneNo: /^(02|0505|[0-9]{3})([0-9]{3,4})([0-9]{4})$/,
	/**
	 * 휴대폰
	 */
	mobileNo: /^(01[0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$/,
	/**
	 * 이메일
	 */
	email: /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,}$/i,
	/**
	 * 로그인 패스워드 - 영문, 숫자조합 8~20자리
	 */
	loginPw: /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/,
	/**
	 * 로그인 패스워드 - 영문, 숫자조합, 특수문자 8~20자리
	 */
	loginPw2: /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-]).{8,20}$/,
	/**
	 * 특수문자 검색
	 *   - 검색할 특수문자(32개): `~!@#$%^&*()-_=+[]{}\|;:'",.<>/?
	 *   - 정규식 escape(5개): ^-[]\
	 */
	specialChar: /[`~!@#$%\^&*()\-_=+\[\]{}\\|;:'",.<>/?]/
};

var StringUtil = {
	/**
	 * 정규표현식에서 특수문자를 검색하기 위해 escape 한다.
	 */
	escapeRegex: function(value) {
		return value.replace( /[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&" );
	},
	/**
	 * 검색문자를 값으로 대체한다.
	 */
	replaceAll: function(text, search, replacement) {
		var regex = new RegExp(this.escapeRegex(search), 'g');
		return text.replace(regex, replacement);
	},
	/**
	 * 검색문자 목록를 값으로 대체한다.
	 *   - 검색문자 목록이 없는 경우, 패턴({0}, {1}, ..)으로 검색한다.
	 */
	replaceEach: function(text, searchList, replacementList) {
		if (!replacementList) {
			replacementList = searchList; // 두 번째 인자로 대체한다.
			searchList = undefined;
		}
		if (!text || !replacementList) {
			return text;
		}
		for (var i = 0; i < replacementList.length; i++) {
			var search = (!searchList ? '{'+ i +'}' : searchList[i]);
			text = this.replaceAll(text, search, replacementList[i]);
		}
		return text;
	}
};

var ArrayUtil = {
	/**
	 * 배열에 값을 추가한다. (중복제거)
	 */
	addDistinct: function(array, value) {
		if (array == null) {
			return null;
		}
		if (!this.contains(array, value)) { // 중복제거
			array.push(value);
		}
		return array;
	},
	/**
	 * 배열에서 해당 값을 삭제한다. (중복제거)
	 */
	removeDistinct: function(array, valueToFind) {
		if (array == null) {
			return null;
		}
		var i = this.indexOf(array, valueToFind);
		if (i > -1) {
			array.splice(i, 1);
			this.removeDistinct(array, valueToFind); // 중복제거
		}
		return array;
	},
	/**
	 * 배열에 해당 값이 있는지 확인한다.
	 */
	contains: function(array, valueToFind) {
		if (array == null) {
			return false;
		}
		return this.indexOf(array, valueToFind) > -1;
	},
	/**
	 * 배열에서 해당 값의 인덱스을 찾는다.
	 */
	indexOf: function(array, valueToFind) {
		if (array == null) {
			return -1;
		}
		return array.indexOf(valueToFind);
	}
};

var TimerUtil = {
	tid: null, // timer id
	sec: null,
	callback: null,
	/**
	 * 타이머를 작동시칸다.
	 */
	start: function(callback) {
		this.sec = 0;
		this.callback = $.isFunction(callback) ? callback : function(sec, str) {};
		
		clearInterval(this.tid);
		this.tid = setInterval(function() {
			var sec = ++TimerUtil.sec;
			var str = TimerUtil.toString();
			TimerUtil.callback(sec, str);
		}, 1000); // 1초
	},
	stop: function() {
		clearInterval(this.tid);
	},
	toString: function() {
		var h = Math.floor(this.sec / 3600     ); // 시 (내림)
		var m = Math.floor(this.sec % 3600 / 60); // 분
		var s = Math.floor(this.sec % 60       ); // 초
		return (h>0?h+':':'')+(m>9?m:'0'+m)+':'+(s>9?s:'0'+s); // ex) '00:01' or '1:00:00'
	}
};

var ColorUtil = {
	/**
	 * 테마에 맞는 color 값을 구한다.
	 */
	_get: function(type, n) {
		if (typeof SITE_THEME == 'undefined') {
			SITE_THEME = 'light';
		}
		n = NumberUtil.isNumber(n) ? parseInt(n) : null;
		var value = null;
		switch (SITE_THEME) {
			case 'dark': switch (type) {
				case 'text'  : value = '#ffffff'; break;
				case 'item3' : value = ['#f8c1a8', '#e75f8a', '#8f3292'][n]; break;
				case 'item12': value = ['#f8cdac', '#efb9a7', '#e6a5a3', '#dd919e', '#d37e9a', '#ca6a95', '#c15691', '#b8428c', '#a93e88', '#993a84', '#8a3680', '#7b337b'][n]; break;
				case 'items' : value = ['#9e539b', '#e86e4d', '#336dc1', '#efc929', '#89bf3c'][n]; break;
			} break;
			case 'light': switch (type) {
				case 'text'  : value = '#4d4d4d'; break;
				case 'item3' : value = ['#a7bde8', '#587fdd', '#3a5590'][n]; break;
				case 'item12': value = ['#c2d2eb', '#adc1e8', '#98b1e5', '#82a0e3', '#6d90e0', '#587fdd', '#4e71c3', '#4463a9', '#3a5590', '#304676', '#26385c', '#1c2a42'][n]; break;
				case 'items' : value = ['#587fdd', '#b07fc9', '#bfcc40', '#ff8c3e', '#ea6890', '#f7d943', '#8b96cc', '#72d3c5', '#3d5477', '#8b96cc'][n]; break;
			} break;
		}
		if (!value) { // 색상 값이 없는 경우, gray
			switch (SITE_THEME) {
				case 'dark' : value = '#808080'; break;
				case 'light': value = '#cccccc'; break;
				default     : value = 'red';
			}
		}
		return value;
	},
	text: function() {
		return this._get('text');
	},
	item3: function(n) {
		return this._get('item3', n);
	},
	item12: function(n) {
		return this._get('item12', n);
	},
	items: function(n) {
		return this._get('items', n);
	}
};

/**
 * IE9 이하인 경우, 업그레이드 안내 표시
 *   - body 태그의 onload에서 호출해야 함. (jquery가 동작하지 않을 수 있음)
 */
function checkInternetExplorerUpgrade() {
	var rv = -1;
	if (navigator.appName == 'Microsoft Internet Explorer') {
		var ua = navigator.userAgent;
		var re = new RegExp('MSIE ([0-9]{1,}[\.0-9]{0,})');
		if (re.exec(ua) != null) {
			rv = parseFloat(RegExp.$1);
		}
	}
	if (rv > -1 && rv < 10) { // IE9 이하인 경우, 업그레이드 안내 표시
		var url = 'http://windows.microsoft.com/ko-kr/internet-explorer/download-ie';
		var str = '현재 IE9 이하 버전의 브라우저를 이용중입니다. 사이트의 정상적인 이용이 불가능합니다.&nbsp;'
				+ '<a href="' + url + '" target="_blank">여기</a>를 클릭하여 브라우저를 업그레이드 하시기 바랍니다.';
		document.getElementById('internetExplorerUpgrade').innerHTML = str;
		document.getElementById('internetExplorerUpgrade').className = 'browserupgrade';
		document.getElementById('internetExplorerUpgrade').style.display = 'block';
	}
	/* 브라우저 업그레이드 */
	/*.browserupgrade {margin: 0 auto;background: #ff5959;color: #fff;padding: 15px 0;font-size:15px;font-weight:bold;position:relative;z-index:10000;text-align:center;}
	.browserupgrade a{color:#feee00 !important;text-decoration:underline !important;}*/
}

/**
 * 폴리필 - 아래 코드를 포함하면 지원하지 않는 환경에서도 사용할 수 있습니다.
 */
// find() 메서드는 주어진 판별 함수를 만족하는 첫 번째 요소의 값을 반환합니다. 그런 요소가 없다면 undefined를 반환합니다.
if (!Array.prototype.find) {
  Object.defineProperty(Array.prototype, 'find', {
    value: function(predicate) {
      // 1. Let O be ? ToObject(this value).
      if (this == null) {
        throw new TypeError('"this" is null or not defined');
      }

      var o = Object(this);

      // 2. Let len be ? ToLength(? Get(O, 'length')).
      var len = o.length >>> 0;

      // 3. If IsCallable(predicate) is false, throw a TypeError exception.
      if (typeof predicate !== 'function') {
        throw new TypeError('predicate must be a function');
      }

      // 4. If thisArg was supplied, let T be thisArg; else let T be undefined.
      var thisArg = arguments[1];

      // 5. Let k be 0.
      var k = 0;

      // 6. Repeat, while k < len
      while (k < len) {
        // a. Let Pk be ! ToString(k).
        // b. Let kValue be ? Get(O, Pk).
        // c. Let testResult be ToBoolean(? Call(predicate, T, « kValue, k, O »)).
        // d. If testResult is true, return kValue.
        var kValue = o[k];
        if (predicate.call(thisArg, kValue, k, o)) {
          return kValue;
        }
        // e. Increase k by 1.
        k++;
      }

      // 7. Return undefined.
      return undefined;
    },
    configurable: true,
    writable: true
  });
}
// findIndex() 메서드는 주어진 판별 함수를 만족하는 배열의 첫 번째 요소에 대한 인덱스를 반환합니다. 만족하는 요소가 없으면 -1을 반환합니다.
if (!Array.prototype.findIndex) {
  Object.defineProperty(Array.prototype, 'findIndex', {
    value: function(predicate) {
      'use strict';
      if (this == null) {
        throw new TypeError('Array.prototype.findIndex called on null or undefined');
      }
      if (typeof predicate !== 'function') {
        throw new TypeError('predicate must be a function');
      }
      var list = Object(this);
      var length = list.length >>> 0;
      var thisArg = arguments[1];
      var value;

      for (var i = 0; i < length; i++) {
        value = list[i];
        if (predicate.call(thisArg, value, i, list)) {
          return i;
        }
      }
      return -1;
    },
    enumerable: false,
    configurable: false,
    writable: false
  });
}

/**
 * 우편번호 검색 팝업
 *   - <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
 */
function postcode(callback) {
	
	var width = 500; //팝업의 너비
	var height = 600; //팝업의 높이
	
	var _top = (screen.availHeight - height) / 2 - 10;
	var _left = (screen.availWidth - width) / 2;
	if (window.screenLeft < 0) {
		_left += window.screen.width*-1;
		_top = (screen.availHeight - height) / 2 - 100;
	} else if ( window.screenLeft > window.screen.width ) {
		_left += window.screen.width;
	}
	
	new daum.Postcode({
		width: width, //생성자에 크기 값을 명시적으로 지정해야 합니다.
		height: height,
		oncomplete: function(data) {
			var fullAddr = ''; // 최종 주소 변수
			var extraAddr = ''; // 조합형 주소 변수
			
			// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				fullAddr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				fullAddr = data.jibunAddress;
			}
			
			// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다.
				if (data.bname !== '') {
					extraAddr += data.bname;
				}
				// 건물명이 있을 경우 추가한다.
				if (data.buildingName !== '') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
			}
			
			var zip_cd6 = data['postcode1'] + data['postcode2'];
			var zip_cd5 = data['zonecode'];
			
			var addrData = {
				'SIGUNGU_CD': data['sigunguCode'], // 시군구 코드 (5자리)
				'DONG_CD': data['bcode'],          // 법정동 코드 (10자리)
				'ZIP_CD': zip_cd6 == '' ? zip_cd5 : zip_cd6,
				'ADDR': fullAddr,
				'ZIP_CD_5': data['zonecode'],
                'ROAD_ADDR': data['roadAddress']
			};
			
			if (typeof callback == 'function') {
				callback(addrData);
			} else {
				alert('callback 함수가 없습니다.');
			}
		}
	}).open({
//		left: (window.screen.width / 2) - (width / 2),
//		top: (window.screen.height / 2) - (height / 2)
		left: _left,
		top: _top
	});
}
