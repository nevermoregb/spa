package com.park.spa.config.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.park.spa.common.SessionUtil;
import com.park.spa.config.security.CustomUser;
import com.park.spa.config.security.CustomUserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공시 세션에 사용자vo를 세팅, 게시판 페이지로 redirect
 */
@Slf4j
@Service
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		
		try {
			SessionUtil.setAttribute("MemberVo", customUser.getMemberVo());		//로그인 성공시 세션에 유저vo 정보 세팅
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/spa/main");
	}
}
