package com.jamongda.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamongda.mypage.dao.MypageDAO;
import com.jamongda.review.dto.ReviewDTO;

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

    @Override
    public List<ReviewDTO> getReviewsWithImagesByEmail(String email, int page, int size) throws Exception {
        int offset = (page - 1) * size;
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("offset", offset);
        params.put("size", size);

        List<ReviewDTO> reviews = mypageDAO.getReviewsWithImagesByEmail(params);

        for (ReviewDTO review : reviews) {
            String roName = mypageDAO.getRoomNameById(review.getRo_id());
            review.setRo_name(roName);
        }

        return reviews;
    }

    @Override
    public String getRoomNameById(int ro_id) throws Exception {
        return mypageDAO.getRoomNameById(ro_id);
    }
    
}
