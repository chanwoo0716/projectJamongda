package com.jamongda.review.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.jamongda.review.dto.ReviewDTO;

public interface ReviewService {
	// 리뷰 등록
	public void insertReview(ReviewDTO reviewDTO, List<MultipartFile> images) throws Exception;
}
