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
	
	//자몽다pick 이미지 8개,acc_id 가져오기(2024/07/24 : 객실가격 50,000원~100000원 인 숙소들)
	public List<Map<String, Object>> jamongdaAcc() throws DataAccessException {

		List<Map<String, Object>> jamongdaAccList = homeControllerDAO.getJamongdaAccImages();
		
		return jamongdaAccList;
	}
	
	
	//최저가 숙소 이미지 8개,acc_id 가져오기
	public List<Map<String, Object>> lowestAcc() throws DataAccessException {

		List<Map<String, Object>> lowestAccList = homeControllerDAO.getLowestPricedAccImages();
		
		return lowestAccList;
	}

}
