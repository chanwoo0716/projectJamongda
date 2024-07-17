package com.jamongda.admin.controller;

import java.util.List; 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.admin.service.AdminService;
import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("adminController")
public class AdminControllerImpl implements AdminController {
	
	private static String ACCOMMODATION_IMAGE_REPO="D:\\Hwang\\FileuploadAcc";
	private static String ROOM_IMAGE_REPO="D:\\Hwang\\FileuploadRo";
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MemberDTO memberDTO;
	
	//관리자 메인페이지
	@Override
	@GetMapping("/admin/mainAdmin.do")
	public ModelAndView mainAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인한 관리자
	    /*
		HttpSession session = multipartRequest.getSession();
		LoginDTO loginDTO=(LoginDTO)session.getAttribute("email");
		String email=loginDTO.getEmail();
		*/
		/*email이 admin이 아닐 시 main.do로 가게 하기?
		ModelAndView mav = new ModelAndView();
		if(email="admin") {
			mav.setViewName("/admin/mainAdmin");
			return mav;
		}else {
			mav.setViewName("/main.do");
			return mav;
		}
		*/
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/mainAdmin");
		return mav;
	}
	
	//숙소 등록 요청 페이지
	@GetMapping("/admin/regManageAccommodation.do")
	@Override
	public ModelAndView regManageAccommodation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//숙소등록요청 조회하기(*regCheck가 N인 사업자의 숙소를 보여줘야함)
		Map regN_accRoMap = adminService.regN_AccRo();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/regManageAccommodation");
		mav.addObject("adminSidebar", "admin/adminSidebar");
		mav.addObject("regN_accRoMap", regN_accRoMap);
		return mav;
	}
	//숙소 등록 승인 페이지
	@GetMapping("/admin/viewApproveAccList.do")
	@Override
	public ModelAndView viewApproveAccList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//숙소등록요청 조회하기(*regCheck가 Y인 사업자의 숙소를 보여줘야함)
		Map regY_accRoMap = adminService.regY_AccRo();
		
		HttpSession session=request.getSession();
		session.setAttribute("regY_accRoMap", regY_accRoMap);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewApproveAccList");
		mav.addObject("adminSidebar", "admin/adminSidebar");
		mav.addObject("regY_accRoMap", regY_accRoMap);
		return mav;
	}
	//등록된 숙소 수정하기 admin/modAccommodation.do
	@PostMapping("admin/modAccommodation.do")
	@Override
	public ModelAndView modAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//세션에 세팅된 등록된 숙소 가져오기
		HttpSession session=request.getSession();
		Map regY_accRoMap=(Map)session.getAttribute("regY_accRoMap");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewApproveAccList");
		mav.addObject("adminSidebar", "admin/adminSidebar");
		mav.addObject("regY_accRoMap", regY_accRoMap);
		return mav;
	}
	
	
	
	//승인 숙소 검색(이메일)
	@GetMapping("/admin/searchApproveAccList.do")
	@Override
	public ModelAndView searchApproveAccList(@RequestParam("searchEmail") String searchEmail, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//특정 이메일에 해당하는 회원정보 가져오기
		Map search_regY_accRoMap = adminService.search_RegY_AccRo(searchEmail);
		
		System.out.println(search_regY_accRoMap);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewApproveAccList");
		mav.addObject("adminSidebar", "admin/adminSidebar");
		mav.addObject("regY_accRoMap", search_regY_accRoMap);
		return mav;
	}
	
	//숙소 등록 거부 페이지
	@GetMapping("/admin/viewRejectAccList.do")
	@Override
	public ModelAndView viewRejectAccList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//숙소등록요청 조회하기(*regCheck가 C인 사업자의 숙소를 보여줘야함)
		Map regC_accRoMap = adminService.regC_AccRo();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewRejectAccList");
		mav.addObject("adminSidebar", "admin/adminSidebar");
		mav.addObject("regC_accRoMap", regC_accRoMap);
		return mav;
	}
	
	//숙소 등록 승인하기
	@PostMapping("/admin/approveAccList.do")
	@Override
	public ModelAndView approveAccList(@RequestParam("acc_id") int acc_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//regCheck를 Y로 바꾸기 (update수행)
		adminService.approveAcc(acc_id);
		
	    ModelAndView mav = new ModelAndView("redirect:/admin/regManageAccommodation.do");
	    return mav;
	}
	//숙소 등록 거부하기
	@PostMapping("/admin/rejectAccList.do")
	@Override
	public ModelAndView rejectAccList(@RequestParam("acc_id") int acc_id, @RequestParam("reject_reason") String reject_reason, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//regCheck를 C로 바꾸기 (update수행)
		adminService.rejectAcc(acc_id);
		
		//취소사유 테이블에 추가하기(update)
		adminService.rejectReason(acc_id, reject_reason);
		
		ModelAndView mav = new ModelAndView("redirect:/admin/regManageAccommodation.do");
	    return mav;
	}

	//회원관리 페이지
	@GetMapping("/admin/manageMember.do")
	@Override
	public ModelAndView manageMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//회원정보 모두 가져오기(가입일자 순으로)
		List membersList = adminService.listMembers();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/manageMember");
		mav.addObject("membersList",membersList);
		return mav;
	}
	//회원검색기능(이메일) ->해당 이메일의 회원정보만 가져오기
	@Override
	@GetMapping("/admin/searchMember.do")
	public ModelAndView searchMember(@RequestParam("searchEmail") String searchEmail, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//특정 이메일에 해당하는 회원정보 가져오기
		List searchMembersList = adminService.searchMembers(searchEmail);
		
		System.out.println(searchMembersList);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/manageMember");
		mav.addObject("membersList",searchMembersList);
		return mav;
	}
	
	//회원수정하기 페이지
	@Override
	@GetMapping("/admin/modMember.do")
	public ModelAndView modMemberForm(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//특정 회원(email)의 회원정보 가져오기
		memberDTO=adminService.findMember(email);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/modMemberForm");
		mav.addObject("member", memberDTO);
		return mav;
	}
	
	//회원정보수정
	@Override
	@PostMapping("/admin/updateMember.do")
	public ModelAndView updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		//회원정보수정
		adminService.updateMember(memberDTO);
		ModelAndView mav = new ModelAndView("redirect:/admin/manageMember.do");
		return mav;
	}
	
	//회원삭제하기
	@Override
	@GetMapping("/admin/delMember.do")
	public ModelAndView delMember(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		adminService.delMember(email);
		ModelAndView mav = new ModelAndView("redirect:/admin/manageMember.do");
		return mav;
	}
	
}
