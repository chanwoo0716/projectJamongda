$(document).ready(function(){
	function formatNumber(number) {
	    return number.toLocaleString() + '원';
	}
	
	let priceElement = document.querySelector('#bo_price');

	priceElement.textContent = formatNumber(bookingPrice);
	
    $('#close-btn').click(function(){
        window.close();
    });
});