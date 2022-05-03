/**
 * [공통] 모달 유틸
 *   - 모달(div) open/close 기능 // FIXME(김지훈): 연속 호출 시, 동기화 문제 있음..
 */
var ModalUtil = {
	_id_prefix: 'common-modal-div-',
	_mask_class: 'common-modal-mask', // 'z-index'를 재설정한 mask div의 클래스명
	_findClosedId: function() {
		var id = null; // 모달 div의 id
		
		var cnt = $('div[id^="' + this._id_prefix + '"]').length;
		if (!cnt) {
			id = this._id_prefix + 1; // 모달 div가 없는 경우
		} else {
			for (var level = 1; level <= cnt; level++) {
				if (!$('#' + this._id_prefix + level + ' > .modal').length) {
					id = this._id_prefix + level; // 닫혀 있는 모달 div를 찾는다.
					break;
				}
			}
			if (!id) {
				id = this._id_prefix + (cnt + 1); // 닫혀 있는 모달 div가 없는 경우
			}
		}
		
		if (!$('#' + id).length) {
			$div = $('<div id="' + id + '"></div>').appendTo('body');
		}
		return id;
	},
	_findOpenedId: function() {
		var id = null; // 모달 div의 id
		
		var cnt = $('div[id^="' + this._id_prefix + '"]').length;
		for (var level = cnt; level > 0; level--) { // (역순)
			if ($('#' + this._id_prefix + level + ' > .modal').length) {
				id = this._id_prefix + level; // 열려 있는 모달 div를 찾는다.
				break;
			}
		}
		
		return id;
	},
	open: function(url, param, callback) {
		if (typeof $.fn.modal === 'undefined') {
			alert('ERROR: bootstrap 함수($.fn.modal)가 필요합니다.');
			return;
		}
		
		if ($.isFunction(param)) {
			callback = param;
			param = undefined;
		}
		
		var id = this._findClosedId(); // 닫혀 있는 모달 div의 id를 찾는다.
		
		ajaxLoad('#' + id, url, param, function() { /* $(selector).load(url) 대신 사용 */
			$('#' + id + ' > .modal').off('shown.bs.modal').on('shown.bs.modal', function() {
				if ($.isFunction(callback)) callback();
				
				// 모달(div)가 여러 개 생성될 경우, 'z-index'값이 동일한 값으로 중복적용되기 때문에 'z-index'를 재설정해야 한다.
				var level = parseInt(id.substring(ModalUtil._id_prefix.length));
				var zIndex = parseInt($(this).css('z-index')) + (10 * level); // ex) 1050 + (10 * 1) = 1060
				// z-index 재설정
				$(this).css('z-index', zIndex);
				$('.modal-backdrop').not('.' + ModalUtil._mask_class) // bootstrap이 생성하는 mask div
						.css('z-index', zIndex - 1).addClass(ModalUtil._mask_class); // ex) 1059
			}).modal('show');
		});
	},
	close: function(callback) {
		if (typeof $.fn.modal === 'undefined') {
			alert('ERROR: bootstrap 함수($.fn.modal)가 필요합니다.');
			return;
		}
		
		var id = this._findOpenedId(); // 열려 있는 모달 div의 id를 찾는다.
		
		if (id) {
			$('#' + id + ' > .modal').off('hidden.bs.modal').on('hidden.bs.modal', function() {
				$('#' + id).empty(); // clear
				if (ModalUtil._findOpenedId()) { // 열려 있는 모달 div의 id를 찾는다.
					$('body').addClass('modal-open'); /* 두 번째 모달을 닫으면, 첫 번째 모달에 스크롤이 사라지는 문제 해결 */
				}
				if ($.isFunction(callback)) callback();
			}).modal('hide');
		} else {
			if ($.isFunction(callback)) callback();
		}
	}
};
