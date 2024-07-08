//상세글 
function fn_modBoardForm(modBoardForm){
	location.href='modBoardForm.do';
}

function fn_romove_board(url, boardId){
	//동적으로 form 태그 생성
	let del_form=document.createElement("form");
	
	del_form.setAttribute("action",url);
	del_form.setAttribute("method", "post");
	
	//동적으로 input태그 생성
	let BoardNoInput=document.createElement("input");
	
	BoardNoInput.setAttribute("type","hidden");
	BoardNoInput.setAttribute("name", "boardId");
	BoardNoInput.setAttribute("value", boardId);
	del_form.appendChild(BoardNoInput);
	document.body.appendChild(del_form);
	del_form.submit();
}