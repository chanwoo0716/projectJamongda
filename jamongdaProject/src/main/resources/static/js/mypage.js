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