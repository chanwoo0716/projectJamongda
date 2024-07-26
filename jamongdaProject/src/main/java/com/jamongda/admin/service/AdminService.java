package com.jamongda.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.member.dto.MemberDTO;

public interface AdminService {
	
	//숙소등록요청 조회하기
	public Map regN_AccRo() throws DataAccessException;
	//숙소등록승인
	public void approveAcc(int acc_id) throws DataAccessException;
	//숙소등록거부
	public void rejectAcc(int acc_id) throws DataAccessException;
	//숙소등록거부 사유
	public void rejectReason(int acc_id, String reject_reason) throws DataAccessException;
	//해당 숙소 관련 모든 정보 삭제하기
	public void removeAcc(int acc_id) throws DataAccessException;
	//해당 숙소의 객실 폴더 삭제하기
	public List<RoomDTO> getRo_id(int acc_id) throws DataAccessException;
	
	//숙소등록승인 조회하기
	public Map regY_AccRo() throws DataAccessException;
	//승인 숙소 검색
	public Map search_RegY_AccRo(String searchEmail) throws DataAccessException;
	
	//숙소등록거부 조회하기
	public Map regC_AccRo() throws DataAccessException;
	
	//회원정보 모두 가져오기(가입일자 순으로)
	public List listMembers() throws DataAccessException;
	
	//회원검색기능(이메일)
	public List searchMembers(Map<String, Object> paramMap) throws DataAccessException;
	
	//특정 회원(email)의 회원정보 가져오기
	public MemberDTO findMember(String email) throws DataAccessException;
	
	//회원정보수정
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;	//진짜 수정하기 위한 메서드
	
	//회원정보삭제
	public void delMember(String email) throws DataAccessException;
	
	
}
