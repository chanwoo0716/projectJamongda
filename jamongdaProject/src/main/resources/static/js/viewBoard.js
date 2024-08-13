// 페이지가 로드될 때 실행
document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('#boardSidebar a');
    
    // 사이드바에서 현재 활성화된 링크에 'active' 클래스 추가
    links.forEach(link => {
        link.addEventListener('click', function() {
            links.forEach(link => link.classList.remove('active'));
            this.classList.add('active');
        });

        // 현재 URL이 링크의 href와 일치하는 경우 'active' 클래스 추가
        if (window.location.href.includes(link.href)) {
            link.classList.add('active');
        }
    });

    // 이전 페이지로 이동하는 버튼에 클릭 이벤트 연결
	const backButton = document.querySelector('.back-button');
	    
    if (backButton) {
        backButton.addEventListener('click', function(e) {
            e.preventDefault(); // 기본 동작을 막고 커스텀 동작 수행
            scrollPositionSave();
            window.history.back(); // 이전 페이지로 이동
        });
    } else {
        console.error("이전 페이지로 이동하는 버튼을 찾을 수 없습니다.");
    }
});

// 게시글 수정 폼으로 이동하는 함수
function fn_modBoardForm(obj) {
    obj.action = "/board/modBoardForm.do";
    obj.method = "GET";
    obj.submit();
}

// 게시글 삭제를 위한 함수
function fn_remove_board(url, board_id) {
    // 동적으로 폼 태그 생성
    let del_form = document.createElement("form");
    del_form.setAttribute("action", url);
    del_form.setAttribute("method", "post");

    // 동적으로 input 태그 생성
    let BoardNoInput = document.createElement("input");
    BoardNoInput.setAttribute("type", "hidden");
    BoardNoInput.setAttribute("name", "board_id");
    BoardNoInput.setAttribute("value", board_id);
    del_form.appendChild(BoardNoInput);

    document.body.appendChild(del_form);
    del_form.submit();
}

// 스크롤 위치를 세션 스토리지에 저장하는 함수
function scrollPositionSave() {
    sessionStorage.setItem('scrollPosition', window.scrollY);
}
