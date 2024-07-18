package com.jamongda.search.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.accommodation.dto.AccommodationDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SearchController {
	
	 public ModelAndView detail(
			 @RequestParam("acc_id") int acc_id,
	         @RequestParam(value = "bo_checkIn", required = false) String bo_checkIn,
	         @RequestParam(value = "bo_checkOut", required = false) String bo_checkOut,
			 HttpServletRequest request,HttpServletResponse response) throws Exception;
	 
}
