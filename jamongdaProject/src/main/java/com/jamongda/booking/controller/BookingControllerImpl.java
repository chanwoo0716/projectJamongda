package com.jamongda.booking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.booking.dto.BookingDTO;
import com.jamongda.booking.service.BookingService;
import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@Controller("bookingController")
public class BookingControllerImpl implements BookingController {
	@Autowired
	BookingService bookingService;

	@Override
	@GetMapping("/booking/bookingForm.do")
	public ModelAndView bookingForm(@RequestParam("aid") int acc_id, @RequestParam("rid") int ro_id,
			@RequestParam("checkIn") String bo_checkIn, @RequestParam("checkOut") String bo_checkOut,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");

		// 숙소 id로 조회, 객실 id로 조회하는 쿼리문 실행
		String acc_name = bookingService.showAccInfo(acc_id);
		RoomDTO roomDTO = bookingService.showRoInfo(ro_id);

		if (guest != null) {
			mav.addObject("guest", guest);
			mav.addObject("acc_name", acc_name);
			mav.addObject("room", roomDTO);
			mav.addObject("bo_checkIn", bo_checkIn);
			mav.addObject("bo_checkOut", bo_checkOut);
			mav.setViewName("booking/bookingForm");
		} else {
			String redirectUrl = String.format("/search/detailSearch.do?aid=%d&datetimes=&checkIn=%s&checkOut=%s",
					acc_id, bo_checkIn, bo_checkOut);
			session.setAttribute("redirect", redirectUrl);
			mav.setViewName("redirect:/member/loginForm_guest.do");
		}
		return mav;
	}

	// 아임포트 API를 통해 결제 요청 처리
	@ResponseBody
	@PostMapping("/booking/requestPayment.do")
	public String requestPayment(@RequestParam("bo_name") String bo_name, @RequestParam("bo_tel") String bo_tel,
			HttpServletRequest request) throws Exception {
		return "결제 요청이 성공적으로 전송되었습니다.";
	}

	@Override
	@ResponseBody
	@PostMapping("/booking/insertBooking.do")
	public Map<String, Object> insertBooking(@RequestBody BookingDTO bookingDTO, HttpServletRequest request)
			throws Exception {
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
			@RequestParam("aname") String acc_name, @RequestParam("rid") int ro_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");

		RoomDTO roomDTO = bookingService.showRoInfo(ro_id);
		BookingDTO bookingDTO = bookingService.showBoInfo(bo_number);

		mav.addObject("guest", guest);
		mav.addObject("acc_name", acc_name);
		mav.addObject("room", roomDTO);
		mav.addObject("bo_number", bo_number);
		mav.addObject("booking", bookingDTO);
		mav.setViewName("booking/bookingComplete");

		return mav;
	}
	
	@PostMapping("/booking/refundBooking")
	public ResponseEntity<String> refundBooking(@RequestBody Map<String, Object> requestBody) {
	    try {
	        String impUid = (String) requestBody.get("imp_uid");
	        Long number = Long.valueOf(requestBody.get("number").toString());
	        Integer amount = Integer.valueOf(requestBody.get("amount").toString());

	        boolean paymentRefunded = bookingService.refundPayment(impUid, amount);

	        if (paymentRefunded) {
	            boolean bookingCancelled = bookingService.cancelBooking(number);

	            if (bookingCancelled) {
	                return ResponseEntity.ok("예약이 취소되었습니다.");
	            } else {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 취소에 실패했습니다.");
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 취소에 실패했습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다: " + e.getMessage());
	    }
	}


}