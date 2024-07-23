package com.jamongda.mypage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;
import com.jamongda.mypage.service.MypageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("mypageControllerImpl")
public class MypageControllerImpl implements MypageController {

	@Autowired
	MypageService mypageService;

	@Override
	@GetMapping("/mypage/mypage.do")
	public ModelAndView mypageForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");

		// 이메일로 최신 예약 정보 가져오기
		Map<String, Object> latestBooking = mypageService.getLatestBoInfoByEmail(guest.getEmail());

		// 날짜 비교(체크인,체크아웃이 varchar 이어서 변환해서 비교하는 거 추가)
		boolean isPastCheckout = false;
		if (latestBooking != null && latestBooking.get("bo_checkOut") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date checkOutDate = sdf.parse((String) latestBooking.get("bo_checkOut"));
			Date now = new Date();
			isPastCheckout = checkOutDate.before(now);
		}

		mav.addObject("guest", guest);
		mav.addObject("latestBooking", latestBooking);
		mav.addObject("isPastCheckout", isPastCheckout);
		mav.setViewName("/mypage/mypage");

		return mav;
	}

	@Override
	@GetMapping("/mypage/allBookings.do")
	public ModelAndView allBookings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");

		// 이메일로 모든 예약 내역 가져오기
		List<Map<String, Object>> bookingList = mypageService.getAllBoInfoByEmail(guest.getEmail());

		// 날짜 비교 로직 추가
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();

		for (Map<String, Object> booking : bookingList) {
			if (booking.get("bo_checkOut") != null) {
				Date checkOutDate = sdf.parse((String) booking.get("bo_checkOut"));
				boolean isPastCheckout = checkOutDate.before(now);
				booking.put("isPastCheckout", isPastCheckout);
			} else {
				booking.put("isPastCheckout", false); // 체크아웃 날짜가 없는 경우
			}
		}

		mav.addObject("guest", guest);
		mav.addObject("bookingList", bookingList);
		mav.setViewName("/mypage/myBookingList");

		return mav;
	}

	@Override
	@GetMapping("/mypage/myBookingDetails.do")
	public ModelAndView myBookingDetails(@RequestParam("number") Long bo_number, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		Map<String, Object> myBookingDetails = mypageService.myBookingDetails(bo_number);

		mav.addObject("myBookingDetails", myBookingDetails);
		mav.setViewName("/mypage/myBookingDetails");
		return mav;
	}
}