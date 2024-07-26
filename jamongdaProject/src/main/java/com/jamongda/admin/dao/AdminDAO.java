package com.jamongda.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.member.dto.MemberDTO;

@Mapper
@Repository("adminDAO")
public interface AdminDAO {

	// *regCheck=N 인 사업자의 email 가져오기
	public String notRegEmail() throws DataAccessException;
	
	// regCheck=N 숙소 가져오기
	public List<AccommodationDTO> selectAcc_RegN() throws DataAccessException;
	// regCheck=N 객실 정보 가져오기
	public List<RoomDTO> selectRo_RegN(int acc_id) throws DataAccessException;
	// regCheck=N 숙소 이미지 가져오기
	public List<AccommodationImageDTO> selectAccImages_RegN() throws DataAccessException;
	// regCheck=N 객실 이미지 가져오기
	public List<RoomImageDTO> selectRoImages_RegN() throws DataAccessException;
	
	// 숙소등록승인
	public void approveAcc(int acc_id);
	// 숙소등록거부
	public void rejectAcc(int acc_id);
	// 숙소등록거부 사유
	public void rejectReason(Map<String, Object> rejectReasonMap);
	// 해당 숙소 관련 모든 정보 삭제하기
	public void removeAcc(int acc_id);
	// 해당 숙소의 객실 폴더 삭제하기
	public List<RoomDTO> getRo_id(int acc_id) throws DataAccessException;
	
	// regCheck=Y 숙소 가져오기
	public List<AccommodationDTO> selectAcc_RegY() throws DataAccessException;
	// regCheck=Y 객실 정보 가져오기
	public List<RoomDTO> selectRo_RegY(int acc_id) throws DataAccessException;
	// regCheck=Y 숙소 이미지 가져오기
	public List<AccommodationImageDTO> selectAccImages_RegY() throws DataAccessException;
	// regCheck=Y 객실 이미지 가져오기
	public List<RoomImageDTO> selectRoImages_RegY() throws DataAccessException;
	
	// 사업자 이메일 검색 및 regCheck=Y 숙소 가져오기
	public List<AccommodationDTO> searchAcc_RegY(String searchEmail) throws DataAccessException;
	// 사업자 이메일 검색 및 regCheck=Y 객실 정보 가져오기
	public List<RoomDTO> searchRo_RegY(int acc_id) throws DataAccessException;
	// 사업자 이메일 검색 및 regCheck=Y 숙소 이미지 가져오기
	public List<AccommodationImageDTO> searchAccImages_RegY(String searchEmail) throws DataAccessException;
	// 사업자 이메일 검색 및 regCheck=Y 객실 이미지 가져오기
	public List<RoomImageDTO> searchRoImages_RegY(String searchEmail) throws DataAccessException;
	
	
	// regCheck=C 숙소 가져오기
	public List<AccommodationDTO> selectAcc_RegC() throws DataAccessException;
	// regCheck=C 객실 정보 가져오기
	public List<RoomDTO> selectRo_RegC(int acc_id) throws DataAccessException;
	// regCheck=C 숙소 이미지 가져오기
	public List<AccommodationImageDTO> selectAccImages_RegC() throws DataAccessException;
	// regCheck=C 객실 이미지 가져오기
	public List<RoomImageDTO> selectRoImages_RegC() throws DataAccessException;
	
	//회원정보 모두 가져오기(가입일자 순으로)
	public List selectAllMembersList() throws DataAccessException;
	//특정 회원(email)의 회원정보 가져오기(검색기능)
	public List searchMembers(Map<String, Object> paramMap) throws DataAccessException;
	
	//특정 회원(email)의 회원정보 가져오기(수정하기)
	public MemberDTO selectMemberByEmail(String email) throws DataAccessException;
	
	//회원정보수정
	public void updateMember(MemberDTO memberDTO) throws DataAccessException;	//수정 다 하면 테이블 업데이트.
	
	//회원정보삭제
	public void delMember(String email) throws DataAccessException;
	
	//등록된 숙소 수정
	
	
}
