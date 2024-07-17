package com.jamongda.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.member.dao.MemberDAO;
import com.jamongda.member.dto.MemberDTO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Override
	public Boolean isEmailAvailable(String email) throws DataAccessException {
		return memberDAO.existsByEmail(email);
	}

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
	public MemberDTO loginG(MemberDTO member) throws DataAccessException {
		return memberDAO.loginCheck(member);
	}
	@Override
	public MemberDTO loginH(MemberDTO member) throws DataAccessException {
		return memberDAO.loginCheck(member);
	}
    public MemberDTO getMemberByEmail(String email) throws DataAccessException {
        return memberDAO.getMemberByEmail(email);
    }

}
