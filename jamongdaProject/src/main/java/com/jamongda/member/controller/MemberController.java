package com.jamongda.member.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberController {
	
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView memberFormH(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView insertMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView updateMemberForm(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMemberFormH(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView deleteMember(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;


	public ModelAndView loginForm_guest(@ModelAttribute("member") MemberDTO member,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ModelAndView loginForm_host(@ModelAttribute("host") MemberDTO host,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	

	public ModelAndView login_guest(@ModelAttribute("member") MemberDTO member,
			RedirectAttributes rAttr,//실제 회원 정보를 데이터를 통해 찾아서 정보가 없으면 다시 redirect해서 로그인 폼으로 돌려줄거다.
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ModelAndView login_host(@ModelAttribute("member") MemberDTO member,
			RedirectAttributes rAttr,//실제 회원 정보를 데이터를 통해 찾아서 정보가 없으면 다시 redirect해서 로그인 폼으로 돌려줄거다.
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	

	
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
