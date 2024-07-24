package com.jamongda.search.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.search.dto.SearchDTO;

@Mapper
public interface SearchDAO {
   public List selectAllAccsList() throws DataAccessException;
   //2024-07-20수정
   public List searchAccList(AccommodationDTO acc) throws DataAccessException;
   
   public List searchAccType(AccommodationDTO acc) throws DataAccessException;
   //2024-07-20수정완료
   
   public AccommodationDTO selectAccId(int acc_id) throws DataAccessException;
   
   public AccommodationDTO selectRadio(AccommodationDTO acc) throws DataAccessException;
   
   public List searchImage(AccommodationDTO acc) throws DataAccessException;
   
   public List<SearchDTO> searchPriceRange(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice) throws DataAccessException;
   
    // acc_id에 해당하는 숙소 가져오기
    public List<AccommodationDTO> selectAcc(int acc_id) throws DataAccessException;
    // acc_id에 해당하는  객실 정보 가져오기
    public List<RoomDTO> selectRo(int acc_id) throws DataAccessException;
    // acc_id에 해당하는  숙소 이미지 가져오기
    public List<AccommodationImageDTO> selectAccImages(int acc_id) throws DataAccessException;
    // acc_id에 해당하는  객실 이미지 가져오기
    public List<RoomImageDTO> selectRoImages(int acc_id) throws DataAccessException;
    
   // 대표자명, 사업자번호 추가
      public Map<String, Object> selectHostInfo(int acc_id) throws Exception;
    
}
