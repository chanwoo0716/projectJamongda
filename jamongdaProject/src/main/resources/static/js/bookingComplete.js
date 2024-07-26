function getToday(date) {
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    let today = new Date(date).getDay();
    let day = week[today];
    return day;
}

function formatNumber(number) {
    return number.toLocaleString(); // 기본적으로 쉼표를 사용하여 숫자를 포맷팅
}

$(document).ready(function() {
    // 요일 추가
    let dayOfWeek_checkIn = getToday(checkInDate);
    let dayOfWeek_checkOut = getToday(checkOutDate);
    let dayOfWeek_pay = getToday(payDate);

    let checkInDateElement = document.querySelector('#checkInDateDay');
    let checkOutDateElement = document.querySelector('#checkOutDateDay');
    let payDateElement = document.querySelector('#payDateDay');
    let priceElement = document.querySelector('#bo_price');

    if (checkInDateElement) {
        checkInDateElement.textContent += ` (${dayOfWeek_checkIn})`;
    }
    if (checkOutDateElement) {
        checkOutDateElement.textContent += ` (${dayOfWeek_checkOut})`;
    }
    if (payDateElement) {
        payDateElement.textContent += ` (${dayOfWeek_pay})`;
    }
    if (priceElement) {
        priceElement.textContent = formatNumber(bookingPrice); // 결제 금액 포맷팅
    }
});
