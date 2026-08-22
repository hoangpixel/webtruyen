document.addEventListener("DOMContentLoaded", function() {
    const btnToggle = document.getElementById('btn-theme-toggle');
    const iconTheme = document.getElementById('icon-theme');
    const htmlTag = document.documentElement;
    
    // Kiểm tra xem trang có nút đổi màu không (vì có thể có trang không xài)
    if(btnToggle && iconTheme) {
        const savedTheme = localStorage.getItem('webtruyen-theme') || 'dark';
        applyTheme(savedTheme);

        btnToggle.addEventListener('click', () => {
            const currentTheme = htmlTag.getAttribute('data-bs-theme');
            const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
            applyTheme(newTheme);
            localStorage.setItem('webtruyen-theme', newTheme);
        });

        function applyTheme(theme) {
            htmlTag.setAttribute('data-bs-theme', theme);
            if (theme === 'dark') {
                iconTheme.className = 'bi bi-moon-stars-fill text-warning';
            } else {
                iconTheme.className = 'bi bi-sun-fill text-danger';
            }
        }
    }
});