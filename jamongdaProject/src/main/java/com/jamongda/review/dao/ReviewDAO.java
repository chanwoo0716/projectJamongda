package com.jamongda.review.dao;

import java.util.List;
import java.util.Map;

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
    public void insertReviewImage(ReviewImageDTO reviewImageDTO) throws DataAccessException;
    
    public void updateReviewImage(ReviewImageDTO reviewImageDTO) throws DataAccessException;
    
    // 숙소 ID로 작성한 리뷰와 이미지 리스트 가져오기
    List<ReviewDTO> getReviewsByAccId(Map<String, Object> params) throws DataAccessException;

    // ro_id를 통해 room의 ro_name 가져오기
    String getRoomNameById(int ro_id) throws DataAccessException;
}
