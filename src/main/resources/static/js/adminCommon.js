let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".sidebarBtn");
let disabled = document.querySelector(".disabled");



// 메뉴바 접기
sidebarBtn.onclick = function () {
  sidebar.classList.toggle("active");
  if (sidebar.classList.contains("active")) {
    sidebarBtn.classList.replace("bx-menu", "bx-menu-alt-right");
  } else
    sidebarBtn.classList.replace("bx-menu-alt-right", "bx-menu");
}



