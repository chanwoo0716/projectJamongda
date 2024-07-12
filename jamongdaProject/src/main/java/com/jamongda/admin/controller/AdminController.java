package com.jamongda.admin.controller;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminController {

	//사업자의 숙소 등록 요청
	public ModelAndView regAccommodation(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
}
