package com.jamongda.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.login.dto.LoginDTO;
import com.jamongda.login.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("LoginController")
public abstract class LoginControllerImpl implements LoginController{
	/*@Autowired
	private LoginService loginService;
	
	@Autowired
	private LoginDTO loginDTO;
	
	@Override
	@GetMapping("/login/guestloginForm.do")
	public ModelAndView guestLoginForm(@ModelAttribute("guest") LoginDTO guest,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(); //세션이 있다면 세션 정보를 가져오고, 없다면 세션을 생성하는 거다.
		session.setAttribute("action",action);
		ModelAndView mav=new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName("/login/guestloginForm");
		return mav;
	}

	@Override
	@PostMapping("/login/guestlogin.do")
	public ModelAndView guestLogin(@ModelAttribute("guest")  LoginDTO guest,@RequestParam("email") String email, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		loginDTO=loginService.login(guest);
		ModelAndView mav=new ModelAndView();
		if(loginDTO != null) {
			//회원이 있다.
			HttpSession session=request.getSession();
			session.setAttribute("guest", loginDTO);
			session.setAttribute("isLogOn", true);
			String action=(String)session.getAttribute("action");
			
			String adminEmail=loginService.getAdminEmail(email);	// 관리자(admin)이메일 가져오기
			
			if(action != null && adminEmail == "admin") {
				mav.setViewName("redirect:" + action);
			}else {
				mav.setViewName("redirect:/main.do");
			}
		}else {
			//회원 정보가 없다.
			rAttr.addAttribute("result","아이디나 비밀번호가 틀립니다. 다시 로그인해주세요.");
			mav.setViewName("redirect:/login/guestloginForm.do");
		}
		return mav;
	}

	@Override
	@GetMapping("/login/guestlogout.do")
	public ModelAndView guestLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		session.removeAttribute("guest");
		session.removeAttribute("isLogOn");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/main.do");
		return mav;
	}
	
	@Override
	@GetMapping("/login/hostloginForm.do")
	public ModelAndView hostLoginForm(@ModelAttribute("host") LoginDTO host,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result2", required = false) String result2,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(); //세션이 있다면 세션 정보를 가져오고, 없다면 세션을 생성하는 거다.
		session.setAttribute("action",action);
		ModelAndView mav=new ModelAndView();
		mav.addObject("result2", result2);
		mav.setViewName("/login/hostloginForm");
		return mav;
	}
	

	@Override
	@PostMapping("/login/hostlogin.do")
	public ModelAndView hostLogin(@ModelAttribute("host")  LoginDTO host, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		loginDTO=loginService.login(host);
		ModelAndView mav=new ModelAndView();
		if(loginDTO != null) {
			//회원이 있다.
			HttpSession session=request.getSession();
			session.setAttribute("host", host);
			session.setAttribute("isLogon", true);
			String email=host.getEmail();
			session.setAttribute("email", email);
			String action=(String)session.getAttribute("action");
			if(action != null) {
				mav.setViewName("redirect:" + action);
			}else {
				mav.setViewName("redirect:/mainhost.do");
				//mav.setViewName("redirect:/accommodation/regAccommodation.do");
			}
		}else {
			//회원 정보가 없다.
			rAttr.addAttribute("result2","아이디나 비밀번호가 틀립니다. 다시 로그인해주세요.");
			mav.setViewName("redirect:/login/hostloginForm.do");
		}
		return mav;
	}

	@Override
	@GetMapping("/login/hostlogout.do")
	public ModelAndView hostLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		session.removeAttribute("host");
		session.removeAttribute("isLogon");
		session.removeAttribute("email");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/mainhost.do");
		return mav;
	}

	@Override
	public ModelAndView adminLogin(LoginDTO admin, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	
}
