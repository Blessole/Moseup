/* 습관 시작/종료날짜 최소값 오늘 날짜 */
$(function() {
	let now_utc = Date.now() //지금 날짜를 밀리초로
	let timeOff = new Date().getTimezoneOffset()*60000; //분단위를 밀리초로 변환(getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환)
	let today = new Date(now_utc-timeOff).toISOString().split("T")[0];//new Date(now_utc-timeOff).toISOString()은 현재기준
																										//'2022-08-19T23:18:38.134Z'를 반환
																										//여기서 "T"를 기준으로 split 하고 그 앞부분은 '2022-08-19'이 된 후 today에 저장
	document.getElementById("startDate").setAttribute("min", today);	//최소값을 오늘 날짜로 지정
	document.getElementById("endDate").setAttribute("min", today);
});

/* 모집 인원 값 보여주기*/
$(function() {
	let slider = document.getElementById("teamVolume");
	let output = document.getElementById("teamVolumeValue");
	output.innerHTML = slider.value + "명";

	slider.oninput = function() {
		output.innerHTML = this.value + "명";
	}
});

/*textarea 글자 수 체크*/
function fn_checkText(obj){
	const maxText = 500; //최대 1000바이트
	const text_val = obj.value; //입력한 문자
	const text_len = text_val.length; //입력한 문자수

	let totalText=0;
	for(let i=0; i<text_len; i++){
		const each_char = text_val.charAt(i);
		const uni_char = escape(each_char) //유니코드 형식으로 변환
		if(uni_char.length>0){
			totalText += 1;
		}
	}

	if(totalText>maxText){
		alert('최대 500글자까지만 입력가능합니다.');
		document.getElementById("nowText").innerText = totalText;
		document.getElementById("nowText").style.color = "red";
	}else{
		document.getElementById("nowText").innerText = totalText;
		document.getElementById("nowText").style.color = "black";
	}
}

/* submit 체크*/
function submitChk() {
	//카테고리 체크
	let teamCategory = document.getElementById("teamCategory1");
	let teamCategoryErr = document.getElementById("teamCategoryErr");

	if (teamCategory.value == "0") {
		teamCategoryErr.innerText="습관 대분류는 필수 선택사항입니다!";
		teamCategory.focus();
		return false;
	}
	
	//예치금 체크
	let teamDeposit = document.getElementById("teamDeposit");
	let teamDepositErr = document.getElementById("teamDepositErr");

	if (teamDeposit.value == "0") {
		teamDepositErr.innerText="예치금은 필수 선택사항입니다!";
		teamDeposit.focus();
		return false;
	}

	//습관 시작or종료일 체크
	let startDate = document.getElementById("startDate");
	let endDate = document.getElementById("endDate");
	let dateErr = document.getElementById("dateErr");

	if (startDate.value == "" || endDate.value == "") {
		startDateErr.innerText="습관 시작일과 습관 종료일은 필수 선택사항입니다!";
		startDate.focus();
		return false;
	}

	if (startDate.value == endDate.value || startDate.value > endDate.value) {
		endDateErr.innerText="습관 종료일은 습관 시작일보다 이전이거나 같을 수 없습니다!";
		endDate.focus();
		return false;
	}
	
	//팀 소개 사진 체크
	let teamPhoto = document.getElementById("teamPhoto");
	let teamPhotoErr = document.getElementById("teamPhotoErr");
	
	if(teamPhoto.value == null || teamPhoto.value == "") {
		teamPhotoErr.innerText="팀 소개 사진은 필수 선택사항입니다!";
		teamPhoto.focus();
		return false;
	}
}

/* 확장자 체크 or 사진 미리보기 */
function setImage(event) {
	let teamPhoto = document.getElementById("teamPhoto");
	let teamPhotoErr = document.getElementById("teamPhotoErr");
	let fileVal = $("#teamPhoto").val();
	let imgTag = $("#imgTag");
	let photoVal = fileVal.slice(fileVal.indexOf(".")+1).toLowerCase();
	
	function del() {
		$("#teamPhoto").val(""); //input file 파일명을 지우기
		$('#image_container').addClass('photoDisplayNone');
	}
	
	//확장자 체크
	if(photoVal == null || photoVal == "") {
		teamPhotoErr.innerText="사진을 선택해주세요!";
		del()
	} else if (photoVal != "jpg" && photoVal != "png" && photoVal != "jpeg" && photoVal != "gif") {
		teamPhotoErr.innerText="확장자가 jpg, png, jpeg, gif 파일만 가능합니다!";
		teamPhoto.focus();
		imgTag.remove();
		del()
	} else {	
		//팀 소개 사진 미리보기
		$('#image_container').removeClass('photoDisplayNone');
		let img = document.createElement("img");
		let reader = new FileReader();
		
		function readerOnload() {
			reader.onload = function(e) {
				img.setAttribute("src", e.target.result);
				img.setAttribute("width", "500");
				img.setAttribute("height", "300");
				img.setAttribute("id", "imgTag");
				teamPhotoErr.innerText="";
				document.querySelector("div#image_container").appendChild(img);
			};
			reader.readAsDataURL(event.target.files[0]);
		}

		if (imgTag.length) {	//이미 업로드한 사진이 있으면
			imgTag.remove();	//원래 있던 사진 지우기
			readerOnload();
		} else readerOnload();
	}
};

/* 아이디 중복체크/팀명 정규표현식 */
$(function() {
	$('#teamName').focus();
	$('#teamName').blur(function() {
		let teamName = $('#teamName').val();
		let regex = /^[가-힣a-zA-Z0-9]{2,14}$/; //한글,영문(대/소문자),숫자만 가능한 2~14글자 정규표현식
		$.ajax({
			type : "get",
			url : '/teams/nameChk',
			data : {"teamName" : teamName},
			success : function(data) { //data : /teams/nameChk에서 넘겨준 결과값
				if (teamName == "") {
					$('#teamNameChk').text("팀명을 입력해 주세요!");
					$('#teamName').focus();
				} else if (!regex.test(teamName)) {
					$('#teamNameChk').text("2~14자의 한글, 영문 대/소문자, 숫자만 사용 가능합니다.");
					$('#teamName').val("");
					$('#teamName').focus();
				} else {
					$('#teamNameChk').html(data);
				}
			}
		})
	})
});

/* 대분류 선택시 display */
$(function() {
	$('#teamCategory1').change(function() { // 대분류 선택하면
		$('#teamCategory2Label').removeClass('displayNone'); //중분류 디스플레이 보이게
		if ($('#teamCategory1').val() == "공부") { //대분류 중 공부 선택
			deleteAllInputs();
			$('.study').removeClass('displayNone');
		} else if ($('#teamCategory1').val() == "운동") { //대분류 중 운동 선택
			deleteAllInputs();
			$('.exercise').removeClass('displayNone');
		} else if ($('#teamCategory1').val() == "습관") { //대분류 중 습관 선택
			deleteAllInputs();
			$('.habit').removeClass('displayNone');
		} else if ($('#teamCategory1').val() == "ETC") { //대분류 중 기타 등등 선택
			deleteAllInputs();
			$('#teamCategory3Label, #teamCategory3Div').removeClass('displayNone');
		}
	});
});

/* 중분류 선택시 display */
$(function() {
	$('#teamCategory2Exercise, #teamCategory2Study, #teamCategory2Habit').change(function() {
		$('#teamCategory3Label, #teamCategory3Div').removeClass('displayNone');
	});
});

/* 카테고리2/3의 디스플레이, 값 없애기 */
function deleteAllInputs() {
	$('.study, .exercise, .habit').addClass('displayNone'); //모든 중분류 디스플레이 감추기
	$("input:checkbox[name='teamCategory2']").prop("checked", false); //모든 중분류 카테고리 체크 되어있는것 해제
	$('#teamCategory3Label, #teamCategory3Div').addClass('displayNone'); //소분류 라벨과 input 감추기
	$('#teamCategory3').val(''); //소분류 input값 없애기
}

/* 카테고리3 공백사용 금지 */
$(function() {
	$('#teamCategory3').blur(function() {
		let teamCategory3 = $('#teamCategory3').val();
		let space = /\s/; //공백체크
		if (space.exec(teamCategory3)) {//공백 체크
			$('#teamCategory3Err').text("공백은 사용할 수 없습니다!");
			$('#teamCategory3').val(null);
		}
		if(!space.exec(teamCategory3)) {
			$('#teamCategory3Err').text("");
		}
	});
});