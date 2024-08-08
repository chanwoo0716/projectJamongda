package com.jamongda.main.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("homeControllerDAO")
public interface HomeControllerDAO {

	//자몽다 pick 이미지 8개,acc_id 가져오기
	public List<Map<String, Object>> getJamongdaAccImages() throws DataAccessException;
	
	//최저가 숙소 이미지 8개,acc_id 가져오기
	public List<Map<String, Object>> getLowestPricedAccImages() throws DataAccessException;
}