package com.jamongda.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.accommodation.dao.AccommodationDAO;
import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.admin.dao.AdminDAO;
import com.jamongda.member.dto.MemberDTO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	// 숙소등록요청 조회(regCheck=N)
	@Override
	public Map regN_AccRo() throws DataAccessException {
		Map regN_accRoMap = new HashMap();
		
		// *regCheck=N 인 사업자의 email 가져오기
		//String notRegEmail = adminDAO.notRegEmail();
		//notRegAccRoMap.put("notRegEmail", notRegEmail);

		// regCheck='N'인 숙소 가져오기
		List<AccommodationDTO> accList = adminDAO.selectAcc_RegN();
		regN_accRoMap.put("accList", accList);
		
		// regCheck='N'인 객실 가져오기
	    List<List<RoomDTO>> roLists = new ArrayList<>();  // 각 숙소별 객실 리스트를 저장할 리스트
	    for (AccommodationDTO acc : accList) {
	        List<RoomDTO> rooms = adminDAO.selectRo_RegN(acc.getAcc_id());  // 숙소별 객실 리스트를 가져옴
	        roLists.add(rooms);  // 로컬 리스트에 추가
	        regN_accRoMap.put("roList" + acc.getAcc_id(), rooms);  // 맵에 추가
	    }
	    
	    // regCheck='N'인 숙소 이미지 가져오기
	    List<AccommodationImageDTO> accImageFileList = adminDAO.selectAccImages_RegN();
	    regN_accRoMap.put("accImageFileList", accImageFileList);
	    
	    // regCheck='N'인 객실 이미지 가져오기(서브쿼리 이용)
	    List<RoomImageDTO> roImageFileList = adminDAO.selectRoImages_RegN();
	    regN_accRoMap.put("roImageFileList", roImageFileList);

	    // 각 숙소의 대표 이미지
	    Map<Integer, String> accThumbnail = new HashMap<>();
	    for (AccommodationDTO acc : accList) {
	        for (AccommodationImageDTO image : accImageFileList) {
	            if (image.getAcc_id() == (acc.getAcc_id())) {
	                accThumbnail.put(acc.getAcc_id(), image.getAcc_image());
	                break;
	            }
	        }
	    }
	    regN_accRoMap.put("accThumbnail", accThumbnail);
		return regN_accRoMap;
	}
	
	// 숙소등록승인(rejectReason을 null로 만들어야함)
	@Override
	public void approveAcc(int acc_id) {
		adminDAO.approveAcc(acc_id);
	}
	// 숙소등록거부
	@Override
	public void rejectAcc(int regCheck) {
		adminDAO.rejectAcc(regCheck);
	}
	// 숙소등록거부 사유
	@Override
	public void rejectReason(int acc_id, String reject_reason) {
	    Map<String, Object> rejectReasonMap = new HashMap<>();
	    rejectReasonMap.put("acc_id", acc_id);
	    rejectReasonMap.put("reject_reason", reject_reason);
	    
	    adminDAO.rejectReason(rejectReasonMap);
	}
	
	// 숙소등록승인 조회(regCheck=Y)
	@Override
	public Map regY_AccRo() throws DataAccessException {
		Map regY_accRoMap = new HashMap();
		
		// regCheck='Y'인 숙소 가져오기
		List<AccommodationDTO> accList = adminDAO.selectAcc_RegY();
		regY_accRoMap.put("accList", accList);
		
		// regCheck='Y'인 객실 가져오기
	    List<List<RoomDTO>> roLists = new ArrayList<>();  // 각 숙소별 객실 리스트를 저장할 리스트
	    for (AccommodationDTO acc : accList) {
	        List<RoomDTO> rooms = adminDAO.selectRo_RegY(acc.getAcc_id());  // 숙소별 객실 리스트를 가져옴
	        roLists.add(rooms);  // 로컬 리스트에 추가
	        regY_accRoMap.put("roList" + acc.getAcc_id(), rooms);  // 맵에 추가
	    }
	    
	    // regCheck='Y'인 숙소 이미지 가져오기
	    List<AccommodationImageDTO> accImageFileList = adminDAO.selectAccImages_RegY();
	    regY_accRoMap.put("accImageFileList", accImageFileList);
	    
	    // regCheck='Y'인 객실 이미지 가져오기(서브쿼리 이용)
	    List<RoomImageDTO> roImageFileList = adminDAO.selectRoImages_RegY();
	    regY_accRoMap.put("roImageFileList", roImageFileList);

	    // 각 숙소의 대표 이미지
	    Map<Integer, String> accThumbnail = new HashMap<>();
	    for (AccommodationDTO acc : accList) {
	        for (AccommodationImageDTO image : accImageFileList) {
	            if (image.getAcc_id() == (acc.getAcc_id())) {
	                accThumbnail.put(acc.getAcc_id(), image.getAcc_image());
	                break;
	            }
	        }
	    }
	    regY_accRoMap.put("accThumbnail", accThumbnail);
		return regY_accRoMap;
	}
	//승인 숙소 검색
	@Override
	public Map search_RegY_AccRo(String searchEmail) throws DataAccessException {
		Map search_regY_accRoMap = new HashMap();
		
		// 사업자 이메일 검색 및 regCheck=Y 숙소 가져오기
		List<AccommodationDTO> accList = adminDAO.searchAcc_RegY(searchEmail);
		search_regY_accRoMap.put("accList", accList);
		
		// 사업자 이메일 검색 및 regCheck=Y 객실 정보 가져오기
	    List<List<RoomDTO>> roLists = new ArrayList<>();  // 각 숙소별 객실 리스트를 저장할 리스트
	    for (AccommodationDTO acc : accList) {
	        List<RoomDTO> rooms = adminDAO.searchRo_RegY(acc.getAcc_id());  // 숙소별 객실 리스트를 가져옴
	        roLists.add(rooms);  // 로컬 리스트에 추가
	        search_regY_accRoMap.put("roList" + acc.getAcc_id(), rooms);  // 맵에 추가
	    }
	    
	    // 사업자 이메일 검색 및 regCheck=Y 숙소 이미지 가져오기
	    List<AccommodationImageDTO> accImageFileList = adminDAO.searchAccImages_RegY(searchEmail);
	    search_regY_accRoMap.put("accImageFileList", accImageFileList);
	    
	    // 사업자 이메일 검색 및 regCheck=Y 객실 이미지 가져오기(서브쿼리 이용)
	    List<RoomImageDTO> roImageFileList = adminDAO.searchRoImages_RegY(searchEmail);
	    search_regY_accRoMap.put("roImageFileList", roImageFileList);

	    // 각 숙소의 대표 이미지
	    Map<Integer, String> accThumbnail = new HashMap<>();
	    for (AccommodationDTO acc : accList) {
	        for (AccommodationImageDTO image : accImageFileList) {
	            if (image.getAcc_id() == (acc.getAcc_id())) {
	                accThumbnail.put(acc.getAcc_id(), image.getAcc_image());
	                break;
	            }
	        }
	    }
	    search_regY_accRoMap.put("accThumbnail", accThumbnail);
		return search_regY_accRoMap;
	}
	

	
	// 숙소등록거부 조회(regCheck=C)
	@Override
	public Map regC_AccRo() throws DataAccessException {
		Map regC_accRoMap = new HashMap();

		// regCheck='N'인 숙소 가져오기(한 사업자가 여러개 숙소 등록할 수 있으니 List로 받음)
		List<AccommodationDTO> accList = adminDAO.selectAcc_RegC();
		regC_accRoMap.put("accList", accList);
		
		// regCheck='N'인 객실 가져오기
	    List<List<RoomDTO>> roLists = new ArrayList<>();  // 각 숙소별 객실 리스트를 저장할 리스트
	    for (AccommodationDTO acc : accList) {
	        List<RoomDTO> rooms = adminDAO.selectRo_RegC(acc.getAcc_id());  // 숙소별 객실 리스트를 가져옴
	        roLists.add(rooms);  // 로컬 리스트에 추가
	        regC_accRoMap.put("roList" + acc.getAcc_id(), rooms);  // 맵에 추가
	    }
	    
	    // regCheck='N'인 숙소 이미지 가져오기
	    List<AccommodationImageDTO> accImageFileList = adminDAO.selectAccImages_RegC();
	    regC_accRoMap.put("accImageFileList", accImageFileList);
	    
	    // regCheck='N'인 객실 이미지 가져오기(서브쿼리 이용)
	    List<RoomImageDTO> roImageFileList = adminDAO.selectRoImages_RegC();
	    regC_accRoMap.put("roImageFileList", roImageFileList);

	    // 각 숙소의 대표 이미지
	    Map<Integer, String> accThumbnail = new HashMap<>();
	    for (AccommodationDTO acc : accList) {
	        for (AccommodationImageDTO image : accImageFileList) {
	            if (image.getAcc_id() == (acc.getAcc_id())) {
	                accThumbnail.put(acc.getAcc_id(), image.getAcc_image());
	                break;
	            }
	        }
	    }
	    regC_accRoMap.put("accThumbnail", accThumbnail);
		return regC_accRoMap;
	}

	//회원정보 모두 가져오기(가입일자 순으로)
	@Override
	public List listMembers() throws DataAccessException {
		List membersList = adminDAO.selectAllMembersList();
		return membersList;
	}
	//회원검색기능(이메일)
	@Override
	public List searchMembers(String searchEmail) throws DataAccessException {	
		List searchMembersList = adminDAO.searchMembers(searchEmail);
		return searchMembersList;
	}
	
	
	
	//특정 회원(email)의 회원정보 가져오기(수정하기)
	@Override
	public MemberDTO findMember(String email) throws DataAccessException {
		MemberDTO memberDTO = adminDAO.selectMemberByEmail(email);
		return memberDTO;
	}
	
	//회원정보수정
	@Override
	public void updateMember(MemberDTO memberDTO) throws DataAccessException {
		adminDAO.updateMember(memberDTO);
		
	}
	
	//회원정보삭제
	@Override
	public void delMember(String email) throws DataAccessException {
		adminDAO.delMember(email);
	}

	
}
