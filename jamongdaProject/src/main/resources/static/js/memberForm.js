let phoneCheck = false; // 휴대폰 인증 여부를 저장하는 변수 추가
let pwdCheck = false;	// 비밀번호 확인 여부
let emailCheck = false; // 이메일 인증완료 여부
let emailChecked = false; // 이메일 중복확인 완료 여부를 저장하는 변수 추가

$(document).ready(function() {
    // 이메일 중복 확인 버튼 클릭 시
    $('#checkEmailBtn').click(function() {
        checkEmail(); // 이메일 중복 확인 함수 호출
    });

    // 이메일 인증 버튼 클릭 시
    $('#mail-Check-Btn').click(function() {
        // 이메일 중복 확인 완료 여부 확인
        if (!emailChecked) {
            alert('이메일 중복 확인을 먼저 해주세요.');
            return;
        }

        const emailUsername = $('#email_mem').val();
        const emailDomain = $('#email_domain').val();
        const email = emailUsername + '@' + emailDomain;

        $.ajax({
            type: 'GET',
            url: '/member/sendEmail',
            data: { email: email },
            success: function(data) {
                console.log("data : " +  data);
                $('.mail-check-input').prop('disabled', false);
                code = data;
                alert('인증번호가 전송되었습니다.');
            },
            error: function() {
                alert('이메일 확인 중 오류가 발생했습니다.');
            }
        });
    });

    // 이메일 인증 완료 후 입력란 blur 이벤트 처리
    $('.mail-check-input').blur(function () {
        const inputCode = $(this).val();
        const $resultMsg = $('#mail-check-warn');
        
        if (inputCode === code) {
            $resultMsg.text('인증번호가 일치합니다.');
            $resultMsg.css('color', 'green');
            $('#mail-Check-Btn').prop('disabled', true);
            $('#email_mem').attr('readonly', true);
            $('#email_domain').prop('disabled', true);
            emailChecked = true; // 이메일 중복확인 상태를 true로 변경
            emailCheck = true; // 이메일 인증 완료 상태를 true로 변경
        } else {
            $resultMsg.text('인증번호가 불일치합니다. 다시 확인해주세요.');
            $resultMsg.css('color', 'red');
        }
    });

    // 휴대폰 인증 버튼 클릭 시
    $('#phone-Check-Btn').click(function(){
        // 이메일 중복 확인 완료 여부 확인
        if (!emailChecked) {
            alert('이메일 중복 확인을 먼저 해주세요.');
            return;
        }

        let tel = $('#tel').val().replace(/[^0-9]/g, ''); // 하이픈 제거

        $.ajax({
            type: "GET",
            url: "/member/sendSMS",
            data: {
                "tel": tel
            },
            success: function(res){
                alert('인증번호 발송 완료!');

                // 인증 확인 버튼 클릭 시 처리
                $('#checkBtn').click(function(){
                    if($.trim(res) == $('#inputCertifiedNumber').val()){
                        alert('휴대폰 인증이 정상적으로 완료되었습니다.');
                        phoneCheck = true; // 휴대폰 인증 완료 상태를 true로 변경
                        emailChecked = true; // 휴대폰 인증 완료 시 이메일 인증도 완료된 것으로 처리

                        // 휴대폰 번호 업데이트 Ajax 요청
                        $.ajax({
                            type: "GET",
                            url: "/update/phone",
                            data: {
                                "tel": $('#tel').val().replace(/[^0-9]/g, '')
                            },
                            success: function() {
                                document.location.href="/main.do"; // 메인 페이지로 이동
                            },
                            error: function() {
                                // 오류 처리
                            }
                        });
                    } else {
                        alert('인증번호가 올바르지 않습니다!');
                    }
                });
            },
            error: function() {
                // 오류 처리
            }
        });
    });
});

// 이메일 도메인 선택에 따라 직접 입력 필드를 표시/숨김
function toggleCustomDomain() {
    var emailDomain = document.getElementById('email_domain').value;
    var customDomainInput = document.getElementById('custom_domain');
    if (emailDomain === 'direct') {
        customDomainInput.style.display = 'inline';
        customDomainInput.required = true;
    } else {
        customDomainInput.style.display = 'none';
        customDomainInput.required = false;
    }
}

// 이메일 전체 주소를 업데이트하는 함수
function updateEmail() {
    var emailUsername = document.getElementById('email_mem').value;
    var emailDomain = document.getElementById('email_domain').value;
    if (emailDomain === 'direct') {
        emailDomain = document.getElementById('custom_domain').value;
    }
    document.getElementById('email').value = emailUsername + '@' + emailDomain;
}

function checkEmail() {
    updateEmail();
    let email = document.getElementById("email").value;
    let emailChk_msg = document.getElementById("emailChk_msg");
    
    $.ajax({
        url: '/member/checkEmail',
        type: 'GET',
        data: { email: email },
        success: function(response) {
            if (!response) {
                emailChk_msg.textContent = "사용 가능한 이메일입니다.";
                emailChk_msg.className = "chkSuccess";
                document.getElementById("submitBtn").disabled = false;
                emailChecked = true; // 이메일 중복 확인 완료 상태를 true로 변경
                
                // 이메일 중복 확인 완료 후, 이메일 인증 버튼 활성화
                $('#mail-Check-Btn').prop('disabled', false);
            } else {
                emailChk_msg.textContent = "이미 사용 중인 이메일입니다.";
                emailChk_msg.className = "chkFail";
                document.getElementById("submitBtn").disabled = true;
                emailChecked = false; // 이메일 중복 확인 미완료 상태를 false로 유지
            }
        },
        error: function() {
            emailChk_msg.textContent = "이메일 확인 중 오류가 발생했습니다.";
            emailChk_msg.className = "chkFail";
            document.getElementById("submitBtn").disabled = true;
            emailChecked = false; // 이메일 중복 확인 미완료 상태를 false로 유지

        }
    });
}

function pwd_Chk() {
    console.log("pwdChk함수호출");
    let pwd = document.getElementById("pwd").value;
    let pwdChk = document.getElementById("pwdChk").value;
    let pwdChk_msg = document.getElementById("pwdChk_msg");
    let submitBtn = document.getElementById("submitBtn");
    if (pwd == pwdChk) {
        pwdChk_msg.textContent = "비밀번호가 일치합니다."
        pwdChk_msg.className = "chkSucess";
        submitBtn.disabled = false;
        pwdCheck = true;
    } else {
        pwdChk_msg.textContent = "비밀번호가 일치하지 않습니다."
        pwdChk_msg.className = "chkFail";
        submitBtn.disabled = true;
        pwdCheck = false;
    }
};

function validateForm() {
    // 필수 입력 사항 유효성 검사 및 이메일 중복 체크 
    if (emailCheck && emailChecked && pwdCheck && phoneCheck) {
        // 폼 제출을 허용하기 위해 true를 반환
        let address1 = document.getElementById('address1').value;
        let address2 = document.getElementById('address2').value;
        let address = address1 + ' ' + address2;
        
        // 숨겨진 필드에 결합된 주소 설정
        document.getElementById('address').value = address;
        alert("가입이 완료되었습니다. 로그인 후 이용해주세요.");
        return true;
        
    } else {
        if (!emailChecked) {
            alert("이메일 중복 확인을 해주세요.");
        } else if (!emailCheck) {
            alert("이메일 인증을 완료해주세요.");
		}else if (!pwdCheck) {
            alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        } else if (!phoneCheck) {
            alert("휴대폰 인증을 먼저 완료해주세요.");
        }
    }
    return false;
}

function showPassword(id) {
    document.getElementById(id).type = 'text';
}

function hidePassword(id) {
    document.getElementById(id).type = 'password';
}

function oninputTel(target) {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/(\d{2,3})(\d{3,4})(\d{4})/, "$1-$2-$3");
}

function oninputBirth(target) {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
}

function findPostCode() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('postCode').value = data.zonecode;
            document.getElementById('address1').value = data.address;
        }
    }).open();
}