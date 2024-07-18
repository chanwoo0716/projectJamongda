package com.jamongda.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.login.dao.LoginGuestDAO;
import com.jamongda.login.dto.LoginDTO;


@Service("GuestService")
public class GuestServiceImpl implements GuestService{
	
//	@Autowired
//	private LoginGuestDAO loginGuestDAO;
//	
//	@Override
//	public LoginDTO login(LoginDTO loginGuestDTO) throws DataAccessException {
//		return loginGuestDAO.loginCheck(loginGuestDTO);
//	}
	
	
}
