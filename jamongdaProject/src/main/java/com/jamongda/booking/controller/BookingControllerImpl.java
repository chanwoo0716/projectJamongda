package com.jamongda.booking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.accommodation.dto.RoomDTO;
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

	@Override
	@GetMapping("/booking/bookingForm.do")
	public ModelAndView bookingForm(@RequestParam("aid") int acc_id, @RequestParam("rid") int ro_id,
			@RequestParam("checkIn") String bo_checkIn, @RequestParam("checkOut") String bo_checkOut,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		MemberDTO guest=(MemberDTO) session.getAttribute("guest");

		// 숙소 id로 조회, 객실 id로 조회하는 쿼리문 돌려야함
		String acc_name=bookingService.showAccInfo(acc_id);
		RoomDTO roomDTO = bookingService.showRoInfo(ro_id);
		
		if(guest != null) {	// 이건 상세페이지에서 예약넘어올때 처리할거라서 나중에 지울 예정
			mav.addObject("guest", guest);
			mav.addObject("acc_name", acc_name);
			mav.addObject("room", roomDTO);
			mav.addObject("bo_checkIn", bo_checkIn);
			mav.addObject("bo_checkOut", bo_checkOut);
			mav.setViewName("booking/bookingForm");
		}else { // 이건 상세페이지에서 예약넘어올때 처리할거라서 나중에 지울 예정
			mav.setViewName("redirect:/member/loginForm_guest.do");
		}
		return mav;
	}

	@PostMapping("/booking/requestPayment.do")
	@ResponseBody
	public String requestPayment(@RequestParam("bo_name") String bo_name, @RequestParam("bo_tel") String bo_tel,
	                             HttpServletRequest request) throws Exception {
	    // 아임포트 API를 통해 결제 요청을 처리
	    // 결제 성공 시 예약 정보를 저장하는 요청을 클라이언트로 반환
	    return "결제 요청이 성공적으로 전송되었습니다.";
	}
	
	@Override
	@PostMapping("/booking/insertBooking.do")
	@ResponseBody
	public Map<String, Object> insertBooking(@RequestBody BookingDTO bookingDTO, HttpServletRequest request) throws Exception {
		// 예약정보 넘어오는거 확인용
		System.out.println("Received BookingDTO: " + bookingDTO.toString());
	    Long bo_number = bookingService.createBoNumber(); // 예약번호 생성
	    bookingDTO.setBo_number(bo_number);
	    bookingService.insertBoInfo(bookingDTO);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("bo_number", bo_number);
	    
	    return response;
	}

	@Override
	@GetMapping("/booking/bookingComplete.do")
	public ModelAndView bookingComplete(@RequestParam("bo_number") Long bo_number,
			@RequestParam("aname") String acc_name, @RequestParam("rid") int ro_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		
		HttpSession session=request.getSession();
		MemberDTO guest=(MemberDTO) session.getAttribute("guest");
		
		RoomDTO roomDTO = bookingService.showRoInfo(ro_id);
		BookingDTO bookingDTO= bookingService.showBoInfo(bo_number);
		System.out.println("숙소 이름" + acc_name);
		mav.addObject("guest", guest);
		mav.addObject("acc_name", acc_name);
		mav.addObject("room", roomDTO);
		mav.addObject("bo_number",bo_number);
		mav.addObject("booking", bookingDTO);
		mav.setViewName("booking/bookingComplete");
		
		return mav;
	}
	
	
}
