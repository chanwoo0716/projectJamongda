$(document).ready(function() {
    $('.refund-link').on('click', function(event) {
        event.preventDefault();

        var bookingNumber = $(this).data('bo_number'); // 예약 번호
        var imp_uid = $(this).data('imp_uid'); // 결제 ID
        var bo_price = $(this).data('bo_price'); // 결제 금액
        var refundUrl = $(this).attr('href'); // 결제 취소 요청 URL

        // AJAX 요청
        $.ajax({
            url: refundUrl,
            type: 'GET',
            data: { impNumber: imp_uid, number: bookingNumber, price: bo_price },
            success: function(response) {
                console.log('Response:', response); // 응답 로그
                alert('예약이 취소되었습니다.');
                window.location.reload();
            },
            error: function(xhr, status, error) {
                console.error('서버 응답 상태:', xhr.status);
                console.error('서버 응답 텍스트:', xhr.responseText);
                console.error('상태:', status);
                console.error('오류 메시지:', error);
                alert('예약 취소 중 오류가 발생했습니다.');
            }
        });
    });
});

function openPopup(boNumber) {
    var url = '/mypage/myBookingDetails.do?number=' + encodeURIComponent(boNumber);
	
	// 팝업창 위치 조정하기
	var popupW = 600;
	var popupH = 700;
	var top = Math.ceil((window.screen.height - popupH)/2);
	var left = Math.ceil((window.screen.width - popupW)/2);
	var option='width=' + popupW + ',height=' + popupH + ',scrollbars=yes, top=' + top + ',left=' + left + ', resizable=yes';
	
    window.open(url, 'popup', option);
}