let upCheck=false;
let pwdCheck=true;
let addressCheck=true;

function updatePwd() {
	upCheck=true;
    let pwd = document.querySelector("#pwd");
    let pwdChk = document.querySelector("#pwdChk");
    let pwdChk2 = document.querySelector("#pwdChk2");
	let submitPwd = document.querySelector("#submitPwd");
	let resetPwd = document.querySelector("#resetPwd");
    pwd.disabled = false;
    pwdChk.hidden = false;
    pwdChk2.hidden = false;
    submitPwd.hidden = false;
    resetPwd.hidden = false;
	pwdCheck=false; //비밀번호 변경 버튼 누르면 확인 누르기 전까지 X
}

function pwd_Chk() {
    let pwd = document.getElementById("pwd").value;
    let pwdChk = document.getElementById("pwdChk").value;
    let pwdChk_msg = document.getElementById("pwdChk_msg");
    let submitBtn = document.getElementById("submitBtn");

    if (pwd === pwdChk) {
        pwdChk_msg.textContent = "비밀번호가 일치합니다.";
        pwdChk_msg.className = "chkSuccess";
        submitBtn.disabled = false;
    } else {
        pwdChk_msg.textContent = "비밀번호가 일치하지 않습니다.";
        pwdChk_msg.className = "chkFail";
        submitBtn.disabled = true;
    }
}

function validatePwd() {
    let pwd = document.getElementById("pwd").value;
    let pwdChk = document.getElementById("pwdChk").value;

    if (pwd !== pwdChk) {
        alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
		pwdCheck=false;
    }else if (pwd == pwdChk) {
        alert("비밀번호 변경이 완료되었습니다.");
		pwdCheck=true;
	}
}

function reset_Pwd(){ //비밀번호 변경 취소
	let pwd = document.querySelector("#pwd");
	let pwdChk = document.querySelector("#pwdChk");
	let pwdChk2 = document.querySelector("#pwdChk2");
	let submitPwd = document.querySelector("#submitPwd");
	let resetPwd = document.querySelector("#resetPwd");
	pwd.disabled = true;
	pwdChk.hidden = true;
	pwdChk2.hidden = true;
	submitPwd.hidden = true;
	resetPwd.hidden = true;
	pwdCheck=true;
}

function validateBtn() {
	let pwd = document.querySelector("#pwd");
	let pwdChk1 = document.querySelector("#pwdChk");
	if (!phoneCheck) {
	    alert("전화번호 인증이 완료되지 않았습니다.");
	    return false;
	}
	if(!pwdChk1){
		pwdChk1.disabled=true;
	}
	if(addressCheck==false){
		alert("주소 변경이 완료되지 않았습니다. 변경하기 버튼을 눌러 변경된 주소를 저장해주세요.");
		return false;
	}else if(pwdCheck){
		pwd.disabled=false;
		alert("수정이 완료되었습니다.");	
		return true;
	};
	
	if(pwdCheck==false){
		alert("비밀번호 변경이 완료되지 않았습니다. 변경하기 버튼을 눌러 변경된 비밀번호를 저장해주세요.");
		return false;
	}else if(addressCheck){
		alert("수정이 완료되었습니다.");	
		return true;
	}
}

function showPassword(id) {
    document.getElementById(id).type = 'text';
}

function hidePassword(id) {
    document.getElementById(id).type = 'password';
}

function findPostCode(){
	// Hidden 속성을 변경하여 필드를 보이게 설정
    document.getElementById('postCode').hidden = false;
    document.getElementById('address1').hidden = false;
    document.getElementById('address2').hidden = false;
    document.getElementById('address').hidden = true;
	new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('postCode').value=data.zonecode;
            document.getElementById('address1').value=data.address;
        }
    }).open();
	addressCheck=false;
}

function updateAdd() {
    let address1 = document.getElementById('address1').value;
    let address2 = document.getElementById('address2').value;
    let address = address1 + ' ' + address2;

    // 숨겨진 필드에 결합된 주소 설정
    document.getElementById('address').value = address;
    alert("주소가 업데이트되었습니다: " + address);
	console.log(address);
	addressCheck=true;
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