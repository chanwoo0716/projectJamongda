package com.jamongda.review.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.jamongda.review.dto.ReviewDTO;

public interface ReviewService {
	// 리뷰 등록
	public void insertReview(ReviewDTO reviewDTO, List<MultipartFile> images) throws Exception;
	
    // 숙소 상세페이지에 리뷰 출력(acc_id)
    public List<ReviewDTO> getReviewsByAccId(int acc_id, int page, int size) throws Exception;
    
    // 리뷰 삭제하기
    public boolean deleteReviewById(int rev_id) throws Exception;
}
