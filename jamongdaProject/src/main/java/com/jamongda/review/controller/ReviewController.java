package com.jamongda.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReviewController {

	// 리뷰작성 페이지로 이동
	public ModelAndView reviewForm(@RequestParam("number") Long bo_number,
			@RequestParam("aname") String acc_name, @RequestParam("rname") String ro_name,
			@RequestParam("rid") String ro_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 리뷰작성 DB에 등록
    public ModelAndView insertReview(@RequestParam("rev_content") String rev_content,
            @RequestParam("ro_id") int ro_id,
            @RequestParam("email") String email,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;
}