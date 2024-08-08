$(window).on('load', function () {
    slider();
})

function slider(){
    $(".slider").each(function(index){
        let $this = $(this);
        let swiper = undefined;
        let slideNum =  $this.find('.swiper-slide').length //슬라이드 총 개수
        let slideInx = 0; //현재 슬라이드 index
        
        //디바이스 체크
        let oldWChk = window.innerWidth > 768 ? 'pc' : 'mo';
        sliderAct();
        $(window).on('resize', function () {
            let newWChk = window.innerWidth > 768 ? 'pc' : 'mo';
            if(newWChk != oldWChk){
                oldWChk = newWChk;
                sliderAct();
            }
        })

        function sliderAct(){
            //슬라이드 인덱스 클래스 추가
            $this.addClass(`slider${index}`);

            //슬라이드 초기화 
            if (swiper != undefined){ 
                swiper.destroy();
                swiper = undefined;
            }

            //slidesPerView 옵션 설정
            let viewNum = oldWChk == 'pc' ? 4.19 : 2.8;
            //loop 옵션 체크
            let loopChk = slideNum > viewNum;

            swiper = new Swiper(`.slider${index} .inner`, {
                slidesPerView: viewNum,
                initialSlide :slideInx,
                spaceBetween: 60,
                slidesPerGroup: 1,
                loop: loopChk,
                navigation: {
                    prevEl: $(`.slider${index} .btn_prev`)[0],
                    nextEl: $(`.slider${index} .btn_next`)[0],
                },
                on: {
                    activeIndexChange: function () {
                        slideInx = this.realIndex; //현재 슬라이드 index 갱신
                    }
                },
            });
        }
    });
}

// 달력 라이브러리 JS
$(function() {
    $('input[name="datetimes"]').daterangepicker({
        timePicker: false,
        startDate: moment().startOf('day'),
        endDate: moment().startOf('day').add(32, 'hour'),
        locale: {
            format: 'YYYY-MM-DD'
        }
    }, function(start, end, label) {
        console.log("Start Date: " + start.format('YYYY-MM-DD'));
        console.log("End Date: " + end.format('YYYY-MM-DD'));
    });
	
});

//지역별 이미지 hover시 지역명 나오게하기
document.addEventListener("DOMContentLoaded", function() {
    const slides = document.querySelectorAll(".swiper-slide1");

    slides.forEach(slide => {
        const anchor = slide.querySelector("a");
        const img = slide.querySelector("img");
        if (anchor && img) {
            anchor.setAttribute("data-alt", img.getAttribute("alt"));
        }
    });
});