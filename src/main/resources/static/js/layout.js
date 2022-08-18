/*네비바 submit체크*/
function chk() {
		var text = $('#search').val();
		var filter2 = $('#filter2').val();
		
		if(text == "" || text == null) {
			return false;
		}
		
		if (filter2 == "" || filter2 == null) {
			$('#filter2').val("nothing");
		}
		
		if ($('.filter1value').is(":checked") == false) {
			$('#filter1').val("nothing");
		}
	}