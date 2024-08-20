$(document).ready(function() {
    let page = 1;
    const pageSize = 10; // 페이지당 리뷰 수

    function loadReviews() {
        $.ajax({
            url: '/mypage/getReviewsAjax',
            method: 'POST',
            data: { email: email, page: page, size: pageSize },
            success: function(data) {
                if (data && data.length > 0) {
                    let existingIds = $('#reviews .review').map(function() {
                        return $(this).data('rev_id');
                    }).get();
                    
                    let reviewsHtml = '';
                    data.forEach(review => {
                        if (!existingIds.includes(review.rev_id)) {
                            reviewsHtml += `
								<a class="go-back" href="/mypage/mypage.do"><i class="fa-solid fa-arrow-left fa-xl"></i></a>
                                <div class="review" data-rev_id="${review.rev_id}">
                                    <p class="rev_header">
                                        <span>작성일자 : ${review.rev_date}</span>
                                        <i class="fa-solid fa-trash-can delete-review" style="cursor: pointer;"></i>
                                    </p>
                                    <p><span class="acc_name">이용숙소 : ${review.acc_name}</span></p>
                                    <p><span class="ro_name">이용객실 : ${review.ro_name}</span></p>
                                    <p><span>${review.rev_content}</span></p>
                                    <div class="image-gallery">
                                        ${review.images.map(image => 
                                            `<img src="/review/downloadImage?rev_image=${image.rev_image}" alt="Review Image" class="review-image"/>`
                                        ).join('')}
                                    </div>
                                </div>
                            `;
                        }
                    });

                    $('#reviews').append(reviewsHtml);
                    page++;
                } else {
					if ($('#reviews').children().length === 0) {
					    $('#reviews').html('<a class="go-back" href="/mypage/mypage.do"><i class="fa-solid fa-arrow-left fa-xl"></i></a><div class="no-reviews">작성한 리뷰가 없습니다.</div>');
					}
                    $('#loadMore').hide();
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('AJAX Error:', textStatus, errorThrown);
                alert('리뷰를 로드하는 데 실패했습니다.');
            }
        });
    }

    loadReviews();

    $('#loadMore').on('click', function() {
        loadReviews();
    });

    $('#reviews').on('click', '.delete-review', function() {
        let reviewElement = $(this).closest('.review');
        let reviewId = reviewElement.data('rev_id');

        if (confirm("리뷰를 삭제하시겠습니까?")) {
            $.ajax({
                url: '/review/deleteReviews',
                method: 'POST',
                data: { id: reviewId },
                success: function(response) {
                    console.log('Delete response:', response);
                    reviewElement.remove();
                    alert("리뷰를 삭제했습니다.");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('AJAX Error:', textStatus, errorThrown);
                    alert('리뷰를 삭제하는 데 실패했습니다.');
                }
            });
        }
    });
});
