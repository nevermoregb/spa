package com.park.spa.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.park.spa.service.MemberService;
import com.park.spa.vo.MemberVo;

@Component
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVo memberVo = memberService.getMemberDetail(username);
		
		if(memberVo == null) {
			throw new UsernameNotFoundException("등록되지 않은 회원입니다.");
		} else {
			return User.builder()
				.username(memberVo.getUserId())
				.password(memberVo.getUserPw())
				.roles(memberVo.getUserAuth())
				.build();
		}
	}
}
