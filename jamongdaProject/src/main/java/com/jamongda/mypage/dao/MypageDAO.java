package com.jamongda.mypage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.review.dto.ReviewImageDTO;

@Mapper
@Repository("mypageDAO")
public interface MypageDAO {
    
    // 이메일을 가지고 최신 예약 정보를 가져옴
    public Map<String, Object> getLatestBoInfoByEmail(String email) throws DataAccessException;
    
    // 이메일을 가지고 모든 예약 정보를 가져옴
    public List<Map<String, Object>> getAllBoInfoByEmail(String email) throws DataAccessException;
    
    // 상세보기 누르면 예약번호 가지고가서 예약정보 조회
    public Map<String, Object> myBookingDetails(Long bo_number) throws DataAccessException;
    
    // 회원 이메일로 작성한 리뷰 가져오기
    public List<ReviewDTO> getReviewsByEmail(Map<String, Object> params) throws DataAccessException;
    
    // 리뷰 이미지 가져오기
    public List<ReviewImageDTO> getImagesByReviewId(int rev_id) throws DataAccessException;
    
    // 객실 이름만 가져오기
    public String getRoomNameById(int ro_id) throws DataAccessException;
}