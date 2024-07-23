package com.jamongda.review.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jamongda.review.dao.ReviewDAO;
import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.review.dto.ReviewImageDTO;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
	
    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    public void insertReview(ReviewDTO reviewDTO, List<MultipartFile> images) throws Exception {
        reviewDAO.insertReview(reviewDTO);
        
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                // 이미지 저장 경로 및 파일 이름 생성
                String fileName = saveImage(image);
                
                // ReviewImageDTO 생성 및 값 설정
                ReviewImageDTO reviewImageDTO = new ReviewImageDTO();
                reviewImageDTO.setRev_id(reviewDTO.getRev_id()); // 리뷰 ID 설정
                reviewImageDTO.setRev_image(fileName); // 저장된 이미지 파일 이름 설정
                
                // ReviewImage 저장
                reviewDAO.insertReviewImage(reviewImageDTO);
            }
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String uploadDir = "C:/Users/lynli/OneDrive/바탕 화면/project/fileupload/FileuploadRev/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File file = new File(uploadDir + fileName);
        
        // 이미지 파일 저장
        image.transferTo(file);
        
        return fileName; // 저장된 파일 이름 반환
    }

}

