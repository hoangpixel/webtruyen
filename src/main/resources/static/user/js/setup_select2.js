$(document).ready(function () {
    $('.select2-chuong').select2({
        width: '100%'
    });

    $('.select2-chuong').on('change', function () {
        let chuongId = $(this).val();
        let baseUrl = $(this).attr('data-base-url');

        if (chuongId && baseUrl) {
            window.location.href = baseUrl + "/" + chuongId;
        }
    });
});