//수정폼
$(document).ready(function() {
    // DOM 요소가 존재하는지 확인
    var editorElement = document.getElementById("editorTxt");
    if (editorElement) {
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "editorTxt",
            sSkinURI: "/smartEditor/SmartEditor2Skin.html",
            fCreator: "createSEditor2"
        });

        document.getElementById("smartEditor").onsubmit = function() {
			oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);

		    var content = document.getElementById("editorTxt").value;
		    var title = document.querySelector("input[name='title']").value;

		    // 디버깅 로그
		    console.log('제목:', title);
		    console.log('내용:', content);

		    if (title === '') {
		        alert("제목을 입력해 주세요!");
		        return false;
		    }

		    if (content === '') {
		        alert("내용을 입력해 주세요!");
		        return false;
		    }

		    return true;
        };
    } else {
        console.error('Element with id "editor" does not exist.');
    }
});

//리스트로 돌아가기
function backToList(obj){
	obj.action="/board/boardList.do";
	obj.submit();
}