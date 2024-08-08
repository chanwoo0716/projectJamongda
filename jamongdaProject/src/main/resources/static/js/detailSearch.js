$(function() {
	// 로컬 저장소에서 날짜 값 가져오기
	var startDate = localStorage.getItem('startDate');
	var endDate = localStorage.getItem('endDate');

	$('#bo_checkIn').daterangepicker({
		timePicker: false,
		startDate: startDate ? moment(startDate) : moment().startOf('day'),
		endDate: endDate ? moment(endDate) : moment().startOf('day').add(7, 'days'),
		locale: {
			format: 'YYYY-MM-DD'
		}
	}, function(start, end) {
		// 선택된 날짜 범위를 로컬 저장소에 저장
		localStorage.setItem('startDate', start.format('YYYY-MM-DD'));
		localStorage.setItem('endDate', end.format('YYYY-MM-DD'));
		$('input[name="checkIn"]').val(start.format('YYYY-MM-DD'));
		$('input[name="checkOut"]').val(end.format('YYYY-MM-DD'));
	});


	$('#form3').on('submit', function(event) {
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
});

$(document).ready(function() {
	// 카카오 맵 API를 로드한 후
	kakao.maps.load(function() {
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div
			mapOption = {
				center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
				level: 2 // 지도의 확대 레벨
			};

		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

		console.log('Kakao Maps API loaded');
		var geocoder = new kakao.maps.services.Geocoder();
		console.log('Geocoder:', geocoder);

		// 주소로 좌표를 검색하여 마커를 표시
		geocoder.addressSearch(mapAddress, function(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

				var imageSrc = '/images/map-marker.png', // 마커이미지  
					imageSize = new kakao.maps.Size(64, 69),
					imageOption = { offset: new kakao.maps.Point(32, 69) }; // 마커의 좌표와 일치시킬 이미지 안에서의 좌표 설정

				// 마커의 이미지정보를 가지고 있는 마커이미지를 생성
				var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
					markerPosition = coords; // 마커가 표시될 위치

				var marker = new kakao.maps.Marker({
					map: map,
					position: markerPosition,
					image: markerImage
				});

				marker.setMap(map);

				map.setCenter(coords);
			} else {
				console.error('주소 검색 실패:', status);
			}
		});

		// 길찾기 버튼
		$('#routeButton').on('click', function() {
			var startAddress = '서울특별시 서초구 서초대로77길 41'; // 예시 출발지(실제로는 사용자의 현위치)
			var kakaoMapUrl = `https://map.kakao.com/?sName=${encodeURIComponent(startAddress)}&eName=${encodeURIComponent(mapAddress)}`;
			window.open(kakaoMapUrl, '_blank');
		});
	});

	// 회원 이메일 마스킹
	function maskEmail(email) {
		let [local, domain] = email.split('@');
		if (local.length > 3) {
			return local[0] + local[1] + local[2] + '*'.repeat(local.length - 3) + '@' + domain;
		}
		return local + '@' + domain; // 로컬 부분이 3자 이하인 경우 마스킹 X
	}

	let page = 1; // 현재 페이지
	const pageSize = 10; // 페이지 당 리뷰 수

	function loadReviews() {
		console.log('AJAX 요청을 보내는 acc_id:', acc_id);
		$.ajax({
			url: '/search/reviews',
			method: 'GET',
			data: { aid: acc_id, page: page, size: pageSize },
			success: function(data) {
				if (data && data.length > 0) {
					data.forEach(review => {
						// 리뷰 내용이 100자 이상이면 더보기
						const content = review.rev_content;
						const previewContent = content.length > 200 ? content.substring(0, 200) + '...' : content;
						const fullContent = content.length > 200 ? content : '';

						let reviewHtml = `
							<div class="review_user">
								<div class="user_email"><strong>${maskEmail(review.email)}</strong></div>
							    <div class="review_date">${review.rev_date}</div>
							</div>
                            <div class="review_content">
                                <div class="review_imagesbox">
                                    ${review.images.map(image =>
							`<div class="review_image"><img src="/review/downloadImage?rev_image=${image.rev_image}" alt="Review Image"/></div>`).join('')}
                            </div>
							<div class="review_text">
								<div>${review.ro_name}</div>	
								<div class="preview_content">${previewContent}</div>
								    ${fullContent ? `<div class="full_content">${fullContent}</div>` : ''}
								    ${fullContent ? '<a href="#" class="more"><strong>더보기</strong></a>' : ''}
								</div>
                            </div>
						`;
						$('#review').append(reviewHtml);
					});
					page++;
				} else {
					$('#loadMore').hide(); // 더 이상 로드할 리뷰가 없으면 버튼 숨기기
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.error('AJAX Error:', textStatus, errorThrown);
				alert('리뷰를 로드하는 데 실패했습니다.');
			}
		});

	}

	// '더보기' 클릭 이벤트 핸들러
	$(document).on('click', '.more', function(event) {
		event.preventDefault();
		const reviewText = $(this).siblings('.full_content');
		if (reviewText.is(':visible')) {
			$(this).siblings('.preview_content').show();
			reviewText.hide();
			$(this).html('<strong>더보기</strong>');
		} else {
			$(this).siblings('.preview_content').hide();
			reviewText.show();
			$(this).html('<strong>접기</strong>');
		}
	});

	// 페이지 로드 시 첫 번째 페이지의 리뷰 로드
	loadReviews();

	// 더 보기 버튼 클릭 시 추가 리뷰 로드
	$('#loadMore').on('click', function() {
		loadReviews();
	});

});

function showRoomImages(ro_id) {
	var imgPopupContainer = document.getElementById('imgPopupContainer');
	var imgPopupImages = document.getElementById('imgPopupImages');

	// Swiper 인스턴스 삭제 (기존 인스턴스가 있는 경우)
	if (imgPopupImages.swiper) {
		imgPopupImages.swiper.destroy(true, true);
	}

	// 기존 이미지 내용 제거
	imgPopupImages.innerHTML = '';

	// 객실 이미지 목록 가져오기
	roImages.forEach(function(roImage) {
		if (roImage.ro_id === ro_id) {
			var imgWrapper = document.createElement('div');
			imgWrapper.className = 'swiper-slide';
			var img = document.createElement('img');
			img.src = `/roDownload.do?ro_id=${ro_id}&ro_image=${roImage.ro_image}`;
			imgWrapper.appendChild(img);
			imgPopupImages.appendChild(imgWrapper);
		}
	});

	imgPopupContainer.style.display = 'flex';

	// Swiper
	new Swiper(".mySwiper", {
		direction: 'horizontal', // 가로 슬라이드 설정
		pagination: {
			el: ".swiper-pagination",
			type: "fraction",
		},
		navigation: {
			nextEl: ".swiper-button-next",
			prevEl: ".swiper-button-prev",
		},
	});
}

function closePopup() {
	document.getElementById('imgPopupContainer').style.display = 'none';
}


document.addEventListener("DOMContentLoaded", function() {
	var accInfoElement = document.getElementById("accInfo");
	var accInfoText = accInfoElement.textContent || accInfoElement.innerText;
	accInfoElement.innerHTML = accInfoText.replace(/\n/g, "<br>");
});

function togglePopup(roomId) {
	const popup = document.getElementById('popup-' + roomId);
	if (popup.classList.contains('show')) {
		popup.classList.remove('show');
		setTimeout(() => {
			popup.style.display = 'none';
		}, 300); // 애니메이션 시간이 지난 후 display를 none으로 설정
	} else {
		popup.style.display = 'flex';
		setTimeout(() => {
			popup.classList.add('show');
		}, 10); // display 설정 후 약간의 딜레이 후 show 클래스 추가
	}
}

// 팝업 창 외부를 클릭했을 때 닫히게 하는 함수
function setupPopupCloseEvent(roomId) {
	const popup = document.getElementById('popup-' + roomId);
	popup.addEventListener('click', (event) => {
		if (event.target === popup) {
			togglePopup(roomId);
		}
	});
}