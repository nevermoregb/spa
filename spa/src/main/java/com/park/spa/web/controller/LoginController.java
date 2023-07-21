package com.park.spa.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.spa.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main")
public class LoginController {
	
    @GetMapping("/login")
    public String loginPage() {
    	log.debug("111111111");
        return "login";
    }
    
    @GetMapping("/join")
    public String joinMemberPage() {
    	return "join";
    }
    

}
