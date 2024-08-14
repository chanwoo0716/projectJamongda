package com.jamongda.wishlist.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

public interface WishlistController {

	// 찜목록에 추가
	public ResponseEntity<Map<String, Object>> toggleWish(@RequestParam("aid") int acc_id, HttpServletRequest request)
			throws Exception;

	// 찜목록에서 제거
}
