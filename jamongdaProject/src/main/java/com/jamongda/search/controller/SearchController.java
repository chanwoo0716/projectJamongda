package com.jamongda.search.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.search.dto.SearchDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SearchController {

	public ModelAndView listSearch(@ModelAttribute("acc") AccommodationDTO acc,
			@RequestParam(value = "aname", required = false) String acc_name,
			@RequestParam(value = "area", required = false) String acc_area,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut, HttpServletRequest request,
			HttpServletResponse response) throws Exception;	
	
	public ModelAndView listType(@ModelAttribute("acc") SearchDTO acc,
			@RequestParam(value = "aname", required = false) String acc_name,
			@RequestParam(value = "area", required = false) String acc_area,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut,
			@RequestParam(value = "acc_type", required = false) String acc_type, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ModelAndView listPrice(@ModelAttribute("acc") AccommodationDTO acc,
			@RequestParam(value = "aname", required = false) String acc_name,
			@RequestParam(value = "area", required = false) String acc_area,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut,
			@RequestParam(value = "acc_type", required = false) String acc_type,
			@RequestParam(value = "minPrice", required = false) Integer minPrice,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ModelAndView detail(@RequestParam("acc_id") int acc_id,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "bo_checkIn", required = true) String bo_checkIn,
			@RequestParam(value = "bo_checkOut", required = true) String bo_checkOut, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}