const btn = document.getElementById('sidebarCollapse');

if (btn) {
    btn.addEventListener('click', function () {
        document.getElementById('sidebar')
            ?.classList.toggle('toggled');
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const tbElement = document.getElementById('thongBaoToast');
    if (tbElement) new bootstrap.Toast(tbElement, { delay: 4000 }).show();

    const loiElement = document.getElementById('loiToast');
    if (loiElement) new bootstrap.Toast(loiElement, { delay: 4000 }).show();
});