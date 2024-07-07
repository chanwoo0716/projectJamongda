package com.jamongda.member.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberController {
	//삭제예정
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView insertMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
//	public void sendVerificationEmail(String email,
//			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
	
	public ModelAndView updateMemberForm(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView deleteMember(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView memberFormH(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView insertMemberH(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMemberFormH(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMemberH(@ModelAttribute("memberDTO") MemberDTO memberDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView deleteMemberH(@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}
