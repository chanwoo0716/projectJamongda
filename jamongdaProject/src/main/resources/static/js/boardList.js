// 공지 글 목록 폼으로 이동하는 함수
function fn_boardForm(boardForm) {
    location.href = 'boardForm.do';
}

// 페이지가 로드될 때 실행
document.addEventListener('DOMContentLoaded', function() {
    // 세션 스토리지에서 스크롤 위치를 가져옴
    const scrollPosition = sessionStorage.getItem('scrollPosition');
    
    // 스크롤 위치가 저장되어 있으면 해당 위치로 스크롤
    if (scrollPosition) {
        window.scrollTo(0, parseInt(scrollPosition));
        // 스크롤 위치를 복원한 후에는 세션 스토리지에서 제거
        sessionStorage.removeItem('scrollPosition');
    }
});
