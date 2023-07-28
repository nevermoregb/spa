package com.park.spa.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.park.spa.service.MemberService;
import com.park.spa.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomUserDetailService implements UserDetailsService {
	
	boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    @Autowired
    MemberService memberService;
    
	@Override
	public UserDetails loadUserByUsername(String username) {
		MemberVo memberVo = memberService.getMemberDetail(username);
		
		if(memberVo == null) {
			throw new UsernameNotFoundException("등록되지 않은 회원입니다.");
		} else {
//			return User.builder()
//				.username(memberVo.getUserId())
//				.password(memberVo.getUserPw())
//				.roles(memberVo.getUserAuth())
//				.build();
			CustomUser customUser = new CustomUser(username, memberVo.getUserPw(), memberVo, 
					enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,  authorities(memberVo));
			
			return customUser;
					
		}
	}
	
	private Collection<? extends GrantedAuthority> authorities(MemberVo memberVo) {
        Collection authorities = new ArrayList<>();
        
        if(memberVo.getUserAuth().equals("U")){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        return authorities;
	}
}
