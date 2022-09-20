let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
    arrow[i].addEventListener("click", (e) => {
        let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
        arrowParent.classList.toggle("showMenu");
    });
}
let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".bx-menu");

sidebarBtn.addEventListener("click", () => {
    sidebar.classList.toggle("sideBarClose");
});

let filterVal = $("#memberFilter").val();


// const memberFilter = (target) => {
//     const value = target.value;
//     const url = "memberList";

//     $.ajax({
//         url: url,
//         type: 'get',
//         dataType: 'text',
//         data: { memberFilter: value }
//     });
// }


var edit = document.getElementById('edit');
var askReplyContent = document.getElementById('askReplyContent');

edit.addEventListener('click', function () {

    if (edit.innerText == "취소") {
        askReplyContent.removeAttribute('required');
        askReplyContent.setAttribute('readonly', 'readonly');
        edit.innerText = "수정";
    } else {
        askReplyContent.removeAttribute('readonly');
        askReplyContent.setAttribute('required', 'required');
        edit.innerText = "취소";
    }
});



// 사진 미리보기
function loadFile(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('preview').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById('preview').src = "";
    }
};

function back() {
    history.back();
};

// 패스워드 보이기
$(document).ready(function () {
    $('#showPassword').on('click', function () {
        var passwordField = $('#password');
        var passwordField2 = $('#password2');

        // 속성(타입) 가져오기
        var passwordFieldType = passwordField.attr('type');
        var passwordFieldType2 = passwordField2.attr('type');

        // 타입이 password면 text타입으로 변경
        if (passwordFieldType == 'password' && passwordFieldType2 == 'password') {
            passwordField.attr('type', 'text');
            passwordField2.attr('type', 'text');
            // 텍스트 변경
            $(this).val('Hide');

        } else {
            //타입이 text면 password로 변경
            passwordField.attr('type', 'password');
            passwordField2.attr('type', 'password');

            // 텍스트 변경
            $(this).val('Show');
        }
    });
});

