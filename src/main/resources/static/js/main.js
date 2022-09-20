/*검색창 submit체크*/
function searchChk() {
	var text = $('#mainSearch').val();
	var filter2 = $('#filter2main').val();

	if (text == "" || text == null) {
		return false;
	}

	if (filter2 == "" || filter2 == null) {
		$('#filter2main').val("nothing");
	}

	if ($('.filter1value').is(":checked") == false) {
		$('#filter1main').val("nothing");
	}
}

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