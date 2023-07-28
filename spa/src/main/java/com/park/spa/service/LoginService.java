package com.park.spa.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.spa.common.SessionUtil;
import com.park.spa.common.StringUtil;
import com.park.spa.vo.EmailCheckVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginService {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MailService mailService;
	
	/**
	 * 가입 이력이 없는 이메일일 경우 코드를 보낸다.
	 * 
	 * @param userEmail
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> sendJoinCodeMail(String userEmail) throws Exception {
		
		Map<String, Object> outMap = new HashMap<>();
		EmailCheckVo emailCheckVo = new EmailCheckVo();
		String code = StringUtil.getShortRandomUUid(5);
    	String mailContent = "코드 : " + code;
    	
    	int isMember = memberService.isMemeber(userEmail);
    	
    	if(isMember == 0) {
    		boolean result = mailService.sendMail(userEmail, mailContent);
    		
    		if(result) {
    			emailCheckVo.setCode(code);
    			emailCheckVo.setEmail(userEmail);
    			emailCheckVo.setChecked(true);
    			
    			SessionUtil.setAttribute("emailCheckVo", emailCheckVo);
    			
    			outMap.put("result", true);
    			outMap.put("msg", "메일로 전송된 코드를 입력해 주세요.");
    			
    		} else if(!result) {
    			outMap.put("result", false);
    			outMap.put("msg", "올바른 이메일을 입력해 주세요");
    			
    		} else {
    			outMap.put("result", false);
    			outMap.put("msg", "시스템 오류");
    		}
    		
    	} else {
    		outMap.put("result"	, false);
    		outMap.put("msg", "이미 가입된 회원입니다.");
    	}
    	
    	return outMap;
		
	}

}
