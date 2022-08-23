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