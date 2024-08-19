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
import com.jamongda.review.dto.ReviewDTO;

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
	
	//리뷰 데이터 가져오기(acc_name, ro_name, rev_content, rev_date, email)
	public List<Map<String, Object>> selectReviewsByHostEmail(String email);
	
	//리뷰 등록하기
	public void updateReviewComment(@Param("rev_id") int rev_id, @Param("rev_comment") String rev_comment) throws DataAccessException;
	
	//리뷰 이미지 삭제하기
	public void delReviewImage(int rev_id) throws DataAccessException;
	
	//리뷰 삭제하기
	public void delReview(@Param("rev_id") int rev_id) throws DataAccessException;
}
