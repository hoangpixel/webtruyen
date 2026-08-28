const avatarUpload = document.getElementById('avatarUpload');
const previewImg = document.querySelector('.avatar-preview');

avatarUpload.addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        previewImg.src = URL.createObjectURL(file);
    }
});