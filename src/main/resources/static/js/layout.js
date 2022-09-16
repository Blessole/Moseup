/*네비바 submit체크*/
function chk() {
		let text = $('#search').val();
		let filter2 = $('#filter2').val();

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

// /*네비바 submit체크 (by 솔) */
function navChk() {
	let navText = $('#search').val();

	if(navText == "" || navText == null) {
		return false;
	} else {
		$('#filter2').val("nothing");
		$('#filter1').val("nothing");
	}
	}

/* 네비바 Search by 솔 */
const search = document.querySelector(".nav-search");
const btn = document.querySelector(".searchBtn");
const input = document.querySelector(".input");

btn.addEventListener("click", () => {
	search.classList.toggle("active");
	input.focus();
});

















