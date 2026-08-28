document.addEventListener("DOMContentLoaded", function () {
    const loiElement = document.getElementById('loiToast');
    if (loiElement) new bootstrap.Toast(loiElement, { delay: 4000 }).show();
});