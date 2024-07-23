package com.jamongda.mypage.service;

import java.util.List;
import java.util.Map;

import com.jamongda.review.dto.ReviewDTO;

public interface MypageService {

	// email 가지고 가서 해당 회원이 예약한 거 가지고 와야함
	public Map<String, Object> getLatestBoInfoByEmail(String email) throws Exception;
	
    // 해당 회원 전체 예약숙소
	public List<Map<String, Object>> getAllBoInfoByEmail(String email) throws Exception;

	// 상세보기 누르면 예약번호 가지고가서 예약정보 조회
	public Map<String, Object> myBookingDetails(Long bo_number) throws Exception;
	
    // 회원 이메일로 작성한 리뷰와 이미지 리스트 가져오기
	public List<ReviewDTO> getReviewsWithImagesByEmail(String email, int page, int size) throws Exception;
    
    public String getRoomNameById(int ro_id) throws Exception;
    
}