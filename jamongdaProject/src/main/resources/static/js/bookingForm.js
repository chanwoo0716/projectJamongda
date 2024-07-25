$(document).ready(function() {
    function parseDate(dateString) {
        return new Date(dateString);
    }
	
	// string인 체크인체크아웃 날짜를 Date 로 변환
	checkInDate2=parseDate(checkInDate);
	checkOutDate2=parseDate(checkOutDate);

    function getDayOfWeek(date) {
        const week = ['일', '월', '화', '수', '목', '금', '토'];
        const day = new Date(date).getDay();
        return week[day];
    }
	
	
    function updateDateAndDay() {
        // 체크인 및 체크아웃 날짜와 요일 업데이트
        $('#checkInDateDay').text(checkInDate2.toISOString().slice(0, 10) + ' (' + getDayOfWeek(checkInDate) + ')');
        $('#checkOutDateDay').text(checkOutDate2.toISOString().slice(0, 10) + ' (' + getDayOfWeek(checkOutDate) + ')');
    }

	// 숫자 포맷팅 세자리마다 ,넣기
    function formatNumber(number) {
        return number.toLocaleString();
    }

    function updateTotalPrice() {
        let checkIn = new Date(checkInDate);
        let checkOut = new Date(checkOutDate);

        // 날짜 유효성 검증
        if (isNaN(checkIn.getTime()) || isNaN(checkOut.getTime())) {
            console.error("날짜 형식이 올바르지 않습니다.");
            return;
        }

        if (checkIn >= checkOut) {
            console.error("체크인 날짜가 체크아웃 날짜와 같거나 이후입니다.");
            return;
        }

        // 날짜 차이 계산 (하루 단위)
        let diffTime = checkOut - checkIn;
        let diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

        if (diffDays <= 0) {
            console.error("예약 기간이 유효하지 않습니다.");
            return;
        }

        // 총 결제 금액 계산
        let totalPrice = oneNightPrice * diffDays;

        // 총 결제 금액 포맷팅
        $('#totalPrice').text(formatNumber(totalPrice));

		// 포맷팅된 값으로 설정
		$('#oneday-price').text(formatNumber(oneNightPrice));
    }

    // 날짜 값이 로드된 후 총 결제 금액 업데이트
    updateDateAndDay();
    updateTotalPrice();

    $('#equalMemInfo').click(function() {
        if ($(this).is(':checked')) {
            let guestName = $('#guestName').text();
            let guestTel = $('#guestTel').text();
            $('#bo_name').val(guestName);
            $('#bo_tel').val(guestTel);
        } else {
            $('#bo_name').val('');
            $('#bo_tel').val('');
        }
    });

    $('.checkbox-group').on('click', '#allAgree-btn', function() {
        let checked = $(this).is(':checked');
        if (checked) {
            $(this).parents('.checkbox-group').find('input').prop('checked', true);
        } else {
            $(this).parents('.checkbox-group').find('input').prop('checked', false);
        }
    });

    $('.checkbox-group').on('click', '#agree-btn1, #agree-btn2, #agree-btn3', function() {
        let allChecked = true;
        $('.checkbox-group .check-btn').each(function() {
            if (!$(this).is(':checked')) {
                allChecked = false;
            }
        });
        $('#allAgree-btn').prop('checked', allChecked);
    });

    $('#bo_tel').on('input', function() {
        let tel = $(this).val(); // 입력받은 값
        let bo_tel = tel.replace(/[^0-9]/g, '').replace(/(\d{2,3})(\d{3,4})(\d{4})/, "$1-$2-$3"); // 전화번호 형식으로 바꿈
        $(this).val(bo_tel);
    });

    $('#payBtn').click(function(e) {
        e.preventDefault(); // 기본 폼 제출을 방지

        if ($('#bo_name').val().trim() === '') {
            alert("이용자 정보를 입력해야 결제를 진행할 수 있습니다.");
            return;
        }

        if (!$('#allAgree-btn').is(':checked')) {
            alert("약관에 모두 동의해야 결제를 진행할 수 있습니다.");
            return;
        }

        if (!$('#agree-btn1').is(':checked') || !$('#agree-btn2').is(':checked') || !$('#agree-btn3').is(':checked')) {
            alert("필수 약관에 모두 동의해야 결제를 진행할 수 있습니다.");
            return;
        }

        if ($('#bo_tel').val().trim() === '') {
            alert("이용자 전화번호를 입력해야 결제를 진행할 수 있습니다.");
            return;
        }

        payment();
    });

	function payment() {
	    IMP.init('imp81466281');
	    IMP.request_pay({
	        pg: "kakaopay",
	        pay_method: "card",
	        merchant_uid: "order_" + new Date().getTime(),
	        name: $('#acc_name_hidden').val(),
	        amount: $('#totalPrice').text().replace(' 원', '').replace(/,/g, ''), // 쉼표 제거
	        buyer_email: $('#guestName').val(),
	        buyer_name: $('#bo_name').val(),
	        buyer_tel: $('#bo_tel').val()
	    }, function(rsp) {
	        if (rsp.success) {
	            let totalPrice = $('#totalPrice').text().replace(' 원', '').replace(/,/g, ''); // 쉼표 제거
	            let bookingData = {
	                bo_name: $('#bo_name').val(),
	                bo_tel: $('#bo_tel').val(),
	                bo_checkIn: $('#bo_checkIn_hidden').val(),
	                bo_checkOut: $('#bo_checkOut_hidden').val(),
	                payDate: new Date().toISOString(), // 결제 날짜를 ISO 문자열로 변환
	                bo_payment: "카카오페이",
	                bo_price: parseInt(totalPrice, 10), // 문자열을 숫자로 변환
	                ro_id: $('#ro_id').val(),
	                email: $('#email').val()
	            };

	            $.ajax({
	                url: "/booking/insertBooking.do",
	                method: "POST",
	                contentType: "application/json",
	                data: JSON.stringify(bookingData),
	                success: function(response) {
	                    alert("예약이 완료되었습니다.");
	                    let url = "/booking/bookingComplete.do?bo_number=" + response.bo_number +
	                              "&aname=" + $('#acc_name_hidden').val() +
	                              "&rid=" + $('#ro_id').val();
	                    window.location.href = url;
	                },
	                error: function(xhr, status, error) {
	                    console.error("예약 처리 중 오류가 발생했습니다:", xhr.responseText);
	                    console.error("상태:", status);
	                    console.error("오류 메시지:", error);
	                    alert("예약 처리 중 오류가 발생했습니다.");
	                }
	            });
	        } else {
	            alert("결제 실패: 코드(" + rsp.error_code + ") / 메시지(" + rsp.error_msg + ")");
	        }
	    });
	}

});