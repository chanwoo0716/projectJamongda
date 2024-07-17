package com.jamongda.booking.service;

import com.jamongda.booking.dto.BookingDTO;

public interface BookingService {

	// 예약번호 생성
	public Long createBoNumber()throws Exception;
	// 예약정보
	public void insertBoInfo(BookingDTO bookingDTO)throws Exception;
}
