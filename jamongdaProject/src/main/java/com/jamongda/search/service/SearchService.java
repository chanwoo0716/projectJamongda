package com.jamongda.search.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface SearchService {

	//숙소상세보기
	public Map detailAccRo(int acc_id) throws DataAccessException;
	
	// 대표자명, 사업자번호 추가
	public Map<String, Object> getHostInfo(int acc_id) throws Exception;
}
