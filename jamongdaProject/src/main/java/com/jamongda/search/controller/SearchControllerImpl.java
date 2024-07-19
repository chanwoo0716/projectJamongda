package com.jamongda.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.search.dto.SearchDTO;
import com.jamongda.search.service.SearchService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller("searchController")
public class SearchControllerImpl implements SearchController{
	
   @Autowired
   private SearchService searchService;
   
	@Override
	@RequestMapping(value="/search/detailSearch.do", method=RequestMethod.GET)
	public ModelAndView detail(
			 @RequestParam("acc_id") int acc_id,
	         @RequestParam(value = "bo_checkIn", required = false) String bo_checkIn,
	         @RequestParam(value = "bo_checkOut", required = false) String bo_checkOut,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//acc_id인 숙소 및 객실들
		Map detailAccRoMap = searchService.detailAccRo(acc_id);
		
        // 대표자명, 사업자번호 추가
        Map<String, Object> hostInfo = searchService.getHostInfo(acc_id);
        detailAccRoMap.put("name", hostInfo.get("name"));
        detailAccRoMap.put("regNumber", hostInfo.get("regNumber"));
		
		
	   detailAccRoMap.put("acc_id", acc_id);
	   detailAccRoMap.put("bo_checkIn", bo_checkIn);
	   detailAccRoMap.put("bo_checkOut", bo_checkOut);
	   
      ModelAndView mav=new ModelAndView();
      mav.addObject("detailAccRoMap", detailAccRoMap);
      mav.setViewName("/search/detailSearch");
      return mav;
	}
   
   
   
}
