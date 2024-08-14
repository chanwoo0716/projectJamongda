$(document).ready(function() {
    // 전체 선택/해제
    $('#selectAll').on('click', function() {
        $('.rowCheckbox').prop('checked', this.checked);
    });

    // 선택 삭제
    $('#delReviewBtn').on('click', function() {
        if ($('.rowCheckbox:checked').length > 0) {
            if (confirm('리뷰를 삭제하시겠습니까?')) {
                $('.rowCheckbox:checked').closest('tr').remove();
            }
        } else {
            alert('삭제할 리뷰를 선택하세요.');
        }
    });
});