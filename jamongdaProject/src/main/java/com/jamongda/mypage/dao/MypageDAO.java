package com.jamongda.mypage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("mypageDAO")
public interface MypageDAO {
    
    // 이메일을 가지고 최신 예약 정보를 가져옴
    public Map<String, Object> getLatestBoInfoByEmail(String email) throws DataAccessException;
    
    // 이메일을 가지고 모든 예약 정보를 가져옴
    public List<Map<String, Object>> getAllBoInfoByEmail(String email) throws DataAccessException;

    // 상세보기 누르면 예약번호 가지고가서 예약정보 조회
    public Map<String, Object> myBookingDetails(Long bo_number) throws DataAccessException;
}