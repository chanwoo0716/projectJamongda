package com.jamongda.search.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface SearchService {

	//숙소상세보기
	public Map detailAccRo(int acc_id) throws DataAccessException;
}
