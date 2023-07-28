package com.park.spa.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.park.spa.vo.MemberVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString 
public class CustomUser extends User{

	private static final long serialVersionUID = -1886695811659183317L;
	
	private MemberVo memberVo;

	public CustomUser(String username, String password, MemberVo memberVo, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.memberVo = memberVo;
		
	}
}
