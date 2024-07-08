package com.jamongda.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Parameter;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BoardController {
	
	public ModelAndView listBoards(@RequestParam("section") String _section, @RequestParam("pageNum") String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView boardForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView FAQ(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//새글 추가 메서드
	public ModelAndView addBoard(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//상세글 메서드
	public ModelAndView viewBoard(@RequestParam("boarId") int boardId,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//수정폼으로 이동하는 메서드
	public ModelAndView modBoardForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//수정 메서드
	public ModelAndView modBoard(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//삭제 메서드
	public ModelAndView removeBoard(@RequestParam("boarId") int boardId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}