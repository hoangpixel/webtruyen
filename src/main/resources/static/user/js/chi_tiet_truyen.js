const stars = document.querySelectorAll('#star-container .star');
const diemSaoInput = document.getElementById('diemSaoInput');

stars.forEach((star, index) => {
    // Lướt chuột
    star.addEventListener('mouseover', () => {
        resetStars();
        highlightStars(index + 1);
    });

    // Rút chuột ra
    document.getElementById('star-container').addEventListener('mouseleave', () => {
        resetStars();
        highlightStars(diemSaoInput.value);
    });

    // Click chốt sao
    star.addEventListener('click', () => {
        const val = star.getAttribute('data-value');
        diemSaoInput.value = val;
        highlightStars(val);
    });
});

function highlightStars(count) {
    for (let i = 0; i < count; i++) {
        stars[i].classList.remove('text-secondary');
        stars[i].classList.add('text-warning');
    }
}

function resetStars() {
    stars.forEach(s => {
        s.classList.remove('text-warning');
        s.classList.add('text-secondary');
    });
}

// Chạy lần đầu để set màu sao mặc định (hoặc sao cũ)
const initialStars = document.getElementById('diemSaoInput').value;
highlightStars(initialStars);

// Xử lý nút Chia sẻ (Có phân biệt Điện thoại / Máy tính)
document.getElementById('btnShare').addEventListener('click', async () => {
    // 1. Kiểm tra xem người dùng đang xài Điện thoại hay Máy tính
    const isMobile = /iPhone|iPad|iPod|Android/i.test(navigator.userAgent);

    // 2. Nếu là Điện thoại và trình duyệt có hỗ trợ share
    if (isMobile && navigator.share) {
        try {
            await navigator.share({
                title: 'Đọc truyện hay trên Tàng Kinh Các',
                text: 'Vào đọc thử siêu phẩm này đi đạo hữu ơi!',
                url: window.location.href
            });
        } catch (err) {
            console.error('Lỗi chia sẻ: ', err);
        }
    }
    // 3. Nếu là Máy tính thì ép Copy Link luôn, không thèm xài bảng Share của Windows
    else {
        try {
            await navigator.clipboard.writeText(window.location.href);
            alert("Đã copy link truyện! Mở khung chat dán (Ctrl+V) gửi cho bạn bè ngay nhé.");
        } catch (err) {
            console.error('Lỗi copy: ', err);
            alert("Trình duyệt không hỗ trợ copy tự động, bạn hãy copy link trên thanh địa chỉ nhé!");
        }
    }
});

// Hàm mở khóa danh sách bị ẩn
function moRongDanhSach(classItemAn, classKhuVucNut) {
    // 1. Tìm tất cả các dòng đang bị ẩn và lột bỏ class d-none
    document.querySelectorAll(classItemAn).forEach(item => {
        item.classList.remove('d-none');
    });

    // 2. Tàng hình luôn cái nút bấm sau khi đã hiển thị hết
    const khuVucNut = document.querySelector(classKhuVucNut);
    if (khuVucNut) {
        khuVucNut.remove();
    }
}

// Xử lý tự động hiện nút Xem Thêm cho Mô Tả
document.addEventListener("DOMContentLoaded", function () {
    const moTa = document.querySelector('.noi-dung-mo-ta');
    const btnMoTa = document.querySelector('.nut-xem-them-mo-ta');

    // Nếu chiều cao thực tế của đoạn văn lớn hơn chiều cao đang hiển thị (bị CSS cắt bớt)
    if (moTa && moTa.scrollHeight > moTa.clientHeight) {
        btnMoTa.classList.remove('d-none'); // Chữ dài quá thì mới hiện nút
    }
});

// Hàm gắn vào nút onClick
function moRongMoTa() {
    // Xóa class rút gọn để chữ xổ ra hết
    document.querySelector('.noi-dung-mo-ta').classList.remove('mo-ta-rut-gon');
    // Xóa luôn cái nút
    document.querySelector('.nut-xem-them-mo-ta').remove();
}