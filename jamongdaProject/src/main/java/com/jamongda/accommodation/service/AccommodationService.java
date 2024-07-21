package com.jamongda.accommodation.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccommodationService {

	// 여러장의 숙소 이미지 추가 (+숙소 고유 ID가져오기)
	public int addAccommodation(Map accRoMap) throws DataAccessException;
	
	// 여러장의 객실 이미지 추가 (+객실 고유 ID 가져오기)
	//public int addRoom(Map accRoMap) throws DataAccessException;
	
	// 여러 객실의 여러 이미지 추가 (+객실 고유 ID들 가져오기)	
	public List<Integer> addRoom(Map accRoMap) throws DataAccessException;

	// 숙소/객실관리 (등록요청 or 등록된 숙소,객실을 화면에 뿌려야함)
	public Map listAccRo(String email) throws DataAccessException;
	
}