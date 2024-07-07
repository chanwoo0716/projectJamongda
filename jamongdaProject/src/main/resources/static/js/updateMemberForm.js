function updatePwd(){
	let pwd=document.querySelector("#pwd");
	let pwdChk=document.querySelector("#pwdChk");
	let updatePwdBtn=document.querySelector("#updatePwdBtn");
	pwd.disabled=false;
	pwdChk.disabled=false;
	pwdChk.hidden=false;
	updatePwdBtn.textContent="변경하기"
}

function findPostCode(){
	new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('postCode').value=data.zonecode;
            document.getElementById('address').value=data.address;
        }
    }).open();
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