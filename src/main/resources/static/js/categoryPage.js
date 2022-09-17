/* filter1 submit체크 */
function filter1Chk() {
	let filter2 = $('#filter2').val();

	//필터1 아무것도 체크되어 있지 않으면 false
	/*if ($('.filter1value').is(":checked") == false) {
		return false;
	}*/
	
	//필터1 예치금,모집인원 체크되어 있지 않을때(input값 보내지 않음)
	if ($('#depositCheck').is(':checked') == false) {
		$('#deposit').attr('disabled', 'disabled'); //input 속성에 disabled추가
	}
	if ($('#volumeCheck').is(':checked') == false) {
		$('#volume').attr('disabled', 'disabled'); //input 속성에 disabled추가
	}

	if (filter2=="") {
		filter2="메롱";
	}

	/*if ($('.filter1value').is(":checked") == false) {
		$('#filter1main').val("nothing");
	}*/
};

/* 필터1 예치금, 모집인원, 미포함 선택하고 submit후 체크값 유지*/
$(function() {
	//예치금
	let filter1depositCheck = $(".filter1depositCheck").val(); //히든으로 컨트롤러에서 체크값 가져오기
	let depositValue = $('.depositValue').val();

	$('input:checkbox[class="filter1value"]').each(function() { //each로 돌기
		if (filter1depositCheck.indexOf(this.value) > -1) { //체크된게 있다면
			$(this).prop('checked', true); //체크하기
			$('#deposit').attr('value', depositValue);
		}
	});
	//모집인원
	let filter1volumeCheck = $(".filter1volumeCheck").val(); //히든으로 컨트롤러에서 체크값 가져오기
	let volumeValue = $('.volumeValue').val();

	$('input:checkbox[class="filter1value"]').each(function() { //each로 돌기
		if (filter1volumeCheck.indexOf(this.value) > -1) { //체크된게 있다면
			$(this).prop('checked', true); //체크하기
			$('#volume').attr('value', volumeValue);
		}
	});
	//시작된 습관 미포함
	let filter1notIncludCheck = $(".filter1notIncludCheck").val(); //히든으로 컨트롤러에서 체크값 가져오기

	$('input:checkbox[class="filter1value"]').each(function() { //each로 돌기
		if (filter1notIncludCheck.indexOf(this.value) > -1) { //체크된게 있다면
			$(this).prop('checked', true); //체크하기
		}
	});
});

/*필터1 예치금,모집인원 선택 체크박스 선택되어 있으면 항상 예치금 range 보이게 */
$(function() {
	if($('#depositCheck').is(':checked')) {
		$('#filter1depositDiv, #showDeposit').removeClass('displayNone'); //예치금 range 보이게
	}
	if($('#volumeCheck').is(':checked')) {
		$('#filter1volumeDiv, #showVolume').removeClass('displayNone'); //모집인원 range 보이게
	}
});

/* 필터1 예치금, 모집인원 선택시 display */
$(function() {
	$('#depositCheckDiv').change(function() { //예치금 체크박스 선택하면
		if ($('#depositCheck').is(':checked')) {	//체크박스가 선택 되어있으면
			$('#filter1depositDiv, #showDeposit').removeClass('displayNone'); //예치금 range 보이게
		} else {
			$('#filter1depositDiv, #showDeposit').addClass('displayNone'); //예치금 range 안보이게
		}
	});
	
	$('#volumeCheckDiv').change(function() { //모집인원 체크박스 선택하면
		if ($('#volumeCheck').is(':checked')) {	//체크박스가 선택 되어있으면
			$('#filter1volumeDiv, #showVolume').removeClass('displayNone'); //모집인원 range 보이게
		} else {
			$('#filter1volumeDiv, #showVolume').addClass('displayNone'); //모집인원 range 안보이게
		}
	});
});

/* 예치금 값 보여주기*/
$(function() {
	let slider = document.getElementById("deposit");
	let output = document.getElementById("showDeposit");

	if ($('#deposit').val() == 10) {
		output.innerHTML = "모두";
	} else if ($('#deposit').val() == 1) {
		output.innerHTML = slider.value + "만원";
	} else {
		output.innerHTML = slider.value + "만원 이하";
	}

	slider.oninput = function() {	//range 움직일 때
		if ($('#deposit').val() == 10) {
			output.innerHTML = "모두";
		} else if ($('#deposit').val() == 1) {
			output.innerHTML = slider.value + "만원";
		} else {
			output.innerHTML = slider.value + "만원 이하";
		}
	}
});

/* 모집인원 보여주기*/
$(function() {
	let slider = document.getElementById("volume");
	let output = document.getElementById("showVolume");

	if ($('#volume').val() == 100) {
		output.innerHTML = "모두";
	} else {
		output.innerHTML = slider.value + "명 이하";
	}

	slider.oninput = function() {	//range 움직일 때
		if ($('#volume').val() == 100) {
			output.innerHTML = "모두";
		} else {
			output.innerHTML = slider.value + "명 이하";
		}
	}
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