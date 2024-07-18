package com.jamongda.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.login.dto.LoginDTO;
import com.jamongda.login.service.HostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("loginHostController")
public class HostControllerImpl implements HostController{

	/*@Autowired
	private HostService hostService;
	
	@Autowired
	private LoginDTO loginHostDTO;

	@Override
	@GetMapping("/login/hostloginForm.do")
	public ModelAndView loginForm(@ModelAttribute("host") LoginDTO host,
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
	public ModelAndView login(@ModelAttribute("host")  LoginDTO host, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		loginHostDTO=hostService.login(host);
		ModelAndView mav=new ModelAndView();
		if(loginHostDTO != null) {
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
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		session.removeAttribute("host");
		session.removeAttribute("isLogon");
		session.removeAttribute("email");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/mainhost.do");
		return mav;
	}*/

}
