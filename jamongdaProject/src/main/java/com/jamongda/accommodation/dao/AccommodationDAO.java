package com.jamongda.accommodation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.booking.dto.BookingDTO;

@Mapper
@Repository("accommodationDAO")
public interface AccommodationDAO {

	// 숙소 고유 ID 생성
	public int getNewAccId() throws DataAccessException;

	// 숙소 정보 추가 (acc_image 제외) -> AccommodationDTO
	public void insertNewAcc(Map accMap) throws DataAccessException;

	// 숙소 이미지 추가 -> AccommodationImageDTO
	public void insertNewAccImages(Map accRoMap) throws DataAccessException;

	// 객실 고유 ID 생성
	public int getNewRoId() throws DataAccessException;

	// 객실 정보 추가 (ro_image 제외) -> RoomDTO
	public void insertNewRo(RoomDTO roomDTO) throws DataAccessException;

	// 객실들 이미지 추가 -> RoomImageDTO
	public void insertNewRoImages(@Param("roomImageDTOList") List<RoomImageDTO> roomImageDTOList)
			throws DataAccessException;

	// 숙소 정보 가져오기(regCheck포함)
	public List<AccommodationDTO> selectAllAcc(@Param("email") String email) throws DataAccessException;

	// 객실 정보 가져오기
	public List<RoomDTO> selectAllRo(int acc_id) throws DataAccessException;

	// 숙소 이미지 가져오기
	public List<AccommodationImageDTO> selectAccImages(String email) throws DataAccessException;

	// 객실 이미지 가져오기
	public List<RoomImageDTO> selectRoImages(String email) throws DataAccessException;

	public List<AccommodationDTO> getAccommodationsByEmail(@Param("email") String email);

	public List<RoomDTO> getRoomsByAccIds(@Param("accIds") List<Integer> accIds);

	public List<BookingDTO> getBookingsByRoIds(@Param("roIds") List<Integer> roIds);

}
