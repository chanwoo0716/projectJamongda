package com.jamongda.coming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class comingSoonControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(comingSoonControllerImpl.class);

    // 모든 예외를 처리하는 메서드
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView();

        // 예외 로그 기록
        logger.error("Request: " + request.getRequestURL() + " raised " + e);

        // 에러페이지의 뷰 이름을 설정
        mav.setViewName("comingSoon");

        return mav;
    }
}