let maxImages = 3;

function previewImage(event) {
    var reader = new FileReader();
    reader.onload = function(){
        var img = document.createElement('img');
        img.src = reader.result;

        var previewContainer = document.createElement('div');
        previewContainer.classList.add('image-preview');

        var removeButton = document.createElement('button');
        removeButton.classList.add('remove-image-btn');
        removeButton.onclick = function() {
            previewContainer.remove();
            updateImageCount();
        };

        previewContainer.appendChild(img);
        previewContainer.appendChild(removeButton);

        document.getElementById('image-preview-container').appendChild(previewContainer);
        updateImageCount();
    };
    reader.readAsDataURL(event.target.files[0]);
}

function addFileInput() {
    var fileInputContainer = document.createElement('div');
    fileInputContainer.classList.add('file-upload-section');

    var fileInputLabel = document.createElement('label');
    fileInputLabel.classList.add('file-upload-label');

    var fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.name = 'images';
    fileInput.classList.add('file-upload-input');
    fileInput.onchange = previewImage;

    fileInputLabel.appendChild(fileInput);
    fileInputContainer.appendChild(fileInputLabel);

    document.getElementById('file-input-container').appendChild(fileInputContainer);

    // 파일 입력 필드 클릭 트리거
    fileInput.click();
}

function updateImageCount() {
    var imagePreviews = document.getElementsByClassName('image-preview');
    var count = imagePreviews.length;
    var imgAddBtn = document.querySelector('.img-add-btn');
    var imageCountText = document.querySelector('.image-count');

    if (count >= maxImages) {
        imgAddBtn.classList.add('hidden');
    } else {
        imgAddBtn.classList.remove('hidden');
    }

    imageCountText.textContent = `${count}/${maxImages}`;
}

function validateForm(event) {
    var imagePreviews = document.getElementsByClassName('image-preview');
    if (imagePreviews.length === 0) {
        alert('최소한 하나의 사진을 추가해야 합니다.');
        event.preventDefault();
    }
}