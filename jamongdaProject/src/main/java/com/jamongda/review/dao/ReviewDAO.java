package com.jamongda.review.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.review.dto.ReviewImageDTO;

@Mapper
@Repository("reviewDAO")
public interface ReviewDAO {
	
	// 리뷰작성 DB에 등록
	public void insertReview(ReviewDTO reviewDTO) throws DataAccessException;
	
	// 리뷰 이미지 저장
    void insertReviewImage(ReviewImageDTO reviewImageDTO) throws DataAccessException;
    
    void updateReviewImage(ReviewImageDTO reviewImageDTO) throws DataAccessException;
}
