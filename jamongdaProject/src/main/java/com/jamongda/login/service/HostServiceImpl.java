package com.jamongda.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.login.dao.LoginHostDAO;
import com.jamongda.login.dto.LoginDTO;

@Service("loginHostService")
public class HostServiceImpl implements HostService{
	
//	@Autowired
//	private LoginHostDAO loginDAO;
//	
//	@Override
//	public LoginDTO login(LoginDTO loginHostDTO) throws DataAccessException {
//		return loginDAO.loginCheck(loginHostDTO);
//	}

}
