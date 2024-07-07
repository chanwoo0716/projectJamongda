
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

function oninputTel(target) {
	target.value = target.value
		.replace(/[^0-9]/g, '')
		.replace(/(\d{2,3})(\d{3,4})(\d{4})/, "$1-$2-$3");
}

function oninputRegNum(target) {
	target.value = target.value
		.replace(/[^0-9]/g, '')
		.replace(/(\d{3})(\d{2})(\d{5})/, "$1-$2-$3");
}

