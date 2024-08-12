package com.jamongda.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.search.dto.SearchDTO;

public interface SearchService {
	public List selectAll() throws DataAccessException;

	public List search(AccommodationDTO acc) throws DataAccessException;

	public List searchType(SearchDTO acc) throws DataAccessException;

	public List<SearchDTO> rangePrice(int minPrice, int maxPrice) throws DataAccessException;

	// 숙소상세보기
	 public Map<String, Object> detailAccRo(int acc_id, String bo_checkIn, String bo_checkOut) throws DataAccessException;

	// 대표자명, 사업자번호 추가
	public Map<String, Object> getHostInfo(int acc_id) throws Exception;
}