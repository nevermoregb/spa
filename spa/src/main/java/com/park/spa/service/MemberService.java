package com.park.spa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.park.spa.mapper.MemberMapper;
import com.park.spa.vo.MemberVo;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper memberMapper;

	/**
	 * 
	 * @return 모든 회원 리스트
	 */
	public List<MemberVo> getAllMemberList() {
		return memberMapper.getAllMemberList();
	}
	
	public MemberVo getMemberDetail(String userId) {
		return memberMapper.getMemberDetail(userId);
	}
	
	public MemberVo createMember(String userId, String password, PasswordEncoder passwordEncoder) {
		return memberMapper.getMemberDetail(userId);
	}

}
