package com.jamongda.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.member.dto.MemberDTO;

@Mapper
@Repository("memberDAO")
public interface MemberDAO {
	
	// 이메일 중복확인
	public boolean existsByEmail(String email) throws DataAccessException;
	
	// 회원 정보 등록
	public void insertMember(MemberDTO memberDTO) throws DataAccessException;
	
	// 회원 정보 조회
	public MemberDTO findInfo(String email) throws DataAccessException;
	
	// 회원 정보 수정
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;

	// 회원 정보 삭제
	public void deleteMember(String email) throws DataAccessException;
	
	// 로그인 시도 시 정보 조회
	public MemberDTO getMemberByEmail(String email) throws DataAccessException;
}
