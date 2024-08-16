package com.jamongda.wishlist.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface WishlistController {

	// 찜목록에 추가
	public ResponseEntity<Map<String, Object>> toggleWish(@RequestParam("aid") int acc_id, HttpServletRequest request)
			throws Exception;

	// 찜목록 페이지로 이동
	public ModelAndView myWishlistPage(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	// 찜목록 출력
	public Map<String, Object> getWishlist(@RequestParam("email") String email, @RequestParam("page") int page) throws Exception;
}
