$(document).ready(function () {
    $('#selectTruyen').select2({
        dropdownParent: $('#modalThemChuong'),
        placeholder: "-- Gõ tên truyện để tìm --",
        width: '100%',
        language: {
            noResults: function () {
                return "Không tìm thấy bộ truyện này!";
            }
        }
    });
});