package com.jamongda.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.search.dto.SearchDTO;

public interface SearchService {
   public List selectAll() throws DataAccessException;
   
   //2024-07-20수정
   public List search(AccommodationDTO acc) throws DataAccessException;
   
   public List searchType(AccommodationDTO acc) throws DataAccessException;
   //2024-07-20수정완료
   
   public List searchImage(AccommodationDTO acc) throws DataAccessException;
   
   public AccommodationDTO detailSearch(int acc_id) throws DataAccessException;
   
   public AccommodationDTO radio(AccommodationDTO acc) throws DataAccessException;
   
   //숙소상세보기
   public Map detailAccRo(int acc_id) throws DataAccessException;
   //public List searchImage(AccommodationDTO acc) throws DataAccessException;
   
   
   // 대표자명, 사업자번호 추가
      public Map<String, Object> getHostInfo(int acc_id) throws Exception;
      
      //최저가 가격
      public Map<String, Object> getRoPrice(int acc_id) throws Exception;
}