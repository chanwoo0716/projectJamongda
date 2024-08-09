package com.jamongda.search.dao;

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
import com.jamongda.search.dto.SearchDTO;

@Mapper
@Repository("searchDAO")
public interface SearchDAO {
	public List selectAllAccsList() throws DataAccessException;

	public List searchAccList(AccommodationDTO acc) throws DataAccessException;

	public List searchAccType(SearchDTO acc) throws DataAccessException;

	public AccommodationDTO selectAccId(int acc_id) throws DataAccessException;

	public AccommodationDTO selectRadio(AccommodationDTO acc) throws DataAccessException;

	public List<SearchDTO> searchPriceRange(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice)
			throws DataAccessException;

	// acc_id에 해당하는 숙소 가져오기
	public List<AccommodationDTO> selectAcc(int acc_id) throws DataAccessException;

	// acc_id에 해당하는 객실 정보 가져오기
	public List<RoomDTO> selectRo(int acc_id) throws DataAccessException;

	// acc_id에 해당하는 숙소 이미지 가져오기
	public List<AccommodationImageDTO> selectAccImages(int acc_id) throws DataAccessException;

	// acc_id에 해당하는 객실 이미지 가져오기
	public List<RoomImageDTO> selectRoImages(int acc_id) throws DataAccessException;

	// 대표자명, 사업자번호 추가
	public Map<String, Object> selectHostInfo(int acc_id) throws DataAccessException;

	// 가격, 숙소유형 동시검색
	public List<SearchDTO> searchByTypeAndPrice(@Param("acc_type") String acc_type, @Param("minPrice") int minPrice,
			@Param("maxPrice") int maxPrice) throws DataAccessException;

    // 예약 상태를 확인하는 메서드
    public List<BookingDTO> checkRoomAvailability(SearchDTO searchDTO);
}