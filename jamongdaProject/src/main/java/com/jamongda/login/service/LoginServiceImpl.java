package com.jamongda.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.login.dao.LoginDAO;
import com.jamongda.login.dto.LoginDTO;

@Service("LoginService")
public class LoginServiceImpl implements LoginService{
	
//	@Autowired
//	private LoginDAO loginDAO;
//	
//	@Override
//	public LoginDTO login(LoginDTO loginDTO) throws DataAccessException {
//		return loginDAO.loginCheck(loginDTO);
//	}
//	
//	@Override
//	public String getAdminEmail(String email) throws DataAccessException {
//		return loginDAO.adminCheck(email);
//	}
}
