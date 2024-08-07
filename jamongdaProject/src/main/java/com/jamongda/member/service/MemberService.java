package com.jamongda.member.service;

import org.springframework.dao.DataAccessException;

import com.jamongda.member.dto.MemberDTO;

public interface MemberService {
	// 이메일 중복확인
	public Boolean isEmailAvailable(String email)throws DataAccessException;
	
	// 회원정보 등록
	public void insertMember(MemberDTO memberDTO) throws DataAccessException;
	
	// 회원정보 조회
	public MemberDTO findInfo(String email) throws DataAccessException;
	
	// 회원정보 수정
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;
	
	// 회원정보 삭제
	public void deleteMember(String email)throws DataAccessException;
	
	// 로그인 시도 시 DB 정보 확인
	public MemberDTO getMemberByEmail(String email) throws DataAccessException;
}
