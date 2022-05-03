
$(document).ready(function () {
	// tree
	$(".tree li.show").each(function (index, item) {
		$(this).children(".sub-tree").css("display", "block")
	})

	// tree
	$(".tree-toggle").click(function () {
		if ($(this).closest("li").hasClass("show")) { // tree가 열려있을 경우
			$(this).closest("li").removeClass("show").children(".sub-tree").slideUp(); // sub-tree hide
		} else { // tree가 닫혀있을 경우
			$(this).closest("li").addClass("show").children(".sub-tree").slideDown(); // sub-tree show
		}
	});

	// checkbox allCheck
	$("#allCheck").click(function () {
		var thisName = $(this).attr("name");
		$('input[name="' + thisName + '"').prop('checked', $(this).prop("checked"));
	});



	// selectinput :: 클릭한 옵션의 텍스트를 라벨 안에 넣음
	$(".selectinput .optionItem").click(function () {
		$(this).closest(".selectinput").children(".label").html($(this).text())
	})
	// selectinput :: 라벨을 클릭시 옵션 목록이 열림
	$(".selectinput button").click(function () {
		$(this).next(".optionList").slideToggle();
	})
	// selectinput :: 라벨외 다른영역 클릭시 옵션 목록이 닫힘
	$(".selectinput button").focusout(function(){
		var _this = this;
		// n초뒤에 이벤트 작동
		setTimeout(function () {
			$(_this).next(".optionList").slideUp();
		}, 200);
	})

	// jquery ui - datepicker :: 날짜 출력폼 설정
	$(function () {
		$(".datepicker").datepicker({
			dateFormat: "yy-mm-dd",    // 날짜 출력폼 설정

			numberOfMonths: 1,
			buttonText: "Choose",
			showMonthAfterYear: true,
			changeYear: true,
			yearSuffix: '<span>년</span>',

			buttonImageOnly: true,
			changeMonth: true,
			monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
			monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
			dayNamesMin: ['일','월', '화', '수', '목', '금', '토' ],

			prevText: '이전',
			nextText: '다음'


		});
	});

	// datepicker 값 제거
	$(".textinput .btn-dateReset").click(function(){
		$(this).closest(".textinput").find("input.datepicker").val("");
	})

	// textinput 의 focus, hover
	$(".textinput input, .selectinput button, .textinput textarea").focus(function () {
		$(this).parent().addClass("focus")
		$(this).parent().hasClass("textinput-datepicker") ? $(this).closest(".textinput").addClass("focus") : "";
	})
	$(".textinput input, .selectinput button, .textinput textarea").focusout(function () {
		$(this).parent().removeClass("focus")
		$(this).parent().hasClass("textinput-datepicker") ? $(this).closest(".textinput").removeClass("focus") : "";
	})
	$(".textinput input, .selectinput button, .textinput textarea").hover(function () {
		$(this).parent().addClass("hover")
		$(this).parent().hasClass("textinput-datepicker") ? $(this).closest(".textinput").addClass("hover") : "";
	}, function () {
		$(this).parent().removeClass("hover")
		$(this).parent().hasClass("textinput-datepicker") ? $(this).closest(".textinput").removeClass("hover") : "";
	});


	// textinput.disabled
	$(".textinput input:disabled, .textinput textarea:disabled, .textinput select:disabled").each(function (index, item) {
		$(this).closest(".textinput").addClass("disabled")
	})
	// textinput.disabled
	$(".selectinput button:disabled").each(function (index, item) {
		$(this).closest(".selectinput").addClass("disabled")
	})
})

function Modal_s(target) {
	$(target).removeClass("hide").addClass("show");
	if ($('.modal.show').length == 1) { // 첫번째 모달인 경우
		$("body").addClass("modalActive")
	}
}
function Modal_h(target) {
	$(target).addClass("hide").removeClass("show")
	if ($('.modal.show').length == 0) { // 마지막 모달인 경우
		$("body").removeClass("modalActive")
	}
}