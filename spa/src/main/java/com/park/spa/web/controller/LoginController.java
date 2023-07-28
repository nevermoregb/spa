package com.park.spa.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.spa.service.JoinService;
import com.park.spa.service.LoginService;
import com.park.spa.service.MailService;
import com.park.spa.service.MemberService;
import com.park.spa.vo.MemberVo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main")
public class LoginController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	JoinService joinService;
	
	@Autowired
	MailService mailService;
	
    @RequestMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        return "login";
    }
    
    /**
     * 회원가입 페이지
     * 
     * @return
     */
    @GetMapping("/join")
    public String joinMemberPage() {
    	return "join";
    }
    
    /**
     * 입력한 메일주소로 코드 전송
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    @PostMapping("/checkMail")
    public @ResponseBody Map<String, Object> sendJoinCodeMail(@RequestBody Map<String, String> paramMap) throws Exception {
    	
    	String userEmail = paramMap.get("userEmail");
    	Map<String, Object> outMap = loginService.sendJoinCodeMail(userEmail);		// 코드전송 및 회원가입여부확인
    	
    	return outMap;
    }
    
    /**
     * 이메일로 전송된 코드확인
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    @PostMapping("/checkCode")
    public @ResponseBody Map<String, Object> checkInputCode(@RequestBody Map<String, String> paramMap) throws Exception {
    	return mailService.emailCodeCheck(paramMap);
    }
    
    /**
     * 회원가입
     * 
     * @param memberVo
     * @return
     * @throws Exception
     */
    @PostMapping("/joinMember")
    public @ResponseBody Map<String, Object> joinMember(@RequestBody MemberVo memberVo) throws Exception {
    	
    	Map<String, Object> outMap = new HashMap<>();
    	outMap = joinService.joinMember(memberVo);
    	
    	return outMap;
    }
    

}
