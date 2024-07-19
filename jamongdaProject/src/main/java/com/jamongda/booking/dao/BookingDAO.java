package com.jamongda.booking.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.booking.dto.BookingDTO;

@Mapper
@Repository("bookingDAO")
public interface BookingDAO {
	// 예약정보
	public void insertBoInfo(BookingDTO bookingDTO) throws DataAccessException;
	
	// 예약번호 중복 확인
	public boolean isExistNumber(Long bo_number)throws DataAccessException;
	
	// 
	public BookingDTO showBoInfo(Long bo_number) throws DataAccessException;
}
