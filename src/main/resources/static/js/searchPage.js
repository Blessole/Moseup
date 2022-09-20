/*검색창 submit체크*/
function searchChk() {
	let text = $('#mainSearch').val();
	if (text == "" || text == null) {
		return false;
	}
	
	if ($('.filter1value').is(":checked") == false) {
		$('#filter1main').val("nothing");
	}
	
}

/* filter1 submit체크 */
function filter1Chk() {
	let text = $('#mainSearch').val();
	let filter2 = $('#filter2main').val();

	if (text == "" || text == null) {
		return false;
	}
	
	if ($('.filter1value').is(":checked") == false) {
		return false;
	}

	if (filter2 == "" || filter2 == null) {
		$('#filter2main').val("nothing");
	}

	if ($('.filter1value').is(":checked") == false) {
		$('#filter1main').val("nothing");
	}
};

/* 필터1 체크박스 선택하고 submit후 체크값 유지*/
$(function() {
	let filter1Arr = $(".filter1Arr").val(); //히든으로 컨트롤러에서 체크값 가져오기

	$('input:checkbox[name="filter1"]').each(function() { //each로 돌기
		if (filter1Arr.indexOf(this.value) > -1) { //체크된게 있다면
			$(this).prop('checked', true); //체크하기
		}
	});
});

/* 지난습관 마우스 이벤트 */
$(function() {
 	let pastHabits = $('.pastHabits');
 	let centered = $('.centered');
 	
 	pastHabits.mouseover(function(){
		$(this).find(centered).removeClass('displayNone');
 	});
 	pastHabits.mouseout(function(){
   		$(this).find(centered).addClass('displayNone');
 	});
});