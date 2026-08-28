document.getElementById('fileAnhThem').addEventListener('change', function (e) {
    const file = e.target.files[0];
    const preview = document.getElementById('previewAnhThem');

    if (file) {
        preview.src = URL.createObjectURL(file);
        preview.classList.remove('d-none');
    } else {
        preview.src = "";
        preview.classList.add('d-none');
    }
});