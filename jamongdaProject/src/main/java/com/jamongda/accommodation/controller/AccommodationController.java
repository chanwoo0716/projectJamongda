package com.jamongda.accommodation.controller;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccommodationController {
	
	// 숙소/객실등록 페이지(사업자 로그인에서 넘어와야함)(사업자 이메일 받아야하나?(PK))
	public ModelAndView regAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 숙소/객실관리 페이지
	public ModelAndView manageAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 회원예약관리 페이지
	public ModelAndView manageReservation(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 리뷰관리
	public ModelAndView manageReview(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 숙소/객실 등록하기(regAccommodation.html에서 "등록"눌렀을 때 DB에 insert 수행 후 manageAccommodation.html로..)
	public ModelAndView addAccommodation(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

}