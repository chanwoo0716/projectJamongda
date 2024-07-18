package com.jamongda.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.login.dao.LoginGuestDAO;
import com.jamongda.login.dto.LoginDTO;
import com.jamongda.login.service.GuestService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("GuestController")
public class GuestControllerImpl implements GuestController{

	/*@Autowired
	private GuestService guestService;
	
	@Autowired
	private LoginDTO loginGuestDTO;
	
	@Override
	@GetMapping("/login/guestloginForm.do")
	public ModelAndView loginForm(@ModelAttribute("guest") LoginDTO guest,
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
	public ModelAndView login(@ModelAttribute("guest")  LoginDTO guest, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		loginGuestDTO=guestService.login(guest);
		ModelAndView mav=new ModelAndView();
		if(loginGuestDTO != null) {
			//회원이 있다.
			HttpSession session=request.getSession();
			session.setAttribute("guest", loginGuestDTO);
			session.setAttribute("isLogOn", true);
			String action=(String)session.getAttribute("action");
			if(action != null) {
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
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		session.removeAttribute("guest");
		session.removeAttribute("isLogOn");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/main.do");
		return mav;
	}*/
	

}
