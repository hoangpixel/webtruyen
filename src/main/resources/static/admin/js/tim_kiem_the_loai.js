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