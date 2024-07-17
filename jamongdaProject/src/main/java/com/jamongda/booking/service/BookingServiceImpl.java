package com.jamongda.booking.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamongda.booking.dao.BookingDAO;
import com.jamongda.booking.dto.BookingDTO;

@Service("bookingService")
public class BookingServiceImpl implements BookingService{
	@Autowired
	BookingDAO bookingDAO;

	@Override
	public Long createBoNumber() throws Exception {
		Random r=new Random();
		int rNumber=r.nextInt(8888)+1111;
		Date today=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		Long bo_number=Long.parseLong(dateFormat.format(today) + rNumber);
		// 예약번호가 중복인지 확인
		while(bookingDAO.isExistNumber(bo_number)) {
			rNumber=r.nextInt(8888)+1111;
			bo_number=Long.parseLong(dateFormat.format(today) + rNumber);
			
		}
		return bo_number;
		
		
	}

	@Override
	public void insertBoInfo(BookingDTO bookingDTO) throws Exception {
		bookingDAO.insertBoInfo(bookingDTO);
	}
	
	

}
