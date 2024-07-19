function getToday(date) {
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    let today = new Date(date).getDay();
    let day = week[today];
    return day;
}

$(document).ready(function() {
    let dayOfWeek = getToday(payDate);
    let payDateElement = document.querySelector('.payDateDay');
    if (payDateElement) {
        payDateElement.textContent += ` (${dayOfWeek})`;
    }
});