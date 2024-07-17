package com.jamongda.booking.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.booking.dto.BookingDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BookingController {

	// 예약 화면으로 이동 - 숙소/객실/체크인/체크아웃/사용자 정보
	public ModelAndView bookingForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
//	public ModelAndView bookingForm(@RequestParam("acc_id") int acc_id, @RequestParam("ro_id") int ro_id,
//			@RequestParam("bo_checkIn") int bo_checkIn, @RequestParam("bo_checkOut") int bo_checkOut,
//			HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 예약정보
	public ModelAndView insertBooking(@ModelAttribute("bookingDTO") BookingDTO bookingDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	// 예약완료 페이지
	public ModelAndView bookingComplete(@RequestParam("bo_number") Long bo_number, 
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}
