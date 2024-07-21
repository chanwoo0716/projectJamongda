$(document).ready(function() {	
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

        // 이용자 이름 입력 체크
        if ($('#bo_name').val().trim() === '') {
            alert("이용자 정보를 입력해야 결제를 진행할 수 있습니다.");
            return;
        }

        // 약관 동의 체크
        if (!$('#allAgree-btn').is(':checked')) {
            alert("약관에 모두 동의해야 결제를 진행할 수 있습니다.");
            return;
        }
		
        // 개별 약관 동의 체크
        if (!$('#agree-btn1').is(':checked') || !$('#agree-btn2').is(':checked') || !$('#agree-btn3').is(':checked')) {
            alert("필수 약관에 모두 동의해야 결제를 진행할 수 있습니다.");
            return;
        }

        // 이용자 전화번호 입력 체크
        if ($('#bo_tel').val().trim() === '') {
            alert("이용자 전화번호를 입력해야 결제를 진행할 수 있습니다.");
            return;
        }

        // 모든 조건 충족 시 결제 진행
        payment();
    });

    function payment() {
        // 결제 로직 구현
        IMP.init('imp81466281'); // 아임포트 관리자 콘솔에서 확인한 '가맹점 식별코드' 입력
        IMP.request_pay({
            pg: "kakaopay", // pg사명 or pg사명.CID
            pay_method: "card", // 시물 방법
            merchant_uid: "order_" + new Date().getTime(), // 가맹점 주문번호 (중복되지 않은 임의의 문자열 입력)
            name: $('#acc_name_hidden').val(), // 결제창에 노출될 상품명
            amount: $('#bo_price').val(), // 금액
            buyer_email: $('#guestName').val(),
            buyer_name: $('#bo_name').val(),
            buyer_tel: $('#bo_tel').val()
        }, function(rsp) {
            if (rsp.success) {
                // 결제 성공 시 예약 정보 서버로 전송
                let bookingData = {
                    bo_name: $('#bo_name').val(),
                    bo_tel: $('#bo_tel').val(),
                    bo_checkIn: $('#bo_checkIn_hidden').val(),
                    bo_checkOut: $('#bo_checkOut_hidden').val(),
                    payDate: new Date(),
                    bo_payment: "카카오페이", //실제로는 선택지를 줘야하지만 현재는 카카오페이 결제만 구현
                    bo_price: $('#bo_price').val(), 
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
                    error: function(error) {
                        alert("예약 처리 중 오류가 발생했습니다.");
                    }
                });
            } else {
                alert("결제 실패: 코드(" + rsp.error_code + ") / 메시지(" + rsp.error_msg + ")");
            }
        });
    }
});