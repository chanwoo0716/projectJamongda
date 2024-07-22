package com.jamongda.coming;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class comingSoonControllerImpl{
	
	//모든 예외를 처리하는 메서드
	@ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView();
        
        // 예외 객체를 모델에 추가
        mav.addObject("exception", e);
        
        // 요청된 URL을 모델에 추가
        mav.addObject("url", request.getRequestURL());
        
        // 에러페이지의 뷰 이름을 설정
        mav.setViewName("comingSoon");
        
        return mav;
    }
}
