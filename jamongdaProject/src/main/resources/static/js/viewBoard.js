//상세글 
function fn_modBoardForm(obj) {
	obj.action="/board/modBoardForm.do";
	obj.method="GET";
	obj.submit();
}

function fn_remove_board(url, board_id){
	//동적으로 form 태그 생성
	let del_form=document.createElement("form");
	
	del_form.setAttribute("action",url);
	del_form.setAttribute("method", "post");
	
	//동적으로 input태그 생성
	let BoardNoInput=document.createElement("input");
	
	BoardNoInput.setAttribute("type","hidden");
	BoardNoInput.setAttribute("name", "board_id");
	BoardNoInput.setAttribute("value", board_id);
	del_form.appendChild(BoardNoInput);
	document.body.appendChild(del_form);
	del_form.submit();
}
