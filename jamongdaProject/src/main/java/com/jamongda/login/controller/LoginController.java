package com.jamongda.login.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jamongda.login.dto.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginController {
	/*public ModelAndView guestLoginForm(@ModelAttribute("guest") LoginDTO guest,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView guestLogin(@ModelAttribute("guest")  LoginDTO guest,@RequestParam("email") String email, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	

	public ModelAndView guestLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ModelAndView hostLoginForm(@ModelAttribute("host") LoginDTO host,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "result", required = false) String result,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ModelAndView hostLogin(@ModelAttribute("host") LoginDTO host,
			RedirectAttributes rAttr,//실제 회원 정보를 데이터를 통해 찾아서 정보가 없으면 다시 redirect해서 로그인 폼으로 돌려줄거다.
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ModelAndView hostLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public ModelAndView adminLogin(@ModelAttribute("admin")  LoginDTO admin, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception;*/
}
