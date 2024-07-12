package com.jamongda.member.service;

import org.springframework.dao.DataAccessException;

import com.jamongda.member.dto.MemberDTO;

public interface MemberService {
	
	public Boolean isEmailAvailable(String email)throws DataAccessException;
	public void insertMember(MemberDTO memberDTO) throws DataAccessException;

	
	public MemberDTO findInfo(String email) throws DataAccessException;
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;
	
	public void deleteMember(String email)throws DataAccessException;
}
