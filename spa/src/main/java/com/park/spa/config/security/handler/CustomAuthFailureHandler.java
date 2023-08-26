package com.park.spa.config.security.handler;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import com.park.spa.common.SessionUtil;
import com.park.spa.common.constant.ConstantList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 실패 시 오류메세지를 위한 클래스
 * 오류메세지를 HttpServletRequest에 담아 로그인URL로 보낸다.
 * 
 */
@Slf4j
@Service
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.debug("로그인 실패");
		String errorMessage;
		
		if(exception instanceof BadCredentialsException) {
			errorMessage = "이메일과 비밀번호를 확인해 주세요.";
		} else if(exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
		} else if(exception instanceof UsernameNotFoundException) {
			errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
		} else if(exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
		} else {
			errorMessage = "알수없는 오류발생";
		}
		
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("errorMessage", errorMessage);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(ConstantList.DEFAULT_FAILURE_URL);
		dispatcher.forward(request, response);
		
	}
	

}
