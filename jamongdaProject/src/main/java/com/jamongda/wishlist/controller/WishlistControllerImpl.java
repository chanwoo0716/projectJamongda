package com.jamongda.wishlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;
import com.jamongda.wishlist.service.WishlistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController("wishlistControllerImpl")
public class WishlistControllerImpl implements WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Override
	@PostMapping("/wishlist/toggleWish")
	public ResponseEntity<Map<String, Object>> toggleWish(@RequestParam("aid") int acc_id, HttpServletRequest request)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			HttpSession session = request.getSession(false);

			if (session == null) {
				response.put("success", false);
				response.put("message", "세션이 만료되었습니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			MemberDTO member = (MemberDTO) session.getAttribute("guest");
			if (member == null) {
				member = (MemberDTO) session.getAttribute("member");
			}

			if (member == null) {
				response.put("success", false);
				response.put("message", "로그인이 필요한 기능입니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			String email = member.getEmail();
			if (email == null || email.isEmpty()) {
				response.put("success", false);
				response.put("message", "세션에 이메일 정보가 없습니다.");
				response.put("redirectUrl", "/member/loginForm_guest.do");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			boolean isAdded = wishlistService.toggleWish(email, acc_id);

			response.put("success", true);
			response.put("added", isAdded);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("message", "서버에서 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@Override
	@PostMapping("/wishlist/myWishlistPage.do")
	public ModelAndView myWishlistPage(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.addObject("email", email);
		mav.setViewName("mypage/myWishlist");

		return mav;
	}

	@Override
	@GetMapping("/wishlist/getWishlist")
	public Map<String, Object> getWishlist(@RequestParam("email") String email, @RequestParam("page") int page) throws Exception {
	    int pageSize = 4; // 페이지당 항목 수
	    int offset = (page - 1) * pageSize;
	    List<Map<String, Object>> items = wishlistService.getWishlistByUser(email, pageSize, offset);

	    Map<String, Object> response = new HashMap<>();

	    if(items == null || items.isEmpty()) {
	    	response.put("message","찜한 내역이 없습니다.");
	    	response.put("isLastPage", true); // 데이터 없어서 
	    }else {
		    boolean isLastPage = items.size() < pageSize;	    
		    response.put("data", items);
		    response.put("isLastPage", isLastPage);
	    }
	    
	    return response;
	}

}