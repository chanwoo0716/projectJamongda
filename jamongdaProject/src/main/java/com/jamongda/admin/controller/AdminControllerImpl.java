package com.jamongda.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("adminController")
public class AdminControllerImpl implements AdminController {

	private static String ARTICLE_IMAGE_REPO="D:\\Hwang\\accFileupload";
	
	@Override
	@PostMapping("/admin/regAccommodation.do")
	public ModelAndView regAccommodation(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		
		//사업자가 등록하려는 name들 받아야해
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/accommodation/manageAccommodation");
		mav.addObject("sidebar", "accommodation/hostSidebar");
		return mav;
		//관리자는 어떻게 받아야하는가
	}
	
	

}
