package com.park.spa.web.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main")
public class LoginController {
	
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
    	log.debug("111111111");
    	SecurityContext securityContext = SecurityContextHolder.getContext();
    	securityContext.getAuthentication().getPrincipal();
//    	String errorMessage = null;
//    	
//    	HttpSession session = request.getSession(false);
//    	if(session != null) {
//    		AuthenticationException ex = (AuthenticationException) session
//                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//            if (ex != null) {
//                errorMessage = ex.getMessage();
//            }
//    	}
    	
    	model.addAttribute("test", "test");
//    	model.addAttribute("errorMessage", errorMessage);
    	
        return "login";
    }
    
    @GetMapping("/join")
    public String joinMemberPage() {
    	log.debug("1222");
    	return "join";
    }
    

}
