$(function () {
	//페이징
	var itemsPerPage = 10;
	var currentPage = 1;
	var accItems = $(".acc_box");

	function showPage(page) {
		var start = (page - 1) * itemsPerPage;
		var end = start + itemsPerPage;

		accItems.hide().slice(start, end).show();

		renderPagination();
	}

	function renderPagination() {
		var totalPages = Math.ceil(accItems.length / itemsPerPage);
		var paginationContainer = $(".pagination");
		paginationContainer.empty();

		for (var i = 1; i <= totalPages; i++) {
			var button = $("<button>").text(i).attr("data-page", i);
			if (i === currentPage) {
				button.addClass("active");
			}
			paginationContainer.append(button);
		}
	}

	$(".pagination").on("click", "button", function () {
		currentPage = parseInt($(this).attr("data-page"));
		showPage(currentPage);
	});

	showPage(currentPage);

	// 로컬 저장소에서 날짜 값 가져오기
	var startDate = localStorage.getItem('startDate');
	var endDate = localStorage.getItem('endDate');

	$('#bo_checkIn').daterangepicker({
		timePicker: false,
		startDate: startDate ? moment(startDate) : moment().startOf('day'),
		endDate: endDate ? moment(endDate) : moment().startOf('day').add(7, 'days'),
		minDate: moment().startOf('day'),
		locale: {
			format: 'YYYY-MM-DD'
		}
	}, function (start, end) {
		// 선택된 날짜 범위를 로컬 저장소에 저장
		localStorage.setItem('startDate', start.format('YYYY-MM-DD'));
		localStorage.setItem('endDate', end.format('YYYY-MM-DD'));
		$('input[name="checkIn"]').val(start.format('YYYY-MM-DD'));
		$('input[name="checkOut"]').val(end.format('YYYY-MM-DD'));
	});
	
	var acc_name = localStorage.getItem('acc_name');
	var acc_area = localStorage.getItem('acc_area');
	
	if (acc_name) {
		document.getElementById('acc_name').value = acc_name;
	} else {
		document.getElementById('acc_name').value = '';
	}
	if (acc_area) {
		document.getElementById('acc_area').value = acc_area;
	} else {
		document.getElementById('acc_area').value = '';
	}

	$('#form2').on('submit', function (event) {
		// 로컬 저장소에 저장된 데이터를 클리어하지 않도록 주의
		var acc_name = document.getElementById('acc_name').value;
		var acc_area = document.getElementById('acc_area').value;
		localStorage.setItem('acc_name', acc_name);
		localStorage.setItem('acc_area', acc_area);
		localStorage.setItem('startDate', $('#bo_checkIn').data('daterangepicker').startDate.format('YYYY-MM-DD'));
		localStorage.setItem('endDate', $('#bo_checkIn').data('daterangepicker').endDate.format('YYYY-MM-DD'));
	});

	// 초기 값 설정 (필요시)
	var initialStartDate = $('input[name="datetimes"]').data('daterangepicker').startDate.format('YYYY-MM-DD');
	var initialEndDate = $('input[name="datetimes"]').data('daterangepicker').endDate.format('YYYY-MM-DD');
	$('input[name="checkIn"]').val(initialStartDate);
	$('input[name="checkOut"]').val(initialEndDate);
	
	// 서버에서 초기 데이터 불러오기
	updateInitialPricesFromServer();

	// 폼 제출 함수
	function submitForm() {
		document.getElementById("filterForm").submit();
	}

	// 페이지 로드 시 로컬 저장소에서 선택된 숙소 유형을 가져와 설정
	var selectedAccType = localStorage.getItem('selectedAccType');
	if (selectedAccType) {
		document.getElementById(selectedAccType).checked = true;
	}

	// 라디오 버튼 클릭 시 선택된 값을 로컬 저장소에 저장
	function handleRadioClick(option) {
		// 선택된 라디오 버튼만 checked 속성을 true로 설정
		document.getElementById(option).checked = true;
		// 선택된 숙소 유형을 로컬 저장소에 저장
		localStorage.setItem('selectedAccType', option);
	}
});