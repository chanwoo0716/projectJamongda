package com.jamongda.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.member.dao.MemberDAO;
import com.jamongda.member.dto.MemberDTO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	// 삭제예정
	public List listMembers() throws DataAccessException {
		List membersList = memberDAO.selectAllMembersList();
		return membersList;
	}

//	public void sendVerificationEmail(String email) {
//		MemberDTO memberDTO = memberDAO.findInfo(email);
//		if (memberDTO != null) {
//			// 이미 인증된 사용자인 경우 처리
//			if (memberDTO.isEmailStatus()) {
//				throw new RuntimeException("이미 인증된 사용자입니다.");
//			}
//
//			// 이메일 발송
//			String subject = "이메일 인증";
//			String text = "다음 링크를 클릭하여 이메일을 인증하세요: http://localhost:8080/verify";
//			emailService.sendEmail(email, subject, text);
//
//			// 사용자의 인증 상태 업데이트
//			memberDTO.setEmailStatus(true);
//			memberDAO.updateEmailStatus(memberDTO);
//		} else {
//			throw new RuntimeException("사용자를 찾을 수 없습니다: " + email);
//		}
//	}

	@Override
	public void insertMember(MemberDTO memberDTO) throws DataAccessException {
		memberDAO.insertMember(memberDTO);
	}

	@Override
	public MemberDTO findInfo(String email) throws DataAccessException {
		MemberDTO memberDTO = memberDAO.findInfo(email);
		return memberDTO;
	}

	@Override
	public void updateMember(MemberDTO memberDTO) throws DataAccessException {
		memberDAO.updateMember(memberDTO);

	}

	@Override
	public void deleteMember(String email) throws DataAccessException {
		memberDAO.deleteMember(email);

	}

	@Override
	public void insertMemberH(MemberDTO memberDTO) throws DataAccessException {
		memberDAO.insertMemberH(memberDTO);
	}

	@Override
	public MemberDTO findInfoH(String email) throws DataAccessException {
		MemberDTO memberDTO = memberDAO.findInfoH(email);
		return memberDTO;
	}

	@Override
	public void updateMemberH(MemberDTO memberDTO) throws DataAccessException {
		memberDAO.updateMemberH(memberDTO);

	}

	@Override
	public void deleteMemberH(String email) throws DataAccessException {
		memberDAO.deleteMemberH(email);

	}

}
