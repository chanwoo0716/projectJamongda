package com.jamongda.main.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.main.dao.HomeControllerDAO;

@Service("someControllerService")
public class HomeControllerService {
	
	@Autowired
	private HomeControllerDAO homeControllerDAO;
	
	//최저가 숙소 이미지 8개 가져오기
	public List<Map<String, Object>> lowestAcc() throws DataAccessException {
		//최저가(객실) 숙소 이미지 파일이름 + acc_id 8개 들고오기
		List<Map<String, Object>> lowestAccList = homeControllerDAO.getLowestPricedAccImages();
		
		return lowestAccList;
	}
	//
	
	//
}
