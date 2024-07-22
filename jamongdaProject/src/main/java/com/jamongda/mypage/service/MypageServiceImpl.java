package com.jamongda.mypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamongda.mypage.dao.MypageDAO;

@Service("mypageService")
public class MypageServiceImpl implements MypageService {

    @Autowired
    MypageDAO mypageDAO;
    
    @Override
    public Map<String, Object> getLatestBoInfoByEmail(String email) throws Exception {
        return mypageDAO.getLatestBoInfoByEmail(email);
    }
    
    @Override
    public List<Map<String, Object>> getAllBoInfoByEmail(String email) throws Exception {
        return mypageDAO.getAllBoInfoByEmail(email);
    }

    @Override
    public Map<String, Object> myBookingDetails(Long bo_number) throws Exception {
    	return mypageDAO.myBookingDetails(bo_number);
    }
}