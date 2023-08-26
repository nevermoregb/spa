package com.park.spa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.spa.common.session.UserSessionService;
import com.park.spa.mapper.MemberMapper;
import com.park.spa.vo.MemberVo;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserSessionService userSessionService;

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

	/**
	 * 닉네임 사용여부 
	 * 
	 * @param userName
	 * @return
	 */
	public int isNameUsed(String userName) {
		return memberMapper.isNameUsed(userName);
	}
	
	/**
	 * 비밀번호 업데이트
	 * @param memberVo
	 * @return
	 */
	private int updatePassword(MemberVo memberVo) {
		return memberMapper.updatePassword(memberVo);
	}

	/**
	 * 비밀번호 업데이트
	 * 
	 * @param memberVo
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> chagePassword(MemberVo memberVo) throws Exception {
		Map<String, Object> outMap = new HashMap<>();
		
		if(memberVo.getUserId() != null && memberVo.getUserPw() != null) {
			boolean isCheckedEmail = userSessionService.isCheckedEmail(memberVo.getUserId());	//이메일 전송코드 체크여부
			
			if(isCheckedEmail) {
				String rawPassword = memberVo.getUserPw();
				String encodedPassword = passwordEncoder.encode(rawPassword);	//비밀번호 암호화
				
				memberVo.setUserPw(encodedPassword);
				int result = this.updatePassword(memberVo);						//비밀번호 업데이트
				
				if(result == 1) {
					outMap.put("result", true);
					outMap.put("msg", "비밀번호가 변경되었습니다.");
				} else {
					outMap.put("result", false);
					outMap.put("msg", "오류 발생");
				}
			} else if(!isCheckedEmail) {
				outMap.put("result", false);
				outMap.put("msg", "이메일 인증을 진행해 주세요.");
				
			} else {
				outMap.put("result", false);
				outMap.put("msg", "오류 발생");
			}
		}
		
		return outMap;
	}


}
