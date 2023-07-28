package com.park.spa.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.park.spa.common.session.UserSessionService;
import com.park.spa.vo.MemberVo;

@Service
public class JoinService {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserSessionService userSessionService;
	
	/**
	 * 회원가입
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> joinMember(MemberVo memberVo) throws Exception {

		Map<String, Object> outMap = new HashMap<>();
		
		if(memberVo.getUserId() != null && memberVo.getUserPw() != null) {
			int isMember = memberService.isMemeber(memberVo.getUserId());						//회원 가입여부 확인
			int isNameUsed = memberService.isNameUsed(memberVo.getUserName());					//닉네임 사용여부 확인
			boolean isCheckedEmail = userSessionService.isCheckedEmail(memberVo.getUserId());	//이메일 전송코드 체크여부
			
			if(isNameUsed == 0) {
				if(isMember == 0 && isCheckedEmail) {
					String rawPassword = memberVo.getUserPw();
					String encodedPassword = passwordEncoder.encode(rawPassword);	//비밀번호 암호화
					
					memberVo.setUserPw(encodedPassword);
					int result = memberService.joinMember(memberVo);				//회원가입
					
					if(result == 1) {
						outMap.put("result", true);
						outMap.put("msg", "회원 가입이 완료되었습니다.");
					} else {
						outMap.put("result", false);
						outMap.put("msg", "오류 발생");
					}
				} else if(isMember > 0){
					outMap.put("result", false);
					outMap.put("msg", "이미 가입된 이메일 입니다.");
					
				} else if(!isCheckedEmail) {
					outMap.put("result", false);
					outMap.put("msg", "이메일 인증을 진행해 주세요.");
					
				} else {
					outMap.put("result", false);
					outMap.put("msg", "오류 발생");
				}
			} else {
				outMap.put("result", false);
				outMap.put("msg", "이미 사용중인 닉네임 입니다.");
			}
		}
		
		return outMap;
	}
}
