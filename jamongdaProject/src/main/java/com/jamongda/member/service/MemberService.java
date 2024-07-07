package com.jamongda.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.jamongda.member.dto.MemberDTO;

public interface MemberService {
	//삭제예정
	public List listMembers() throws DataAccessException;
	
	//public void sendVerificationEmail(String email) throws DataAccessException;
	
	public void insertMember(MemberDTO memberDTO) throws DataAccessException;

	
	public MemberDTO findInfo(String email) throws DataAccessException;
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;
	
	public void deleteMember(String email)throws DataAccessException;

	public void insertMemberH(MemberDTO memberDTO) throws DataAccessException;

	public MemberDTO findInfoH(String email) throws DataAccessException;
	public void updateMemberH(MemberDTO memberDTO) throws DataAccessException;
	
	public void deleteMemberH(String email)throws DataAccessException;
}
