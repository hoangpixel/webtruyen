document.addEventListener("DOMContentLoaded", function () {
    const cacOTimKiem = document.querySelectorAll('.tim-kiem-the-loai');

    cacOTimKiem.forEach(oNhap => {
        oNhap.addEventListener('input', function () {
            const tuKhoa = this.value.toLowerCase().trim();
            const hopChua = this.nextElementSibling;
            const danhSachTheLoai = hopChua.querySelectorAll('.form-check');

            danhSachTheLoai.forEach(dong => {
                const tenTheLoai = dong.querySelector('label').textContent.toLowerCase();
                if (tenTheLoai.includes(tuKhoa)) {
                    dong.style.setProperty('display', 'block', 'important');
                } else {
                    dong.style.setProperty('display', 'none', 'important');
                }
            });
        });
    });
});

document.addEventListener("DOMContentLoaded", function() {
            const btnToggle = document.getElementById('sidebarCollapse');
            const sidebar = document.getElementById('sidebar');
            
            // Check null cẩn thận để không bị sập script của cả trang
            if (btnToggle && sidebar) {
                btnToggle.addEventListener('click', function (e) {
                    e.preventDefault(); // Ngăn trình duyệt nhảy lên đầu trang nếu nút là thẻ <a>
                    sidebar.classList.toggle('toggled');
                });
            } else {
                console.warn("Lưu ý: Không tìm thấy ID 'sidebarCollapse' hoặc 'sidebar' trên trang này!");
            }
        });