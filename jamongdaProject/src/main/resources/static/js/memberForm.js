function sendNumber() {
    var email = document.getElementById('email').value;
    // 서버로 이메일 전송 및 인증번호 요청 처리 로직 추가
    // 예시: 서버로 이메일을 전송하여 인증번호를 받는 Ajax 요청
    // Ajax 요청 예시:
    // $.ajax({
    //     type: 'GET',
    //     url: '/sendVerificationEmail',
    //     data: { email: email },
    //     success: function(response) {
    //         alert('인증번호가 이메일로 발송되었습니다.');
    //     },
    //     error: function(error) {
    //         alert('이메일 발송 실패: ' + error.responseJSON.message);
    //     }
    // });
}
function confirmNumber() {
    var number = document.getElementById('number').value;
    document.getElementById('Confirm').value = number;
    // 서버로 인증 번호 확인 처리 로직 추가
    // 폼 submit을 통해 서버로 데이터 전송
    document.frmMember.submit();
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
	} else {
		pwdChk_msg.textContent = "비밀번호가 일치하지 않습니다."
		pwdChk_msg.className = "chkFail";
		submitBtn.disabled = true;
	}
};

function validateBtn(){
	console.log("validateBtn함수호출");
	let pwd = document.getElementById("pwd").value;
	let pwdChk = document.getElementById("pwdChk").value;
	if(pwd == pwdChk){
		alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
		return false;
	}
	return true;
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

document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('form[name="frmMember"]').addEventListener('submit', function(event) {
        event.preventDefault(); // 폼 제출 기본 동작 막기

        let address1 = document.getElementById('address1').value;
        let address2 = document.getElementById('address2').value;
        let fullAddress = address1 + ' ' + address2;

        // 숨겨진 필드에 결합된 주소 설정
        document.getElementById('address').value = fullAddress;

        // 실제 폼 제출
        this.submit();
    });
});
