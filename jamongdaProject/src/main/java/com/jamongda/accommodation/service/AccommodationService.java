package com.jamongda.accommodation.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jamongda.review.dto.ReviewDTO;

public interface AccommodationService {

	// 여러장의 숙소 이미지 추가 (+숙소 고유 ID가져오기)
	public int addAccommodation(Map accRoMap) throws DataAccessException;

	// 여러 객실의 여러 이미지 추가 (+객실 고유 ID들 가져오기)
	public List<Integer> addRoom(Map accRoMap) throws DataAccessException;

	// 숙소/객실관리 (등록요청 or 등록된 숙소,객실을 화면에 뿌려야함)
	public Map listAccRo(String email) throws DataAccessException;

	// 회원 예약 리스트 가져오기
	public List<Map<String, Object>> getAccommodationBookingInfo(String email) throws DataAccessException;
	
	//리뷰 데이터 가져오기(acc_name, ro_name, rev_content, rev_date, email)
	public List<Map<String, Object>> getReviewsByHostEmail(String email) throws DataAccessException;
	
	//리뷰 등록하기
	public void updateReviewComment(int rev_id, String rev_comment) throws DataAccessException;
}