package com.jamongda.mypage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;
import com.jamongda.mypage.service.MypageService;
import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.search.service.SearchService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("mypageControllerImpl")
public class MypageControllerImpl implements MypageController {

	@Autowired
	MypageService mypageService;

	@Autowired
	SearchService searchService;

	@Override
	@GetMapping("/mypage/mypage.do")
	public ModelAndView mypageForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");

		Map<String, Object> latestBooking = mypageService.getLatestBoInfoByEmail(guest.getEmail());

		// 체크아웃날짜가 지나면 리뷰등록 가능
		boolean isPastCheckout = false;
		if (latestBooking != null && latestBooking.get("bo_checkOut") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date checkOutDate = sdf.parse((String) latestBooking.get("bo_checkOut"));
			Date now = new Date();
			isPastCheckout = checkOutDate.before(now);
		}

		// 체크인날짜 전이면 환불 가능
		boolean isAvailableRefund = false;
		if (latestBooking != null && latestBooking.get("bo_checkIn") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date checkInDate = sdf.parse((String) latestBooking.get("bo_checkIn"));
			Date now = new Date();
			isAvailableRefund = checkInDate.after(now); // 체크인 날짜가 현재 날짜보다 이후일 때 환불 가능
		}

		// 최신 예약 내역을 `latestBooking`이 null이 아닐 때만 처리
		if (latestBooking == null) {
			mav.addObject("latestBooking", null); // 빈 Map 객체를 전달
		} else {
			mav.addObject("latestBooking", latestBooking);
			mav.addObject("isPastCheckout", isPastCheckout);
			mav.addObject("isAvailableRefund", isAvailableRefund);
		}

		mav.addObject("guest", guest);
		mav.setViewName("mypage/mypage");

		return mav;
	}

	@Override
	@GetMapping("/mypage/allBookings.do")
	public ModelAndView allBookings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");

		List<Map<String, Object>> bookingList = mypageService.getAllBoInfoByEmail(guest.getEmail());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();

		for (Map<String, Object> booking : bookingList) {
			if (booking.get("bo_checkOut") != null) {
				Date checkOutDate = sdf.parse((String) booking.get("bo_checkOut"));
				boolean isPastCheckout = checkOutDate.before(now);
				booking.put("isPastCheckout", isPastCheckout);
			} else {
				booking.put("isPastCheckout", false);
			}
		}

		for (Map<String, Object> booking : bookingList) {
			if (booking.get("bo_checkIn") != null) {
				Date checkInDate = sdf.parse((String) booking.get("bo_checkIn"));
				boolean isAvailableRefund = checkInDate.after(now);
				booking.put("isAvailableRefund", isAvailableRefund);
			} else {
				booking.put("isAvailableRefund", false);
			}
		}

		mav.addObject("guest", guest);
		mav.addObject("bookingList", bookingList);			
		mav.setViewName("mypage/myBookingList");

		return mav;
	}

	@Override
	@GetMapping("/mypage/myBookingDetails.do")
	public ModelAndView myBookingDetails(@RequestParam("number") Long bo_number, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		Map<String, Object> myBookingDetails = mypageService.myBookingDetails(bo_number);

		mav.addObject("myBookingDetails", myBookingDetails);
		mav.setViewName("mypage/myBookingDetails");
		return mav;
	}

	@Override
	@PostMapping("/mypage/myReviews.do")
	public ModelAndView getMyReviews(@Param("email") String email,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("mypage/myReviews");
		HttpSession session = request.getSession();
		MemberDTO guest = (MemberDTO) session.getAttribute("guest");
		try {
			List<ReviewDTO> reviews = mypageService.getReviewsWithImagesByEmail(email, page, size);

			for (ReviewDTO review : reviews) {
				String ro_name = mypageService.getRoomNameById(review.getRo_id());
				review.setRo_name(ro_name);
			}

			mav.addObject("guest", guest);
			mav.addObject("reviews", reviews);
			mav.addObject("currentPage", page);
			mav.addObject("pageSize", size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping("/mypage/getReviewsAjax")
	public ResponseEntity<List<ReviewDTO>> getReviewsAjax(@RequestParam("email") String email,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		try {
			int offset = (page - 1) * size;
			Map<String, Object> params = new HashMap<>();
			params.put("email", email);
			params.put("offset", offset);
			params.put("size", size);

			List<ReviewDTO> reviews = mypageService.getReviewsWithImagesByEmail(email, page, size);
			return ResponseEntity.ok(reviews);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
