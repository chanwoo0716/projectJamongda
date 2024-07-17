$(document).ready(function() {
	$('#eqaulMemInfo').click(function(){
		if($(this).is(':checked')){
			let guestName=$('#guestName').text();
			let guestTel=$('#guestTel').text();
			$('#bo_name').val(guestName);
			$('#bo_tel').val(guestTel);
		}else{
			$('#bo_name').val('');
			$('#bo_tel').val('');		
		}
	});
	
	$('#bo_tel').on('input',function(){
		let tel=$(this).val(); //입력받은 값
		let bo_tel = tel.replace(/[^0-9]/g, '').replace(/(\d{2,3})(\d{3,4})(\d{4})/, "$1-$2-$3"); //전화번호 형식으로 바꿈
		
		$(this).val(bo_tel);
	})
})
