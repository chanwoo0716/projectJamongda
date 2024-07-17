package com.jamongda.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.member.dto.MemberDTO;

@Mapper
@Repository("memberDAO")
public interface MemberDAO {
	
	public boolean existsByEmail(String email) throws DataAccessException;
	
	public void insertMember(MemberDTO memberDTO) throws DataAccessException;
	
	public MemberDTO findInfo(String email) throws DataAccessException;
	
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;

	public void deleteMember(String email) throws DataAccessException;
	
	public MemberDTO loginCheck(MemberDTO member) throws DataAccessException;
	
	public MemberDTO getMemberByEmail(String email) throws DataAccessException;
	
}
