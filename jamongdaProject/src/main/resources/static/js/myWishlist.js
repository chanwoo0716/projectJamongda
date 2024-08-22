function updateWishlistIcons() {
    $('.wishlist-button').each(function() {
        var button = $(this);
        var acc_id = button.data('acc_id');
        var icon = button.find('.wishlist-icon');

        if (wishlistedItems.includes(acc_id)) {
            icon.removeClass('fa-regular fa-heart').addClass('fa-solid fa-heart');
        } else {
            icon.removeClass('fa-solid fa-heart').addClass('fa-regular fa-heart');
        }
    });
}
$(document).ready(function() {
    let page = 1; // 현재 페이지 번호
    let isLoading = false; // 로딩 중 여부
    const seenAccIds = new Set(); // 전역 Set으로 유지하여 중복 제거

	function loadWishlist() {
	    if (isLoading) return; // 이미 로딩 중이면 중복 요청 방지
	    isLoading = true;
	    $.ajax({
	        url: '/wishlist/getWishlist',
	        method: 'GET',
	        data: {
	            email: email,
	            page: page
	        },
	        dataType: 'json',
	        success: function(response) {
	            const container = $('#myWish');
	            
	            // 데이터가 비어 있는 경우
	            if (response.message) {
	                container.html(`<p class="no-wishlist">${response.message}</p>`);
	                $('#loadMore').hide(); // 더보기 버튼 숨김
	            } else if (response.data && Array.isArray(response.data)) {
	                response.data.forEach(item => {
						if (!seenAccIds.has(item.acc_id)) {
						    seenAccIds.add(item.acc_id);
						    const itemElement = `
						        <div class="wishlist-item" data-acc-id="${item.acc_id}">
						            <div class="image-container">
						                <img src="/accDownload.do?acc_id=${item.acc_id}&acc_image=${item.acc_image}" alt="${item.acc_name}" />
						                <button class="wishlist-button" data-acc_id="${item.acc_id}">
						                    <i class="wishlist-icon fa-solid fa-heart fa-xl"></i>
						                </button>
						            </div>
						            <h2>${item.acc_name}</h2>
						            <p>${item.acc_type}</p>
						            <a href="/search/detailSearch.do?aid=${item.acc_id}" class="detailAcc-link">자세히 보기</a>
						        </div>
						    `;
						    container.append(itemElement);
						}
	                });

                    // 버튼 숨김 및 표시
                    if (response.isLastPage) {
                        $('#loadMore').hide();
                    } else {
                        $('#loadMore').show();
                        page++; // 페이지 번호 증가
                    }
                } else {
                    console.error('Unexpected data format:', response.data); // 응답 데이터 형식 문제
                }
                isLoading = false;
            },
            error: function(xhr, status, error) {
                console.error('Status:', xhr.status);
                console.error('Response Text:', xhr.responseText);
                console.error('Error:', error);
                isLoading = false;
            }
        });
    }

    $('#loadMore').on('click', function() {
        loadWishlist(); // 페이지 번호와 함께 데이터를 로드
    });

    $('#myWish').on('click', '.wishlist-button', function() {
        var button = $(this);
        var acc_id = button.data('acc_id');
        var icon = button.find('.wishlist-icon');

        $.ajax({
            type: 'POST',
            url: '/wishlist/toggleWish',
            data: { aid: acc_id },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    if (response.added) {
                        icon.removeClass('fa-regular fa-heart').addClass('fa-solid fa-heart');
                    } else {
                        icon.removeClass('fa-solid fa-heart').addClass('fa-regular fa-heart');
                    }
                } else {
                    alert(response.message || '알 수 없는 오류가 발생했습니다.');
                }
            },
            error: function(xhr, status, error) {
                if (xhr.status === 401) {
                    window.location.href = xhr.responseJSON.redirectUrl; // 로그인 페이지로 리다이렉션
                } else {
                    console.error("오류 세부 사항:", status, error);
                    alert('서버 요청에 실패했습니다. 오류: ' + error);
                }
            }
        });
    });

    // 초기 로드
    loadWishlist();
});
