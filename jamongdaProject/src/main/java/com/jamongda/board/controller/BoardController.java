package com.jamongda.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BoardController {

	public ModelAndView boardList(@RequestParam("section") String _section, @RequestParam("pageNum") String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
