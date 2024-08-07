package com.jamongda.review.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String uploadDir = "C:\\Users\\lynli\\OneDrive\\바탕 화면\\project\\fileupload\\FileuploadRev/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File file = new File(uploadDir + fileName);
        
        // 이미지 파일 저장
        image.transferTo(file);
        
        return fileName; // 저장된 파일 이름 반환
    }

    // 숙소 상세페이지에 리뷰 출력(acc_id)
    @Override
    public List<ReviewDTO> getReviewsByAccId(int acc_id, int page, int size) throws Exception {
        int offset = (page - 1) * size;
        Map<String, Object> params = new HashMap<>();
        params.put("acc_id", acc_id);
        params.put("offset", offset);
        params.put("size", size);

        // 리뷰 리스트를 가져옵니다.
        List<ReviewDTO> reviews = reviewDAO.getReviewsByAccId(params);

        // 각 리뷰에 대한 이미지와 room 이름을 추가합니다.
        for (ReviewDTO review : reviews) {
            List<ReviewImageDTO> images = reviewDAO.getImagesByReviewId(review.getRev_id());
            review.setImages(images);
            String roName = reviewDAO.getRoomNameById(review.getRo_id());
            review.setRo_name(roName);
        }
        return reviews;
    }

    public List<ReviewImageDTO> getImagesByReviewId(int rev_id) {
        List<ReviewImageDTO> images = reviewDAO.getImagesByReviewId(rev_id);
        System.out.println("Number of images for review ID " + rev_id + ": " + images.size());
        return images;
    }

    @Override
    public boolean deleteReviewById(int rev_id) throws Exception {
        // 리뷰가 존재하는지 확인
        if (reviewDAO.existById(rev_id)) {
            // 리뷰와 관련된 이미지 파일 이름 조회
            List<String> imageFiles = reviewDAO.getImageFilesByReviewId(rev_id);

            // 파일 시스템에서 이미지 파일 삭제
            deleteImageFiles(imageFiles);

            // DB에서 리뷰 삭제
            reviewDAO.deleteById(rev_id);
            return true;
        } else {
            return false;
        }
    }
	
    // 파일 시스템에서 이미지 파일 삭제
    private void deleteImageFiles(List<String> imageFiles) throws Exception {
        String uploadDir = "C:\\Users\\lynli\\OneDrive\\바탕 화면\\project\\fileupload\\FileuploadRev\\";
        
        for (String fileName : imageFiles) {
            File file = new File(uploadDir + fileName);
            if (file.exists()) {
                if (!file.delete()) {
                    System.err.println("Failed to delete file: " + fileName);
                }
            }
        }
    }
}