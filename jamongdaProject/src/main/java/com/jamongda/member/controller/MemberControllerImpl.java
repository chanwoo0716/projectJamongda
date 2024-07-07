package com.jamongda.member.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.member.dto.MemberDTO;
import com.jamongda.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberDTO memberDTO;

	// 삭제예정
	@Override
	@GetMapping("/member/listMembers.do")
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/listMembers");
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@GetMapping("/member/memberForm.do")
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/memberForm");
		return mav;
	}

	@Override
	@PostMapping("/member/insertMember.do")
	public ModelAndView insertMember(@ModelAttribute("memberDTO") MemberDTO memberDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		memberService.insertMember(memberDTO);
		ModelAndView mav = new ModelAndView("redirect:/main.do");
		return mav;
	}

//	@Override
//	@PostMapping("/member/sendVerificationEmail")
//	public void sendVerificationEmail(@RequestParam("email") String email, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		try {
//			memberService.sendVerificationEmail(email);
//			response.sendRedirect("/member/memberForm.do?successMessage=인증 이메일이 발송되었습니다.");
//		} catch (RuntimeException e) {
//			response.sendRedirect("/member/memberForm.do?errorMessage=" + e.getMessage());
//		}
//	}

	@Override
	@GetMapping("/member/updateMemberForm.do")
	public ModelAndView updateMemberForm(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		memberDTO = memberService.findInfo(email);
		ModelAndView mav = new ModelAndView("/member/updateMemberForm");
		mav.addObject("member", memberDTO);
		return mav;
	}

	@Override
	@PostMapping("/member/updateMember.do")
	public ModelAndView updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		memberService.updateMember(memberDTO);
		ModelAndView mav = new ModelAndView("redirect:/main.do");
		return mav;
	}

	@Override
	@GetMapping("/member/deleteMember.do")
	public ModelAndView deleteMember(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/main.do");
		memberService.deleteMember(email);
		return mav;
	}

	@Override
	@GetMapping("/member/memberForm_host.do")
	public ModelAndView memberFormH(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/memberForm_host");
		return mav;
	}

	@Override
	@PostMapping("/member/insertMemberH.do")
	public ModelAndView insertMemberH(MemberDTO memberDTO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		memberService.insertMemberH(memberDTO);
		ModelAndView mav = new ModelAndView("redirect:/main.do");
		return mav;
	}

	@Override
	@GetMapping("/member/updateMemberFormH.do")
	public ModelAndView updateMemberFormH(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		memberDTO = memberService.findInfoH(email);
		ModelAndView mav = new ModelAndView("/member/updateMemberForm");
		mav.addObject("member", memberDTO);
		return mav;
	}

	@Override
	@PostMapping("/member/updateMemberH.do")
	public ModelAndView updateMemberH(@ModelAttribute("memberDTO") MemberDTO memberDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		memberService.updateMemberH(memberDTO);
		ModelAndView mav = new ModelAndView("redirect:/main.do");
		return mav;
	}

	@Override
	@GetMapping("/member/deleteMemberH.do")
	public ModelAndView deleteMemberH(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/main.do");
		memberService.deleteMemberH(email);
		return mav;
	}

}
