package com.jamongda.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.member.dto.MemberDTO;

@Mapper
@Repository("memberDAO")
public interface MemberDAO {
	//삭제예정
	public List selectAllMembersList() throws DataAccessException;
	
	public void insertMember(MemberDTO memberDTO) throws DataAccessException;
	
	public MemberDTO findInfo(String email) throws DataAccessException;
	
	//public void updateEmailStatus(MemberDTO memberDTO) throws DataAccessException;
	
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;

	public void deleteMember(String email) throws DataAccessException;
	
	public void insertMemberH(MemberDTO memberDTO) throws DataAccessException;
	
	public MemberDTO findInfoH(String email) throws DataAccessException;
	
	public void updateMemberH(MemberDTO memberDTO) throws DataAccessException;
	
	public void deleteMemberH(String email) throws DataAccessException;

}
