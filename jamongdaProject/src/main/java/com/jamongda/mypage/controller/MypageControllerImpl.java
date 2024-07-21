package com.jamongda.mypage.controller;

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
public class MypageControllerImpl implements MypageController{

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
	    
	    mav.addObject("guest", guest);
	    mav.addObject("latestBooking", latestBooking);
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

	    mav.addObject("guest", guest);
	    mav.addObject("bookingList", bookingList);
	    mav.setViewName("/mypage/myBookingList");

	    return mav;
	}

	@Override
	@GetMapping("/mypage/myBookingDetails.do")
	public ModelAndView myBookingDetails(@RequestParam("number") Long bo_number,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> myBookingDetails=mypageService.myBookingDetails(bo_number);
		
		mav.addObject("myBookingDetails", myBookingDetails);
		mav.setViewName("/mypage/myBookingDetails");
		return mav;
	}
}