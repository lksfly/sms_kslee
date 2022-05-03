
// 공통 이벤트 설정 (이벤트 충돌방지를 위해, 적용할 영역을 지정해야 함)
function main_setCommonEvent(selector) {
	var $content = $(selector);
	
	// textinput, selectinput - focus, hover
	$content.find('.textinput input, .selectinput button')
		.on('focus',      function() { $(this).closest('.textinput, .selectinput').addClass('focus') })
		.on('focusout',   function() { $(this).closest('.textinput, .selectinput').removeClass('focus') })
		.on('mouseenter', function() { $(this).closest('.textinput, .selectinput').addClass('hover') })
		.on('mouseleave', function() { $(this).closest('.textinput, .selectinput').removeClass('hover') });
	// selectinput - click, focusout
	$content.find('.selectinput button').on('click', function() {
		$(this).next('.optionList').slideToggle(); // 옵션 목록 열기/닫기
	}).on('focusout', function() {
		var $target = $(this).next('.optionList'); // 옵션 목록 닫기
		setTimeout(function() { $target.slideUp() }, 200); // n초뒤에 이벤트 작동
	}).next('.optionList').find('.optionItem').on('click', function() {
		$(this).closest('.selectinput').children('input[type="hidden"]').val($(this).attr('data-value')); // 값 설정
		$(this).closest('.selectinput').children('.label').html($(this).text()); // 클릭한 옵션의 텍스트를 라벨 안에 넣음
	});
	// datepicker
	$content.find('.datepicker').datepicker(DATEPICKER_SETTINGS); // 날짜 출력폼 설정
	$content.find('.textinput .btn-dateReset').on('click', function() {
		$(this).closest('.textinput').find('.datepicker').val(''); // datepicker 값 제거
	});
}

function Modal_s(target) {
	$(target).removeClass('hide').addClass('show');
	if ($('.modal.show').length == 1) { // 첫번째 모달인 경우
		$('body').addClass('modalActive');
	}
}

function Modal_h(target) {
	$(target).addClass('hide').removeClass('show');
	if ($('.modal.show').length == 0) { // 마지막 모달인 경우
		$('body').removeClass('modalActive');
	}
}
