package com.jamongda.mypage.service;

import java.util.List;
import java.util.Map;

public interface MypageService {

	// email 가지고 가서 해당 회원이 예약한 거 가지고 와야함
	public Map<String, Object> getLatestBoInfoByEmail(String email) throws Exception;
	
    // 해당 회원 전체 예약숙소
	public List<Map<String, Object>> getAllBoInfoByEmail(String email) throws Exception;

	// 상세보기 누르면 예약번호 가지고가서 예약정보 조회
	public Map<String, Object> myBookingDetails(Long bo_number) throws Exception;
}