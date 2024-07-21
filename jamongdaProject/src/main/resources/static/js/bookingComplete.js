function getToday(date) {
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    let today = new Date(date).getDay();
    let day = week[today];
    return day;
}

$(document).ready(function() {
    let dayOfWeek_checkIn = getToday(checkInDate);
    let dayOfWeek_checkOut = getToday(checkOutDate);
    let dayOfWeek_pay = getToday(payDate);
    let checkInDateElement = document.querySelector('#checkInDateDay');
    let checkOutDateElement = document.querySelector('#checkOutDateDay');
    let payDateElement = document.querySelector('#payDateDay');
    if (checkInDateElement) {
        checkInDateElement.textContent += ` (${dayOfWeek_checkIn})`;
    }
    if (checkOutDateElement) {
        checkOutDateElement.textContent += ` (${dayOfWeek_checkOut})`;
    }
    if (payDateElement) {
        payDateElement.textContent += ` (${dayOfWeek_pay})`;
    }
});