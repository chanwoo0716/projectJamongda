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

//리뷰 불러오기, 상세정보 불러오기
$(document).ready(function() {
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
						<td class="td-style">
						    <a href="#" class="review-title" data-review-id="${review.rev_id}">${review.rev_content}</a>
						</td>
                        <td class="td-style">${review.rev_date}</td>
                        <td class="td-style">${review.email}</td>
						<td class="td-style">${revComment}</td>
                        <td class="td-style"><button data-review-id="${review.rev_id}">작성</button></td>
                    </tr>
                `;
                tbody.append(row);
				
				// 리뷰 제목 클릭 이벤트
				$('.review-title').on('click', function(e) {
				    e.preventDefault();
				    const reviewId = $(this).data('review-id');
				    fetchReviewDetails(reviewId);
				});
            });
        },
        error: function(error) {
            console.error("Error fetching reviews:", error);
        }
    });
	
	// 리뷰 상세 정보 가져오기
	function fetchReviewDetails(reviewId) {
	    $.ajax({
	        url: `/accommodation/getReviewDetails`,
	        type: 'GET',
	        data: { rev_id: reviewId },
	        success: function(data) {
				// 이미지와 리뷰 내용을 팝업창에 표시
				let imagesHtml = data.rev_images.map(image => {
				    // 이미지 다운로드 URL을 사용하여 이미지를 표시
				    let imageUrl = `/review/downloadImage?rev_image=${encodeURIComponent(image.rev_image)}`;
				    return `<img src="${imageUrl}" alt="리뷰 이미지" class="review-image">`;
				}).join('');

				$('#reviewImages').html(imagesHtml);
				$('#reviewContent').text(data.rev_content);
				$('#reviewDate').text(data.rev_date);
				$('#reviewEmail').text(data.email);
				$('#reviewComment').text(data.rev_comment);

	            // 팝업 열기
	            $('#reviewDetailPopup').show();
	            $('.overlay').show();
	        },
	        error: function(error) {
	            console.error("Error fetching review details:", error);
	        }
	    });
	}
	
});

//리뷰 등록하기
$(document).ready(function() {
	// 일괄 댓글 작성 탭
	$('#batchCommentLink').on('click', function(e) {
	    e.preventDefault();
	    $(this).addClass('active');
	    $('#smartCommentLink').removeClass('active');

	    // 일반 일괄 댓글 작성 UI를 보여줌
	    $('#batchCommentText').show();
	    $('#smartCommentTemplate').hide();
	    $('#saveBatchComment').show();
	    $('#saveSmartBatchComment').hide();
		
		// 닫기 버튼 제어
		$('#closeBatchPopup').eq(0).show();  // 첫 번째 닫기 버튼 보이기
		$('#closeBatchPopup').eq(1).hide();  // 두 번째 닫기 버튼 숨기기
	});
	// 스마트 일괄 댓글 작성 탭
	$('#smartCommentLink').on('click', function(e) {
	    e.preventDefault();
	    $(this).addClass('active');
	    $('#batchCommentLink').removeClass('active');

	    // 스마트 댓글 작성 UI를 보여줌
	    $('#batchCommentText').hide();
	    $('#smartCommentTemplate').show();
	    $('#saveBatchComment').hide();
	    $('#saveSmartBatchComment').show();
		
		// 닫기 버튼 제어
		$('#closeBatchPopup').eq(0).hide();  // 첫 번째 닫기 버튼 숨기기
		$('#closeBatchPopup').eq(1).show();  // 두 번째 닫기 버튼 보이기
	});
	
    // 팝업 열기 (개별 댓글 작성)
    $(document).on('click', '.td-style button', function() {
        const reviewId = $(this).data('review-id');
        $('#saveSingleComment').data('review-id', reviewId);
        console.log("팝업 열림: 리뷰 ID", reviewId);
        $('.overlay').show();
        $('#singleCommentPopup').show();
    });

	// 팝업 열기 (일괄 댓글 작성)
	$('#batchCommentBtn').on('click', function() {
	    let selectedReviews = [];
	    $('.rowCheckbox:checked').each(function() {
	        selectedReviews.push({
	            id: $(this).closest('tr').find('button').data('review-id'),
	            email: $(this).closest('tr').find('td:eq(5)').text(),
	            acc_name: $(this).closest('tr').find('td:eq(1)').text(),
				ro_name: $(this).closest('tr').find('td:eq(2)').text()
	        });
	    });

	    if (selectedReviews.length > 0) {
	        $('#saveBatchComment').data('review-ids', selectedReviews.map(review => review.id));
	        $('#saveSmartBatchComment').data('selected-reviews', selectedReviews);
	        console.log("팝업 열림: 리뷰 IDs", selectedReviews);
	        $('.overlay').show();
	        $('#batchCommentPopup').show();
	    } else {
	        alert('일괄 댓글을 작성할 리뷰를 선택하세요.');
	    }
	});
	
	// 템플릿 적용 버튼 클릭 이벤트
	$('.applyTemplate').on('click', function() {
	    const selectedReviews = $('#saveSmartBatchComment').data('selected-reviews');
	    const selectedTemplate = $(this).data('template');

	    // 각 리뷰마다 개별적으로 템플릿을 적용한 댓글을 생성
	    const generatedComments = selectedReviews.map(review => {
			// 이메일에서 앞 세 글자만 남기고 나머지는 *로 대체
			const emailParts = review.email.split('@');
			const emailMasked = emailParts[0].slice(0, 3) + '*'.repeat(emailParts[0].length - 3) + '@' + emailParts[1];
			
	        let comment = "";
	        switch (selectedTemplate) {
	            case 'template1':
	                comment = `안녕하세요 ${emailMasked} 님. ${review.acc_name}를 이용해주셔서 감사합니다. 더 좋은 서비스로 보답하겠습니다.`;
	                break;
	            case 'template2':
	                comment = `감사합니다 ${emailMasked}님! ${review.ro_name}은 잘 이용하셨나요? 다음에는 더 나은 ${review.acc_name}이 되도록 하겠습니다!`;
	                break;
	            case 'template3':
	                comment = `리뷰 작성 감사합니다 ${emailMasked} 님. 좋은 하루 되세요.`;
	                break;
	        }
	        return { id: review.id, comment: comment };
	    });

	    // 생성된 댓글을 저장 버튼에 저장하여 전송할 준비
	    $('#saveSmartBatchComment').data('generated-comments', generatedComments);
	    
	    // 콘솔에 각 리뷰마다 생성된 템플릿 내용을 확인
	    generatedComments.forEach(review => {
	        console.log(`리뷰 ID: ${review.id} | 템플릿 내용: ${review.comment}`);
	    });
	});

    // 팝업 닫기 (일반 댓글 작성)
    $('.close, #closeSinglePopup').on('click', function() {
        $('#singleCommentPopup').hide();
        $('.overlay').hide();
    });

    // 팝업 닫기 (일괄 댓글 작성)
    $('.close, #closeBatchPopup').on('click', function() {
        $('#batchCommentPopup').hide();
        $('.overlay').hide();
    });

	// 리뷰 상세 팝업 닫기 기능 추가
	$('#closeReviewPopup, #closeReviewPopupBtn').on('click', function() {
	    $('#reviewDetailPopup').hide();
	    $('.overlay').hide();
	});
	
    // 저장 버튼 클릭 이벤트 (개별 댓글 작성)
    $('#saveSingleComment').on('click', function() {
        const rev_comment = $('#singleCommentText').val();
        const rev_id = $(this).data('review-id');

        console.log("rev_id:", rev_id);
        console.log("rev_comment:", rev_comment);
        
        $.ajax({
            url: '/accommodation/updateReviews',
            type: 'POST',
            data: { rev_comment: rev_comment, rev_id: rev_id },
            success: function(response) {
                alert('댓글이 저장되었습니다.');
                $('#singleCommentPopup').hide();
                $('.overlay').hide();
                location.reload();
            },
            error: function(error) {
                console.error("Error saving comment:", error);
            }
        });
    });

    // 저장 버튼 클릭 이벤트 (일괄 댓글 작성)
    $('#saveBatchComment').on('click', function() {
        const rev_comment = $('#batchCommentText').val();
        const reviewIds = $(this).data('review-ids');

        console.log("reviewIds:", reviewIds);
        console.log("rev_comment:", rev_comment);
        
        $.ajax({
            url: '/accommodation/batchUpdateReviews',
            type: 'POST',
            data: JSON.stringify({ reviewIds: reviewIds, rev_comment: rev_comment }),
            contentType: 'application/json',
            success: function(response) {
                alert('댓글이 일괄 등록되었습니다.');
                $('#batchCommentPopup').hide();
                $('.overlay').hide();
                location.reload();
            },
            error: function(error) {
                console.error("Error saving comments:", error);
                alert('댓글 일괄 등록 중 오류가 발생했습니다.');
            }
        });
    });
	
	// 스마트 댓글 저장 버튼 클릭 이벤트
	$('#saveSmartBatchComment').on('click', function() {
	    const generatedComments = $(this).data('generated-comments');

	    // 리뷰 ID와 각 리뷰의 댓글 내용을 서버로 전송
	    const reviewData = generatedComments.map(review => ({
	        rev_id: review.id,
	        rev_comment: review.comment
	    }));

	    console.log("전송할 데이터:", reviewData);
	    
	    $.ajax({
	        url: '/accommodation/smartBatchUpdateReviews',
	        type: 'POST',
	        data: JSON.stringify({ reviews: reviewData }),
	        contentType: 'application/json',
	        success: function(response) {
	            alert('스마트 댓글이 일괄 등록되었습니다.');
	            $('#batchCommentPopup').hide();
	            $('.overlay').hide();
	            location.reload();
	        },
	        error: function(error) {
	            console.error("Error saving comments:", error);
	            alert('스마트 댓글 일괄 등록 중 오류가 발생했습니다.');
	        }
	    });
	});
});

//리뷰 삭제하기
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