//에디터 설정
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
            // 에디터 내용 동기화
            oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
			
			// 동기화된 내용을 확인 (디버깅용)
			var content = document.getElementById("editorTxt").value;
           console.log('에디터 동기화 내용:', content);

           if (editorTxt = '') {
               alert("내용을 입력해 주세요!");
               return false;
           }
            return true;
        };
    } else {
        console.error('Element with id "editor" does not exist.');
    }
});