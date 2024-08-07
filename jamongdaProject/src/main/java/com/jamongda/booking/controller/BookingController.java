package com.jamongda.booking.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.booking.dto.BookingDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BookingController {

	// 예약 화면으로 이동 - 숙소/객실/체크인/체크아웃/사용자 정보
	public ModelAndView bookingForm(@RequestParam("aid") int acc_id, @RequestParam("rid") int ro_id,
			@RequestParam("checkIn") String bo_checkIn, @RequestParam("checkOut") String bo_checkOut,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 예약정보
	public Map<String, Object> insertBooking(@RequestBody BookingDTO bookingDTO, HttpServletRequest request) throws Exception;
	
	// 예약완료 페이지
	public ModelAndView bookingComplete(@RequestParam("bo_number") Long bo_number, 
			@RequestParam("aname") String acc_name, @RequestParam("rid") int ro_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}