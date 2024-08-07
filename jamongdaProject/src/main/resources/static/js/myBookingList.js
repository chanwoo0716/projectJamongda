$(document).ready(function() {
    // 체크박스 클릭 시 필터링
    $('input[type="checkbox"]').on('change', function() {
        var isChecked = $(this).is(':checked');
        if (isChecked) {
            $('.booking-card').each(function() {
                var showBooking = $(this).find('#review-button').length > 0;
                $(this).toggle(showBooking);
            });
        } else {
            $('.booking-card').show(); // 모든 예약 내역 표시
        }
    });
});

function openPopup(boNumber) {
    var url = '/mypage/myBookingDetails.do?number=' + encodeURIComponent(boNumber);
	
	var popupW = 600;
	var popupH = 700;
	var top = Math.ceil((window.screen.height - popupH)/2);
	var left = Math.ceil((window.screen.width - popupW)/2);
	var option='width=' + popupW + ',height=' + popupH + ',scrollbars=yes, top=' + top + ',left=' + left + ', resizable=yes';
	
    window.open(url, 'popup', option);
}