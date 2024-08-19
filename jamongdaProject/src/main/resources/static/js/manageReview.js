//사이드바 선택된 곳 색 적용
document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('#hostSidebar a');

    links.forEach(link => {
        link.addEventListener('click', function() {
            links.forEach(link => link.classList.remove('active'));
            this.classList.add('active');
        });

        // 현재 URL이 링크의 href와 일치하는 경우 active 클래스 추가
        if (window.location.href.includes(link.href)) {
            link.classList.add('active');
        }
    });
});

$(document).ready(function() {
	// 팝업 열기
	$(document).on('click', '.td-style button', function() {
	    const reviewId = $(this).data('review-id'); // 리뷰 ID 가져오기
	    $('#saveComment').data('review-id', reviewId); // 저장 버튼에 리뷰 ID 설정
		console.log("팝업 열림: 리뷰 ID", reviewId);
	    $('.overlay').show();
	    $('.popup').show();
	});

	// 팝업 닫기
	$('.close, #closePopup').on('click', function() {
		console.log("팝업 닫힘"); // 디버깅 메시지 추가
	    $('.popup').hide();
	    $('.overlay').hide();
	});

	// 저장 버튼 클릭 이벤트
	$('#saveComment').on('click', function() {
	    const rev_comment = $('#commentText').val();
	    const rev_id = $(this).data('review-id'); // 설정된 리뷰 ID 가져오기

		// 확인용 로그 추가
		console.log("rev_id:", rev_id);
		console.log("rev_comment:", rev_comment);
		
	    $.ajax({
	        url: '/accommodation/updateReviews',
	        type: 'POST',
	        data: { rev_comment: rev_comment, rev_id: rev_id },
	        success: function(response) {
	            alert('댓글이 저장되었습니다.');
	            $('.popup').hide();
	            $('.overlay').hide();
	        },
	        error: function(error) {
	            console.error("Error saving comment:", error);
	        }
	    });
	});
	
	//리뷰 데이터 불러오기
    $.ajax({
        url: '/accommodation/getReviews',
        type: 'GET',
        data: { email: email },
        success: function(reviews) {
            let tbody = $('tbody');
            tbody.empty();  // 기존 내용을 비움

            reviews.forEach(function(review) {
				// rev_comment가 null이거나 빈 문자열일 경우 "없음"으로 설정
				let revComment = review.rev_comment ? review.rev_comment : "없음";
				
                let row = `
                    <tr>
                        <td class="td-style">
                            <input type="checkbox" class="rowCheckbox">
                        </td>
                        <td class="td-style">${review.acc_name}</td>
                        <td class="td-style">${review.ro_name}</td>
                        <td class="td-style">${review.rev_content}</td>
                        <td class="td-style">${review.rev_date}</td>
                        <td class="td-style">${review.email}</td>
						<td class="td-style">${revComment}</td>
                        <td class="td-style"><button data-review-id="${review.rev_id}">작성</button></td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function(error) {
            console.error("Error fetching reviews:", error);
        }
    });
});

$(document).ready(function() {
    // 전체 선택/해제
    $('#selectAll').on('click', function() {
        $('.rowCheckbox').prop('checked', this.checked);
    });

    // 선택 삭제
    $('#delReviewBtn').on('click', function() {
        let selectedReviews = [];
        $('.rowCheckbox:checked').each(function() {
            selectedReviews.push($(this).closest('tr').find('button').data('review-id'));
        });

        if (selectedReviews.length > 0) {
            if (confirm('선택한 리뷰를 삭제하시겠습니까?')) {
                $.ajax({
                    url: '/accommodation/delReview',
                    type: 'POST',
                    data: JSON.stringify({ reviewIds: selectedReviews }),
                    contentType: 'application/json',
                    success: function(response) {
                        if (response.success) {
                            $('.rowCheckbox:checked').closest('tr').remove();
                            alert('선택한 리뷰가 삭제되었습니다.');
                        } else {
                            alert('리뷰 삭제에 실패했습니다.');
                        }
                    },
                    error: function(error) {
                        console.error("Error deleting reviews:", error);
                        alert('리뷰 삭제 중 오류가 발생했습니다.');
                    }
                });
            }
        } else {
            alert('삭제할 리뷰를 선택하세요.');
        }
    });
});