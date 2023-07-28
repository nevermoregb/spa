package com.park.spa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.spa.mapper.MemberMapper;
import com.park.spa.vo.MemberVo;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper memberMapper;

	/**
	 * 회원 리스트 조회 
	 * 
	 * @return 
	 */
	public List<MemberVo> getAllMemberList() {
		return memberMapper.getAllMemberList();
	}
	
	/**
	 * 회원 정보조회
	 * 
	 * @param userId
	 * @return
	 */
	public MemberVo getMemberDetail(String userId) {
		return memberMapper.getMemberDetail(userId);
	}
	
	/**
	 * 가입 회원인지 확인
	 * 
	 * @param userEmail
	 * @return
	 */
	public int isMemeber(String userEmail) {
		return memberMapper.isMemeber(userEmail);
	}

	/**
	 * 회원 가입
	 * 
	 * @param MemberVo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int joinMember(MemberVo memberVo) {
		return memberMapper.joinMember(memberVo);
	}

	public int isNameUsed(String userName) {
		return memberMapper.isNameUsed(userName);
	}

}
