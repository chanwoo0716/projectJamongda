package com.jamongda.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("boardController")
public class BoardControllerImpl implements BoardController{
	
	//board 조회 메서드
	@Override
	@RequestMapping(value="/board/boardList.do")
	public ModelAndView boardList(@RequestParam(value="section", required = false) String _section, @RequestParam(value="pageNum", required = false) String _pageNum, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/boardList");
		
		return mav;
	}
	
	//board 생성 폼으로 이동하는 메서드
	
	//board 상세글 보는 메서드
	
	//board 상세글 수정하는 메서드
	
	//board 상세글 삭제하는 메서드
	
}
