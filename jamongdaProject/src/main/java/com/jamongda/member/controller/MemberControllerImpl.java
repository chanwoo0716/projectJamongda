package com.jamongda.member.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.member.dto.MemberDTO;
import com.jamongda.member.service.MailSendService;
import com.jamongda.member.service.MemberService;
import com.jamongda.member.service.PhoneSendService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private PhoneSendService phoneSendService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberDTO memberDTO;

	@Override
	@GetMapping("/member/memberForm.do")
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/memberForm");
		return mav;
	}

	@Override
	@GetMapping("/member/memberForm_host.do")
	public ModelAndView memberFormH(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/memberForm_host");
		return mav;
	}

	// 이메일 중복확인
	@ResponseBody // (JSON데이터를 반환하기 위해)
	@GetMapping("/member/checkEmail")
	public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
		boolean isEmailAvailable = memberService.isEmailAvailable(email);
		return ResponseEntity.ok(isEmailAvailable);
	}

	// 이메일 인증
	@ResponseBody
	@GetMapping("/member/sendEmail")
	public String sendEmail(@RequestParam("email") String email) {
		System.out.println("이메일 인증 요청이 들어옴!");
		System.out.println("이메일 인증 이메일 : " + email);
		return mailSendService.joinEmail(email);
	}

	// 휴대폰 인증
	@GetMapping("/member/sendSMS")
	@ResponseBody
	public String sendSMS(@RequestParam("tel") String tel) {
		Random rand = new Random();
		String numStr = "";
		
		for (int i = 0; i < 4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr += ran;
		}

		System.out.println("수신자 번호 : " + tel);
		System.out.println("인증번호 : " + numStr);
		phoneSendService.certifiedPhoneNumber(tel, numStr); // 메서드 호출 시 파라미터 이름 수정
		
		return numStr;
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

	@Override
	@PostMapping("/member/updateMemberForm.do")
	public ModelAndView updateMemberForm(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		memberDTO = memberService.findInfo(email);
		ModelAndView mav = new ModelAndView("member/updateMemberForm");
		
		mav.addObject("member", memberDTO);
		return mav;
	}

	@Override
	@GetMapping("/member/updateMemberForm_host.do")
	public ModelAndView updateMemberFormH(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		memberDTO = memberService.findInfo(email);
		ModelAndView mav = new ModelAndView("member/updateMemberForm_host");
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
	@GetMapping("/member/loginForm_guest.do")
	public ModelAndView loginForm_guest(@ModelAttribute("member") MemberDTO member,
			@RequestParam(value = "redirect", required = false) String redirect,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // 세션이 있다면 세션 정보를 가져오고, 없다면 세션을 생성하는 거다.
		session.setAttribute("action", action);

		// 예약하기 redirect
		if (redirect != null) {
			session.setAttribute("redirect", redirect);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName("member/loginForm_guest");
		return mav;
	}

	@Override
	@GetMapping("/member/loginForm_host.do")
	public ModelAndView loginForm_host(@ModelAttribute("member") MemberDTO member,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result2", required = false) String result2, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result2", result2);
		mav.setViewName("member/loginForm_host");
		return mav;
	}

	@PostMapping("/member/login_guest.do")
	public ModelAndView login_guest(@ModelAttribute("member") MemberDTO member, RedirectAttributes rAttr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		// 이메일로 회원 정보 조회
		MemberDTO existingMember = memberService.getMemberByEmail(member.getEmail());

		if (existingMember != null) {
			// 비밀번호 비교
			if (existingMember.getPwd().equals(member.getPwd())) {
				HttpSession session = request.getSession();
				String memberRole = existingMember.getRole();
				session.setAttribute("member", existingMember);
				session.setAttribute("isLogOn", true);

				if ("A".equals(memberRole)) { // 관리자 로그인
					session.setAttribute("admin", existingMember);
					mav.setViewName("redirect:/admin/mainAdmin.do");
				} else if ("G".equals(memberRole)) { // 일반회원 로그인
					session.setAttribute("guest", existingMember);
					String redirect = (String) session.getAttribute("redirect");
					session.removeAttribute("redirect");
					mav.setViewName(redirect != null ? "redirect:" + redirect : "redirect:/main.do");
				} else { // 잘못된 역할로 로그인 시도
					rAttr.addAttribute("result", "접근 권한이 없습니다.");
					mav.setViewName("redirect:/member/loginForm_guest.do");
					session.invalidate();
				}
			} else {
				// 비밀번호가 틀린 경우
				rAttr.addAttribute("result", "비밀번호가 틀렸습니다. 다시 입력해주세요.");
				mav.setViewName("redirect:/member/loginForm_guest.do");
			}
		} else {
			// 이메일이 존재하지 않는 경우
			rAttr.addAttribute("result", "존재하지 않는 이메일입니다. 다시 확인해주세요.");
			mav.setViewName("redirect:/member/loginForm_guest.do");
		}

		return mav;
	}

	@PostMapping("/member/login_host.do")
	public ModelAndView login_host(@ModelAttribute("member") MemberDTO member, RedirectAttributes rAttr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		// 이메일로 회원 정보 조회
		MemberDTO existingMember = memberService.getMemberByEmail(member.getEmail());

		if (existingMember != null) {
			// 비밀번호 비교
			if (existingMember.getPwd().equals(member.getPwd())) {
				HttpSession session = request.getSession();
				String memberRole = existingMember.getRole();
				session.setAttribute("member", existingMember);
				session.setAttribute("isLogOn", true);

				if ("A".equals(memberRole)) { // 관리자 로그인
					session.setAttribute("admin", existingMember);
					mav.setViewName("redirect:/admin/mainAdmin.do");
				} else if ("H".equals(memberRole)) { // 사업자 로그인
					session.setAttribute("host", existingMember);
					String action = (String) session.getAttribute("action");
					mav.setViewName(
							action != null ? "redirect:" + action : "redirect:/accommodation/regAccommodation.do");
				} else { // 잘못된 역할로 로그인 시도
					rAttr.addAttribute("result", "접근 권한이 없습니다.");
					mav.setViewName("redirect:/member/loginForm_host.do");
					session.invalidate();
				}
			} else {
				// 비밀번호가 틀린 경우
				rAttr.addAttribute("result", "비밀번호가 틀렸습니다. 다시 입력해주세요.");
				mav.setViewName("redirect:/member/loginForm_host.do");
			}
		} else {
			// 이메일이 존재하지 않는 경우
			rAttr.addAttribute("result", "존재하지 않는 이메일입니다. 다시 확인해주세요.");
			mav.setViewName("redirect:/member/loginForm_host.do");
		}
		return mav;
	}

	@Override
	@GetMapping("/member/logout.do")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/main.do");
		return mav;
	}
}