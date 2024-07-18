package com.jamongda.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jamongda.accommodation.dto.AccjoinDTO;
import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.search.dto.SearchDTO;

public interface SearchService {
	public List selectAll() throws DataAccessException;
	
	public List search(AccommodationDTO acc) throws DataAccessException;
	
	public AccommodationDTO detailSearch(int acc_id) throws DataAccessException;
	
	public AccommodationDTO radio(AccommodationDTO acc) throws DataAccessException;
	
	//숙소상세보기
	public Map detailAccRo(int acc_id) throws DataAccessException;
	//public List searchImage(AccommodationDTO acc) throws DataAccessException;
}
