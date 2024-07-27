package com.jamongda.admin.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminController {

	//관리자 첫 로그인 페이지 이동
	public ModelAndView mainAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//숙소 등록 요청 페이지
	public ModelAndView regManageAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//숙소 등록 승인 페이지(세션에 regY_accRoMap세팅)
	public ModelAndView viewApproveAccList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	//등록된 숙소 수정하기(세션에서 regY_accRoMap 가져오기)
	public ModelAndView modAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception; 
	//승인 숙소 검색(이메일)
	public ModelAndView searchApproveAccList(@RequestParam("searchEmail") String searchEmail, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//숙소 등록 거부 페이지
	public ModelAndView viewRejectAccList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//숙소 등록 승인하기
	public ModelAndView approveAccList(@RequestParam("acc_id") int acc_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//숙소 등록 거부하기
	public ModelAndView rejectAccList(@RequestParam("acc_id") int acc_id, @RequestParam("reject_reason") String reject_reason, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//숙소 삭제하기
	public ModelAndView removeAccList(@RequestParam("acc_id") int acc_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//회원관리 페이지
	public ModelAndView manageMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//회원검색기능(이메일)
	public ModelAndView searchMember(@RequestParam("searchEmail") String searchEmail, @RequestParam("role") String role, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//회원정보수정하기 페이지
	public ModelAndView modMemberForm(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//회원정보수정
	public ModelAndView updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//회원삭제하기
	public ModelAndView delMember(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
