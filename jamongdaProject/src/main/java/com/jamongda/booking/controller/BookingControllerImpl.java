package com.jamongda.booking.controller;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.booking.dto.BookingDTO;
import com.jamongda.booking.service.BookingService;
import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("bookingController")
public class BookingControllerImpl implements BookingController {
	@Autowired
	BookingService bookingService;

	// 실제론 이거써야됨 ㅠㅠ
//	@Override
//	@GetMapping("/booking/bookingForm.do")
//	public ModelAndView bookingForm(@RequestParam("acc_id") int acc_id, @RequestParam("ro_id") int ro_id,
//			@RequestParam("bo_checkIn") int bo_checkIn, @RequestParam("bo_checkOut") int bo_checkOut,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mav=new ModelAndView();
//		HttpSession session=request.getSession();
//		MemberDTO guest=(MemberDTO) session.getAttribute("guest");
//		
//		
//		if(guest != null) {	// 이건 상세페이지에서 예약넘어올때 처리할거라서 나중에 지울 예정
//			mav.addObject("guest", guest);
//			
//			mav.addObject("acc", acc_id);
//			mav.addObject("room", ro_id);
//			mav.addObject("bo_checkIn", bo_checkIn);
//			mav.addObject("bo_checkOut", bo_checkOut);
//			
//			mav.setViewName("/booking/bookingForm");
//		}else { // 이건 상세페이지에서 예약넘어올때 처리할거라서 나중에 지울 예정
//			mav.setViewName("redirect:/member/loginForm_guest.do");
//		}
//		return mav;
//	}
	
	@Override
	@GetMapping("/booking/bookingForm.do")
	public ModelAndView bookingForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		MemberDTO guest=(MemberDTO) session.getAttribute("guest");
		
		if(guest != null) {	// 이건 상세페이지에서 예약넘어올때 처리할거라서 나중에 지울 예정
			mav.addObject("guest", guest);
			mav.setViewName("/booking/bookingForm");
		}else { // 이건 상세페이지에서 예약넘어올때 처리할거라서 나중에 지울 예정
			mav.setViewName("redirect:/member/loginForm_guest.do");
		}
		return mav;
	}

	@Override
	@PostMapping("/booking/insertBooking.do")
	public ModelAndView insertBooking(@ModelAttribute("bookingDTO") BookingDTO bookingDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		Long bo_number=bookingService.createBoNumber(); // 예약번호 생성한거 가져옴
		bookingDTO.setBo_number(bo_number);
		bookingService.insertBoInfo(bookingDTO);
		mav.setViewName("redirect:/booking/bookingComplete.do?bo_number="+bo_number);
		return mav;
	}

	@Override
	@GetMapping("/booking/bookingComplete.do")
	public ModelAndView bookingComplete(@RequestParam("bo_number") Long bo_number, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		mav.addObject(bo_number);
		mav.setViewName("/booking/bookingComplete");
		return mav;
	}
	
	
	
	
}
