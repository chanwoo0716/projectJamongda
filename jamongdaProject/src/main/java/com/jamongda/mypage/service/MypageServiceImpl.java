package com.jamongda.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamongda.mypage.dao.MypageDAO;
import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.review.dto.ReviewImageDTO;

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

        try {
            // 리뷰 리스트 가져오기
            List<ReviewDTO> reviews = mypageDAO.getReviewsByEmail(params);

            // 각 리뷰에 대해 이미지 가져오기
            for (ReviewDTO review : reviews) {
                List<ReviewImageDTO> images = mypageDAO.getImagesByReviewId(review.getRev_id());
                review.setImages(images);
            }

            return reviews;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching reviews and images", e);
        }
    }

    
    @Override
    public String getRoomNameById(int ro_id) throws Exception {
        return mypageDAO.getRoomNameById(ro_id);
    }
    
}
