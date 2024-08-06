package com.jamongda.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;
import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.review.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("reviewControllerImpl")
public class ReviewControllerImpl implements ReviewController {
	
    @Autowired
    ReviewService reviewService;

    @Override
    @GetMapping("/review/reviewForm.do")
    public ModelAndView reviewForm(@RequestParam("number") Long bo_number,
                                   @RequestParam("aname") String acc_name,
                                   @RequestParam("rname") String ro_name,
                                   @RequestParam("rid") String ro_id,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();
        MemberDTO guest = (MemberDTO) session.getAttribute("guest");

        System.out.println("Booking number: " + bo_number);
        mav.addObject("guest", guest);
        mav.addObject("bo_number", bo_number);
        mav.addObject("acc_name", acc_name);
        mav.addObject("ro_id", ro_id);
        mav.addObject("ro_name", ro_name);
        mav.setViewName("review/reviewForm");
        return mav;
    }

    @Override
    @PostMapping("/review/insertReview.do")
    public ModelAndView insertReview(@RequestParam("rev_content") String rev_content,
                                     @RequestParam("ro_id") int ro_id,
                                     @RequestParam("email") String email,
                                     @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        System.out.println("Review content: " + rev_content);
        System.out.println("Room ID: " + ro_id);
        System.out.println("Email: " + email);
        System.out.println("Images: " + (images == null ? "No images" : images.size()));

        // Email을 session에서 가져오는 경우 (중복 확인 필요)
        HttpSession session = request.getSession();
        MemberDTO guest = (MemberDTO) session.getAttribute("guest");
        if (guest != null) {
            email = guest.getEmail();
        }

        // 리뷰 DTO 설정
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRev_content(rev_content);
        reviewDTO.setRo_id(ro_id);
        reviewDTO.setEmail(email);

        // 리뷰 삽입
        reviewService.insertReview(reviewDTO, images);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mypage/mypage.do");
        return mav;
    }
    
	// 숙소 상세페이지에 리뷰 출력(acc_id) - ajax 엔드포인트
	@GetMapping("/search/reviews")
	@ResponseBody
	public ResponseEntity<List<ReviewDTO>> getReviewsByAccId(@RequestParam("aid") int acc_id,
												            @RequestParam(value = "page", defaultValue = "1") int page,
												            @RequestParam(value = "size", defaultValue = "5") int size) {
		try {
			List<ReviewDTO> reviews = reviewService.getReviewsByAccId(acc_id, page, size);
			return ResponseEntity.ok(reviews);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	// 리뷰 삭제하기
	@PostMapping("/review/deleteReviews")
	public ResponseEntity<?> deleteReviews(@RequestParam("id") int rev_id) {
        try {
            boolean isDeleted = reviewService.deleteReviewById(rev_id);
            if (isDeleted) {
                return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(404).body("리뷰를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("리뷰 삭제 중 오류가 발생했습니다.");
        }
		
	}

}
