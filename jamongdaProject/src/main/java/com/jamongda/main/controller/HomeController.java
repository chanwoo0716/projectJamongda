package com.jamongda.main.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.main.service.HomeControllerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

	@Autowired
	private HomeControllerService homeControllerService;
	
	@GetMapping("/main.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//자몽다pick 이미지 8개,acc_id 가져오기(2024/07/24 황찬우가 주는 조건 : 객실가격 50,000원~100000원 숙소들)
		List<Map<String, Object>> jamongdaAccList = homeControllerService.jamongdaAcc();
        //jamongdaAccList 출력
        System.out.println("jamongdaAccList: " + jamongdaAccList);
		// jamongdaAccList 이미지 파일 이름 리스트
        List<String> jamongdaAccListImage = new ArrayList<>();
		// jamongdaAccList acc_id 리스트
        List<Integer> jamongdaAccListAcc_id = new ArrayList<>();
        // jamongdaAccList에서 acc_image와 acc_id 분리
        for (Map<String, Object> map : jamongdaAccList) {
        	jamongdaAccListImage.add((String) map.get("acc_image"));
        	jamongdaAccListAcc_id.add((Integer) map.get("acc_id"));
        }
        System.out.println("jamongdaAccListImage:" + jamongdaAccListImage);
        System.out.println("jamongdaAccListAcc_id" + jamongdaAccListAcc_id);
        
        
		//최저가 숙소 이미지 8개,acc_id 가져오기
		List<Map<String, Object>> lowestAccList = homeControllerService.lowestAcc();
        // lowestAccList 출력
        System.out.println("lowestAccList: " + lowestAccList);
		// 이미지 파일 이름 리스트
        List<String> lowestAccListImage = new ArrayList<>();
		// acc_id 리스트
        List<Integer> lowestAccListAcc_id = new ArrayList<>();
        // lowestAccList에서 acc_image와 acc_id 분리
        for (Map<String, Object> map : lowestAccList) {
            lowestAccListImage.add((String) map.get("acc_image"));
            lowestAccListAcc_id.add((Integer) map.get("acc_id"));
        }
        //System.out.println("lowestAccListImage:" + lowestAccListImage);
        //System.out.println("lowestAccListAcc_id" + lowestAccListAcc_id);
        
		ModelAndView mav = new ModelAndView();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        mav.addObject("today", today.toString());
        mav.addObject("tomorrow", tomorrow.toString());
        mav.addObject("datetimes", today.toString() + " - " + tomorrow.toString());
        
        mav.addObject("jamongdaAccListImage",jamongdaAccListImage);
		mav.addObject("jamongdaAccListAcc_id",jamongdaAccListAcc_id);
		mav.addObject("lowestAccListImage",lowestAccListImage);
		mav.addObject("lowestAccListAcc_id",lowestAccListAcc_id);
		mav.setViewName("/main");
		return mav;
	}
}

