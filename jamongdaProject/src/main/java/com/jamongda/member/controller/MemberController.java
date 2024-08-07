package com.jamongda.member.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberController {
	// 회원가입 페이지
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView memberFormH(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 회원가입 정보 등록
	public ModelAndView insertMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 회원정보수정 페이지
	public ModelAndView updateMemberForm(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMemberFormH(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 회원정보수정 정보 업데이트
	public ModelAndView updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 회원탈퇴
	public ModelAndView deleteMember(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 로그인 페이지(일반회원)
	public ModelAndView loginForm_guest(@ModelAttribute("member") MemberDTO member,
			@RequestParam(value = "redirect", required = false) String redirect,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	// 로그인 페이지(사업자)
	public ModelAndView loginForm_host(@ModelAttribute("host") MemberDTO host,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	// 일반회원 로그인
	public ModelAndView login_guest(@ModelAttribute("member") MemberDTO member,
			RedirectAttributes rAttr,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	// 사업자 로그인
	public ModelAndView login_host(@ModelAttribute("member") MemberDTO member,
			RedirectAttributes rAttr,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	// 로그아웃
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
}