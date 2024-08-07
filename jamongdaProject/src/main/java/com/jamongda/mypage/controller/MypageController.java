package com.jamongda.mypage.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MypageController {

	// 마이페이지로 이동
	public ModelAndView mypageForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 마이페이지 예약내역 더보기 누르면 해당 회원 전체 예약내역
	public ModelAndView allBookings(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
    // 상세보기 누르면 예약번호 가지고가서 예약정보 조회
    public ModelAndView myBookingDetails(@RequestParam("number") Long bo_number,
    		HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // 회원 이메일로 리뷰내역 불러오기
    public ModelAndView getMyReviews(@RequestParam("email") String email,
    		@RequestParam("page") int page, @RequestParam("size") int size,
    		HttpServletRequest request, HttpServletResponse response) throws Exception;
}
