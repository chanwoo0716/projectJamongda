package com.jamongda.booking.service;

import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.booking.dto.BookingDTO;

public interface BookingService {

	// 예약번호 생성
	public Long createBoNumber()throws Exception;
	
	// 예약정보 DB에 넣기
	public void insertBoInfo(BookingDTO bookingDTO)throws Exception;
	
	// 예약정보 불러와서 예약완료페이지에 보여주기
	public BookingDTO showBoInfo(Long bo_number)throws Exception;
	
	// acc_id로 객실 정보 조회하기
	public String showAccInfo(int acc_id)throws Exception;
	
	// ro_id로 객실 정보 조회하기
	public RoomDTO showRoInfo(int ro_id)throws Exception;
	
	// 환불 처리
	public boolean refundPayment(String imp_uid, int bo_price) throws Exception;

	// 환불 처리
	public boolean cancelBooking(Long bo_number) throws Exception;
}