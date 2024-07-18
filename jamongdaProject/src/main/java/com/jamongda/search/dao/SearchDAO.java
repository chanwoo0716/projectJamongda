package com.jamongda.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;

@Mapper
@Repository("searchDAO")
public interface SearchDAO {

	// acc_id에 해당하는 숙소 가져오기
	public List<AccommodationDTO> selectAcc(int acc_id) throws DataAccessException;
	// acc_id에 해당하는  객실 정보 가져오기
	public List<RoomDTO> selectRo(int acc_id) throws DataAccessException;
	// acc_id에 해당하는  숙소 이미지 가져오기
	public List<AccommodationImageDTO> selectAccImages(int acc_id) throws DataAccessException;
	// acc_id에 해당하는  객실 이미지 가져오기
	public List<RoomImageDTO> selectRoImages(int acc_id) throws DataAccessException;
}
