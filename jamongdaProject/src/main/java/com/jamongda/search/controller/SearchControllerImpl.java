package com.jamongda.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.member.dto.MemberDTO;
import com.jamongda.search.dto.SearchDTO;
import com.jamongda.search.service.SearchService;
import com.jamongda.wishlist.dao.WishlistDAO;
import com.jamongda.wishlist.service.WishlistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("searchController")
public class SearchControllerImpl implements SearchController {
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private WishlistDAO wishlistDAO;

	@Override
	@GetMapping("/search/selectSearch.do")
	public ModelAndView listSearch(@ModelAttribute("acc") AccommodationDTO acc,
			@RequestParam(value = "aname", required = false) String acc_name,
			@RequestParam(value = "area", required = false) String acc_area,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List accList = searchService.selectAll(); // 숙소 전체 리스트

		List accListsrch = searchService.search(acc); // 숙소 검색 리스트(이름,지역)

		ModelAndView mav = new ModelAndView();
		mav.setViewName("search/selectSearch");
		mav.addObject("accList", accList);
		mav.addObject("accListsrch", accListsrch);
		mav.addObject("bo_checkIn", bo_checkIn);
		mav.addObject("bo_checkOut", bo_checkOut);
		return mav;
	}

	@Override
	@GetMapping("/search/selectRadio.do")
	public ModelAndView listType(@ModelAttribute("acc") SearchDTO acc,
			@RequestParam(value = "aname", required = false) String acc_name,
			@RequestParam(value = "area", required = false) String acc_area,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut,
			@RequestParam(value = "acc_type", required = false) String acc_type, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List accList = searchService.selectAll(); // 숙소 전체 리스트
		List accListType = searchService.searchType(acc);// 숙소 검색 리스트(타입)

		ModelAndView mav = new ModelAndView();
		mav.setViewName("search/selectSearch");
		mav.addObject("acc_type", acc_type);
		mav.addObject("accListType", accListType);
		mav.addObject("bo_checkIn", bo_checkIn);
		mav.addObject("bo_checkOut", bo_checkOut);
		return mav;
	}

	@Override
	@GetMapping("/search/selectPrice.do")
	public ModelAndView listPrice(@ModelAttribute("acc") AccommodationDTO acc,
			@RequestParam(value = "aname", required = false) String acc_name,
			@RequestParam(value = "area", required = false) String acc_area,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut,
			@RequestParam(value = "acc_type", required = false) String acc_type,
			@RequestParam(value = "minPrice", required = false) Integer minPrice,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 기본값 설정
		if (minPrice == null) {
			minPrice = 0; // 기본값을 0으로 설정
		}
		if (maxPrice == null) {
			maxPrice = Integer.MAX_VALUE; // 기본값을 최대값으로 설정
		}

		List<SearchDTO> accRangePrice = searchService.rangePrice(minPrice, maxPrice);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("search/selectSearch");
		mav.addObject("accRangePrice", accRangePrice);
		mav.addObject("bo_checkIn", bo_checkIn);
		mav.addObject("bo_checkOut", bo_checkOut);
		return mav;
	}

	@Override
	@RequestMapping(value = "/search/detailSearch.do", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("aid") int acc_id,
			@RequestParam(value = "datetimes", required = false) String datetimes,
			@RequestParam(value = "checkIn", required = false) String bo_checkIn,
			@RequestParam(value = "checkOut", required = false) String bo_checkOut, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
	    HttpSession session = request.getSession(false);
	    MemberDTO member = null;
	    
	    if (session != null) {
	        member = (MemberDTO) session.getAttribute("guest");
	        if (member == null) {
	            member = (MemberDTO) session.getAttribute("member");
	        }
	    }

	    if (member != null) {
	        String email = member.getEmail();
	        boolean isWishlisted = wishlistDAO.checkWishlist(email, acc_id) > 0;
	        mav.addObject("isWishlisted",isWishlisted);
	    }		
		// acc_id인 숙소 및 객실들
		Map<String, Object> detailAccRoMap = searchService.detailAccRo(acc_id, bo_checkIn, bo_checkOut);

		// 대표자명, 사업자번호 추가
		Map<String, Object> hostInfo = searchService.getHostInfo(acc_id);
		detailAccRoMap.put("name", hostInfo.get("name"));
		detailAccRoMap.put("regNumber", hostInfo.get("regNumber"));

		detailAccRoMap.put("acc_id", acc_id);
		// 요청에 포함된 체크인/체크아웃 날짜 추가
		detailAccRoMap.put("bo_checkIn", bo_checkIn);
		detailAccRoMap.put("bo_checkOut", bo_checkOut);
		
		mav.addObject("detailAccRoMap", detailAccRoMap);
		mav.setViewName("search/detailSearch");
		return mav;
	}
	
	@GetMapping("/search/check-roomAvailability")
	public ResponseEntity<Map<Integer, Boolean>> checkRoomAvailability(
	        @RequestParam("aid") int acc_id,
	        @RequestParam("checkIn") String bo_checkIn,
	        @RequestParam("checkOut") String bo_checkOut) {
	    try {
	        Map<String, Object> detailAccRoMap = searchService.detailAccRo(acc_id, bo_checkIn, bo_checkOut);
	        @SuppressWarnings("unchecked")
	        Map<Integer, Boolean> roomAvailability = (Map<Integer, Boolean>) detailAccRoMap.get("roomAvailability");
	        if (roomAvailability == null) {
	            roomAvailability = new HashMap<>(); // 빈 객체로 초기화
	        }
	        return ResponseEntity.ok(roomAvailability);
	    } catch (DataAccessException e) {
	        e.printStackTrace(); // 에러 로그 추가
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
}