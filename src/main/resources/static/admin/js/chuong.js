let selectedFiles = [];

document.getElementById('inputAnhChuong').addEventListener('change', function (e) {
    let newFiles = Array.from(e.target.files);
    selectedFiles = selectedFiles.concat(newFiles);

    selectedFiles.sort((a, b) => {
        return a.name.localeCompare(b.name, undefined, { numeric: true, sensitivity: 'base' });
    });

    renderPreviews();
});

function renderPreviews() {
    const previewContainer = document.getElementById('previewContainer');
    const fileInput = document.getElementById('inputAnhChuong');

    previewContainer.innerHTML = '';

    if (selectedFiles.length === 0) {
        previewContainer.innerHTML = '<p class="text-muted w-100 text-center m-0 align-self-center">Chưa có trang truyện nào được chọn.</p>';
        fileInput.value = '';
        return;
    }

    const dataTransfer = new DataTransfer();

    selectedFiles.forEach((file, index) => {
        dataTransfer.items.add(file);

        const div = document.createElement('div');
        div.className = 'position-relative';
        div.style.width = '120px';

        const img = document.createElement('img');
        img.src = URL.createObjectURL(file);
        img.className = 'img-thumbnail';
        img.style.width = '100%';
        img.style.height = '160px';
        img.style.objectFit = 'cover';

        const badge = document.createElement('span');
        badge.className = 'badge bg-primary position-absolute top-0 start-0 m-1';
        badge.innerText = `Trang ${index + 1}`;

        const btnDelete = document.createElement('button');
        btnDelete.className = 'btn btn-sm btn-danger position-absolute top-0 end-0 m-1 p-0';
        btnDelete.style.width = '24px';
        btnDelete.style.height = '24px';
        btnDelete.innerHTML = '<i class="bi bi-x"></i>';
        btnDelete.type = 'button';

        btnDelete.onclick = function () {
            selectedFiles.splice(index, 1);
            renderPreviews();
        };

        div.appendChild(img);
        div.appendChild(badge);
        div.appendChild(btnDelete);
        previewContainer.appendChild(div);
    });

    fileInput.files = dataTransfer.files;
}