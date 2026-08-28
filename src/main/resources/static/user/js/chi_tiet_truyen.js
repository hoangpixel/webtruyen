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